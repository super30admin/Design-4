
// Space Complexity :O(N * max(F,T) --> N(number of users) F(avg followers of all users) T(avg tweets for all users)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

// Your code here along with comments explaining your approach
// 1. Use two hashmaps to store 1.User and their followers  2. User and their tweets
// 2. Create a private class tweet whch has tweetid and time of creation and initialize feedsize.

class Twitter {
    private class Tweet{
        int tweetId;
        int createdAt;
        public Tweet(int tweetId,int createdAt){
            this.tweetId = tweetId;
            this.createdAt = createdAt;
        }
    }
    HashMap<Integer,Set<Integer>> followers ;
    HashMap<Integer,List<Tweet>> tweets ;
    int feedsize ;
    int timestamp;
    /** Initialize your data structure here. */
    public Twitter() {
        followers = new HashMap<>();
        tweets = new HashMap<>();
        feedsize = 10;
        timestamp = 0;
    }
    
    /** Compose a new tweet. */
    // Time Complexity :O(1) --> putting in a hashmap
    
    // Your code here along with comments explaining your approach
    // 1 .Check if user exists in tweets table and add ths tweet to the user's list and increase timetamp.
    // 2. Also, if user is not present in followers table add user to followers table and follow himself.
    public void postTweet(int userId, int tweetId) {
        if(!tweets.containsKey(userId)) tweets.put(userId,new ArrayList<Tweet>());
        tweets.get(userId).add(new Tweet(tweetId,timestamp++));
        if(!followers.containsKey(userId)){
            followers.put(userId,new HashSet<Integer>());
            followers.get(userId).add(userId);
        } 
        
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    // Time Complexity :O(F*T) --> F( number of followers )  T (average number of tweets for a user that the user follows)
    // Your code here along with comments explaining your approach
    // 1 .Check if user exists in followers table and get his followers.
    // 2. Now , add tweets of all followers of the user and put them in the priority queue 
    // 3. If priority queue reaches feedsize , then for each upcoming tweet , compare it's time created with top most element 
    //  of the queue . If it's greater ,then remove top most tweet from priority queue as this is the oldest tweet.
    // 4. Now add all the tweets from priority queue into a list and return the list.
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((Tweet t1,Tweet t2) -> t1.createdAt - t2.createdAt);
        List<Integer> result = new ArrayList<>();
        int size = 0;
        if(followers.containsKey(userId)){
            for(Integer followee : followers.get(userId)){
                if(tweets.containsKey(followee)){
                    List<Tweet> templist = tweets.get(followee);
                    for(Tweet tw : templist){
                        if(size<feedsize)
                            pq.add(tw);
                        else if(tw.createdAt>pq.peek().createdAt){
                            pq.add(tw);
                            pq.poll();
                        }
                        size++;
                    }
                }
            }
        }
        while(!pq.isEmpty())
            result.add(0,pq.poll().tweetId);
        return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    // Time Complexity :O(1) --> putting in a hashmap
    // Your code here along with comments explaining your approach
    // 1 .If follower is not present in followers table add follower to followers table and follow himself.
    // 2. Now add followee to follower's set.
    public void follow(int followerId, int followeeId) {
        if(!followers.containsKey(followerId)){
            followers.put(followerId,new HashSet<Integer>());
            followers.get(followerId).add(followerId);
        } 
        followers.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    // Time Complexity :O(1) --> removing from hashmap
    // Your code here along with comments explaining your approach
    // 1 .Check if followerId exists in followers table and also check if followerId and followeeId are not same as a user
    // needs to follow himself in our implementation.
    // 2 . If the above condition is true , then remove followee from followerId's set in followers table.
    
    public void unfollow(int followerId, int followeeId) {
        if(followers.containsKey(followerId) && followerId!=followeeId)
            followers.get(followerId).remove(followeeId);
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
