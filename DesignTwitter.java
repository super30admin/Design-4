package design4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class DesignTwitter {
	//Time Complexity : Defined on every method
	//Space Complexity : O(n), where n is the max of users vs tweets
	//Did this code successfully run on Leetcode : Yes
	//Any problem you faced while coding this : No
	class Tweet {
        int tweetId;
        int time;
        
        public Tweet(int tweetId, int time) {
            this.tweetId = tweetId;
            this.time = time;
        }
    }
    
    Map<Integer, Set<Integer>> follow;
    Map<Integer, List<Tweet>> tweets;
    int time = 0;
    public DesignTwitter() {
        follow = new HashMap<>();
        tweets = new HashMap<>();
    }
    
    // O(1)
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if(!tweets.containsKey(userId))
            tweets.put(userId, new ArrayList<>());
        tweets.get(userId).add(new Tweet(tweetId, time++));
    }
    
    // O(n^2)
    public List<Integer> getNewsFeed(int userId) {
        Set<Integer> following = follow.get(userId);
        PriorityQueue<Tweet> minHeap = new PriorityQueue<>((a, b) -> a.time - b.time);
        if(following != null) {
            for(Integer i: following) {
                List<Tweet> list = tweets.get(i);
                
                if(list != null) {
                    for(Tweet t: list) {
                        minHeap.add(t);
                        if(minHeap.size() > 10)
                            minHeap.poll();
                    }
                }
            }
        }
        
        List<Integer> res = new ArrayList<>();
        while(!minHeap.isEmpty())
            res.add(0, minHeap.poll().tweetId);
        return res;
    }
    
    // O(1)
    public void follow(int followerId, int followeeId) {
        if(!follow.containsKey(followerId))
            follow.put(followerId, new HashSet<>());
        follow.get(followerId).add(followeeId);
    }
    
    // O(1)
    public void unfollow(int followerId, int followeeId) {
        if(follow.containsKey(followerId) && followerId != followeeId)
            follow.get(followerId).remove(followeeId);
    }
}
