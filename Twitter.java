class Twitter {
    int timestamp;
    class Tweet{
        int id;
        int createdAt;
        public Tweet(int id, int created){
            this.id = id;
            this.createdAt = created;
        }
    }

    HashMap<Integer, Set<Integer>> followerMap;
    HashMap<Integer, List<Tweet>> tweetMap;
    public Twitter() {
        followerMap = new HashMap<>();
        tweetMap = new HashMap<>();
        timestamp = 0;
    }
    
    //TC is O(1)
    public void postTweet(int userId, int tweetId) {
        if(!tweetMap.containsKey(userId)){
            follow(userId,userId);
            tweetMap.put(userId, new ArrayList<>());
        }
        tweetMap.get(userId).add(new Tweet(tweetId,timestamp++));
    }
    
    //TC is O(1)
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> result = new ArrayList<>();
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b)-> a.createdAt-b.createdAt);
         if(followerMap.containsKey(userId)){
            Set<Integer> users = followerMap.get(userId);
            if(!users.isEmpty()){
                for(int user: users){
                     if(tweetMap.containsKey(user)){

                        List<Tweet> tweets = tweetMap.get(user);
        
                        for(Tweet tw: tweets){

                            pq.add(tw);
                            if(pq.size() > 10) {
                                pq.poll();
                            }
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
    //TC os O(1)
    public void follow(int followerId, int followeeId) {
        if(!followerMap.containsKey(followerId)){
            followerMap.put(followerId, new HashSet<>());
        }
        followerMap.get(followerId).add(followeeId);

    }
    //TC is O(1)
    public void unfollow(int followerId, int followeeId) {
        if(followerMap.containsKey(followerId) && followerId != followeeId){
            followerMap.get(followerId).remove(followeeId);
        }
    }
}