// Time Complexity :
// Space Complexity :
// Did this code successfully run on Leetcode :
// Any problem you faced while coding this :


// Your code here along with comments explaining your approach


class Twitter {
    /*take a global variable for introducing the timestamp at which a tweet is created , this will give some order which can be used in the getNewsFeed method*/
    private static int time = 0;
    
    //Define a map which has User Id and the corresponding details of that user object
    
    Map<Integer, User> userMap;
    
    /*Create a Tweet Class which an User is associated with as an user can have many tweets posted by the User or his followers .
    This class is going to act as a LinkedList and it will have a pointer to the next node
    
    */
    
    public class Tweet{
        int tweetId;
        int timeStamp;
        Tweet next;
        
        public Tweet(int tweetId ){
            this.tweetId = tweetId;
            this.timeStamp = time ++ ;
            this.next = null;
            
        }
    }
    
    
    //Create an User class
    public class User{
        //Has id
        int userId;
        //User can have people whom they can Follow 
        Set<Integer> followed; 
        //Create a Tweet head pointer
        Tweet tweetHead;
        
        User(int id){
            this.userId= id;
            followed = new HashSet<>();
            tweetHead = null;
            //First follow itself
            follow(id); 
        }
        
        public void follow(int id) {
        followed.add(id);
        }
        public void unfollow(int id) {
        followed.remove(id);
        }
        
        //now when user posts a tweet, add it to the head of the tweet list
        public void post(int id) {
        Tweet t = new Tweet(id);
        t.next = tweetHead;
        tweetHead = t;
    }
    }
    

    /** Initialize your data structure here. */
    public Twitter() {
        userMap = new HashMap<Integer, User>();
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        if(!userMap.containsKey(userId)){
			User u = new User(userId);
			userMap.put(userId, u);
		}
		userMap.get(userId).post(tweetId);
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> res = new LinkedList<>();

		if(!userMap.containsKey(userId))   return res;

		Set<Integer> users = userMap.get(userId).followed;
		PriorityQueue<Tweet> q = new PriorityQueue<Tweet>(users.size(), (a,b)->(b.timeStamp-a.timeStamp));
		for(int user: users){
			Tweet t = userMap.get(user).tweetHead;
			// check if we are adding null to the head 
			if(t!=null){
				q.add(t);
			}
		}
		int n=0;
		while(!q.isEmpty() && n<10){
		  Tweet t = q.poll();
		  res.add(t.tweetId);
		  n++;
		  if(t.next!=null)
			q.add(t.next);
		}

		return res;
        
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!userMap.containsKey(followerId)){
			User u = new User(followerId);
			userMap.put(followerId, u);
		}
		if(!userMap.containsKey(followeeId)){
			User u = new User(followeeId);
			userMap.put(followeeId, u);
		}
		userMap.get(followerId).follow(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(!userMap.containsKey(followerId) || followerId==followeeId)
			return;
		userMap.get(followerId).unfollow(followeeId);
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