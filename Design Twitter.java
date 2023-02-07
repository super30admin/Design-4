class Twitter {
    
    class Tweet{
        private int tid;
        private int createdAt;
        public Tweet(int id, int time){
            this.tid = id;
            this.createdAt = time;
        }
        // getter setter
    }
    
    HashMap <Integer, HashSet<Integer>> followed;
    HashMap <Integer, List<Tweet>> tweets;
    int time;
    
    public Twitter() {
        this.followed = new HashMap<>();
        this.tweets = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if(!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<>());
        }
        Tweet tweet = new Tweet(tweetId, time);
        time++;
        tweets.get(userId).add(tweet);
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        HashSet <Integer> fIds = followed.get(userId);
        if(fIds == null) return new ArrayList<>();
        for(Integer fId : fIds) {
            List <Tweet> fTweets = tweets.get(fId); 
            if(fTweets != null){
                int size = fTweets.size();
                int n = size -1;
                for (int k = 0; k<10 && k < size;k++){
                    Tweet fTweet = fTweets.get(n-k);
                    pq.add(fTweet);
                    if(pq.size() > 10){
                        pq.poll();
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
        if(!followed.containsKey(followeeId) || followerId == followeeId) return;
        
        followed.get(followerId).remove(followeeId);
        
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