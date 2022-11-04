// TC : O(nlogk) k - > 10
// SC : O(n * m)
class Twitter {

    class Tweet {
        int tweetId;
        int time;
        public Tweet(int Id, int ti) {
            this.tweetId = Id;
            this.time = ti;
        }
    }
    
    HashMap<Integer, List<Tweet>> tweetMap;
    HashMap<Integer, HashSet<Integer>> followerMap;
    int globalTime = 0;
    public Twitter() {
        tweetMap = new HashMap<>();
        followerMap = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
                
        
        globalTime++;
        Tweet tw = new Tweet(tweetId, globalTime);
        
        if(!tweetMap.containsKey(userId)) {
            List<Tweet> tweetList = new ArrayList<>();
            tweetMap.put(userId, tweetList);
        }
        tweetMap.get(userId).add(tw);
        
    }
    
    public List<Integer> getNewsFeed(int userId) {
        follow(userId, userId);
        List<Integer> result = new ArrayList<>();
        if(!followerMap.containsKey(userId))
            return result; 
        HashSet<Integer> temp = followerMap.get(userId);
        
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> b.time - a.time);
        
        for(Integer id : temp) {
            List<Tweet> temp1 = tweetMap.get(id);
            if(temp1 != null) {
                for(Tweet tw : temp1) {
                    pq.add(tw);
                }
            }
        }
        int i = 0;
        while(!pq.isEmpty()) {
            if(i > 9)
                break;
            result.add(pq.poll().tweetId);
            i++;
        }
          return result;  
    }
    
    public void follow(int followerId, int followeeId) {

        if(!followerMap.containsKey(followerId)) {
            followerMap.put(followerId, new HashSet<>());
        }
        
        HashSet<Integer> temp = followerMap.get(followerId);
        temp.add(followerId);
        temp.add(followeeId);

    }
    
    public void unfollow(int followerId, int followeeId) {
        
        if(!followerMap.containsKey(followerId))
            return;
        
        HashSet<Integer> temp = followerMap.get(followerId);
        temp.remove(followeeId);
        
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
