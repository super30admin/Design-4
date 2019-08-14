class Twitter {
    
    class Tweet {
        int id;
        int createdAt;
        
        public Tweet(int id, int createdAt){
            this.id = id;
            this.createdAt = createdAt;
        }
    }
    
    HashMap<Integer, HashSet<Integer>> followedUsers = new HashMap<>();
    HashMap<Integer, List<Tweet>> tweets = new HashMap<>();
    
    int timeStamp=0;
    int capacity = 10;

    /** Initialize your data structure here. */
    public Twitter() {
        
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        
        // adding self to followedUsers map
        if(!followedUsers.containsKey(userId)) followedUsers.put(userId, new HashSet<Integer>());
        followedUsers.get(userId).add(userId);
        
        //adding tweets to tweet map
        if(!tweets.containsKey(userId)) tweets.put(userId, new ArrayList<Tweet>());
        tweets.get(userId).add(new Tweet(tweetId, timeStamp++));
        
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        
        // Priority queue
        PriorityQueue<Tweet> pq = new PriorityQueue<>( (t1, t2) -> t1.createdAt - t2.createdAt);
        
        // followed user IDs
        HashSet<Integer> followedUserIds = followedUsers.get(userId);
       // System.out.println(followedUserIds);
        if(followedUserIds != null){
            for(int id : followedUserIds){
                List<Tweet> userTweets = tweets.get(id);
                if(userTweets != null){
                    for(Tweet tweet: userTweets){
                    System.out.println(tweet.id);
                    // heap not full
                    if(pq.size() < capacity){
                        pq.add(tweet);
                    }
                    // full
                    else {
                        if(tweet.createdAt > pq.peek().createdAt){
                            pq.poll();
                            pq.add(tweet);
                        }
                    }
                }
                }
                
            }
        }
        
        
        // get tweets from heap
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()) result.add(0, pq.poll().id);
        
        return result;
        
        
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        
        if(!followedUsers.containsKey(followerId)) followedUsers.put(followerId, new HashSet<Integer>());
        followedUsers.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        
        if(followedUsers.containsKey(followerId) && followerId != followeeId){
            followedUsers.get(followerId).remove(followeeId);
        }
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */