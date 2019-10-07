# Design-4

## Problem 1: Design Twitter (https://leetcode.com/problems/design-twitter/)

Design a simplified version of Twitter where users can post tweets, follow/unfollow another user and is able to see the 10 most recent tweets in the user's news feed. Your design should support the following methods:

postTweet(userId, tweetId): Compose a new tweet.
getNewsFeed(userId): Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
follow(followerId, followeeId): Follower follows a followee.
unfollow(followerId, followeeId): Follower unfollows a followee.
Example:

Twitter twitter = new Twitter();

// User 1 posts a new tweet (id = 5).
twitter.postTweet(1, 5);

// User 1's news feed should return a list with 1 tweet id -> [5].
twitter.getNewsFeed(1);

// User 1 follows user 2.
twitter.follow(1, 2);

// User 2 posts a new tweet (id = 6).
twitter.postTweet(2, 6);

// User 1's news feed should return a list with 2 tweet ids -> [6, 5].
// Tweet id 6 should precede tweet id 5 because it is posted after tweet id 5.
twitter.getNewsFeed(1);

// User 1 unfollows user 2.
twitter.unfollow(1, 2);

// User 1's news feed should return a list with 1 tweet id -> [5],
// since user 1 is no longer following user 2.
twitter.getNewsFeed(1);


## Problem 2: Skip Iterator(https://leetcode.com/discuss/interview-question/341818/Google-or-Onsite-or-Skip-Iterator)

Design a SkipIterator that supports a method skip(int val). When it is called the next element equals val in iterator sequence should be skipped. If you are not familiar with Iterators check similar problems.

    class SkipIterator implements Iterator<Integer> {


	public SkipIterator(Iterator<Integer> it) {

	}


	public boolean hasNext() {

	}


	public Integer next() {

	}


	/**

	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.

	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.

	*/ 

	public void skip(int val) {

	}

}
Example:

SkipIterator itr = new SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10]);

itr.hasNext(); // true

itr.next(); // returns 2

itr.skip(5);

itr.next(); // returns 3

itr.next(); // returns 6 because 5 should be skipped

itr.next(); // returns 5

itr.skip(5);

itr.skip(5);

itr.next(); // returns 7

itr.next(); // returns -1

itr.next(); // returns 10

itr.hasNext(); // false

itr.next(); // error
