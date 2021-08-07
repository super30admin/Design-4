//Time Complexity : postTweet: O(1), getNewsFeed: O(n), follow: O(1), unfollow: O(1)
// Space Complexity : O(n)
class Tweet {
    int tweetId;
    int tweetCreatedAt;

    public Tweet(int tweetId, int tweetCreatedAt) {
        this.tweetId = tweetId;
        this.tweetCreatedAt = tweetCreatedAt;
    }
}

class Twitter {

    Map<Integer, List<Tweet>> tweetMap;
    Map<Integer, Set<Integer>> followeesMap;
    int timestamp;

    /**
     * Initialize your data structure here.
     */
    public Twitter() {

        tweetMap = new HashMap<>();
        followeesMap = new HashMap<>();
        timestamp = 0;
    }

    /**
     * Compose a new tweet.
     */
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if (!tweetMap.containsKey(userId)) {
            tweetMap.put(userId, new ArrayList<Tweet>());
        }
        Tweet tweet = new Tweet(tweetId, timestamp++);
        tweetMap.get(userId).add(tweet);
    }

    /**
     * Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
     */
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<Tweet>((x, y) -> x.tweetCreatedAt - y.tweetCreatedAt);
        Set<Integer> followed = followeesMap.get(userId);
        if (followed != null) {
            for (Integer followId : followed) {
                List<Tweet> tweets = tweetMap.get(followId);
                if (tweets != null) {
                    for (Tweet tweet : tweets) {
                        pq.add(tweet);
                        if (pq.size() > 10) {
                            pq.poll();
                        }
                    }
                }

            }
        }

        List<Integer> result = new ArrayList<>();
        while (!pq.isEmpty()) {
            result.add(0, pq.poll().tweetId);
        }
        return result;
    }

    /**
     * Follower follows a followee. If the operation is invalid, it should be a no-op.
     */
    public void follow(int followerId, int followeeId) {
        if (!followeesMap.containsKey(followerId)) {
            followeesMap.put(followerId, new HashSet<>());
        }
        followeesMap.get(followerId).add(followeeId);
    }

    /**
     * Follower unfollows a followee. If the operation is invalid, it should be a no-op.
     */
    public void unfollow(int followerId, int followeeId) {
        if (followeesMap.containsKey(followerId) && followerId != followeeId) {
            followeesMap.get(followerId).remove(followeeId);
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