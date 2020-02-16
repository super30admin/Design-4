import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class TwitterDesign {
	HashMap<Integer, Set<Integer>> followed;
	HashMap<Integer, List<Tweet>> tweets;
	int feedSize = 10;
	int timestamp = 0;

	class Tweet {
		int id;
		int createdAt;

		public Tweet(int id, int createdAt) {
			this.id = id;
			this.createdAt = createdAt;
		}
	}

	/** Initialize your data structure here. */
	public TwitterDesign() {
		followed = new HashMap<>();
		tweets = new HashMap<>();
	}

	/** Compose a new tweet. */
	public void postTweet(int userId, int tweetId) {
		follow(userId, userId);
		if (!tweets.containsKey(userId)) {
			tweets.put(userId, new ArrayList<>());
		}
		tweets.get(userId).add(new Tweet(tweetId, timestamp++));
	}

	/**
	 * Retrieve the 10 most recent tweet ids in the user's news feed. Each item in
	 * the news feed must be posted by users who the user followed or by the user
	 * herself. Tweets must be ordered from most recent to least recent.
	 */
	public List<Integer> getNewsFeed(int userId) {
		PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);
		Set<Integer> flds = followed.get(userId);
		if (flds != null) {
			for (Integer fld : flds) {
				List<Tweet> fTweets = tweets.get(fld);
				if (fTweets != null) {
					for (Tweet fTweet : fTweets) {
						if (pq.size() < feedSize) {
							pq.add(fTweet);
						} else {
							if (fTweet.createdAt > pq.peek().createdAt) {
								pq.poll();
								pq.add(fTweet);
							}
						}
					}
				}
			}
		}
		List<Integer> result = new ArrayList<>();
		while (!pq.isEmpty()) {
			result.add(0, pq.poll().id);
		}
		return result;
	}

	/**
	 * Follower follows a followee. If the operation is invalid, it should be a
	 * no-op.
	 */
	public void follow(int followerId, int followeeId) {
		if (!followed.containsKey(followerId)) {
			followed.put(followerId, new HashSet<>());
		}
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
