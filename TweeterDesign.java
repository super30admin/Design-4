//idea : Use hashmap to store followees and tweets. followees are unique so use hashset. and to store onne or more tweets use list.
//we should get our followees' tweets and select the most recent 10 tweet. So there should have a timestamp inside the tweet. 
//So we create a new class to represent a tweet
//min heap to get most recent 10 tweet based on time stamp so minimum timestamp will be on top

package Design4;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class TweeterDesign {
	
		private static class Tweet{
		    int tweetId;
		    int timePosted;
		    public Tweet(int tId, int time){
		        tweetId = tId;
		        timePosted = time;
		    }
		}

		static int timeStamp;
		int feedMaxNum;
		Map<Integer, Set<Integer>> followees;
		Map<Integer, List<Tweet>> tweets;

		/** Initialize your data structure here. 
		 * @return */
		public  TweeterDesign() {
		    timeStamp = 0;
		    feedMaxNum = 10;
		    followees = new HashMap<>();
		    tweets = new HashMap<>();
		}

		/** Compose a new tweet. */
		public void postTweet(int userId, int tweetId) {
		    if(!tweets.containsKey(userId)) {
		        tweets.put(userId, new LinkedList<Tweet>());
		        follow(userId, userId);  //follow itself
		    }
		    tweets.get(userId).add(0, new Tweet(tweetId, timeStamp++)); //add new tweet on the first place
		}

		/** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
		public List<Integer> getNewsFeed(int userId) {
		    //min heap that the earliest tweet is on the top
		    PriorityQueue<Tweet> feedHeap = new PriorityQueue<>(new Comparator<Tweet>(){
		        public int compare(Tweet t1, Tweet t2){
		            return t1.timePosted - t2.timePosted;
		        }
		    });

		    //add tweets of the followees
		    Set<Integer> myFollowees = followees.get(userId);
		    if(myFollowees != null){
		        for(int followeeId : myFollowees){
		            List<Tweet> followeeTweets = tweets.get(followeeId);
		            if(followeeTweets == null) continue;
		            for(Tweet t : followeeTweets){
		                if(feedHeap.size() < feedMaxNum) feedHeap.add(t);
		                else{
		                    if(t.timePosted <= feedHeap.peek().timePosted) break;
		                    else{
		                        feedHeap.add(t);
		                        feedHeap.poll(); //remove the oldest tweet
		                    }
		                }
		            }
		        }
		    }
		    List<Integer> myFeed = new LinkedList<>();
		    while(!feedHeap.isEmpty()){
		        myFeed.add(0, feedHeap.poll().tweetId);
		    }
		    return myFeed;
		}

		/** Follower follows a followee. If the operation is invalid, it should be a no-op. */
		public void follow(int followerId, int followeeId) {
		    if(!followees.containsKey(followerId)) followees.put(followerId, new HashSet<Integer>());
		    followees.get(followerId).add(followeeId);
		}

		/** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
		public void unfollow(int followerId, int followeeId) {
		    if(!followees.containsKey(followerId) || followerId == followeeId) return; //cannot unfollow itself
		    followees.get(followerId).remove(followeeId);
		}
		

}
