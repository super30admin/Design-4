/**
  Follow un follow O(1)
  getFeeds (k log k ) where k =  total number of twits in userfeeds
  Post O(1)
**/

class Twitter {
    int createdAt;
    class Twit {
        int id;
        int time;
        
        public Twit(int id, int time) {
            this.id = id;
            this.time = time;
        }
        
        public int getId() {
            return this.id;
        }
        public String toString() {
            return "ID :"+ this.id;
        }
        
    }
    
    Map<Integer, List<Twit>> userTwitMap ;
    Map<Integer, Set<Integer>> userfollower ;
    
    /** Initialize your data structure here. */
    public Twitter() {
        createdAt = 0;
        userTwitMap = new HashMap();
        userfollower = new HashMap();
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        Twit twit = new Twit(tweetId,createdAt);
        createdAt++;
        userTwitMap.get(userId).add(twit);
        
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        List< Integer> result = new ArrayList();
        PriorityQueue<Twit> pq = new PriorityQueue<> ((t1, t2) -> t1.time - t2.time);
        Set<Integer> followeeSet =  userfollower.get(userId);
        if(followeeSet != null) {
            for(Integer followeeId : followeeSet) {
                
                List<Twit> twitForEachFollowee = userTwitMap.get(followeeId);
                if(twitForEachFollowee != null) {
                    for(Twit t  : twitForEachFollowee ) {
                        pq.add(t);
                        if(pq.size() > 10) {
                            pq.poll();
                        }
                    }    
                }
                
            }
    
        }
                
        List<Integer> ids = new ArrayList();
        
        while(!pq.isEmpty()) {
            ids.add(0, pq.poll().id);
        }
        
        return ids;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!userfollower.containsKey(followerId)) {
            userTwitMap.put(followerId, new ArrayList<Twit>());
            userfollower.put(followerId, new HashSet<Integer>());
        }
        userfollower.get(followerId).add(followeeId);
        
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
         if(userfollower.containsKey(followerId) && followerId != followeeId ){
            userfollower.get(followerId).remove(followeeId);
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
