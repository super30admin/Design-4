class Twitter {
    class Tweet{
        int tid,createdat;
        Tweet(int tid,int createdat){
            this.tid=tid;
            this.createdat=createdat;
        }
    }
    int time;
    HashMap<Integer,HashSet<Integer>>userMap;
    HashMap<Integer,List<Tweet>>tweetMap;
    public Twitter() {
        time=0;
        userMap=new HashMap<>();
        tweetMap=new HashMap<>();  
    }
    
    public void postTweet(int userId, int tweetId) {
        if(!tweetMap.containsKey(userId)){
            tweetMap.put(userId,new ArrayList<>());
        }
        tweetMap.get(userId).add(new Tweet(tweetId,time++));
        
    }
    
    public List<Integer> getNewsFeed(int userId) {
        follow(userId,userId);
        PriorityQueue<Tweet>pq=new PriorityQueue<>((a,b)->a.createdat-b.createdat);
        HashSet<Integer>allusers=userMap.get(userId);
        for(Integer user:allusers){
            List<Tweet>alltweets=tweetMap.get(user);
            if(alltweets==null)continue;
            for(Tweet t:alltweets){
                pq.add(t);
                if(pq.size()>10){
                    pq.poll();
                }
            }
        }
        List<Integer>res=new ArrayList<>();
        while(!pq.isEmpty()){
            res.add(0,pq.poll().tid);
        }
        return res;
    }
    
    public void follow(int followerId, int followeeId) {
        if(!userMap.containsKey(followerId)){
            userMap.put(followerId,new HashSet<>());
        }
        userMap.get(followerId).add(followeeId);
        
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(!userMap.containsKey(followerId)||followerId==followeeId){
            return;
        }
        if(userMap.get(followerId).contains(followeeId)){
            userMap.get(followerId).remove(followeeId);
        }
        
    }
}