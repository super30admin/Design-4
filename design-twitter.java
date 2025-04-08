// TC: O(1) for follow/unfollow, O(N) for getNewsFeed
// SC: O(N × (M + T))
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

class Twitter {

    class Tweet {
        int id, createdAt;

        public Tweet(int id, int createdAt) {
            this.id = id;
            this.createdAt = createdAt;
        }
    }

    // Data structures
    private Map<Integer, List<Tweet>> tweets;
    private Map<Integer, Set<Integer>> followee;
    private int time; // global time counter for tweets

    public Twitter() {
        this.tweets = new HashMap<>();
        this.followee = new HashMap<>();
        this.time = 0;
    }

    public void postTweet(int userId, int tweetId) {
        follow(userId, userId); // Ensure user follows themselves

        tweets.putIfAbsent(userId, new ArrayList<>());
        tweets.get(userId).add(new Tweet(tweetId, ++time));
    }

    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt); // min-heap of tweets

        Set<Integer> followeeSet = followee.get(userId);
        if (followeeSet != null) {
            for (int uid : followeeSet) {
                List<Tweet> fTweets = tweets.get(uid);
                if (fTweets != null) {
                    for (Tweet fTweet : fTweets) {
                        pq.add(fTweet);
                        if (pq.size() > 10) pq.poll(); // keep only top 10 recent tweets
                    }
                }
            }
        }

        List<Integer> result = new ArrayList<>();
        while (!pq.isEmpty()) {
            result.add(0, pq.poll().id); // Add tweets from latest to oldest
        }
        return result;
    }

    public void follow(int followerId, int followeeId) {
        followee.putIfAbsent(followerId, new HashSet<>());
        followee.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (followerId != followeeId && followee.containsKey(followerId)) {
            followee.get(followerId).remove(followeeId);
        }
    }
}
