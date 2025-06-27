class Twitter {
    class Tweet{
        int tweetId;
        int createdAt;
        public Tweet(int id, int time){
            this.tweetId = id;
            this.createdAt = time;
        }
    }
    int time;
    HashMap<Integer, HashSet<Integer>> follows;
    HashMap<Integer, List<Tweet>> tweets;
    public Twitter() {
        follows = new HashMap<>();
        tweets = new HashMap<>();
        time = 0;
    }
    
    public void postTweet(int userId, int tweetId) {
        follow(userId,userId);
        if(tweets.containsKey(userId)){
            Tweet twee = new Tweet(tweetId, time++);
            tweets.get(userId).add(twee);
        }else{
            List<Tweet> list = new ArrayList<>();
            Tweet twee = new Tweet(tweetId, time++);
            list.add(twee);
            tweets.put(userId, list);
        }
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        if(follows.containsKey(userId)){
            HashSet<Integer> following = follows.get(userId);
            for(int user : following){
                List<Tweet> Ftweets = tweets.get(user);
                if(Ftweets != null){
                    for(Tweet Ftweet : Ftweets){
                        pq.add(Ftweet);
                        if(pq.size() > 10){
                            pq.poll();
                        }
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while(pq.size() != 0){
            result.add(0,pq.poll().tweetId);
        }
        return result;
    }
    
    public void follow(int followerId, int followeeId) {
        if(follows.containsKey(followerId)){
            follows.get(followerId).add(followeeId);
        }else{
            HashSet<Integer> set = new HashSet<>();
            set.add(followeeId);
            follows.put(followerId, set);
        }
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(followerId == followeeId || !follows.containsKey(followerId)) return;

        follows.get(followerId).remove(followeeId);
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
