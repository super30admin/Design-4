// ## Problem 1: Design Twitter (https://leetcode.com/problems/design-twitter/)

// postTweet
// Time Complexity : O(1)
// Space Complexity : O(n)

// getNewsFeed
// Time Complexity : O(Nk logk) k is avg length of all tweets of each user it follows, log k for sorting
// Space Complexity : O(NK) Array which stores all tweets

// follow
// Time Complexity : O(1)
// Space Complexity : O(n)

// unfollow
// Time Complexity : O(1)
// Space Complexity : O(n)

// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach

class Tweets {
    constructor(tweetId, timeStamp) {
        this.tweetId = tweetId;
        this.timeStamp = timeStamp;
    }
}

class Twitter {

    constructor() {
        // Global time to see which tweet was postest first
        this.time = 0;
        // Maps userId and all members it follows including self. The values is a hashset
        this.followMap = new Map();
        // All tweets(containind id and timestamp) of a user
        this.tweetMap = new Map();
    }

    /** 
     * @param {number} userId 
     * @param {number} tweetId
     * @return {void}
     */
    postTweet = function (userId, tweetId) {
        // Create a new tweet and new timestamp, add it to usersid - tweets map
        let tweet = new Tweets(tweetId, this.time++);
        if (!this.tweetMap.has(userId)) {
            this.tweetMap.set(userId, []);
        }
        let userTweets = this.tweetMap.get(userId);
        userTweets.push(tweet);
        this.tweetMap.set(userId, userTweets);
    };

    /** 
     * @param {number} userId
     * @return {number[]}
     */
    getNewsFeed = function (userId) {
        // Loop through all tweets of all followers of user and add to PQ which is sorted by timestamp
        this.follow(userId, userId)
        let allFollowees = this.followMap.get(userId);
        let resultArr = [];
        // Iterate hashset
        allFollowees.forEach((followee) => {
            if (this.tweetMap.get(followee)) {
                resultArr = [...resultArr, ...this.tweetMap.get(followee)];
            }
        })

        // Get 1st 10 and return
        let i = 0;
        let ans = [];
        // Sort by timestamp in descending order
        resultArr.sort((a, b) => b.timeStamp - a.timeStamp);
        // Return 1st 10
        while (i < 10 && resultArr.length > 0) {
            ans.push(resultArr.shift().tweetId)
            i++;
        }
        return ans;
    };

    /** 
     * @param {number} followerId 
     * @param {number} followeeId
     * @return {void}
     */
    follow = function (followerId, followeeId) {
        if (!this.followMap.has(followerId)) {
            this.followMap.set(followerId, new Set());
        }
        let set = this.followMap.get(followerId);
        set.add(followeeId);
        this.followMap.set(followerId, set);
    };

    /** 
     * @param {number} followerId 
     * @param {number} followeeId
     * @return {void}
     */
    unfollow = function (followerId, followeeId) {
        if (this.followMap.has(followerId) && followerId !== followeeId) {
            let set = this.followMap.get(followerId);
            set.delete(followeeId);
            this.followMap.set(followerId, set);
        }
    };

}

/** 
 * Your Twitter object will be instantiated and called as such:
 * var obj = new Twitter()
 * obj.postTweet(userId,tweetId)
 * var param_2 = obj.getNewsFeed(userId)
 * obj.follow(followerId,followeeId)
 * obj.unfollow(followerId,followeeId)
 */