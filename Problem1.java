import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

class Twitter {

    int timestamp, feedsize;
    HashMap<Integer, List<Tweet>> userTweetMap;
    HashMap<Integer, Set<Integer>> userFollowMap;

    /** Initialize your data structure here. */
    public Twitter() {
        timestamp = 0;
        feedsize = 10;

        userTweetMap = new HashMap<>();
        userFollowMap = new HashMap<>();

    }

    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {

        isFirstTime(userId);
        List<Tweet> tweets = userTweetMap.get(userId);
        tweets.add(new Tweet(tweetId, timestamp++));

    }

    /**
     * Retrieve the 10 most recent tweet ids in the user's news feed. Each item in
     * the news feed must be posted by users who the user followed or by the user
     * herself. Tweets must be ordered from most recent to least recent.
     */
    public List<Integer> getNewsFeed(int userId) {

        isFirstTime(userId);
        Set<Integer> followees = userFollowMap.get(userId);

        List<Tweet> tweets = new LinkedList<>();

        for (Integer x : followees) {
            tweets.addAll(userTweetMap.get(x));
        }

        Collections.sort(tweets, (a, b) -> (b.createdAt - a.createdAt));

        Iterator<Tweet> it = tweets.iterator();
        List<Integer> recent = new ArrayList<>();
        int count = feedsize;
        while (count > 0 && it.hasNext()) {
            recent.add(it.next().tweetId);
            count--;
        }

        return recent;

    }

    /**
     * Follower follows a followee. If the operation is invalid, it should be a
     * no-op.
     */
    public void follow(int followerId, int followeeId) {
        isFirstTime(followerId);
        isFirstTime(followeeId);

        Set<Integer> followees = userFollowMap.get(followerId);
        followees.add(followeeId);
    }

    /**
     * Follower unfollows a followee. If the operation is invalid, it should be a
     * no-op.
     */
    public void unfollow(int followerId, int followeeId) {
        isFirstTime(followerId);
        isFirstTime(followeeId);

        Set<Integer> followees = userFollowMap.get(followerId);
        if (followerId != followeeId) {
            followees.remove(followeeId);
        }

    }

    private void isFirstTime(int userId) {
        Set<Integer> followees = userFollowMap.get(userId);
        if (followees == null) {
            followees = new HashSet<Integer>();
            followees.add(userId);
            userFollowMap.put(userId, followees);
        }

        List<Tweet> tweets = userTweetMap.get(userId);
        if (tweets == null) {
            tweets = new LinkedList<>();
            userTweetMap.put(userId, tweets);
        }
    }

}

class Tweet {
    int tweetId;
    int createdAt;

    Tweet(int tweetId, int createdAt) {
        this.tweetId = tweetId;
        this.createdAt = createdAt;
    }
}
