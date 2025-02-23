
/**
 * Time Complexity
 * Follow: O(1)
 * Unfollow: O(1)
 * Post Tweet: O(1)
 * Newsfeed: O(m * n) where m is number of users and n is number of tweets
 * 
 * Space Complexity: O(m + n + o) where m is number of users, n is number of tweets
 * and o is number of relationships.
 *  
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class Twitter {

    Map<Integer, Set<Integer>> userMap;
    Map<Integer, List<Tweet>> tweetMap;
    int time;

    public Twitter() {
        userMap = new HashMap<>();
        tweetMap = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        if (!tweetMap.containsKey(userId)) {
            tweetMap.put(userId, new ArrayList<>());
        }
        tweetMap.get(userId).add(new Tweet(tweetId, time++));
    }

    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);
        if (tweetMap.containsKey(userId)) {
            for (Tweet tweet : tweetMap.get(userId)) {
                pq.add(tweet);
                if (pq.size() > 10) {
                    pq.poll();
                }
            }
        }
        Set<Integer> followers = userMap.get(userId);
        if (followers != null) {
            for (int follower : followers) {
                List<Tweet> tweets = tweetMap.get(follower);
                if (tweets != null) {
                    for (Tweet tweet : tweets) {
                        pq.add(tweet);
                        if (pq.size() > 10) {
                            pq.poll();
                        }
                    }
                }
            }
        }

        List<Integer> result = new ArrayList<>();
        while (!pq.isEmpty()) {
            result.add(0, pq.poll().tweetId);
        }
        return result;

    }

    public void follow(int followerId, int followeeId) {
        if (!userMap.containsKey(followerId)) {
            userMap.put(followerId, new HashSet<>());
        }
        userMap.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (!userMap.containsKey(followerId)) {
            return;
        }
        if (userMap.get(followerId).contains(followeeId)) {
            userMap.get(followerId).remove(followeeId);
        }
    }

    class Tweet {
        int tweetId;
        int createdAt;

        public Tweet(int tweetId, int createdAt) {
            this.tweetId = tweetId;
            this.createdAt = createdAt;
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
}
