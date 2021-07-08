// Time Complexity : For get news feed, the time complexity is O(nlogn) because of the sort
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

/**
 * Initialize your data structure here.
 */
class Tweet {
    tweetId;
    createdAt;
    constructor(tweetId, createdAt) {
        this.tweetId = tweetId;
        this.createdAt = createdAt;
    }
}
var Twitter = function() {
    this.timestamp = 0;
    this.feedsize = 10;
    this.userFollowsMap = new Map;
    this.userTweetsMap = new Map;
};

Twitter.prototype.isFirstTime = function(userId) {
    if (!this.userFollowsMap.has(userId)) {
        followees = new Set;
        followees.add(userId);
        this.userFollowsMap.set(userId, followees);
    }

    if (!this.userTweetsMap.has(userId)) {
        this.userTweetsMap.set(userId, []);
    }
}

/**
 * Compose a new tweet. 
 * @param {number} userId 
 * @param {number} tweetId
 * @return {void}
 */
Twitter.prototype.postTweet = function(userId, tweetId) {
    this.isFirstTime(userId);
    const tweets = this.userTweetsMap.get(userId);
    tweets.push(new Tweet(tweetId, ++this.timestamp));
};

/**
 * Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. 
 * @param {number} userId
 * @return {number[]}
 */
Twitter.prototype.getNewsFeed = function(userId) {
    this.isFirstTime(userId);
    let feeds = [];

    let followees = this.userFollowsMap.get(userId);
    let recentFeed = [];

    for (const followee of followees) {
        feeds.push(...this.userTweetsMap.get(followee));
    }

    feeds = feeds.sort(function(a, b) {
        return b.createdAt - a.createdAt;
    });

    for (let i = 0; i < Math.min(this.feedsize, feeds.length); i++) {
        if (!feeds[i] || feeds[i].tweetId == 0) continue;
        recentFeed.push(feeds[i].tweetId);
    }
    return recentFeed;
};

/**
 * Follower follows a followee. If the operation is invalid, it should be a no-op. 
 * @param {number} followerId 
 * @param {number} followeeId
 * @return {void}
 */
Twitter.prototype.follow = function(followerId, followeeId) {
    this.isFirstTime(followerId);
    this.isFirstTime(followeeId);
    const followees = this.userFollowsMap.get(followerId);
    followees.add(followeeId);
};

/**
 * Follower unfollows a followee. If the operation is invalid, it should be a no-op. 
 * @param {number} followerId 
 * @param {number} followeeId
 * @return {void}
 */
Twitter.prototype.unfollow = function(followerId, followeeId) {
    this.isFirstTime(followerId);
    this.isFirstTime(followeeId);
    const followees = this.userFollowsMap.get(followerId);
    if (followerId != followeeId) {
        followees.delete(followeeId);
    }
};

/** 
 * Your Twitter object will be instantiated and called as such:
 * var obj = new Twitter()
 * obj.postTweet(userId,tweetId)
 * var param_2 = obj.getNewsFeed(userId)
 * obj.follow(followerId,followeeId)
 * obj.unfollow(followerId,followeeId)
 */
