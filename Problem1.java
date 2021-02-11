private static class Tweet {
	int timestamp;
	int tweetId;

	public Tweet(int tweetId, int timestamp) {
		this.tweetId = tweetId;
		this.timestamp = timestamp;
	}
}

private Map<Integer, Set<Integer>> followMap = new HashMap<Integer, Set<Integer>>();
private Map<Integer, List<Tweet>> tweetMap = new HashMap<Integer, List<Tweet>>();

private AtomicInteger timestamp;

/** Initialize your data structure here. */
public Twitter() {
	timestamp = new AtomicInteger(0);
}

/** Compose a new tweet. */
public void postTweet(int userId, int tweetId) {
	Tweet newTweet = new Tweet(tweetId, timestamp.getAndIncrement());

	if (!tweetMap.containsKey(userId)) {
		tweetMap.put(userId, new ArrayList<Tweet>());  //Assuming no deletion for now?
	}

	tweetMap.get(userId).add(newTweet);
}

/**
 * Retrieve the 10 most recent tweet ids in the user's news feed. Each item
 * in the news feed must be posted by users who the user followed or by the
 * user herself. Tweets must be ordered from most recent to least recent.
 */
public List<Integer> getNewsFeed(int userId) {
	List<Integer> result = new ArrayList<Integer>(10);

	PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>() {
		public int compare(int[] it1, int[] it2) {
			return tweetMap.get(it2[0]).get(it2[1]).timestamp - tweetMap.get(it1[0]).get(it1[1]).timestamp;
		}
	});

	Set<Integer> followeeSet = new HashSet<Integer>();
	followeeSet.add(userId);
	if (followMap.containsKey(userId)) {
		followeeSet.addAll(followMap.get(userId));
	}

	for (Integer followee : followeeSet) {
		if (tweetMap.containsKey(followee)) {
			List<Tweet> tweetList = tweetMap.get(followee);
			if (tweetList.size() > 0) {
				pq.add(new int[] { followee, tweetList.size() - 1 });
			}
		}
	}

	while (result.size() < 10 && pq.size() > 0) {
		int[] it = pq.poll();

		result.add(tweetMap.get(it[0]).get(it[1]).tweetId);
		it[1]--;
		if (it[1] >= 0) {
			pq.add(it);
		}
	}

	return result;
}

/**
 * Follower follows a followee. If the operation is invalid, it should be a
 * no-op.
 */
public void follow(int followerId, int followeeId) {
	Set<Integer> followSet = followMap.get(followerId);
	if (followSet == null) {
		followSet = new HashSet<Integer>();
		followMap.put(followerId, followSet);
	}

	followSet.add(followeeId);
}

/**
 * Follower unfollows a followee. If the operation is invalid, it should be
 * a no-op.
 */
public void unfollow(int followerId, int followeeId) {
	Set<Integer> followSet = followMap.get(followerId);
	if (followSet == null) {
		followSet = new HashSet<Integer>();
		followMap.put(followerId, followSet);
	}

	followSet.remove(followeeId);
}