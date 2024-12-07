class Twitter {
    Map<Integer, HashSet<Integer>> followeesMap;
    Map<Integer, List<Tweet>> tweetsMap;
    int timeStamp;
    
    class Tweet{
        int tweetId;
        int createdAt;

        public Tweet(int tweetId, int timeStamp){
            this.tweetId = tweetId;
            this.createdAt = timeStamp;
        }
    }
    public Twitter() {
        this.followeesMap = new HashMap<>();
        this.tweetsMap = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if(!tweetsMap.containsKey(userId)){
            tweetsMap.put(userId, new ArrayList<>());
        }
        tweetsMap.get(userId).add(new Tweet(tweetId, timeStamp++));
    }
    
    public List<Integer> getNewsFeed(int userId) {  
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b)-> a.createdAt - b.createdAt);
        if(!followeesMap.containsKey(userId)){
            return new ArrayList<>();
        }
        HashSet<Integer> followees = followeesMap.get(userId);
        for(Integer followee : followees){
            List<Tweet> tweets = tweetsMap.get(followee);
            if(tweets != null){
                for(int i = tweets.size()-1; i >= 0 && i >= tweets.size()-10; i--){ //O(nklogk)
                    Tweet tw = tweets.get(i);
                    pq.add(tw);
                    if(pq.size() > 10){
                        pq.poll();
                    }
                }
                // for(Tweet tw: tweets){ //O(NlogK)
                    // pq.add(tw);
                    // if(pq.size() > 10){
                    //     pq.poll();
                    // }
                // }
            }
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0, pq.poll().tweetId);
        }
        return result;
    }
    
    public void follow(int followerId, int followeeId) {
        if(!followeesMap.containsKey(followerId)){
            followeesMap.put(followerId, new HashSet<>());
        }
        followeesMap.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(followeesMap.containsKey(followerId)){
           followeesMap.get(followerId).remove(followeeId);
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