/*
Time: follow, unfollow, postTweet -> O(1) and getNewsFeed -> nlogk where n=users, k=tweets
Space: O(Users)
Did this code successfully run on Leetcode : Yes
Any problem you faced while coding this : None
*/
class Twitter {

    class Tweet {
        int tid;
        int createdAt;

        public Tweet(int tid, int createdAt) {
            this.tid = tid;
            this.createdAt = createdAt;
        }
    }

    HashMap<Integer, HashSet> followers;
    HashMap<Integer, List<Tweet>> tweet;
    int time;

    /** Initialize your data structure here. */
    public Twitter() {
        followers = new HashMap<>();// follow, unfollow
        tweet = new HashMap<>();// Tweets

    }

    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if (!tweet.containsKey(userId)) // is user present ?
        {
            tweet.put(userId, new ArrayList<>());
        }
        tweet.get(userId).add(new Tweet(tweetId, time++));

    }

    /**
     * Retrieve the 10 most recent tweet ids in the user's news feed. Each item in
     * the news feed must be posted by users who the user followed or by the user
     * herself. Tweets must be ordered from most recent to least recent.
     */
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt); // min heap
        HashSet<Integer> followersSet = followers.get(userId);
        if (followersSet != null) {
            for (int user : followersSet) {
                List<Tweet> tweets = tweet.get(user);
                if (tweets != null) {
                    for (Tweet tw : tweets) {
                        pq.add(tw);
                        if (pq.size() > 10) {
                            pq.poll();
                        }
                    }
                }
            }
        }

        List<Integer> result = new ArrayList<>();
        while (!pq.isEmpty()) {
            result.add(0, pq.poll().tid);
        }

        return result;

    }

    /**
     * Follower follows a followee. If the operation is invalid, it should be a
     * no-op.
     */
    public void follow(int followerId, int followeeId) {
        if (!followers.containsKey(followerId)) {
            followers.put(followerId, new HashSet<>());
        }
        followers.get(followerId).add(followeeId);

    }

    /**
     * Follower unfollows a followee. If the operation is invalid, it should be a
     * no-op.
     */
    public void unfollow(int followerId, int followeeId) {
        if (followers.containsKey(followerId)) {
            if (followers.get(followerId).contains(followeeId)) {
                followers.get(followerId).remove(followeeId);
            }
        }
    }
}