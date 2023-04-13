class Twitter {
    // the main idea is to have two hashmaps, one for user mapping to their following and user to their twitter posts
    // hashmap of first kind maps userid to hashset of following userids.We use hashset here to add or remove them in O(1) and also avoid duplicate addition
    // hashmap, use get and put elements / keys and add to add values to hashset
    HashMap<Integer,HashSet<Integer>> followed;
    HashMap<Integer, List<Tweet>> tweets;
    int time;
    // tweet object
    class Tweet{
        int id;
        int createdAt;
        public Tweet(int id, int time){
            this.id = id;
            this.createdAt = time;
        }

    }
    public Twitter() {
        // initialise
        this.followed= new HashMap<>();
        this.tweets = new HashMap<>();
    }
    
    //O(1)
    public void postTweet(int userId, int tweetId) {
        // user should follow himself to get his/her tweet in their feed
        follow(userId,userId);
        // add tweet
        if (!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId,time));
        time++;
        }
    
    //O(N)-total no of posts
    public List<Integer> getNewsFeed(int userId) {
        // get followers of the user
        HashSet<Integer> following = followed.get(userId);
        List<Integer> result = new ArrayList<>();
        //min priority queue to get 10 most recent tweets
        PriorityQueue<Tweet> pq=new PriorityQueue<>((a,b)->a.createdAt - b.createdAt);
        if(following!=null){
            for(int id:following){
                List<Tweet>  ftweets = tweets.get(id);
                if(ftweets!=null){
                    for(Tweet tw:ftweets){
                        pq.add(tw);
                        if(pq.size()>10){
                            pq.poll();
                        }
                    }
                }
            }
        }
        while(!pq.isEmpty()){
            result.add(0,pq.poll().id);
        }
        return result;
    }
    
    //O(1)
    public void follow(int followerId, int followeeId) {
        if (!followed.containsKey(followerId)){
            followed.put(followerId, new HashSet<>());
            
        }
        followed.get(followerId).add(followeeId);
    }
    
    //O(1)
    public void unfollow(int followerId, int followeeId) {
        if(followed.containsKey(followerId) && followerId!=followeeId){
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