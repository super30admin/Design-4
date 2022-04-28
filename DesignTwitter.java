//TC: O(m * n) where m is # of users and n is # of tweets
//SC: O(m * n) where m is # of users and n is # of tweets
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

import java.util.*;

class Twitter {
    
    private static int seq = 0;
    private static final int K = 10;
    private Map<Integer, User> userMap;

    class User {
        int id;
        Tweet head;
        Set<Integer> following;
        
        User(int id) {
            this.id = id;
            following = new HashSet<>();
            following.add(id);
        }
        
        public void follow(int followeeId) {
            following.add(followeeId);
        }
        
        public void unfollow(int followeeId) {
            following.remove(followeeId);
        }
        
        public void postTweet(int tweetId) {
            Tweet tweet = new Tweet(tweetId);
            if (head == null) {
                head = tweet;
            }
            else {
                tweet.next = head;
                head = tweet;
            }
        }
    }
    
    class Tweet {
        int id;
        int timeStamp;
        Tweet next;
        
        Tweet(int id) {
            this.id = id;
            this.timeStamp = seq++;
        }
    }
    
    public Twitter() {
        userMap = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        
        createUserIfNotExists(userId);
        
        userMap.get(userId).postTweet(tweetId);
    }
    
    public List<Integer> getNewsFeed(int userId) {
        
        Set<Integer> following = userMap.getOrDefault(userId, new User(-1)).following;
        
        Queue<Tweet> q = new PriorityQueue<>(
            (a,b) -> b.timeStamp - a.timeStamp
        );
        
        for (int followeeId : following) {
            User user = userMap.getOrDefault(followeeId, new User(-1));
            
            if (user.head != null) {
                q.add(user.head);
            }
        }
        
        List<Integer> newsFeed = new ArrayList<>();
        
        while (!q.isEmpty() && newsFeed.size() < K) {
            
            Tweet tweet = q.remove();
            
            newsFeed.add(tweet.id);
            
            if (tweet.next != null) {
                q.add(tweet.next);
            }
        }
        
        return newsFeed;
    }
    
    public void follow(int followerId, int followeeId) {
        createUserIfNotExists(followerId);
        createUserIfNotExists(followeeId);
        
        userMap.get(followerId).follow(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        userMap.get(followerId).unfollow(followeeId); 
    }
    
    private void createUserIfNotExists(int userId) {
        if (!userMap.containsKey(userId)) {
            userMap.put(userId, new User(userId));
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