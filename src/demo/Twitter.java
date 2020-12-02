package demo;

import java.util.*;


//Time complexity : O(n.logk) k is the length of PQ
//Space Complexity: O(n) // 2 hashmaps + List
//LeetCode : yes


class Twitter {

	/** Initialize your data structure here. */
	HashMap <Integer, Set<Integer>> followers = new HashMap<>();
	HashMap <Integer, List<Tweet>> tweets = new HashMap<>();
	int feedSize = 10;
	int timestamp;
	class Tweet{
		int id;
		int timestamp;
		// String text;  ignoring for simplicity
		private Tweet(int id , int tweetTimestamp){
			this.id = id;
			this.timestamp = tweetTimestamp;
		}
	}
	public Twitter() {

	}

	/** Compose a new tweet. */
	public void postTweet(int userId, int tweetId) {
		//put entry in followers table
		if(!followers.containsKey(userId)){
			followers.put(userId, new HashSet<>());
		}
		followers.get(userId).add(userId);
		//add tweet to hm tweets
		if(!tweets.containsKey(userId)){
			tweets.put(userId, new ArrayList<>());
		}
		Tweet t = new Tweet(tweetId , timestamp++);
		tweets.get(userId).add(t);
	}

	/** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
	public List<Integer> getNewsFeed(int userId) {
		PriorityQueue<Tweet> pq = new PriorityQueue<>((t1,t2) -> t1.timestamp 
				-t2.timestamp);
		Set <Integer> fIds = followers.get(userId);
		//edge
		if(fIds!=null){
			for(int fId : fIds ){
				List<Tweet> ftweets = tweets.get(fId);
				if(ftweets == null) continue;

				for(Tweet t : ftweets){
					//case 1 pq full
					if(pq.size() < feedSize){
						pq.add(t);
					}
					//case 2 pq not full
					else{
						//add to my pq if tweet is recent than top/1st tweet
						Tweet top = pq.peek();
						if(t.timestamp > top.timestamp){
							pq.poll();
							pq.add(t);
						}
					}
				}
			}
		}
		List<Integer> result = new ArrayList<>();
		while(!pq.isEmpty()){
			//Adding always in front of the row
			result.add(0,pq.poll().id);
		}
		return result;
	}

	/** Follower follows a followee. If the operation is invalid, it should be a no-op. */
	public void follow(int followerId, int followeeId) {
		if(!followers.containsKey(followerId)) followers.put(followerId, new HashSet<>());
		followers.get(followerId).add(followeeId);
	}

	/** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
	public void unfollow(int followerId, int followeeId) {
		if(followers.containsKey(followerId) && followerId != followeeId){
			followers.get(followerId).remove(followeeId);
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