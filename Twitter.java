// Time Complexity : O(1) for follow and unfollow, but O(n) for operation next,advance and skip. O(N log k) n number of tweets and k feedSize=5
// Space Complexity : O(n)

// Did this code successfully run on Leetcode :yes. 

// Any problem you faced while coding this :


// Your code here along with comments explaining your approach
// 
//Runtime: 25 ms, faster than 75.85% of Java online submissions for Design Twitter.
//Memory Usage: 44.5 MB, less than 100.00% of Java online submissions for Design Twitter.

class Twitter {

    private int timeStamp;
    private int feedSize;//in prod this will be externalize configuration.
    Map<Integer, Set<Integer>> followed;
    Map<Integer,List<Tweet>> tweets;//we can use only ids intead of tweets.
    
    class Tweet{
        //String text;
        int id;//prod case we will use UUID.
        //DateTime createdAt;
        int createdAt;
        public Tweet(int id,int createdAt){
            this.id=id;
            this.createdAt=createdAt;
        }
        
    }
    
   
    /** Initialize your data structure here. */
    public Twitter() {
         this.followed = new HashMap<>();
         this.tweets = new HashMap<>();
        this.feedSize=10;
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) { //O(1)
        if (!followed.containsKey(userId)){
           followed.put(userId,new HashSet<>());   
        }
        followed.get(userId).add(userId);
        if (!tweets.containsKey(userId)){
            tweets.put(userId,new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId,timeStamp++));
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((t1,t2)-> t1.createdAt - t2.createdAt); 
        Set<Integer> fIds= followed.get(userId);
        if (fIds!=null){
            for (Integer fId:fIds){
               List<Tweet> fTweets= tweets.get(fId);
               if (fTweets !=null){
                   for (Tweet twt:fTweets){
                       if (pq.size() < feedSize){
                           pq.add(twt);
                       }else{
                           if (twt.createdAt>pq.peek().createdAt){
                               pq.poll();
                               pq.add(twt);
                           }
                       }
                   }
               }
            }
        }
        List<Integer> result=new ArrayList<>();
        while (!pq.isEmpty()){
            result.add(0,pq.poll().id);
        }
        return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) { //O(1)
         if (!followed.containsKey(followerId)){
           followed.put(followerId,new HashSet<>());   
        }
        followed.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) { //O(1)
         if (followed.containsKey(followerId) && followerId!=followeeId){
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