// Time Complexity : 
// Space Complexity : O(N*max(M,T)) where N is the number of users, M is the average number of followers and T is the average number of tweets.
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

class Twitter {
    
    class Tweet{
       public int timeStamp,id;
        public Tweet(int id,int timeStamp){
            this.id=id;
            this.timeStamp=timeStamp;
        }
    }
    
    HashMap<Integer,Set<Integer>> followedMap;
    HashMap<Integer,List<Tweet>> tweetsMap;
    int feedSize,timeStamp;

    /** Initialize your data structure here. */
    public Twitter() {
        this.followedMap = new HashMap();
        this.tweetsMap = new HashMap();
        this.feedSize =10;
        this.timeStamp=0;
        
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        
       // if user is not there in followed table add him

          // if(!followedMap.containsKey(userId)) 
          //     followedMap.put(userId, new HashSet<>());
          // followedMap.get(userId).add(userId);
        
        follow(userId,userId);
        
        if(!tweetsMap.containsKey(userId))
            tweetsMap.put(userId,new ArrayList());
        
        tweetsMap.get(userId).add(new Tweet(tweetId,timeStamp++));
        
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        
        PriorityQueue<Tweet>pq = new PriorityQueue<>((a,b)-> a.timeStamp-b.timeStamp);
        
        if(followedMap.containsKey(userId)){
            Set<Integer>followersSet = followedMap.get(userId);
            if(followersSet!=null){
                
                 for(Integer followerIds : followersSet){
                    
                     List<Tweet>individualUserTweets = tweetsMap.get(followerIds);
                     if(individualUserTweets!=null){
                         for(Tweet tweet:individualUserTweets){
                          pq.add(tweet);
                             if(pq.size()>feedSize){
                                 pq.poll();
                             }
                         }
                     }
                     
                }
            }
           
        }
        
        List<Integer>resultList = new ArrayList();
        while(!pq.isEmpty()){
            Tweet t = pq.poll();
            resultList.add(0,t.id);
        }
        return resultList;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
       
        //creating followerId in map if it is not present
        if(!followedMap.containsKey(followerId)){
            followedMap.put(followerId,new HashSet());
        }
        followedMap.get(followerId).add(followeeId);
        // //creating followeeId if it is not present in hashamp
        // if(!followedMap.containsKey(followeeId))
        //     followedMap.put(followeeId,new HashSet());
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        
        if(followedMap.containsKey(followerId) && followerId!=followeeId)
            followedMap.get(followerId).remove(followeeId);
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
