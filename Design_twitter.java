class Twitter {
    class Tweet{
        int tid;
        int createdAt;
        
        public Tweet(int tid,int createdAt){
            this.tid = tid;
            this.createdAt = createdAt;
        }
    }
    
    
    
    
    private HashMap<Integer, Set<Integer>> followed;
    private HashMap<Integer, List<Tweet>> tweets;
    
    private int time;
    public Twitter() {
        followed = new HashMap<>();
        tweets = new HashMap<>();
        
    }
    
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if(!tweets.containsKey(userId) ){
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId, time++));
    }
    
    public List<Integer> getNewsFeed(int userId) {
        //get all users that i am following
        Set<Integer> fIds = followed.get(userId);
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        if(fIds != null){
            for(int fId: fIds){
                List<Tweet> fTweets = tweets.get(fId);
                if(fTweets != null){
                    for(Tweet fTweet: fTweets){
                        pq.add(fTweet);
                        if(pq.size() > 10){
                            pq.poll();
                        }
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0, pq.poll().tid);
        }
        
        return result;
    }
    
    public void follow(int followerId, int followeeId) {
        if(!followed.containsKey(followerId)){
            followed.put(followerId, new HashSet<>());
        }
        followed.get(followerId).add(followeeId);
        
    }
    
    public void unfollow(int followerId, int followeeId) {
        
        if(followed.containsKey(followerId) && followerId != followeeId){
            Set<Integer> set = followed.get(followerId);
            if(set.contains(followeeId)){
                set.remove(followeeId);
            };
        
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


//TC : O(N * Max(M,T))
