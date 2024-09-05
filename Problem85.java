class Twitter {
    private static int timestamp = 0; // A global timestamp to track the order of tweets
    private Map<Integer, Set<Integer>> following; // Map of users and who they follow
    private Map<Integer, List<Tweet>> tweets; // Map of users and their tweets

    private class Tweet {
        int id, time;

        public Tweet(int id, int time) {
            this.id = id;
            this.time = time;
        }
    }

    public Twitter() {
        following = new HashMap<>();
        tweets = new HashMap<>();
    }

    // Post a tweet for the user
    public void postTweet(int userId, int tweetId) {
        if (!tweets.containsKey(userId)) {
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId, timestamp++));
    }

    // Get the 10 most recent tweets from the user's feed
    public List<Integer> getNewsFeed(int userId) {
        List<Tweet> recentTweets = new ArrayList<>();
        Set<Integer> users = following.getOrDefault(userId, new HashSet<>());
        users.add(userId); // The user sees their own tweets too

        for (int user : users) {
            List<Tweet> userTweets = tweets.getOrDefault(user, new ArrayList<>());
            recentTweets.addAll(userTweets);
        }

        // Sort tweets by timestamp in descending order to get the most recent ones
        Collections.sort(recentTweets, (a, b) -> b.time - a.time);

        // Get the 10 most recent tweet ids
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < Math.min(10, recentTweets.size()); i++) {
            result.add(recentTweets.get(i).id);
        }
        return result;
    }

    // Follow a user
    public void follow(int followerId, int followeeId) {
        following.putIfAbsent(followerId, new HashSet<>());
        following.get(followerId).add(followeeId);
    }

    // Unfollow a user
    public void unfollow(int followerId, int followeeId) {
        if (following.containsKey(followerId) && followerId != followeeId) {
            following.get(followerId).remove(followeeId);
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
