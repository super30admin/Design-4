// Time Complexities:
// follow() - O(1)
// unfollow() - O(1)
// postTweet() - O(1)
// getNewsFeed() - O(t), where t is the total number of tweets of all the users that the current user is following and the current user itself.

// Space Complexity: O(U⋅k+U⋅n), where U is the number of users, k is the average number of followees per user, and n is the average number of tweets per user.

import java.util.*;

class Twitter {

    class Tweet {
        int tweetId;
        int createdAt;

        public Tweet(int tweetId, int createdAt) {
            this.tweetId = tweetId;
            this.createdAt = createdAt;
        }
    }

    HashMap<Integer, HashSet<Integer>> userMap;
    HashMap<Integer, List<Tweet>> tweetMap;
    int time;

    public Twitter() {
        userMap = new HashMap<>();
        tweetMap = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        if (!tweetMap.containsKey(userId)) {
            tweetMap.put(userId, new ArrayList<>());
        }
        Tweet tweet = new Tweet(tweetId, time++);
        tweetMap.get(userId).add(tweet);
    }

    public List<Integer> getNewsFeed(int userId) {
        HashSet<Integer> totalEntries = new HashSet<>(userMap.getOrDefault(userId, new HashSet<>()));
        totalEntries.add(userId);

        PriorityQueue<Tweet> minHeap = new PriorityQueue<>((t1, t2) -> (t1.createdAt - t2.createdAt));
        int size = 0;

        for (int uId : totalEntries) {
            for (Tweet tweet : tweetMap.getOrDefault(uId, new ArrayList<>())) {
                if (size < 10) {
                    size++;
                } else {
                    if (tweet.createdAt < minHeap.peek().createdAt)
                        continue;
                    minHeap.poll();
                }
                minHeap.offer(tweet);
            }
        }

        List<Integer> newsFeed = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            newsFeed.add(0, minHeap.poll().tweetId);
        }

        return newsFeed;

    }

    public void follow(int followerId, int followeeId) {
        if (!userMap.containsKey(followerId)) {
            userMap.put(followerId, new HashSet<>());
        }
        userMap.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (!userMap.containsKey(followerId))
            return;
        userMap.get(followerId).remove(followeeId);
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