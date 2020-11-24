package Nov24;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

class DesignTwitter {
	   
    /*
    
    Time Complexity: O(n) where n is no.of tweets posted by the users whom the current user follows. This is because we iterate through all thesen tweets and push them to the priority queue one by one. The priority queue uses heapify function to keep the oldest tweet at the top of the queue. Also, copying priority queue contents to the result list is O(k) where k is size of the queue. In this case, k is given constant as 10 in problem statement. So,oberall time complexity will be O(n).
    
    Space Complexity: O(u^2) where u is no.of users in the system. So, there will be u no.of entries in followed map. Assuming in worst case, each user follows all users in the system, for every entry in followed map, there will be u no.of elements in the set. So, it results in u^2.
    Hashmap for tweets will also contribute to space. and will be O(u*t) where each user posts t no.of tweets on an average.
    So, total space complexity will be O(u^2 + u*t)
    PQ space will not be counted as it will be of constant size (10 in this case).
    
    Did this code successfully run on Leetcode : Yes
    
    Approach: priority queue used to maintain recent 10 tweets for a user's newsfeed.
    
    */
    
    Map<Integer, Set<Integer>> followed;
    Map<Integer, List<Tweet>> tweets;
    int time;

    /** Initialize your data structure here. */
    public DesignTwitter() {
        followed = new HashMap<>();
        tweets = new HashMap<>();
        time = 0;
    }
    
    class Tweet {
        int tId;
        int timestamp;
        public Tweet(int ID, int TS) {
            tId = ID;
            timestamp = TS;
        }
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        time++;
        Tweet newTweet = new Tweet(tweetId, time);
        if (!tweets.containsKey(userId)) {
            tweets.put(userId, new ArrayList<>());
            if (!followed.containsKey(userId)) {
                followed.put(userId, new HashSet<>());
                followed.get(userId).add(userId);
            }
        }
        tweets.get(userId).add(newTweet);
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> result = new ArrayList<>();
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b)->a.timestamp-b.timestamp);
        if (followed.containsKey(userId)) {
            Set<Integer> followedUsers =  followed.get(userId);
            for (int fUser: followedUsers) {
                if (tweets.containsKey(fUser)) {
                    List<Tweet> fUserTweets = tweets.get(fUser);
                    for (Tweet t: fUserTweets) {
                        pq.add(t);
                        if (pq.size() > 10) {
                            pq.poll();
                        }
                    }
                }
            }
        }
        while (!pq.isEmpty()) {
            result.add(0, pq.poll().tId);
        }
        return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if (!followed.containsKey(followerId)) {
            followed.put(followerId, new HashSet<>());
            followed.get(followerId).add(followerId);
        }
        followed.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if (!followed.containsKey(followerId) || followerId == followeeId || !followed.get(followerId).contains(followeeId)) {
            return;
        }
        followed.get(followerId).remove(followeeId);
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