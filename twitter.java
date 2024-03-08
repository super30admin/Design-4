// Time Complexity :O(k log n)
// Space Complexity :O(n)
// Did this code successfully run on Leetcode :yes
// Any problem you faced while coding this :no

public class Twitter {
    private HashMap<Integer, HashSet<Integer>> follows;
    private HashMap<Integer, LinkedList<Tweet>> tweets;
    private int timestamp;
    private class Tweet implements Comparable<Tweet> {
        private int tweetId;
        private int ts;
        private int userId;
        public Tweet(int tweetId, int userId, int timestamp) {
            this.tweetId = tweetId;
            this.ts = timestamp;
            this.userId = userId;
        }
        @Override
        public int compareTo(Tweet t2) {
            return t2.ts - this.ts;
        }
    }
    public Twitter() {
        follows = new HashMap<Integer, HashSet<Integer>>();
        tweets = new HashMap<Integer, LinkedList<Tweet>>();
        timestamp = 0;
    }
    public void postTweet(int userId, int tweetId) {
        if (!tweets.containsKey(userId)) {
            tweets.put(userId, new LinkedList<Tweet>());
        }
        tweets.get(userId).add(0, new Tweet(tweetId, userId, timestamp++));
        if (!follows.containsKey(userId)) {
            follows.put(userId, new HashSet());
            follows.get(userId).add(userId);
        }
    }
     public List<Integer> getNewsFeed(int userId) {
        List<Integer> feed = new LinkedList<Integer>();
        PriorityQueue<Tweet> q = new PriorityQueue<Tweet>();
        if (!follows.containsKey(userId)) {
            return feed;
        }
        HashSet<Integer> followed = follows.get(userId);
        HashMap<Integer, Integer> count = new HashMap<Integer, Integer>();
        for (Integer i:followed) {
            if (tweets.containsKey(i)) {
                Tweet t = tweets.get(i).get(0);
                q.add(t);
                count.put(t.userId, 1);
            }
        }
        while (q.size() > 0 && feed.size() < 10) {
            Tweet t = q.poll();
            feed.add(t.tweetId);
            int next = count.get(t.userId);
            count.put(t.userId, next + 1);
            if (next < tweets.get(t.userId).size()) {
                q.add(tweets.get(t.userId).get(next));
            }
        }
        return feed;
    }
    public void follow(int followerId, int followeeId) {
        if (!follows.containsKey(followerId)) {
            follows.put(followerId, new HashSet());
            follows.get(followerId).add(followerId);
        }
        follows.get(followerId).add(followeeId);
    } public void unfollow(int followerId, int followeeId) {
        if (followerId == followeeId) {
            return;
        }
        if (follows.containsKey(followerId)) {
            follows.get(followerId).remove(followeeId);
        }
    }
}