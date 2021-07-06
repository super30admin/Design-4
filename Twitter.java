/*

    Time Complexity : O(k log k ) k size of feed
    is worked on leetcode  : YES
*/

public class Twitter {
    int feedsize =10;
    HashMap<Integer, List<Twit>> twitmap;
    HashMap<Integer, HashSet<Integer>> followermap;
    int timestamp;
    /** Initialize your data structure here. */
    class Twit{
        
        int id;
        int createdAt;
        public Twit(int id,int timestamp){
            this.id = id;
            createdAt = timestamp;
        }
    }
    public Twitter() {
        twitmap = new HashMap();
        followermap = new HashMap();        
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if(!twitmap.containsKey(userId)){
            twitmap.put(userId, new ArrayList());
        }
        twitmap.get(userId).add(new Twit(tweetId, timestamp++));
        
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        List< Integer> result = new ArrayList();
        PriorityQueue<Twit> pq = new PriorityQueue<> ((t1, t2) -> t1.createdAt - t2.createdAt);
        
        Set<Integer> feedIds = followermap.get(userId);
        if(feedIds != null){
            for(Integer uid : feedIds ){
                List<Twit> tweets  =  twitmap.get(uid);
                if(tweets != null){
                    for(Twit twitId :  tweets){
                        pq.add(twitId);
                        if(pq.size() > feedsize){
                            pq.poll();
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
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!followermap.containsKey(followerId)){
            followermap.put(followerId, new HashSet<>());
        }
        followermap.get(followerId).add(followeeId);
        
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
         if(followermap.containsKey(followerId) && followerId != followeeId ){
            followermap.get(followerId).remove(followeeId);
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