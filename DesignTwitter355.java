import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

//time complexity : Nlogn
//space complexity : O(n)
//leet code : yes
//steps: consider two maps one is for maintaining userids and followees and other for tweets mapping. 
class Twitter {

    int timestamp;
    int feedsize;
    
    Map<Integer,Set<Integer>> userFollowsMap;
    Map<Integer,List<Tweet>> userTweetsMap;
    
    /** Initialize your data structure here. */
    public Twitter() {
        
        timestamp = 0;
        feedsize = 10;
        
        userFollowsMap = new HashMap<>();
        userTweetsMap =  new HashMap<>();
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        isFirstTime(userId);
        List<Tweet> tweetList = userTweetsMap.get(userId);
        tweetList.add(new Tweet(tweetId,++timestamp));
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        
        isFirstTime(userId);
        List<Tweet> feeds = new ArrayList<>();
        Set<Integer> followees = userFollowsMap.get(userId);
        
        for(Integer followee: followees){
            feeds.addAll(userTweetsMap.get(followee));
        }
        
        Collections.sort(feeds,(a,b)->(b.createdAt - a.createdAt));
        
        Iterator<Tweet> iterator = feeds.iterator();
        List<Integer> recentFeed = new ArrayList<>(); 
        int count = feedsize;
        
        while(count > 0 && iterator.hasNext()){
            recentFeed.add(iterator.next().tweetId);
            count--;
        }
        return recentFeed;
        
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        
        isFirstTime(followerId);
        isFirstTime(followeeId);
        
        Set<Integer> followees = userFollowsMap.get(followerId);
        followees.add(followeeId);
        
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        
        isFirstTime(followerId);
        isFirstTime(followeeId);
        
         Set<Integer> followees = userFollowsMap.get(followerId);
        if(followeeId != followerId) {
            followees.remove(followeeId);
        }
        
        
    }
    
    private void isFirstTime(int userId){
        Set<Integer> followees = userFollowsMap.get(userId);
               if(followees == null) {
            followees = new HashSet<>();
            followees.add(userId);
            userFollowsMap.put(userId, followees);
        }
        
        
        List<Tweet> tweets = userTweetsMap.get(userId);
        if(tweets == null) {
            tweets = new LinkedList<>();
            userTweetsMap.put(userId,tweets);
        }
    }
    
    
    
    class Tweet{
        int tweetId;
        int createdAt;
        
        Tweet(int tweetId,int createdAt){
            this.tweetId = tweetId;
            this.createdAt = createdAt;
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