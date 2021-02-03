// Time Complexity : O(n), as for Heap the complexity becomes nklogk, but k=10 always
// Space Complexity : O(f, t), where f is no of users having f followers and t tweets 
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : No

/**
 * follow-> Add to corresponding Hashset
 * unfollow-> if not self remove and follower exists, remove from corresponding Hashset
 * postTweet-> Add Tweet Object to list corresponding to the user
 * getNewsFeed-> Add and maintain size and order(maintained by recent Tweets) by using a Priority Queu/MinHeap 
 * **/

import java.util.*;

class Twitter {
	class Tweet{
		int id;
		int createdAt;

		public Tweet(int tweetId, int timestamp){
			this.id = tweetId;
			this.createdAt = timestamp;
		}
	}

	HashMap<Integer, Set<Integer>> following;
	HashMap<Integer, List<Tweet>> tweets;
	int feedsize;
	int timestamp;

	/** Initialize your data structure here. */
	public Twitter() {
		following = new HashMap<>();
		tweets = new HashMap<>();
		feedsize = 10;
	}

	/** Compose a new tweet. */
	public void postTweet(int userId, int tweetId) {
		//first check userId is there in users(following table)
		follow(userId, userId);
		//creating a userId entry in tweets table if userId is not there
		if(!tweets.containsKey(userId))
			tweets.put(userId, new ArrayList<>());

		//Add that object to list of tweets of that user
		tweets.get(userId).add(new Tweet(tweetId, timestamp++));
	}

	/** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
	public List<Integer> getNewsFeed(int userId) {
		PriorityQueue<Tweet> pq = new PriorityQueue<>((tweet1, tweet2) -> tweet1.createdAt-tweet2.createdAt);  // min head
		//take all the users i am following
		Set<Integer> fIds = following.get(userId);
		if(fIds != null){
			//take tweets of all fIds
			for(Integer fId : fIds){
				List<Tweet> fTweets = tweets.get(fId);
				if(fTweets != null){
					for(Tweet fTweet : fTweets){
						pq.add(fTweet);

						if(pq.size() > feedsize)
							pq.poll();
					}
				}
			}
		}
		List<Integer> result = new ArrayList<>();
		while(!pq.isEmpty()){
			Tweet tweet = pq.poll();
			result.add(0, tweet.id);	// we have to give most recent first so always put to the start of list
		}
		return result;
	}

	/** Follower follows a followee. If the operation is invalid, it should be a no-op. */
	public void follow(int followerId, int followeeId) {
		//check if followerId is there in map of following
		if(!following.containsKey(followerId))
			following.put(followerId, new HashSet<>());

		//get the follower and add to its list the followee
		following.get(followerId).add(followeeId);
	}

	/** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
	public void unfollow(int followerId, int followeeId) {
		if(followerId != followeeId && following.containsKey(followerId))
			following.get(followerId).remove(followeeId);
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
