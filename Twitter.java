// Time Complexity : O(nlog(k)), where k = 10 using minHeap
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this :    No
package design4;

import java.util.*;

class Twitter {

    class Tweet {

        int id;
        int createdAt;

        public Tweet(int id, int createdAt) {
            this.id = id;
            this.createdAt = createdAt;
        }

    }
    HashMap<Integer, List<Tweet>> tweets;
    HashMap<Integer, Set<Integer>> followed;
    int time;

    /**
     * Initialize your data structure here.
     */
    public Twitter() {
        tweets = new HashMap<>();
        followed = new HashMap<>();
    }

    /**
     * Compose a new tweet.
     */
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if (!tweets.containsKey(userId)) {
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId, time++));
    }

    /**
     * Retrieve the 10 most recent tweet ids in the user's news feed. Each item
     * in the news feed must be posted by users who the user followed or by the
     * user herself. Tweets must be ordered from most recent to least recent.
     */
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> (a.createdAt - b.createdAt));
        Set<Integer> fIds = followed.get(userId);
        if (fIds != null) {
            for (int fId : fIds) {
                List<Tweet> fTweets = tweets.get(fId);
                if (fTweets != null) {
                    for (Tweet fTweet : fTweets) {
                        pq.add(fTweet);
                        if (pq.size() > 10) {
                            pq.poll();
                        }
                    }
                }
            }
        }
        List<Integer> res = new ArrayList<>();
        while (!pq.isEmpty()) {
            res.add(0, pq.poll().id);
        }
        return res;
    }

    /**
     * Follower follows a followee. If the operation is invalid, it should be a
     * no-op.
     */
    public void follow(int followerId, int followeeId) {
        if (!followed.containsKey(followerId)) {
            followed.put(followerId, new HashSet<>());
        }
        followed.get(followerId).add(followeeId);
    }

    /**
     * Follower unfollows a followee. If the operation is invalid, it should be
     * a no-op.
     */
    public void unfollow(int followerId, int followeeId) {
        if (followed.containsKey(followerId) && followerId != followeeId) {
            Set<Integer> set = followed.get(followerId);
            if (set.contains(followeeId)) {
                set.remove(followeeId);
            }
        }
    }
}

/**
 * Your Twitter object will be instantiated and called as such: Twitter obj =
 * new Twitter(); obj.postTweet(userId,tweetId); List<Integer> param_2 =
 * obj.getNewsFeed(userId); obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */
