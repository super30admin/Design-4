
// Time Complexity : o(nlogk)
// Space Complexity : o(n)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach
class Twitter {

    class Tweet
    {
        int tweetId;
        int createdAt;
        
        public Tweet(int tweetId,int createdAt)
        {
            this.tweetId=tweetId;
            this.createdAt=createdAt;
        }
    }
    /** Initialize your data structure here. */
    Map<Integer,List<Tweet>> tweetMap;
    Map<Integer,Set<Integer>> followeeMap;
    int timeStamp;
    public Twitter() {
        
        tweetMap=new HashMap<>();
        followeeMap=new HashMap<>();
        timeStamp=0;
        
    }
    
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        
        follow(userId,userId);
        
        if(!tweetMap.containsKey(userId))
        {
            tweetMap.put(userId,new ArrayList<>());
        }
        
        Tweet tweet=new Tweet(tweetId,timeStamp++);
        tweetMap.get(userId).add(tweet);
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        
        PriorityQueue<Tweet> pq=new PriorityQueue<>((a,b)->(a.createdAt-b.createdAt));
        
                 Set<Integer> followed= followeeMap.get(userId);
        
        if(followed!=null)
        {
           for(int fid:followed)
           {
             List<Tweet> tweets= tweetMap.get(fid);
               
               if(tweets!=null)
               {
                   for(Tweet t:tweets)
                   {
                       pq.add(t);
                       
                       if(pq.size()>10)
                           pq.poll();
                   }
               }
           }
        }
        
        List<Integer> result=new ArrayList<>();
        
        while(!pq.isEmpty())
        {
            result.add(0,pq.poll().tweetId);
        }
            
        return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        
        //new user
        if(!followeeMap.containsKey(followerId))
        {
            followeeMap.put(followerId,new HashSet<>());
        }
        
        // new follower
        followeeMap.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
       
        if(followeeMap.containsKey(followerId) && followerId!=followeeId)
        {
           followeeMap.get(followerId).remove(followeeId);
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