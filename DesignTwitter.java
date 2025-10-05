import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

//TC: postTweet, follow, unfollow: O(1), get news feed: O(n × 10 × log 10) = O(n log 10) = effectively O(n)
//SC: O(tweets + followers + users)
public class DesignTwitter {

    static class Tweet {
        private final int id;
        private final int timestamp;

        private Tweet(int id, int timestamp) {
            this.id = id;
            this.timestamp = timestamp;
        }
    }

    private int time;
    private final Map<Integer, Set<Integer>> follows;
    private final Map<Integer, List<Tweet>> tweets;

    public DesignTwitter() {
        this.follows = new HashMap<>();
        this.tweets = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        this.tweets.computeIfAbsent(userId, u -> new ArrayList<>()).add(new Tweet(tweetId, time));
        this.time++;
    }

    public List<Integer> getNewsFeed(int userId) {
        final PriorityQueue<Tweet> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.timestamp));
        final Set<Integer> followers = this.follows.get(userId);
        final List<Integer> feed = new ArrayList<>();

        if (followers == null || followers.isEmpty()) return feed;
        for (Integer follower: followers) {
            final List<Tweet> fTweets = this.tweets.get(follower);
            if (fTweets == null || fTweets.isEmpty()) continue;

            for (int i = fTweets.size() - 1; i >= fTweets.size() - 10 && i >= 0; i--) {
                pq.offer(fTweets.get(i));

                if (pq.size() > 10) pq.poll();
            }
        }

        while (!pq.isEmpty()) feed.addFirst(pq.poll().id);

        return feed;
    }

    public void follow(int followerId, int followeeId) {
        this.follows.computeIfAbsent(followerId, f -> new HashSet<>()).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (this.follows.containsKey(followerId)) {
            this.follows.get(followerId).remove(followeeId);
        }
    }
}
