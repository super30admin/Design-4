// Time Complexity : O(mn)
// Space Complexity : O(m + n)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no

import java.util.*;

class Twitter {
    // custom DS for Tweet
    public class Tweets {
        int Tid;
        int createdAt;

        public Tweets(int Tid, int createdAt) {
            this.Tid = Tid;
            this.createdAt = createdAt;
        }
    }

    // Map for user and its followers
    private HashMap<Integer, Set<Integer>> following;
    // Map for user and list of tweets of its followers and
    private HashMap<Integer, List<Tweets>> tweets;
    // count for createdAt tweet;
    private int count = 0;

    public Twitter() {
        following = new HashMap<>();
        tweets = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        // add userId in to the self following map
        follow(userId, userId);
        // add tweet into tweets map;
        if (!tweets.containsKey(userId)) {
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweets(tweetId, count++));
    }

    public List<Integer> getNewsFeed(int userId) {
        // customized priority Queue
        PriorityQueue<Tweets> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);
        if (following.containsKey(userId)) {
            // get all the following userId
            for (int uid : following.get(userId)) {
                // get all the tweets posted by user
                if (tweets.containsKey(uid)) {
                    for (Tweets tweet : tweets.get(uid)) {
                        // add all the tweets in pq
                        pq.add(tweet);
                        // if pq size increase than 10 poll element from the pq
                        if (pq.size() > 10) {
                            pq.poll();
                        }
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        // while pq is not empty add it into the result
        while (!pq.isEmpty()) {
            // add it into the front of the result
            result.add(0, pq.poll().Tid);
        }
        return result;
    }

    public void follow(int followerId, int followeeId) {
        // if following table contains followerId we directly add it into the set
        // or make a new set corresponding to followerId and add it to them.
        if (!following.containsKey(followerId)) {
            following.put(followerId, new HashSet<>());
        }
        following.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        // check if following contains followerId than delete followeeId from the set.
        if (following.containsKey(followerId)) {
            following.get(followerId).remove(followeeId);

        }
    }

    public static void main(String[] args) {
        Twitter twitter = new Twitter();
        twitter.postTweet(1, 5); // User 1 posts a new tweet (id = 5).
        twitter.getNewsFeed(1); // User 1's news feed should return a list with 1 tweet id -> [5]. return [5]
        twitter.follow(1, 2); // User 1 follows user 2.
        twitter.postTweet(2, 6); // User 2 posts a new tweet (id = 6).
        System.out.println(twitter.getNewsFeed(1)); // User 1's news feed should return a list with 2 tweet ids ->
                                                    // [6,5]. Tweet id
        // 6 should precede tweet id 5 because it is posted after tweet id 5.
        twitter.unfollow(1, 2); // User 1 unfollows user 2.
        System.out.println(twitter.getNewsFeed(1)); // User 1's news feed should return a list with 1 tweet id ->
                                                    // [5],since user 1 is no longer following user 2.
    }
}