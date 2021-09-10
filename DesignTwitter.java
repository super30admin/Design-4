    
// Time Complexity : O(NlogK)
// Space Complexity : O(N)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : yes


// Your code here along with comments explaining your approach
 /*
 Approach: 
 1) we have follow, unfollow and top 10 tweets 
 2) Thus we use a hashMap having key as Integer and List<Integer> contaiing follow userIds
 3) In tweet Map, we store the user id and all tweets associated with him
 4) and finally we need a class storing the tweetId and the timestamp at which the tweet was created.
 5) Thus in prioirty queue we can have a min heap as we need to give top 10 tweets
    -> first approach, use a max heap and put all the elements in it. However the approach would be NlogN 
    -> we create a min heap and push elements only till K, thus according to the logic all min elements will be pushed outside and we would be left with just top 10 elements.
    having a complexiety of NlogK
6) we can sort all the tweets and implement the basic implementation of twitter.
 
 */   
    
 import java.util.*;
    class DesignTwitter {

    
        class Tweet{
            
            int tId;
            int createdAt; // timestamp for capturing the time of evcery tweet as we need to return the 10 most recent tweets
            
            public Tweet(int tId, int createdAt)
            {
                this.tId = tId;
                this.createdAt = this.createdAt;
                
            }
            
        }
        
        Map<Integer,Set<Integer>> followMap; // for having follower, followee
        Map<Integer,List<Tweet>> tweetMap;
        int time;
        
        
        /** Initialize your data structure here. */
        public Twitter() {
            
            followMap = new HashMap();
            tweetMap = new HashMap();
            
            
        }
        
        /** Compose a new tweet. */
        public void postTweet(int userId, int tweetId) {
            
            follow(userId,userId);
            
            
            // creation of new tweet
            if(!tweetMap.containsKey(userId))
            {
                tweetMap.put(userId,new ArrayList<>());
            }
            tweetMap.get(userId).add(new Tweet(tweetId,time++));
            
            
        }
        
        /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
        public List<Integer> getNewsFeed(int userId) {
            
            
            PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b)->(a.createdAt-b.createdAt));
            Set<Integer> set = followMap.get(userId);
            if(set!=null)
            {
                for(int user : set)
                {
                    List<Tweet> tweets = tweetMap.get(user);
                    if(tweets!=null)
                    {
                     for(Tweet t: tweets)
                     {
                         pq.add(t);
                         if(pq.size()>10)
                         {
                             pq.poll();
                         }
                     }
                        
                    }
                    
                }
            }
            List<Integer> res = new ArrayList<>();
            
            while(!pq.isEmpty())
            {
                res.add(0,pq.poll().tId);
            }
            
            return res;
            
        }
        
        /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
        public void follow(int followerId, int followeeId) {
            if(!followMap.containsKey(followerId))
            {
                followMap.put(followerId,new HashSet<>());
            }
            followMap.get(followerId).add(followeeId);
            
        }
        
        /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
        public void unfollow(int followerId, int followeeId) {
            
            if(followMap.containsKey(followerId))
            {
                if(followMap.get(followerId).contains(followeeId))
                {
                    followMap.get(followerId).remove(followeeId);
                }
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

