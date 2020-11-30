class Twitter {
    
    class Tweet{
        
        int id;
        int createdAt;
    }

    HashMap<Integer, Set<Integer>> following;
    HashMap<Integer, List<Tweet>> tweets;
    int time = 0;
    /** Initialize your data structure here. */
    public Twitter() {
        following = new HashMap<>();
        tweets = new HashMap<>();
        
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        
        follow(userId, userId);
        Tweet newTweet = new Tweet();
        newTweet.id = tweetId;
        newTweet.createdAt = time++;
        
        if(!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(newTweet);    
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);  //min heap....to make max heap, we use b-a
        Set<Integer> fIds = following.get(userId);
        if(fIds != null){
            for(Integer fId: fIds){
                
                List<Tweet> fTweets = tweets.get(fId);
                if(fTweets != null){
                    for(Tweet t: fTweets){
                        pq.add(t);
                        if(pq.size() > 10){
                            pq.poll();
                        }
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add( pq.poll().id);
        }
        return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        
        if(!following.containsKey(followerId)){
            following.put(followerId, new HashSet<>());
        }
        following.get(followerId).add(followeeId);
        
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(followerId != followeeId){
            if(following.containsKey(followerId))
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