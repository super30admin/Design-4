//Time Complexity : postTweet: O(1), getNewsFeed: O(n), follow: O(1), unfollow: O(1)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No
package problem1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class Twitter {
	Map<Integer, List<Tweet>> tweetMap;
	Map<Integer, Set<Integer>> followeeMap;
	int timestamp;

	/** Initialize your data structure here. */
	public Twitter() {
		tweetMap = new HashMap<>();
		followeeMap = new HashMap<>();
		timestamp = 0;
	}

	/** Compose a new tweet. */
	public void postTweet(int userId, int tweetId) {
		follow(userId, userId);
		if (!tweetMap.containsKey(userId)) {
			tweetMap.put(userId, new ArrayList<>());
		}

		Tweet tweet = new Tweet(tweetId, timestamp++);
		tweetMap.get(userId).add(tweet);
	}

	/**
	 * Retrieve the 10 most recent tweet ids in the user's news feed. Each item in
	 * the news feed must be posted by users who the user followed or by the user
	 * herself. Tweets must be ordered from most recent to least recent.
	 */
	public List<Integer> getNewsFeed(int userId) {
		PriorityQueue<Tweet> queue = new PriorityQueue<Tweet>((n1, n2) -> n1.createdAt - n2.createdAt);
		Set<Integer> followedUsers = followeeMap.get(userId);
		if (followedUsers != null) {
			for (Integer user : followedUsers) {
				List<Tweet> tweets = tweetMap.get(user);
				if (tweets != null) {
					for (Tweet tweet : tweets) {
						queue.add(tweet);
						if (queue.size() > 10) {
							queue.poll();
						}
					}
				}
			}
		}

		List<Integer> recentTweets = new ArrayList<>();
		while (!queue.isEmpty()) {
			recentTweets.add(0, queue.poll().tweetId);
		}
		return recentTweets;
	}

	/**
	 * Follower follows a followee. If the operation is invalid, it should be a
	 * no-op.
	 */
	public void follow(int followerId, int followeeId) {
		if (!followeeMap.containsKey(followerId)) {
			followeeMap.put(followerId, new HashSet<>());
		}
		followeeMap.get(followerId).add(followeeId);
	}

	/**
	 * Follower unfollows a followee. If the operation is invalid, it should be a
	 * no-op.
	 */
	public void unfollow(int followerId, int followeeId) {
		if (followeeMap.containsKey(followerId) && followerId != followeeId) {
			followeeMap.get(followerId).remove(followeeId);
		}
	}

	public void print(List<Integer> feed) {
		if (feed == null || feed.size() == 0) {
			return;
		}

		for (Integer tweet : feed) {
			System.out.print(tweet + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		Twitter obj = new Twitter();
		obj.postTweet(1, 5);
		obj.print(obj.getNewsFeed(1));
		obj.follow(1, 2);
		obj.postTweet(2, 6);
		obj.print(obj.getNewsFeed(1));
		obj.unfollow(1, 2);
		obj.print(obj.getNewsFeed(1));
	}

}
