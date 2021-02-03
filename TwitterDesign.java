/*
 * #355. Design Twitter
 * 
 * Design a simplified version of Twitter where users can post tweets, follow/unfollow another user and is able to see the 10 most recent tweets in the user's news feed. Your design should support the following methods:

1. postTweet(userId, tweetId): Compose a new tweet.
2. getNewsFeed(userId): Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
3. follow(followerId, followeeId): Follower follows a followee.
4. unfollow(followerId, followeeId): Follower unfollows a followee.


Example:

Twitter twitter = new Twitter();

// User 1 posts a new tweet (id = 5).
twitter.postTweet(1, 5);

// User 1's news feed should return a list with 1 tweet id -> [5].
twitter.getNewsFeed(1);

// User 1 follows user 2.
twitter.follow(1, 2);

// User 2 posts a new tweet (id = 6).
twitter.postTweet(2, 6);

// User 1's news feed should return a list with 2 tweet ids -> [6, 5].
// Tweet id 6 should precede tweet id 5 because it is posted after tweet id 5.
twitter.getNewsFeed(1);

// User 1 unfollows user 2.
twitter.unfollow(1, 2);

// User 1's news feed should return a list with 1 tweet id -> [5],
// since user 1 is no longer following user 2.
twitter.getNewsFeed(1);

 */


/*
 * Time Complexity: 
 * postTweet(), follow(), unfollow() -> O (1)
 * getNewsFeed() -> O (n*m) -> for each followee, we are getting 'm' tweets, for 'n' followees we will get n*m tweets, 'n' is number of users and 'm' is number of tweets
 * 
 * Space Complexity: O (n+m) -> 'n' is number of users and 'm' is number of tweets
 * 
 * Did this code successfully run on leetcode: Yes
 * 
 * Any problem you faced while coding this: No
 * 
 */

package com.s30.edu.design4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class TwitterDesign {
	
	// A class for initializing tweet ID and timestamp at which tweet was posted for each tweet
	class Tweet{
		int id;
	    int createdAt;
	        
	    // Whenever new tweet is posted by a user, constructor is invoked 
	    public Tweet(int tweetID, int timestamp){
	    	this.id = tweetID;
	        this.createdAt = timestamp;
	    }
	        
	}
	
	// HashMap to store the user ID as a key and tweet information -> tweet ID and time of its creation as a value
	HashMap<Integer, List<Tweet>> tweets;
	// HashMap to store the user ID as a key and its followees(whom user is following) as a value in HashSet since need unique followee Id's
    HashMap<Integer, HashSet<Integer>> following;
    // feedSize to get 10 most recent tweets, timestamp to record time of each tweet by a user when it was posted
    int feedSize, timestamp;
    
    /** Initialize your data structure here. */
    public TwitterDesign() {
        tweets = new HashMap<Integer, List<Tweet>>();
        following = new HashMap<Integer, HashSet<Integer>>();
        feedSize = 10;
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
    	// When user posts a tweet, user is added as its own followee
    	// Beacause, when we call getNewsFeed method which should return tweet ID's of tweets posted by user himself and its followees,
    	// We can get the user itself as a followee and its other followees from hashmap to get the tweets posted by user and its followees
        follow(userId, userId);
        // If map does not have an entry for user ID, create one and an empty ArrayList as a value which will contain tweet ID and its timestamp
        if(!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<Tweet>());
        }
        // After making an entry of user ID or if user ID is already present, means same user is posting another tweet now
        // Add the new tweet's ID and time at which it is posted as a value
        tweets.get(userId).add(new Tweet(tweetId, timestamp++));
        
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
    	// We are using Priority queue to store the tweet information
    	// Priority queue will use the comparator while adding tweet information to the queue
    	// Since, Priority queue is a Min Heap, it will store the tweets with least recent timestamps at front of queue followed by most recent timestamped tweets
        PriorityQueue<Tweet> q = new PriorityQueue<Tweet>((t1,t2)->t1.createdAt-t2.createdAt);
        
        // Check if the userID is in a map for which we are getting news feed
        if(following.containsKey(userId)){
        	// If yes, get the followees of that user in a Set(unordered)
            Set<Integer> followees = following.get(userId);
            
            // If that user has followees including himself
            if(followees != null){
            	// For each followee of that user
                for(Integer f : followees){
                	// Get all the tweets for the followee in a List from "tweets" map
                    List<Tweet> fTweets = tweets.get(f);
                    
                    // If that followee has tweets posted
                    if(fTweets != null){
                    	// For each tweet of a followee
                        for(Tweet t : fTweets){
                        	// Add each tweet of that followee to a priority queue, tweet information will be added based on timestamp with least recent timestamped tweet at front of a queue
                            q.add(t);
                            
                            // If the queue size bacomes greater than the feedSize
                            // Means, queue has more than 10 tweets information, remove the tweets from queue
                            if(q.size() > feedSize){
                                q.poll();
                            }
                        }
                    }
                }
            }
              
        }
        
        // Create a list to store the tweet ID's of user's all followees including himself
        List<Integer> result = new ArrayList<>();
            
            // Until Priority queue becomes empty 
            while(!q.isEmpty()){
            	// Poll the tweets information from least recent to most recent timestamp
                Tweet mostRecent = q.poll();
                // And, add the tweet ID at the first position in a List
                // This way, we will have most recent tweet at start of List
                result.add(0, mostRecent.id);
            }
            
            return result; // return the list of tweet ID's
        
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
    	// If map does not have an entry for follower ID, create one and an empty HashSet as a value which will contain the followees of a user including himself
        if(!following.containsKey(followerId)){
            following.put(followerId, new HashSet<>());
        }
        // After making an entry of follower ID or If follower ID is already present, add the followee for that follower
        following.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
    	// If the map contains follower ID and followee is not the user himself, then remove the followee for that user from HashSet in a map
        if(following.containsKey(followerId) && followerId != followeeId){
            following.get(followerId).remove(followeeId);
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
