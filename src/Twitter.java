import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

class Twitter {
	class Tweet {
		int tid;
		int createdAt;

		public Tweet(int tid, int createdAt) {
			this.tid = tid;
			this.createdAt = createdAt;
		}
	}

	HashMap<Integer, HashSet<Integer>> followed = new HashMap<>();
	HashMap<Integer, List<Tweet>> tweets = new HashMap<>();
	int time;

	public Twitter() {

	}

	/**
	 * @param userId
	 * @param tweetId Follows ourselves and pushes the tweet to our list with time.
	 *                TC: O(1)
	 */
	public void postTweet(int userId, int tweetId) {
		follow(userId, userId);
		if (!tweets.containsKey(userId)) {
			tweets.put(userId, new ArrayList<>());
		}
		tweets.get(userId).add(new Tweet(tweetId, time++));
	}

	/**
	 * @param userId
	 * @return Use Priority Queue to iterate over all the following list and their
	 *         tweets to get latest 10 tweets. TC: O(NlogN)
	 */
	public List<Integer> getNewsFeed(int userId) {
		PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);
		HashSet<Integer> followers = followed.get(userId);
		if (followers != null) {
			for (int followeeId : followers) {
				List<Tweet> ftweets = tweets.get(followeeId);
				if (ftweets == null)
					continue;
				for (Tweet ftweet : ftweets) {
					pq.add(ftweet);
					if (pq.size() > 10)
						pq.poll();
				}
			}
		}
		List<Integer> result = new ArrayList<>();
		while (!pq.isEmpty()) {
			result.add(0, pq.poll().tid);
		}
		return result;
	}

	/**
	 * @param followerId
	 * @param followeeId
	 * 
	 *                   Push the follower and followee to the map to track the list
	 *                   of following people for each person.
	 */
	public void follow(int followerId, int followeeId) {
		if (!followed.containsKey(followerId)) {
			followed.put(followerId, new HashSet<>());
		}
		followed.get(followerId).add(followeeId);
	}

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