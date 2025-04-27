import java.util.*;

public class DesignTwitter {
    HashMap<Integer, HashSet<Integer>> followeeMap;
    HashMap<Integer, List<Tweet>> tweetMap;
    int time;

    class User {
        int userId;
        List<Integer> followees;
    }

    class Tweet {
        int tweetId;
        int timestamp;

        public Tweet(int tweetId, int time) {
            this.tweetId = tweetId;
            this.timestamp = time;
        }
    }

    public DesignTwitter() {
        this.followeeMap = new HashMap<>();
        this.tweetMap = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) { //TC: O(1) SC: O(n)
        if (!tweetMap.containsKey(userId)) {
            tweetMap.put(userId, new ArrayList<>());
        }

        Tweet tweet = new Tweet(tweetId, time++);
        tweetMap.get(userId).add(tweet);

        follow(userId, userId); //ensure user follows themselves

    }

    public List<Integer> getNewsFeed(int userId) { //TC:O(nlogk),n=users*tweets
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.timestamp - b.timestamp);
        List<Integer> res = new ArrayList<>();

        if (followeeMap.containsKey(userId)) {
            HashSet<Integer> followees = followeeMap.get(userId);

            for (int followee : followees) {
                if (tweetMap.containsKey(followee)) {
                    List<Tweet> tweets = tweetMap.get(followee);

                    for (Tweet tweet : tweets) {
                        pq.add(tweet);
                        if (pq.size() > 10) {
                            pq.poll();
                        }
                    }
                }
            }

            while (!pq.isEmpty()) {
                res.add(pq.poll().tweetId);
            }
            Collections.reverse(res);
        }

        return res;

    }

    public void follow(int followerId, int followeeId) { //TC: O(1)
        if (!followeeMap.containsKey(followerId)) {
            followeeMap.put(followerId, new HashSet<>());
        }
        followeeMap.get(followerId).add(followeeId);

    }

    public void unfollow(int followerId, int followeeId) { //TC: O(1)
        if (followeeMap.containsKey(followerId)) {
            Set<Integer> followeeSet = followeeMap.get(followerId);
            followeeSet.remove(followeeId);
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
