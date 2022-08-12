// Time Complexity : o(n)
// Space Complexity : o(n)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no




// Your code here along with comments explaining your approach

class Twitter {
    int time;
    HashMap<Integer,HashSet<Integer>> umap;
    HashMap<Integer,List<Tweet>> tmap;
    // class user{
    //     int userId;
    //     public user(int userId){
    //         this.userId=userId;
    //     }
    // }
    
    class Tweet{
        int tweetId;
        int time;
        public Tweet(int tweetId,int time){
            this.tweetId=tweetId;
            this.time=time;
        }
    }
    
    public Twitter() {
        umap = new HashMap<>();
        tmap = new HashMap<>();
        
    }
    
    public void postTweet(int userId, int tweetId) {
        time++;
        Tweet tw = new Tweet(tweetId,time); 
        if(!tmap.containsKey(userId)){
            tmap.put(userId,new ArrayList<>());
        }
        tmap.get(userId).add(tw);
    }
    
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> result = new ArrayList<>();
        follow(userId,userId);
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b)->a.time-b.time); // min heap on basis of time
        HashSet<Integer> h = umap.get(userId);
        for(int uid:h ){
            List<Tweet> ls=tmap.get(uid);
            if(ls!=null){
            for(Tweet t:ls){
                pq.add(t);
                if(pq.size()>10){
                    pq.poll();
                }
            }
            }
        }
        
        while(!pq.isEmpty()){
            result.add(0,pq.poll().tweetId);
        }
        return result;
    }
    
    public void follow(int followerId, int followeeId) {
        if(!umap.containsKey(followerId)){
            umap.put(followerId,new HashSet<>());
        }
        umap.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(umap.containsKey(followerId)&&followerId!=followeeId){
            umap.get(followerId).remove(followeeId);
        }
    }
}