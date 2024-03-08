// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

class Twitter {
    Map<Integer, Set<Integer>> followerMap;
    Map<Integer, Set<Tweet>> followerTweetMap;
    int time;

    class Tweet {
        int tweetId;
        int time;

        Tweet(int tweetId, int time) {
            this.tweetId = tweetId;
            this.time = time;
        }
    }

    public Twitter() {
        followerMap = new HashMap<>();
        followerTweetMap = new HashMap<>();
        time = 0;
    }

    //TC: O(1)
    public void postTweet(int userId, int tweetId) {
        if (followerTweetMap.get(userId) == null) {
            followerTweetMap.put(userId, new HashSet<>());
        }

        followerTweetMap.get(userId).add(new Tweet(tweetId, this.time++));
        follow(userId, userId);
    }

    //TC: O(n * T) [n: number to users in twitter; t: no. of tweets per user; T: ∑(t); ]
    //SC: O(1)
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> list = new ArrayList<>();
        Set<Integer> followeeSet = followerMap.get(userId);

        if (followeeSet == null || followeeSet.isEmpty()) {
            return list;
        }

        PriorityQueue<Tweet> pq = new PriorityQueue<>(
                (a, b) -> (Integer.valueOf(a.time).compareTo(Integer.valueOf(b.time))));

        for (int id : followeeSet) {
            Set<Tweet> tweetSet = followerTweetMap.get(id);
            if (tweetSet != null && !tweetSet.isEmpty()) {
                for (Tweet tw : tweetSet) {
                    pq.offer(tw);
                    if (pq.size() > 10) {
                        pq.poll();
                    }
                }
            }
        }

        while (!pq.isEmpty()) {
            list.add(0, pq.poll().tweetId);
        }

        return list;

    }

    //TC: O(1)
    public void follow(int followerId, int followeeId) {
        if (followerMap.get(followerId) == null) {
            followerMap.put(followerId, new HashSet<>());
        }
        followerMap.get(followerId).add(followeeId);
    }

    //TC: O(1)
    public void unfollow(int followerId, int followeeId) {
        if (followerMap.get(followerId) == null) {
            return;
        }
        followerMap.get(followerId).remove(followeeId);
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