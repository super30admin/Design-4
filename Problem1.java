class Twitter {
    HashMap<Integer, List<Tweet>> tweet; 
    HashMap<Integer, HashSet<Integer>> followed;
    int time;
    class Tweet{
        int id; 
        int createdAt; 
        public Tweet(int tweetId, int time){
            this.id = tweetId; 
            this.createdAt = time;
        }
    }
    public Twitter() {
        this.tweet = new HashMap<>();
        this.followed = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        if(!tweet.containsKey(userId))
            tweet.put(userId, new ArrayList<>());
        tweet.get(userId).add(new Tweet(tweetId, time));
        time++;
    }
    
    public List<Integer> getNewsFeed(int userId) {
        follow(userId, userId);
        HashSet<Integer> following = followed.get(userId);
        List<Integer> result = new ArrayList<>();
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        if(following != null){
        for(int fId: following){
            List<Tweet> fTweets = tweet.get(fId);
            if(fTweets != null){
                for(Tweet tId: fTweets){
                    pq.add(tId);
                    if(pq.size() > 10)
                        pq.poll();
                }
            }
        }
    }
    while(!pq.isEmpty()){
        result.add(0, pq.poll().id);
    }
    return result;
    }
    
    public void follow(int followerId, int followeeId) {
        if(!followed.containsKey(followerId))
            followed.put(followerId, new HashSet<>());
        followed.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(followed.containsKey(followerId) && followerId != followeeId)
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