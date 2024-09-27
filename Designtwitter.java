import java.util.*;

public class Designtwitter {

	int timestamp = 0;
	HashMap<Integer, User> userMap;
	class Tweet{
		int userId;
		int time;
		Tweet next;
		Tweet(int id){
			userId = id;
			time = timestamp++;
			next = null;
		}
		
	}
	
	public class User{
		
		int userId;
		Set<Integer> followed;
		Tweet tweet_head;
		User(int id){
			
			userId = id;
			followed = new HashSet<>();
			follow(id);
			tweet_head = null;
		}
		
		public void follow(int id) {
			followed.add(id);
		}
		
		public void unfollow(int id) {
			followed.remove(id);
		}
		
		public void post(int id) {
			Tweet t = new Tweet(id);
			t.next = tweet_head;
			tweet_head = t;	
		}
	}
	
	public Designtwitter() {
		userMap = new HashMap<>();
	
	}
	
	public void postTweet(int userId, int tweetId) {
		
		if(!userMap.containsKey(userId)) {
			User u = new User(userId);
			userMap.put(userId, u);
		}
		userMap.get(userId).post(tweetId);
	}
	
	public List<Integer> getNewsfeed(int userId){
	
		List<Integer> res = new ArrayList<>();
		if(!userMap.containsKey(userId)) {
			return res;
		}
		
		Set<Integer> users = userMap.get(userId).followed;
		PriorityQueue<Tweet> pq = new PriorityQueue<>(users.size(), (a, b)->(b.time - a.time));
		
		for(int user : users) {
			Tweet t = userMap.get(user).tweet_head;
			if(t!=null)
				pq.add(t);
		}
		
		int n = 0;
		while(!pq.isEmpty() && n < 10) {
			Tweet t = pq.poll();
			res.add(t.userId);
			n++;
			
			if(t.next!=null) {
				pq.add(t.next);
			}
		}
		return res;
	}
	
	public void follow(int followerId, int followeeId) {
		if(!userMap.containsKey(followerId)) {
			User u = new User(followerId);
			userMap.put(followerId, u);
		}
		if(!userMap.containsKey(followeeId)) {
			User u = new User(followeeId);
			userMap.put(followeeId, u);
		}
		
		userMap.get(followerId).follow(followeeId);
	}
	
	public void unfollow(int followerId, int followeeId) {
		
		if(!userMap.containsKey(followerId) || followerId == followeeId)
			return;
		
		userMap.get(followerId).unfollow(followeeId);		
	}
}
