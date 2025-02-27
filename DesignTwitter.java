import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

// TC: O(n log K) where n is the number of tweets from users and its followers, 
// k is the number of tweets to return
// SC: O(U + T), where U is the total number of users and T is the total number of tweets
class Tweet {
    int tweetId, createdAt;

    public Tweet(int tweetId, int createdAt) {
        this.tweetId = tweetId;
        this.createdAt = createdAt;
    }

}

class Twitter {

    Map<Integer, Set<Integer>> userMap;
    Map<Integer, List<Tweet>> tweetMap;
    int time;

    public Twitter() {
        userMap = new HashMap<>();
        tweetMap = new HashMap<>();
        time = 0;
    }

    // TC: O(1)
    public void postTweet(int userId, int tweetId) {
        if (!tweetMap.containsKey(userId)) {
            tweetMap.put(userId, new ArrayList<>());
        }
        tweetMap.get(userId).add(new Tweet(tweetId, time++));
    }

    // TC: O(N log K)
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> q = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);
        if (tweetMap.containsKey(userId)) {
            for (Tweet tweet : tweetMap.get(userId)) {
                q.offer(tweet);
                if (q.size() > 10)
                    q.poll();
            }
        }
        Set<Integer> followers = userMap.get(userId);
        if (followers != null) {
            for (int follower : followers) {
                List<Tweet> allTweets = tweetMap.get(follower);
                if (allTweets != null) {
                    for (Tweet tweet : allTweets) {
                        q.offer(tweet);
                        if (q.size() > 10)
                            q.poll();
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while (!q.isEmpty()) {
            result.add(0, q.poll().tweetId);
        }
        return result;

    }

    // TC: O(1)
    void follow(int followerId, int followeeId) {
        if (!userMap.containsKey(followerId))
            userMap.putIfAbsent(followerId, new HashSet<>());
        userMap.get(followerId).add(followeeId);
    }

    // TC: O(1)
    void unfollow(int followerId, int followeeId) {
        if (!userMap.containsKey(followerId))
            return;
        if (userMap.get(followerId).contains(followeeId))
            userMap.get(followerId).remove(followeeId);
    }

}

public class DesignTwitter {
    public static void main(String[] args) {
        Twitter obj = new Twitter();
        obj.postTweet(1, 5);
        for (Integer feeds : obj.getNewsFeed(1)) {
            System.out.print(feeds + " ");
        }
        System.out.println();
        obj.follow(1, 2);
        obj.postTweet(2, 6);
        for (Integer feeds : obj.getNewsFeed(1)) {
            System.out.print(feeds + " ");
        }
        System.out.println();
        obj.unfollow(1, 2);
        for (Integer feeds : obj.getNewsFeed(1)) {
            System.out.print(feeds + " ");
        }
        System.out.println();
    }
}