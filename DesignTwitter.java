// Time Complexity :
// Space Complexity :
// Did this code successfully run on Leetcode :
// Any problem you faced while coding this :

// Your code here along with comments explaining your approach
//355. Design Twitter (Medium) - https://leetcode.com/problems/design-twitter/
class Twitter {

	class Tweet {
		int tid;
		int createdAt;

		public Tweet(int id, int time) {
			this.tid = id;
			this.createdAt = time;
		}
	}

	HashMap<Integer, HashSet<Integer>> followed; // mapping of user to his followed users set to load news feed
	HashMap<Integer, List<Tweet>> tweets; // mapping of user to his list of tweets
	int time;

	public Twitter() {
		this.followed = new HashMap<>();
		this.tweets = new HashMap<>();
	}

	public void postTweet(int userId, int tweetId) { // O(1)
		follow(userId, userId);
		if (!tweets.containsKey(userId)) {
			tweets.put(userId, new ArrayList<>());
		}

		tweets.get(userId).add(new Tweet(tweetId, time++));
	}

//    public List<Integer> getNewsFeed(int userId) { // O(n*k) where n = number of users, k = average number of tweets of each user
//        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);
//        HashSet<Integer> followedSet = followed.get(userId); // get the followed users set
//        
//        if (followedSet != null) {
//            for (int followedId : followedSet) { // for each followed user, get his list of tweets
//                List<Tweet> followedTweets = tweets.get(followedId);
//                
//                if (followedTweets != null) {
//                    for (Tweet tweet : followedTweets) { // go over tweets and them to priority queue
//                        pq.add(tweet);
//                        
//                        if (pq.size() > 10) {
//                            pq.poll();
//                        }
//                    }
//                }
//            }
//        }
//        
//        List<Integer> result = new ArrayList<>();
//        
//        while (!pq.isEmpty()) {
//            result.add(0, pq.poll().tid);
//        }
//        
//        return result;
//    }

	public List<Integer> getNewsFeed(int userId) { // O(n) where n = number of users
		PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);
		HashSet<Integer> followedSet = followed.get(userId); // get the followed users set

		if (followedSet != null) {
			for (int followedId : followedSet) { // for each followed user, get his list of tweets
				List<Tweet> followedTweets = tweets.get(followedId);

				if (followedTweets != null) {
					int size = followedTweets.size();

					for (int k = size - 1; k >= 0 && k > size - 11; k--) {
						Tweet tweet = followedTweets.get(k);
						pq.add(tweet);

						if (pq.size() > 10) {
							pq.poll();
						}
					}
				}
			}
		}

		List<Integer> result = new ArrayList<>();

		while (!pq.isEmpty()) {
			result.add(0, pq.poll().tid);
		}

		return result;
	}

	public void follow(int followerId, int followeeId) { // O(1)
		if (!followed.containsKey(followerId)) {
			followed.put(followerId, new HashSet<>());
		}

		followed.get(followerId).add(followeeId);
	}

	public void unfollow(int followerId, int followeeId) { // O(1)
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