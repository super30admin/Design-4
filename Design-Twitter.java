## Problem 1: Design Twitter (https://leetcode.com/problems/design-twitter/)

//time Complexity : O(mn)

class Twitter {
    class Tweet{
        int tweetId;
        int time;
        public Tweet(int tweetId,int time){
            this.tweetId=tweetId;
            this.time=time;
        }
    }
    int count;
    HashMap<Integer,HashSet<Integer>> followerMap;
    HashMap<Integer,List<Tweet>> tweetMap;
    public Twitter() {
        followerMap=new HashMap<>();
        tweetMap = new HashMap<>();
        count=0;
    }
    
    public void postTweet(int userId, int tweetId) {
        if(!tweetMap.containsKey(userId)){
            tweetMap.put(userId,new ArrayList<>());
        }
        tweetMap.get(userId).add(new Tweet(tweetId,count++));
    }
    
    public List<Integer> getNewsFeed(int userId) {
        List<Tweet> list = new ArrayList<>();
        HashSet<Integer> set = new HashSet<>();
        if(followerMap.get(userId)!=null){
            set = followerMap.get(userId);
        }
        set.add(userId);
        for(int user:set){
            if(tweetMap.get(user)!=null){
                list.addAll(tweetMap.get(user));
            }
            
        }
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.time - b.time);
        for(Tweet t:list){
            pq.add(t);
            if(pq.size()>10){
                pq.poll();
            }
        }
        List<Integer> result= new ArrayList<>();
        while (!pq.isEmpty()) {
            result.add(0, pq.poll().tweetId);
        }

        return result;
        
    }
    
    public void follow(int followerId, int followeeId) {
        if(!followerMap.containsKey(followerId)){
            followerMap.put(followerId,new HashSet<>());
        }
        if(followerMap.get(followerId)!=null){
            followerMap.get(followerId).add(followeeId);
        }
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(!followerMap.containsKey(followerId)){
            return;
        }
        followerMap.get(followerId).remove(followeeId);
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