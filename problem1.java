class Twitter {
    class Tweet {
        int id;
        int timestamp;
        public Tweet(int id, int time) {
            this.id = id;
            this.timestamp = time;
        }
    }
    
    HashMap<Integer, HashSet<Integer>>  followed = new HashMap<>();
    HashMap<Integer, List<Tweet>> tweets = new HashMap<>();
    int time = 0;
    public Twitter() {
        this.followed = new HashMap<>();
        this.tweets = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        Tweet newTweet = new Tweet(tweetId, time++);
        if(!tweets.containsKey(userId)) {
            tweets.put(userId, new ArrayList<>());
        }        
        tweets.get(userId).add(newTweet);
    }
    
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> res = new ArrayList<>();
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.timestamp-b.timestamp);
        if(!followed.containsKey(userId)) {
            return res;
        }
        for(int user: followed.get(userId)) {
            if(tweets.containsKey(user)) {
                for(Tweet t : tweets.get(user)) {
                    System.out.println(t.id + "  " + t.timestamp);
                    pq.add(t);
                    if(pq.size() > 10)
                        pq.poll();
                }
            }
        }
        
        while(!pq.isEmpty()) {
            Tweet t = pq.poll();
            res.add(0,t.id);
        }
        
        return res;
        
    }
    
    public void follow(int followerId, int followeeId) {
        if(!followed.containsKey(followerId)) {
            followed.put(followerId, new HashSet<>());
        }
        followed.get(followerId).add(followeeId);
        
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(followed.containsKey(followerId) && followerId != followeeId) {
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
