
/**
   postTweet:
       TC: O(1) - Each tweet is posted in O(1) time.
       SC: O(1) for each tweet
   getNewsFeed:
       TC: O(n log k) where n is the total number of tweets, and k is the number of users followed
       SC: O(k) for heap
   follow:
       TC: O(1)
       SC: O(1)
   unfollow:
       TC: O(1)
       SC: O(1)
  Did this code successfully run on Leetcode : yes
  Any problem you faced while coding this :
 */

import java.util.*;

public class Twitter {
    int time = 0;
    Map<Integer, List<int[]>> tweetMap;
    Map<Integer, HashSet<Integer>> followMap;

    public Twitter() {
        tweetMap = new HashMap<>();
        followMap = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        tweetMap.putIfAbsent(userId, new ArrayList<>());
        tweetMap.get(userId).add(new int[]{time++, tweetId});
    }

    public List<Integer> getNewsFeed(int userId) {
        List<Integer> result = new ArrayList<>();
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(b[0], a[0]));

        followMap.putIfAbsent(userId, new HashSet<>());
        followMap.get(userId).add(userId);

        for (int followeeId : followMap.get(userId)) {
            List<int[]> followeeTweets = tweetMap.get(followeeId);
            if (followeeTweets != null && !followeeTweets.isEmpty()) {
                int lastTweetIndex = followeeTweets.size() - 1;
                int[] lastTweet = followeeTweets.get(lastTweetIndex);
                pq.offer(new int[]{lastTweet[0], lastTweet[1], followeeId, lastTweetIndex - 1});
            }
        }

        while (!pq.isEmpty() && result.size() < 10) {
            int[] tweetData = pq.poll();
            result.add(tweetData[1]);
            if (tweetData[3] >= 0) {
                int[] nextTweet = tweetMap.get(tweetData[2]).get(tweetData[3]);
                pq.offer(new int[]{nextTweet[0], nextTweet[1], tweetData[2], tweetData[3] - 1});
            }
        }

        return result;
    }

    public void follow(int followerId, int followeeId) {
        followMap.putIfAbsent(followerId, new HashSet<>());
        followMap.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (followMap.containsKey(followerId)) {
            followMap.get(followerId).remove(followeeId);
        }
    }
}