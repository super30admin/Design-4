// Time Complexity : O(M* N)
// Space Complexity : O(M* N)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no

// Approach
// we create a custom class that has tid and time
// we make use of two hashmaps and pq

class Twitter {
    class Tweet {
        int tid;
        int createdAt;

        public Tweet(int tid, int createdAt) {
            this.tid = tid;
            this.createdAt = createdAt;
        }
    }

    int time;
    HashMap<Integer, HashSet<Integer>> followers;
    HashMap<Integer, List<Tweet>> tweets;

    public Twitter() {
        followers = new HashMap<>();
        tweets = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        Tweet t = new Tweet(tweetId, time++);
        if (!tweets.containsKey(userId)) {
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(t);
    }

    public List<Integer> getNewsFeed(int userId) {
        follow(userId, userId);
        HashSet<Integer> users = followers.get(userId);
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);
        for (Integer user : users) {
            List<Tweet> alltweets = tweets.get(user);
            if (alltweets != null) {
                for (Tweet tweet : alltweets) {
                    pq.add(tweet);
                    if (pq.size() > 10) {
                        pq.poll();
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

    public void follow(int followerId, int followeeId) {
        if (!followers.containsKey(followerId)) {
            followers.put(followerId, new HashSet<>());
        }
        followers.get(followerId).add(followeeId);

    }

    public void unfollow(int followerId, int followeeId) {
        if (followers.containsKey(followerId) && followerId != followeeId) {
            followers.get(followerId).remove(followeeId);
        }

    }
}
