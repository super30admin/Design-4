
// postTweet:
//   TC: O(1) - Each tweet is added in constant time.
//   SC: O(1) per tweet, overall O(n) where n is the number of tweets by a user.
//
// getNewsFeed:
//   TC: O(n log k) where n is the total number of tweets in the followed users' timelines, and k is the number of users followed plus 1 (for the user themselves).
//   SC: O(k) for the priority queue where k is the number of followed users plus 1.
//
// follow:
//   TC: O(1) - Each follow action is processed in constant time.
//   SC: O(1) per follow, overall O(f) where f is the number of follow relationships.
//
// unfollow:
//   TC: O(1) - Each unfollow action is processed in constant time.
//   SC: O(1) per unfollow, overall O(f) where f is the number of follow relationships.
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

    public static void main(String[] args) {
        Twitter twitter = new Twitter();

        twitter.postTweet(1, 5);
        System.out.println(twitter.getNewsFeed(1)); // [5]

        twitter.follow(1, 2);
        twitter.postTweet(2, 6);
        System.out.println(twitter.getNewsFeed(1)); // [6, 5]

        twitter.unfollow(1, 2);
        System.out.println(twitter.getNewsFeed(1)); // [5]
    }
}
