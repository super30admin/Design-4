class Twitter {
    
    class Tweet{
        int id;
        int createdAt;
        public Tweet(int id, int time){
            this.id = id;
            this.createdAt = time;
        }
    }
    private HashMap<Integer, HashSet<Integer>> followers; 
    private HashMap<Integer, List<Tweet>> tweetMap;
    private int time;

    public Twitter() {
        this.followers = new HashMap<>();
        this.tweetMap = new HashMap<>();        
    }
    
    // Time Complexity: O(1)
    public void postTweet(int userId, int tweetId) {
                if(!tweetMap.containsKey(userId)){
            tweetMap.put(userId, new ArrayList<>());
            follow(userId, userId);
        }
        tweetMap.get(userId).add(new Tweet(tweetId, time));
        time++;
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new 
    PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
     HashSet<Integer> allfollowers = followers.get(userId); 
        if(allfollowers != null){ 
            for(Integer follower: allfollowers){
                List<Tweet> allTweets = tweetMap.get(follower);
                if(allTweets==null)
                {
                    continue;
                }
                for(Tweet tw: allTweets)
                {
                    pq.add(tw);
                    if(pq.size() > 10){
                            pq.poll();
                        }
                }  
           }
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0, pq.poll().id);
        }
        return result;
    }
    
    // Time Complexity: O(1)
    public void follow(int followerId, int followeeId) {
        if(!followers.containsKey(followerId)){
            followers.put(followerId, new HashSet<>());
        }
        followers.get(followerId).add(followeeId);
    }
    
    // Time Complexity: O(1)
    public void unfollow(int followerId, int followeeId) {
        if(followers.containsKey(followerId) && followerId != followeeId){
            followers.get(followerId).remove(followeeId);
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
