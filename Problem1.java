//Time Complexity: Follow - O(1); Unfollow - O(1); PostTweet - O(1), GetNews - O(m*n*log(10)), where m is the no. of users and n is the tweets per user.
//Space Complexity: O(m)
//Code run successfully on LeetCode.

public class Problem1 {

	class Twitter {
	    
	    int time;
	    int size = 10;
	    
	    class Tweet{
	        
	        int tweetId;
	        int time;
	        
	        Tweet(int tweetId, int time){
	            
	            this.tweetId = tweetId;
	            this.time = time;
	        }  
	    }
	    
	    HashMap<Integer, HashSet<Integer>> users;
	    HashMap<Integer, List<Tweet>> tweets;

	    public Twitter() {
	        
	        users = new HashMap<>();
	        tweets = new HashMap<>();
	    }
	    
	    public void postTweet(int userId, int tweetId) {
	        
	        time++;
	        Tweet tweet = new Tweet(tweetId, time);
	        
	        if(!tweets.containsKey(userId))
	            tweets.put(userId, new ArrayList<>());
	        
	        tweets.get(userId).add(tweet);
	        
	    }
	    
	    public List<Integer> getNewsFeed(int userId) {
	        
	        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.time - b.time);
	        follow(userId, userId);
	        
	        if(users.get(userId) != null){
	            
	            List<Integer> list = new ArrayList<>(users.get(userId));
	            
	            for(int user : list){
	                
	                if(tweets.get(user) != null){
	                    
	                    List<Tweet> allTweets = tweets.get(user);
	                    for(Tweet t : allTweets){
	                        
	                        pq.add(t);
	                        if(pq.size() > size)
	                            pq.poll();
	                    }
	                }
	            }
	        }
	        
	        List<Integer> result = new ArrayList<>();
	        while(!pq.isEmpty()){
	            Tweet t = pq.poll();
	            result.add(0, t.tweetId);
	        }
	        return result; 
	    }
	    
	    public void follow(int followerId, int followeeId) {
	        
	        if(!users.containsKey(followerId))
	            users.put(followerId, new HashSet<>());
	        
	        users.get(followerId).add(followeeId);
	        
	    }
	    
	    public void unfollow(int followerId, int followeeId) {
	        
	        if(users.containsKey(followerId)){
	            
	            users.get(followerId).remove(followeeId);
	        }
	        
	    }
	}


}
