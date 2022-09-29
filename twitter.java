// TC : O(1)
// SC : O(n) // Queue size

class Twitter {

	HashMap<Integer, HashSet<Integer>> users;
	
	HashMap<Integer, List<Tweet>> tweets;
	
	public Twitter() {
		 users = new HashMap<>();
		 tweets = new HashMap<>();
	}
	
	class Tweet {
		 int tid;
		 int createdAt;
		 public Tweet(int id, int ca) {
			  this.tid = id;
			  this.createdAt = ca;
		 }
	}
	
	int time;
	public void postTweet(int userId, int tweetId) {
		 follow(userId, userId);
		 if(!tweets.containsKey(userId)) {
			  tweets.put(userId, new ArrayList<>());
		 }
		 tweets.get(userId).add(new Tweet(tweetId, time++));
	}
	
	public List<Integer> getNewsFeed(int userId) { // O(n)
		 PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);
		 HashSet<Integer> following = users.get(userId);
		 if (following != null) {
			  for(int followId : following) {
					List<Tweet> allTweetsOfUser = tweets.get(followId);
					if (allTweetsOfUser != null) {
						 for(Tweet tweet : allTweetsOfUser) {
							  pq.add(tweet);
							  if(pq.size() > 10) {
									pq.poll();
							  }
						 }
					}
			  }
		 }
		 
		 List<Integer> res = new ArrayList<>();
		 while(!pq.isEmpty()) {
			  res.add(0, pq.poll().tid);
		 }
		 return res;
	}
	
	public void follow(int followerId, int followeeId) {
		 if(!users.containsKey(followerId)){
			  users.put(followerId, new HashSet<>());
		 }
		 users.get(followerId).add(followeeId);
	}
	
	public void unfollow(int followerId, int followeeId) {
		 if(users.containsKey(followerId) && followerId != followeeId) {
			  users.get(followerId).remove(followeeId);
		 }
	}
}

/**
* Your Twitter object will be instantiated and called as such:
* Twitter obj = new Twitter();
* obj.postTweet(userId,tweetId);
* List<Integer> param_2 = obj.getNewsFeed(userId);
* obj.follow(followerId,followeeId);
* obj.unfollow(followerId,followeeId);
*/