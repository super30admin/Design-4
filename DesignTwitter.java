
// Time Complexity :
// postTweet: O(1) to append a tweet and (for new users) O(1) to create user & self-follow.
// getNewsFeed: Let F be the number of followees (including self). We examine up to the last 10 tweets per followee, so at most 10F pushes to a size‑≤10 min-heap → O(10F · log 10) = O(F). The final extraction of up to 10 tweets is O(10 · log 10) = O(1).
// follow / unfollow: O(1) average for HashSet add/remove and possible O(1) user creation.

// Space Complexity :
// Persistent storage: O(U + T) where U is number of users and T is total number of tweets stored in all lists.
// Per getNewsFeed call: O(10) = O(1) auxiliary for the min-heap and result list (capped to 10).

Approach:

// Maintain a User object per user with a set of followees and a list of their tweets, each tweet stamped with a global increasing timeStamp.
// When posting a tweet, create the user if needed, ensure they self-follow, and append a (tweetId, time) record.
// To generate the news feed, iterate all followees of the user and push up to the last 10 tweets from each followee into a min-heap ordered by time, trimming the heap to size 10 so it keeps only the most recent tweets overall.
// After collecting, repeatedly pop from the heap (oldest first) and prepend ids to the result to produce most-recent-first order.
// Following adds the follow relationship (and auto-creates users as needed), and unfollowing removes it.

class Twitter {
    int timeStamp;
    HashMap<Integer, User> userMap;

    class User {
        int userId;
        HashSet<Integer> followees;
        List<Tweet> tweets;

        public User(int userId) {
            this.userId = userId;
            this.followees = new HashSet<>();
            this.tweets = new ArrayList<>();
        }
    }

    class Tweet {
        int tweetId;
        int time;

        public Tweet(int tweetId, int time) {
            this.tweetId = tweetId;
            this.time = time;
        }
    }

    public Twitter() {
        this.userMap = new HashMap<>();
        this.timeStamp = 0;
    }

    public void postTweet(int userId, int tweetId) {
        Tweet newTweet = new Tweet(tweetId, timeStamp++);
        if (userMap.containsKey(userId)) {
            userMap.get(userId).tweets.add(newTweet);
        } else {
            User newUser = new User(userId);
            newUser.followees.add(userId);
            newUser.tweets.add(newTweet);
            userMap.put(userId, newUser);
        }
    }

    public List<Integer> getNewsFeed(int userId) {
        List<Integer> result = new ArrayList<>();
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.time - b.time);
        if (!userMap.containsKey(userId))
            return result;
        User user = userMap.get(userId);
        HashSet<Integer> followees = user.followees;
        if (followees != null) {
            for (Integer followee : followees) {
                List<Tweet> tweets = userMap.get(followee).tweets;
                if (tweets != null) {
                    int n = tweets.size();
                    for (int i = n - 1; i >= n - 10 && i > -1; i--) {
                        Tweet tweet = tweets.get(i);
                        pq.add(tweet);
                        if (pq.size() > 10)
                            pq.poll();
                    }
                }
            }
        }
        while (!pq.isEmpty()) {
            result.add(0, pq.poll().tweetId);
        }
        return result;
    }

    public void follow(int followerId, int followeeId) {
        if (!userMap.containsKey(followerId)) {
            User newUser = new User(followerId);
            newUser.followees.add(followerId);
            newUser.followees.add(followeeId);
            userMap.put(followerId, newUser);
        } else {
            userMap.get(followerId).followees.add(followerId);
            userMap.get(followerId).followees.add(followeeId);
        }
        if (!userMap.containsKey(followeeId)) {
            User newUser = new User(followeeId);
            userMap.put(followeeId, newUser);
        }
    }

    public void unfollow(int followerId, int followeeId) {
        if (userMap.containsKey(followerId)) {
            userMap.get(followerId).followees.remove(followeeId);
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
