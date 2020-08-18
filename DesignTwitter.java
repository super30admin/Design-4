class Twitter {
    class Tweet{
        int t_id;
        int createdDt;
        public Tweet(int id, int time){
            this.t_id = id;
            this.createdDt = time;
        }
    }
    Map<Integer, List<Tweet>> tweets;
    Map<Integer, Set<Integer>> following;
    int timestamp; int feedSize;
    /** Initialize your data structure here. */
    public Twitter() {
        tweets = new HashMap();
        following = new HashMap();
        feedSize = 10;
    }
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        Tweet t = new Tweet(tweetId, timestamp++);
        if(!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList());
        }        
        tweets.get(userId).add(t);
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b)->a.createdDt-b.createdDt);
        
        Set<Integer> f_set = following.get(userId);
        if(f_set != null){
            for(Integer f : f_set){
                List<Tweet> f_tweets = tweets.get(f);
                if(f_tweets!=null){
                     for(Tweet t : f_tweets){
                        pq.add(t);
                        if(pq.size()>feedSize){
                            pq.poll();
                        }
                    }
                }
            }
        }
  
        List<Integer> newsFeed = new ArrayList();
        while(!pq.isEmpty()){
            newsFeed.add(0, pq.poll().t_id);
        }
        return newsFeed;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!following.containsKey(followerId)){
            following.put(followerId, new HashSet());
        }
        following.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(following.containsKey(followerId) && followerId != followeeId){
            following.get(followerId).remove(followeeId);
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
