import java.util.*;

public class Twitter {

	class Tweet {
		int tweetId;
		int createdAt;

		Tweet(int tweetId, int createdAt) {
			this.tweetId = tweetId;
			this.createdAt = createdAt;

		}
	}

	Map<Integer, List<Tweet>> userTweetMap;
	Map<Integer, Set<Integer>> userFollowMap;
	int timeStamp, feedSize;

	public Twitter() {
		userTweetMap = new HashMap<>();
		userFollowMap = new HashMap<>();
		timeStamp = 0;
		feedSize = 10;
	}

	private void isFirstTime(int userId) {
		List<Tweet> tweets;
		Set<Integer> set;
		Set<Integer> followees;

		if (!userTweetMap.containsKey(userId)) {
			tweets = new LinkedList<>();
			userTweetMap.put(userId, tweets);
		}

		if (!userFollowMap.containsKey(userId)) {
			followees = new HashSet<Integer>();
		} else {
			followees = userFollowMap.get(userId);
		}
		followees.add(userId);
		userFollowMap.put(userId, followees);
	}

	  /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        isFirstTime(userId);
        
        List<Tweet> tweets = userTweetMap.get(userId);
        tweets.add(new Tweet(tweetId, timeStamp++));
    	
    }
    
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. 
     * Each item in the news feed must be posted by users who the user 
     * followed or by the user herself. Tweets must be ordered from most 
     * recent to least recent. */
   
    
    public List<Integer> getNewsFeed(int userId) {
    	isFirstTime(userId);
    
    	List<Tweet> tweets = new ArrayList<Tweet>();
    	Set<Integer> followees = userFollowMap.get(userId);
    	
    	for(int followee : followees) {
    		tweets.addAll(userTweetMap.get(followee));
    	}
    	
    	Collections.sort(tweets, (a,b) -> (b.createdAt - a.createdAt));
    	
    	
    	List<Integer> recentTweets = new ArrayList<>();
    	int feefSizeCount = feedSize;
    	Iterator<Tweet> iterator = tweets.iterator();
    	
    	while(feefSizeCount >0 && iterator.hasNext()) {
    		
    		
    		recentTweets.add(iterator.next().tweetId);
    		feedSize--;
    	}
    	
    	
        return recentTweets;
    }
    
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
    	isFirstTime(followerId);
    	isFirstTime(followeeId);
    	
    	Set<Integer> followees = userFollowMap.get(followerId);
    	followees.add(followeeId);
    	
    	
        
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        
    	isFirstTime(followerId);
    	isFirstTime(followeeId);	
    	
    	Set<Integer> followees = userFollowMap.get(followerId);
    	
    	if(followerId != followeeId)
    		followees.remove(followeeId);
    	
    }
    
    
	public static void main(String[] args) {

		Twitter twitter = new Twitter();

		// User 1 posts a new tweet (id = 5).
		twitter.postTweet(1, 5);

		// User 1's news feed should return a list with 1 tweet id -> [5].
		System.out.println(twitter.getNewsFeed(1));

		// User 1 follows user 2.
		twitter.follow(1, 2);

		// User 2 posts a new tweet (id = 6).
		twitter.postTweet(2, 6);
		twitter.postTweet(2, 7);
		twitter.postTweet(2, 8);

		// User 1's news feed should return a list with 2 tweet ids -> [6, 5].
		// Tweet id 6 should precede tweet id 5 because it is posted after tweet id 5.
		System.out.println(twitter.getNewsFeed(1));

		// User 1 unfollows user 2.
	//	twitter.unfollow(1, 2);

		// User 1's news feed should return a list with 1 tweet id -> [5],
		// since user 1 is no longer following user 2.
		System.out.println(twitter.getNewsFeed(1));

	}

}
