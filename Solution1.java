//Time Complexity: 
//postTweet - O(1)
//getNewsFeed - O(F*T), where F is the number of followed users and T is the maximum number of tweets of a followed user, 
//follow - O(1), unfollow - O(1)

import java.util.*;

class Tweet {
    int tweetId;
    int createdAt;
    public Tweet(int tweetId, int timestamp) {
        this.tweetId = tweetId;
        createdAt = timestamp; 
    }
}

class Twitter {
    //user -> tweets
    HashMap<Integer, List<Tweet>> tweetMap;
    //user -> followed users
    HashMap<Integer, Set<Integer>> userMap;
    int timestamp; 
    /** Initialize your data structure here. */
    public Twitter() {
        tweetMap = new HashMap<>(); 
        userMap = new HashMap<>(); 
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        //add user in userMap
        follow(userId, userId);
        //add tweet in tweetMap
        List<Tweet> tweets = tweetMap.getOrDefault(userId, new ArrayList<>());
        tweets.add(new Tweet(tweetId, timestamp));
        timestamp++;
        tweetMap.put(userId, tweets); 
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> tweets = new ArrayList<>(); 
        //check if user exists in tweetMap
        if(!userMap.containsKey(userId)) return tweets; 
        
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);
        for(int followeeId: userMap.get(userId)) {
            if(tweetMap.containsKey(followeeId)) {
                for(Tweet tweet: tweetMap.get(followeeId)) {
                    pq.offer(tweet); 
                    if(pq.size() > 10) {
                        pq.poll(); 
                    }
                }
            }
        }
        
        while(!pq.isEmpty()) {
            tweets.add(0, pq.poll().tweetId);
        }
        
        return tweets;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        Set<Integer> followedUsers = userMap.getOrDefault(followerId, new HashSet<>());
        followedUsers.add(followeeId);
        userMap.put(followerId, followedUsers); 
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(userMap.containsKey(followerId) && followerId != followeeId) {
            userMap.get(followerId).remove(followeeId);  
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