// Time Complexity : O(nlogk)
// Space Complexity : O(k), where n is the size of the map
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach
/*
1. Store the user ids and their followees in a map.
2. Store the tweets of each user in a map.
3. To get the news feed, get the followees of the user and get their tweets and add them to a min heap.
4. Return the top 10 tweets from the min heap.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

class Tweet {
    int id, timeStamp;
    public Tweet(int id, int timeStamp){
        this.id = id;
        this.timeStamp = timeStamp;
    }
}

class Twitter {
    private HashMap<Integer, HashSet<Integer>> followedMap;
    private HashMap<Integer, List<Tweet>> tweetsMap;
    private int time;
    private static final int MAX_TWEETS = 10;

    public Twitter() {
        this.followedMap = new HashMap<>();
        this.tweetsMap = new HashMap<>();
        this.time = 1;
    }
    
    public void postTweet(int userId, int tweetId) {
        if(!followedMap.containsKey(userId)){
            follow(userId, userId);
        }

        if(!tweetsMap.containsKey(userId)){
            tweetsMap.put(userId, new ArrayList<>());
        }
        tweetsMap.get(userId).add(new Tweet(tweetId, time++));
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> minHeap = new PriorityQueue<>((a, b) -> (a.timeStamp - b.timeStamp));
        List<Integer> result = new ArrayList<>();
        if(!followedMap.containsKey(userId))
            return result;

        List<Integer> users = new ArrayList<>(followedMap.get(userId));
        List<Tweet> fTweets = new ArrayList<>();
        for(Integer user : users){
            if(!tweetsMap.containsKey(user))
                continue;
            List<Tweet> tweets = tweetsMap.get(user);
            for(Tweet tweet : tweets){
                fTweets.add(tweet);
            }
        }

        for(Tweet tweet : fTweets){
            minHeap.offer(tweet);
            if(minHeap.size() > MAX_TWEETS){
                minHeap.poll();
            }
        }

        while(!minHeap.isEmpty()){
            result.add(0, minHeap.poll().id);
        }

        return result;
    }
    
    public void follow(int followerId, int followeeId) {
        if(!followedMap.containsKey(followerId)){
            followedMap.put(followerId, new HashSet<>());
            followedMap.get(followerId).add(followerId);
        }
        followedMap.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(followerId == followeeId || !followedMap.containsKey(followerId))
            return;
        
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