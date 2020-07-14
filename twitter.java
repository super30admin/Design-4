class Twitter {
    class Tweet{
        int createdAt;
        int id;
        public Tweet(int id,int createdAt){
            this.id=id;
            this.createdAt=createdAt;
        }
    }
    HashMap<Integer,HashSet<Integer>> users;
    HashMap<Integer,List<Tweet>> tweets;
    int time;
    /** Initialize your data structure here. */
    public Twitter() {
        users=new HashMap();
        tweets=new HashMap();
        time=0;
        
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        follow(userId,userId);
        if(!tweets.containsKey(userId)){
            tweets.put(userId,new ArrayList());
        }
        tweets.get(userId).add(new Tweet(tweetId,time++));
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        
        List<Integer> result=new ArrayList();
        if(!users.containsKey(userId)){
            return result;
        }
        follow(userId,userId);
        PriorityQueue<Tweet> q=new PriorityQueue<Tweet>((t1,t2) -> t1.createdAt - t2.createdAt);
        Set<Integer> following=users.get(userId);
        for(Integer user:following){
            List<Tweet> userTweetList=new ArrayList();
            if(tweets.containsKey(user)){
                userTweetList = tweets.get(user);
                for(Tweet t:userTweetList){
                    q.add(t);
                    if(q.size()>10){
                        q.poll();
                    }
                }
            }
        }
        while(!q.isEmpty()){
            result.add(0, q.poll().id);
        }
        return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!users.containsKey(followerId)){
            users.put(followerId,new HashSet());
        }
        users.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(users.containsKey(followerId) && followerId!=followeeId){
            users.get(followerId).remove(followeeId);
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