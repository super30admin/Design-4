// Time Complexity : O(n)
// Space Complexity : O(1)
// Did this code successfully run on Leetcode :
// Any problem you faced while coding this :
class Twitter {
    class Tweet{
        int tid;
        int update;
        public Tweet(int id, int now){
            this.tid = id;
            this.update = now;
        }
    }
    Map<Integer,HashSet<Integer>> umap;
    Map<Integer, List<Tweet>> utweet;
    int time;

    public Twitter() {
        this.umap = new HashMap<>();
        this.utweet = new HashMap<>();
        this.time = 0;
    }
    
    public void postTweet(int userId, int tweetId) {
        
        if(!umap.containsKey(userId)){
            umap.put(userId, new HashSet<>());
            
        }
        umap.get(userId).add(userId);
        time++;
        Tweet t = new Tweet(tweetId,time);
        if(!utweet.containsKey(userId))
            utweet.put(userId,new ArrayList<>());
        utweet.get(userId).add(t);
        
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b)->a.update - b.update);
        List<Integer> ans=new ArrayList<>();
        List<Tweet> li = new ArrayList<>();
        if(umap.containsKey(userId)){
        for(int id : umap.get(userId)){
            li = utweet.get(id);
            if(li!=null){
            for(Tweet t : li){
                pq.add(t);
                if(pq.size()>10)
                    pq.poll();
            }}

        }}
        while(!pq.isEmpty()){
            ans.add(0,pq.peek().tid);
            pq.poll();
        }
        return ans;
    }
    
    public void follow(int followerId, int followeeId) {
        if(!umap.containsKey(followerId))
            umap.put(followerId,new HashSet<>());
        if(!umap.get(followerId).contains(followeeId))
            umap.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(!umap.containsKey(followerId))
            umap.put(followerId,new HashSet<>());
        if(umap.get(followerId).contains(followeeId))
            umap.get(followerId).remove(followeeId);
    }
}