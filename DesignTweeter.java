// Time Complexity : o(nlogk)
// Space Complexity : o(logk)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no

class Twitter {
    class Tweet{
        int tid;
        int createdAt;
        public Tweet(int tid, int createdAt){
            this.tid = tid;
            this.createdAt = createdAt;
        }
    }
    int time=0;
    Map<Integer,HashSet<Integer>> followed;
    Map<Integer,List<Tweet>> tweets;
    public Twitter() {
        this.followed = new HashMap<>();
        this.tweets = new HashMap<>();
        
    }
    
    public void postTweet(int userId, int tweetId) {
        follow(userId,userId);
        if(!tweets.containsKey(userId)){
            tweets.put(userId,new ArrayList<Tweet>());
        }
        tweets.get(userId).add(new Tweet(tweetId,time));
        time++;
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<Tweet>((a,b)->a.createdAt-b.createdAt);
        HashSet<Integer> fids = followed.get(userId);
        if(fids!=null){
            for(int fid : fids){
                List<Tweet> twts = tweets.get(fid);
                if(twts!=null){
                    int size = twts.size()-1;
                    for(int k=size;k>=0 && k>=size-11;k--){
                        Tweet tweet = twts.get(k);
                        pq.add(tweet);
                        if(pq.size()>10){
                            pq.poll();
                        }
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0,pq.poll().tid);
        }
        return result;
        } 
    
    public void follow(int followerId, int followeeId) {
        if(!followed.containsKey(followerId)){
            followed.put(followerId,new HashSet<Integer>());
        }
        followed.get(followerId).add(followeeId);
    }
    
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
