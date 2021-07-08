import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

class Twitter {
    
    class Tweet{
        int tweetId;
        int timestamp;
        public Tweet(int tweetId,int timestamp){
            this.tweetId=tweetId;
            this.timestamp=timestamp;
        }
    }

    int timestamp;
    Map<Integer,Set<Integer>> userFollowMap;
    Map<Integer,List<Tweet>> userTweetMap;
    
    /** Initialize your data structure here. */
    public Twitter() {
        timestamp=1;
        userFollowMap= new HashMap<>();
        userTweetMap= new HashMap<>();
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
    	//user should follow themself
    	follow(userId,userId);
        userTweetMap.computeIfAbsent(userId, k-> new ArrayList<Tweet>()).add(new Tweet(tweetId,timestamp++));
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq= new PriorityQueue<>((a,b) -> a.timestamp-b.timestamp);
        if(userFollowMap.containsKey(userId)) {
        	for(Integer followeeId:userFollowMap.get(userId)) {
        		if(userTweetMap.containsKey(followeeId)) {
        			for (Tweet tweet : userTweetMap.get(followeeId)) {
						pq.add(tweet);
						if(pq.size()>10)
							pq.poll();
					}
        		}
        	}        	
        }
        List<Integer> result = new ArrayList<>();
        
        while(!pq.isEmpty()) {
        	result.add(0,pq.poll().tweetId);
        }
        
        return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        userFollowMap.computeIfAbsent(followerId,k->new HashSet<Integer>()).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        //user should not unfollow themself
        if(followerId!=followeeId){
            userFollowMap.computeIfAbsent(followerId,k->new HashSet<Integer>()).remove(followeeId);   
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