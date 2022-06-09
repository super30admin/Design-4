class Twitter {
    
    class Tweet{
        private int time;
        private int tweetId;
        Tweet(int time, int tweetId){
            this.time = time;
            this.tweetId = tweetId;
        }
    }
    HashMap<Integer, List<Integer>> followers;
    HashMap<Integer, List<Tweet>> tweets;
    int time =0;

    public Twitter() {
        this.followers = new HashMap<>();
        this.tweets = new HashMap<>();
        
    }
    
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if(!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(time++, tweetId));
        
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> (a.time-b.time));
        List<Integer> fids = followers.get(userId);
        for(int fid : fids){
            List<Tweet> tweetList = tweets.get(fid);
            if(tweetList!=null){
                for(Tweet tweet : tweetList){
                    pq.add(tweet);
                }            
                while(pq.size()>10){
                    pq.poll();
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0, pq.poll().tweetId);
        }
        return result;
    }
    
    public void follow(int followerId, int followeeId) {
        if(!followers.containsKey(followerId)){
            followers.put(followerId ,new ArrayList<>());
        }
        followers.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(followers.containsKey(followerId) && followerId!=followeeId){
            followers.get(followerId).remove(followeeId);}
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