class Twitter {
    class Tweet{
        int tweetId;
        int createdAt;
        public Tweet(int tweetId, int createdAt){
            this.tweetId=tweetId;
            this.createdAt=createdAt;
        }
    }
    /** Initialize your data structure here. */
    HashMap<Integer, HashSet<Integer>> followers;
    HashMap<Integer, List<Tweet>> tweets;
    int time;
    public Twitter() {
        followers= new HashMap<>();
        tweets = new HashMap<>();
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        Tweet tweet = new Tweet(tweetId, time);
        time++;
        if(!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(tweet);
        
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        HashSet<Integer> users = followers.get(userId);
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b)-> a.createdAt - b.createdAt);
        if(users!=null){
            for(Integer user :users){
                List<Tweet> tws= tweets.get(user);
                if(tws!=null){
                    for(Tweet tw:tws){
                        pq.add(tw);
                        if(pq.size()>10){
                            pq.poll();
                        }
                    }
                }
            }
        }
        List<Integer> res = new ArrayList<>();
        while(!pq.isEmpty()){
            res.add(0,pq.poll().tweetId);
        }
        return res;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!followers.containsKey(followerId)){
            followers.put(followerId, new HashSet<>());
        }
        followers.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(followers.containsKey(followerId)){
            if(followers.get(followerId).contains(followeeId)) followers.get(followerId).remove(followeeId);
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