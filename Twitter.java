// Time Complexity : Ok(logk)
// Space Complexity : O(1k)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No 
class Twitter {

    int userId;
    class Tweet{
        int tweetId;
        int createdAt;
        
        public Tweet(int tId, int cAt){
            this.tweetId = tId;
            this.createdAt = cAt;
        }
    }
    
    
    HashMap<Integer, HashSet<Integer>> followed;
    HashMap<Integer, List<Tweet>> tweets;
    int feedSize, timeStamp;
    /** Initialize your data structure here. */
    public Twitter() {
        followed = new HashMap<>();
        tweets = new HashMap<>();
        this.feedSize = 10;
        
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        
        if(!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId, timeStamp++));
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((t1, t2) -> t1.createdAt - t2.createdAt);
        Set<Integer> fIds = followed.get(userId);
        if(fIds != null){
            for(int fId : fIds)
            {
                List<Tweet> feeds = tweets.get(fId);
                if(feeds != null){
                    for(Tweet feed : feeds){
                        pq.add(feed);
                    
                    
                    if(pq.size() > feedSize){
                        pq.poll();
                    }
                }
            }
        }
    }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            Tweet t = pq.poll();
        result.add(0, t.tweetId);
        }
        return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!followed.containsKey(followerId)){
            followed.put(followerId, new HashSet<>());
        }
        followed.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(followed.containsKey(followerId) && followerId != followeeId ){
            followed.get(followerId).remove(followeeId);
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