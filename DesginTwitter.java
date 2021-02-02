//TC: for O(1) for postTweet(), follow(), unfollow(), O(NlogN) to form heap for getNewsFeed(), where N is total number of tweets
//SC: O(U*T), for storing All tweets for all users

//Difficulty: Had implemented the follower/followeeMap the other way first, user is followed by who
//but that wan't useful to get what users is one following, to generate the news feed
//hence implemented it the other way
//had a bug wrt timestamp not being stored with updated value when I was creating the tweet with field value of timestamp and referencing parent class, timestamp value
//priority queue comparator was not working because of it, needed to look at ascending vs descending

//Approach: Using HashMap to store followee relating ship, who all is a user following
//using a hashmap to store user's tweets
//follow/unfollow is straightforward
//getNewsFeed is implemented by using a priority queue to get top 10 latest tweets

class Twitter {
    HashMap<Integer, Set<Integer>> followingMap; 
    HashMap<Integer, List<Tweet>> tweets;
    int timestamp=1;
    int feedSize = 10;
    
    private static class Tweet{
        int userId;
        int tweetId;
        int creationTime;
        
        public Tweet(int userId, int tweetId, int creationTime){
            this.userId = userId;
            this.tweetId = tweetId;
            this.creationTime = creationTime;
        }
    }
    
    private class TweetComparator implements Comparator<Tweet>{ 
            // Overriding compare()method of Comparator  
            // to arrange in ascending order of timestamp
            // to be able to have min timestamp tweet on top and remove it 
            public int compare(Tweet t1, Tweet t2) { 
                if (t1.creationTime < t2.creationTime) 
                    return -1; 
                else if (t1.creationTime > t2.creationTime) 
                    return 1; 
                return 0; 
            } 
    } 
    
    /** Initialize your data structure here. */
    public Twitter() {
        followingMap = new HashMap<>(); 
        tweets = new HashMap<>();
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        if(!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<Tweet>());
        }
        tweets.get(userId).add(new Tweet(userId, tweetId, timestamp));
        timestamp++;
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> feed = new ArrayList<Integer>();
        
        if(!tweets.containsKey(userId) && !followingMap.containsKey(userId)) return feed;
        
        
        PriorityQueue<Tweet> pq = new PriorityQueue<>(new TweetComparator());
        
        if(tweets.containsKey(userId)){
            List<Tweet> ownTweets = tweets.get(userId);
            for(Tweet t : ownTweets){
                if(pq.size()<feedSize){
                    pq.offer(t);
                }
                else if(pq.peek().creationTime < t.creationTime){
                    pq.poll();
                    pq.offer(t);
                }
            }
        }    
            
        if(followingMap.containsKey(userId)){
            Set<Integer> followees = followingMap.get(userId);             
            for(int f : followees){
                if(tweets.containsKey(f)){
                    List<Tweet> followeeTweets = tweets.get(f);
                    for(Tweet t : followeeTweets){
                        if(pq.size()<feedSize){
                            pq.offer(t);
                        }
                        else if(pq.peek().creationTime < t.creationTime){
                            pq.poll();
                            pq.offer(t);
                        }
                    }
                }
            }
        }
                
        while(!pq.isEmpty()){
            feed.add(0, pq.poll().tweetId);
        }
        
        return feed;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(followerId==followeeId) return;
        
        if(!followingMap.containsKey(followerId)){
            followingMap.put(followerId, new HashSet<Integer>());
            followingMap.get(followerId).add(followeeId);
        }
        else{
            followingMap.get(followerId).add(followeeId);
        }
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(followingMap.containsKey(followerId)){
            Set<Integer> followees = followingMap.get(followerId);
            if(followees.contains(followeeId)){
                followees.remove(followeeId);
            }
        }
    }
}