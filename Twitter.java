
//Time Complexity : O(1) -  As size is constant
//Space Complexity : O(m + n) where m is number of users and n is number of tweets
//Any problem while coding: Not sure about time and space complexity
//Sucessfully ran on leetcode: Yes
import java.util.*;

class Twitter {
	class Tweet {
		int createdAt;
		int id;

		public Tweet(int id, int timeStamp) {
			this.createdAt = timeStamp;
			this.id = id;
		}
	}

	HashMap<Integer, HashSet<Integer>> followed;
	HashMap<Integer, List<Tweet>> tweet;
	int timeStamp;
	int feedSize;

	/** Initialize your data structure here. */
	public Twitter() {
		followed = new HashMap<>();
		tweet = new HashMap<>();
		this.feedSize = 10;
	}

	/** Compose a new tweet. */
	public void postTweet(int userId, int tweetId) {
		// follow self
		follow(userId, userId);
		if (!tweet.containsKey(userId)) {
			tweet.put(userId, new ArrayList<>());
		}
		tweet.get(userId).add(new Tweet(tweetId, timeStamp++));
	}

	/**
	 * Retrieve the 10 most recent tweet ids in the user's news feed. Each item in
	 * the news feed must be posted by users who the user followed or by the user
	 * herself. Tweets must be ordered from most recent to least recent.
	 */
	public List<Integer> getNewsFeed(int userId) {
		PriorityQueue<Tweet> pq = new PriorityQueue<>((t1, t2) -> t1.createdAt - t2.createdAt);
		HashSet<Integer> fids = followed.get(userId);
		if (fids != null) {
			for (Integer fid : fids) {
				List<Tweet> ftweets = tweet.get(fid);
				if (ftweets != null) {
					for (Tweet tweet : ftweets) {
						pq.add(tweet);
						// size of pq goes more than feedsize
						// maintaining pq of size 10
						if (pq.size() > feedSize) {
							pq.poll();
						}
					}
				}
			}
		}
		List<Integer> result = new ArrayList<>();
		while (!pq.isEmpty()) {
			Tweet pTweet = pq.poll();
			result.add(0, pTweet.id);
		}
		return result;
	}

	/**
	 * Follower follows a followee. If the operation is invalid, it should be a
	 * no-op.
	 */
	public void follow(int followerId, int followeeId) {
		if (!followed.containsKey(followerId)) { // create user if user not there in followed table
			followed.put(followerId, new HashSet<>());
		}
		// add the person I am following to set
		followed.get(followerId).add(followeeId);
	}

	/**
	 * Follower unfollows a followee. If the operation is invalid, it should be a
	 * no-op.
	 */
	public void unfollow(int followerId, int followeeId) {
		if (followed.containsKey(followerId) && followerId != followeeId) {
			followed.get(followerId).remove(followeeId);
		}
	}
}

/**
 * Your Twitter object will be instantiated and called as such: Twitter obj =
 * new Twitter(); obj.postTweet(userId,tweetId); List<Integer> param_2 =
 * obj.getNewsFeed(userId); obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */