// Time Complexity : O(nlogk)
// Space Complexity : O(k)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

class Twitter {

    class Tweet {
        int tweetId;
        int createdAt;
        
        public Tweet(int tweetId, int createdAt){
            this.tweetId = tweetId;
            this.createdAt = createdAt;
        }
    }
    
    Map<Integer, List<Tweet>> tweetMap;
    Map<Integer, Set<Integer>> followeeMap;
    int timestamp;
    
    /** Initialize your data structure here. */
    public Twitter() {
        tweetMap = new HashMap<>();
        followeeMap = new HashMap<>();
        timestamp = 0;
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if(!tweetMap.containsKey(userId))
            tweetMap.put(userId, new ArrayList<>());
        
        //add tweet
        Tweet tweet = new Tweet(tweetId, timestamp++);
        tweetMap.get(userId).add(tweet);
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pqueue = new PriorityQueue<>((a,b)-> (a.createdAt - b.createdAt));
        //followee list
        Set<Integer> followed = followeeMap.get(userId);
        
        if(followed != null){
            for(Integer fId: followed){
                List<Tweet> tweets = tweetMap.get(fId);
                if(tweets != null){
                    for(Tweet t: tweets){
                        pqueue.add(t);
                        
                        if(pqueue.size() >10)
                            pqueue.poll();
                    }
                }
            }
        }
        
        List<Integer> result = new ArrayList<>();
        
        while(!pqueue.isEmpty()){
            result.add(0, pqueue.poll().tweetId);
        }
        
        return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        //new user
        if(!followeeMap.containsKey(followerId)){
            followeeMap.put(followerId, new HashSet<>());
        }
        
        //add followee
        followeeMap.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(followeeMap.containsKey(followerId) && followerId != followeeId){
            followeeMap.get(followerId).remove(followeeId);
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