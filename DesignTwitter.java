// Time Complexity : follow, unfollow & postTweet : O(1) , getNewsFeed : O(NlogK)
// Space Complexity : O(N) - userperspective O(1)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No
class Twitter {
    
    class Tweet{
        int tid;
        int createdAt;
        
        public Tweet(int tid, int createdAt){
            this.tid = tid;
            this.createdAt = createdAt;
        }
    }
    
    HashMap<Integer, List<Tweet>> tweets;
    HashMap<Integer, HashSet<Integer>> users;
    int time;
    
    public Twitter() {
        this.tweets = new HashMap<>();
        this.users = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if(!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId, time++));
        
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b)->a.createdAt - b.createdAt);
        HashSet<Integer> fids = users.get(userId);
        if(fids!=null){
            for(int fid:fids){
                List<Tweet> ftweets = tweets.get(fid);
                if(ftweets!=null){
                    for(Tweet ftweet:ftweets){
                        pq.add(ftweet);
                        if(pq.size()>10){
                            pq.poll();
                        }
                    }   
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0, pq.poll().tid);
        }
        return result;        
    }
    
    public void follow(int followerId, int followeeId) {
        if(!users.containsKey(followerId)){
            users.put(followerId, new HashSet<>());
        }
        users.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(users.containsKey(followerId) && followerId!=followeeId){
            users.get(followerId).remove(followeeId);
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