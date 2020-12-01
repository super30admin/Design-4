//Time Complexity : O(n)
//Space Complexity : O(1)
//Did this code successfully run on Leetcode : This problem is locked
//Any problem you faced while coding this : 

package com.s30.satish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

class Twitter_Design_355 {
    class tweet
    {
        int id;
        int createdAt;
        public tweet(int id, int createdAt)
        {
            this.id = id;
            this.createdAt = createdAt;
        }
    }
    HashMap<Integer, Set<Integer>> userMap; 
    HashMap<Integer, List<tweet>> tweetsMap; 
    int tweetTime;
    /** Initialize your data structure here. */
    public Twitter_Design_355() {
        userMap = new HashMap<>();
        tweetsMap = new HashMap<>(); 
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        
        tweet t = new tweet(tweetId, tweetTime++);
        
        if(!tweetsMap.containsKey(userId))
            tweetsMap.put(userId, new ArrayList<>());
        tweetsMap.get(userId).add(t);    
        
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        Set<Integer> uIds = userMap.get(userId);
        if(uIds != null)
        {
           for(Integer uId : uIds)
            {
                List<tweet> lt = tweetsMap.get(uId);
                if(lt != null)
                {
                    for(tweet tw : lt)
                    {
                        pq.add(tw);
                        if(pq.size() > 10)
                        {   
                            pq.poll();
                        }
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty())
            result.add(0, pq.poll().id);
        return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!userMap.containsKey(followerId))
            userMap.put(followerId, new HashSet<>());
        userMap.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(userMap.containsKey(followerId) && followerId != followeeId)
            userMap.get(followerId).remove(followeeId);
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
