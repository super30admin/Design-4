//Problem 85 : Design Twitter
//TC: postTweet(),follow(),unfollow : O(1) | getNewsFeed()-> O(Number of Followees * Number of Tweets)
//SC: Max(O(number of userIds * tweets),O(number of userIds * followees))

/*Steps
  Using Top-Down thinking. Users and Followers are big entities where as Tweet is a small entity.
  Take two maps, one for maintaining userTweet and other one for followers.
  userTweet should contain list of tweets where as followers will contain Set of followees including his or her id.Here we are using set because followees should be unique.
  Eg: userTweet map { userId : [t1,t2,t3] } | followerMap : {followerId : [foloweeId]}
  Whenever we need tweets fo a user just use followerMap and get all the tweets of the followeeIds.
  
  For TimeStamp use a global variable time and whenever some tweet is posted increment the time count.

  GetNews Feed :
  as we have to find/return largest/descending time stamp use minHeap i.e similar to find kth largest element.

  Just get the list of followees of the userId provided. Then Iterate over the foloweeIds and add all the tweets in the minHeap. If minHeap size is exceeding 10, poll the peek element. Once all the traversal is done, just insert all the elements of the minHeap in the result list which should be LinkedList because we will be adding all the element on the first index of the result as we will be needing max Timestamp element first.


*/

import java.util.*;
class Twitter {

    class Tweet{
        int tweetId;
        int createdAt;
        
        Tweet(int tweetId, int createdAt){
            this.tweetId   = tweetId;
            this.createdAt = createdAt;
        }
        
    }
    
    Map<Integer,List<Tweet>> userTweet;
    Map<Integer,Set<Integer>> followerMap;
    int time;//as it is global variable will be initialised to 0 by default
    
    /** Initialize your data structure here. */
    public Twitter() {
        userTweet = new HashMap<>();
        followerMap = new HashMap<>();
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {//TC:O(1)
        //before tweeting I have to make sure that tweeting user following himself
        follow(userId,userId);
        
        if(!userTweet.containsKey(userId)){
            userTweet.put(userId,new ArrayList<>());
        }
        
        userTweet.get(userId).add(new Tweet(tweetId,time++));//everytime time will increase works like timestamp
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {//TC:O(Number of Followees * Number of Tweets)
        //as we have to find/return largest/descending time stamp use minHeap i.e similar to find kth largest element
        PriorityQueue<Tweet> minHeap = new PriorityQueue<>((a,b)->a.createdAt-b.createdAt);
        
        List<Integer> result = new LinkedList<>();//Not to use Array List because as min heap will contain the time stamp in ascending order and I have to return the max time stamp first, so on polling the lement every time I have to insert the element in first position. Therefore in case of ArrayList TC wiil be O(n), because every time list will be moved. i.e use LinkedList over here. Insertion TC will be O(1)
        
        Set<Integer> followees = followerMap.get(userId);
        
        if(followees==null) return result;
        
        for(int followeeId : followees){
            List<Tweet> tweetsList = userTweet.get(followeeId);
            
            if(tweetsList==null) return result;
            
            for(Tweet t:tweetsList){
                minHeap.add(t);
                if(minHeap.size()>10){//if heap size is greater than 10 poll current top element
                    minHeap.poll();
                }
            }
            
        }
        
        
        
        while(!minHeap.isEmpty()){
           result.add(0,minHeap.poll().tweetId);    
        }
        
        return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {//TC:O(1)
        
        if(!followerMap.containsKey(followerId)){
            followerMap.put(followerId,new HashSet<>());
        }
        
        followerMap.get(followerId).add(followeeId);
        
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {//TC:O(1)
        
        if(followerMap.containsKey(followerId) && followerId!=followeeId){//because here follower is also following himself so preventing himself from unfollowing
            followerMap.get(followerId).remove(followeeId);
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