import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

// Time complexity is O(N)
// Space complexity is O(N)
// This solution is submitted on leetcode

public class BigN79TwittersDesign {
	class Twitter {
		class Tweet {
			int createdAt;
			int tweetId;

			public Tweet(int tweetId, int createdAt) {
				this.tweetId = tweetId;
				this.createdAt = createdAt;
			}
		}

		HashMap<Integer, List<Tweet>> tweetTable;
		HashMap<Integer, HashSet<Integer>> followerTable;
		int timestamp = 0;
		int feedsize = 10;

		/** Initialize your data structure here. */
		public Twitter() {
			this.tweetTable = new HashMap<>();
			this.followerTable = new HashMap<>();
		}

		/** Compose a new tweet. */
		public void postTweet(int userId, int tweetId) {

			// here will add the itself user hack
			if (!followerTable.containsKey(userId)) {
				followerTable.put(userId, new HashSet<Integer>());
			}
			followerTable.get(userId).add(userId);

			if (!tweetTable.containsKey(userId)) {
				tweetTable.put(userId, new ArrayList<>());
			}
			tweetTable.get(userId).add(new Tweet(tweetId, timestamp++));
		}

		/**
		 * Retrieve the 10 most recent tweet ids in the user's news feed. Each item in
		 * the news feed must be posted by users who the user followed or by the user
		 * herself. Tweets must be ordered from most recent to least recent.
		 */
		public List<Integer> getNewsFeed(int userId) {
			PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);
			// get all the users
			HashSet<Integer> ids = followerTable.get(userId);
			if (ids != null) {
				for (Integer idd : ids) {
					// get all the tweets
					List<Tweet> twt = tweetTable.get(idd);
					if (twt != null) {
						for (Tweet t : twt) {
							// empty queue
							if (pq.size() < feedsize) {
								pq.add(t);
							} else {
								if (pq.peek().createdAt < t.createdAt) {
									pq.poll();
									pq.add(t);
								}
							}
						}
					}

				}
			}

			List<Integer> result = new ArrayList<>();
			while (!pq.isEmpty()) {
				result.add(0, pq.poll().tweetId);
			}
			return result;
		}

		/**
		 * Follower follows a followee. If the operation is invalid, it should be a
		 * no-op.
		 */
		public void follow(int followerId, int followeeId) {
			if (!followerTable.containsKey(followerId)) {
				followerTable.put(followerId, new HashSet<Integer>());
			}
			followerTable.get(followerId).add(followeeId);
		}

		/**
		 * Follower unfollows a followee. If the operation is invalid, it should be a
		 * no-op.
		 */
		public void unfollow(int followerId, int followeeId) {
			if (followerTable.containsKey(followerId) && followerId != followeeId) {
				followerTable.get(followerId).remove(followeeId);
			}
		}
	}

	/**
	 * Your Twitter object will be instantiated and called as such: Twitter obj =
	 * new Twitter(); obj.postTweet(userId,tweetId); List<Integer> param_2 =
	 * obj.getNewsFeed(userId); obj.follow(followerId,followeeId);
	 * obj.unfollow(followerId,followeeId);
	 */
}