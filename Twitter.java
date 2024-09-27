/**
 * Design a simplified version of Twitter where users can post tweets,
 * follow/unfollow another user and is able to see the 10 most recent tweets in
 * the user's news feed. Your design should support the following methods:
 * 
 * 1. postTweet(userId, tweetId): Compose a new tweet. 
 * 2. getNewsFeed(userId): Retrieve the 10 most recent tweet ids in the user's news feed.
 *      Each item in the news feed must be posted by users who the user followed or by the user
 *      herself. Tweets must be ordered from most recent to least recent.
 * 3. follow(followerId, followeeId): Follower follows a followee.
 * 4. unfollow(followerId, followeeId): Follower unfollows a followee.
 * 
 * 
 * Implementation in Progress.
 */
class Twitter {

    Set<User> users;

    /** Initialize your data structure here. */
    public Twitter() {
        users = new HashSet<>();
    }

    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        // user does not exist?
        if (!users.contains(new User(userId))) {

        }
    }

    /**
     * Retrieve the 10 most recent tweet ids in the user's news feed. Each item in
     * the news feed must be posted by users who the user followed or by the user
     * herself. Tweets must be ordered from most recent to least recent.
     */
    public List<Integer> getNewsFeed(int userId) {

    }

    /**
     * Follower follows a followee. If the operation is invalid, it should be a
     * no-op.
     */
    public void follow(int followerId, int followeeId) {

    }

    /**
     * Follower unfollows a followee. If the operation is invalid, it should be a
     * no-op.
     */
    public void unfollow(int followerId, int followeeId) {

    }
}

class User {
    int userId;
    Set<User> followers;
    Set<Tweet> tweets;

    User(int userId) {
        this.userId = userId;
        followers = new HashSet<>();
        tweets = new HashSet<>();
    }

    @Override
    public int hashCode() {
        return userId;
    }
}

class Tweet {
    int tweetId;

    Tweet(int tweetId) {
        this.tweetId = tweetId;
    }

    @Override
    public int hashCode() {
        return tweetId;
    }
}

/**
 * Your Twitter object will be instantiated and called as such: Twitter obj =
 * new Twitter(); obj.postTweet(userId,tweetId); List<Integer> param_2 =
 * obj.getNewsFeed(userId); obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */