class Twitter {
    
    HashMap<Integer,HashSet<Integer>> followeesMap;
    HashMap<Integer,List<Tweet>> tweetsMap;
    int timeStamp;
    class Tweet{
        int tweetId;
        int createdAt;
        
        public Tweet(int id, int time){
            this.tweetId=id;
            this.createdAt=time;
        }
    }

    public Twitter() {
        this.followeesMap=new HashMap<>();
        this.tweetsMap=new HashMap<>();
        this.timeStamp=0;
        
    }
    
    public void postTweet(int userId, int tweetId) {
        follow(userId,userId);
        if(!tweetsMap.containsKey(userId)){
            tweetsMap.put(userId,new ArrayList<>());
        }
        
        tweetsMap.get(userId).add(new Tweet(tweetId,timeStamp));
        timeStamp++;
    }
    
    public List<Integer> getNewsFeed(int userId) {
        HashSet<Integer> fIds=followeesMap.get(userId);
        PriorityQueue<Tweet> pq=new PriorityQueue<>((a,b)->a.createdAt-b.createdAt);
        if(fIds!=null){
            for(Integer id:fIds){
                List<Tweet> tweets=tweetsMap.get(id);
                if(tweets!=null){
                    for(Tweet tw:tweets){
                        pq.add(tw);
                        if(pq.size()>10){
                            pq.poll();
                        }
                    }
                }
            }
        }
        List<Integer> result=new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0,pq.poll().tweetId);
        }
        return result;
        
    }
    
    public void follow(int followerId, int followeeId) {
        if(!followeesMap.containsKey(followerId)){
            followeesMap.put(followerId,new HashSet<>());
        }
        followeesMap.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
          if(followeesMap.containsKey(followerId)){
            followeesMap.get(followerId).remove(followeeId);
        }
    }
}