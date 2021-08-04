// Time Complexity : O(nlogk)
// Space Complexity : O(k)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No
class Twitter {
    class Tweet {
        int tweetId;
        int createdAt;

        public Tweet(int tweetId, int createdAt) {
            this.tweetId = tweetId;
            this.createdAt = createdAt;
        }
    }

    Map<Integer, List<Tweet>> tweetMap;
    Map<Integer, Set<Integer>> userMap;
    int timestamp;

    /**
     * Initialize your data structure here.
     */
    public Twitter() {
        userMap = new HashMap<>();
        tweetMap = new HashMap<>();
        timestamp = 0;
    }

    /**
     * Compose a new tweet.
     */
    public void postTweet(int userId, int tweetId) {

        if (!tweetMap.containsKey(userId)) {
            tweetMap.put(userId, new ArrayList<>());
            follow(userId, userId);
        }
        Tweet tweet = new Tweet(tweetId, timestamp++);
        tweetMap.get(userId).add(tweet);
    }

    /**
     * Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
     */
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pqueue = new PriorityQueue<>((a, b) -> (a.createdAt - b.createdAt));
        Set<Integer> followed = userMap.get(userId);
        List<Integer> newsFeed = new ArrayList<>();
        if (followed != null) {
            for (Integer fId : followed) {
                List<Tweet> tweets = tweetMap.get(fId);
                if (tweets != null) {
                    for (Tweet tweet : tweets) {
                        pqueue.add(tweet);
                        if (pqueue.size() > 10) {
                            pqueue.poll();
                        }
                    }
                }
            }
        }

        while (!pqueue.isEmpty()) {
            newsFeed.add(0, pqueue.poll().tweetId);
        }
        return newsFeed;
    }

    /**
     * Follower follows a followee. If the operation is invalid, it should be a no-op.
     */
    public void follow(int followerId, int followeeId) {
        if (!userMap.containsKey(followerId)) {
            userMap.put(followerId, new HashSet<>());
        }
        userMap.get(followerId).add(followeeId);
    }

    /**
     * Follower unfollows a followee. If the operation is invalid, it should be a no-op.
     */
    public void unfollow(int followerId, int followeeId) {
        if (userMap.containsKey(followerId) && followerId != followeeId) {
            Set<Integer> followees = userMap.get(followerId);
            followees.remove(followeeId);
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