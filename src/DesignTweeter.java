
// Time Complexity : Post, Follow, Unfollow - O(1), GetFeed - O(n*t) - n = number of users, t = avg number of tweets by each user
// Space Complexity: Not considering space needed to store follower and tweets, auxilary space needed is O(1)  i.e. min heap of max size 10
// Did this code successfully run on Leetcode: Yes
// Any problem you faced while coding this : No

/**
 * https://leetcode.com/problems/design-twitter/submissions/
 * 
 *
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Twitter {

    class Tweet{
        int id;
        int createdAt;
        
        public Tweet(int id, int createdAt) {
            this.id = id;
            this.createdAt = createdAt;
        }
    }
    
    int timeStamp;
    //map of user and users he is following
    Map<Integer, HashSet<Integer>> followers;
    
    //map of user and its list of tweets
    Map<Integer, ArrayList<Tweet>> userTweetsMap;
    int feedSize;
    
    /** Initialize your data structure here. */
    public Twitter() {
        userTweetsMap = new HashMap<>();
        followers = new HashMap<>();
        feedSize = 10;
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        //user follows himself (since we need to get own tweets as well in the feed)
        if(!followers.containsKey(userId)) {
            followers.put(userId, new HashSet<>());
        }
        
        followers.get(userId).add(userId);
        
        if(userTweetsMap.get(userId) == null) {
            userTweetsMap.put(userId, new ArrayList<>());
        }
        
        //add user tweet to tweet map
        userTweetsMap.get(userId).add(new Tweet(tweetId, timeStamp++));
        
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        // use min head to maintain the tweets based on createdAt timestamp
        
        PriorityQueue<Tweet> pQueue = new PriorityQueue<>((t1, t2) -> {return t1.createdAt - t2.createdAt; });
        HashSet<Integer> followersSet = followers.get(userId);
        
        if(followersSet != null) {
            for(Integer followerId: followersSet) {
               ArrayList<Tweet> tweets =  userTweetsMap.get(followerId);

                if(tweets != null) {
                   for(Tweet tweet: tweets) {
                      pQueue.add(tweet);
                       
                       //if feedsize crossed, remove the tweet from top of heap (tweet with smallest timestamp will be removed as its min heap and we maintain recent tweets this way)
                      if(pQueue.size() > feedSize) {
                          pQueue.poll();
                      }
                   } 
                }
                
            }
        }
        
       Integer[] res = new Integer[pQueue.size()];
        
        //now heap has max number of tweets = capacity, keep polling min heap to get min timestamped tweet and keep filling array from end to start so we get 
        //list of tweets from recent to old order
        for(int i = pQueue.size()-1; i >= 0; i--) {
            res[i] = pQueue.poll().id;
        }
        
        return Arrays.asList(res);
        
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!followers.containsKey(followerId)) {
            followers.put(followerId, new HashSet<>());
        }
        
        followers.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        //if followerId has record in map and user not trying to unfollow himself
        if(followers.containsKey(followerId) && followerId != followeeId) {
            followers.get(followerId).remove(followeeId);
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