import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

//Time Complexity : O(n) for getNewsFeed(),  rest O(1)
//Space Complexity : O(1)
public class Twitter {	
	class Tweet{
        int id;
        int createdAt;
        public Tweet(int tweetId, int createTime){
            this.id=tweetId;
            this.createdAt=createTime;
        }
    }
    
    HashMap<Integer, HashSet<Integer>> followedMap;
    HashMap<Integer, ArrayList<Tweet>> tweetMap; 
    int time;
    public Twitter() {
        this.followedMap= new HashMap<>();
        this.tweetMap= new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {//O(1)      
        if(!tweetMap.containsKey(userId)){
            tweetMap.put(userId, new ArrayList<>());
        }
        
        tweetMap.get(userId).add(new Tweet(tweetId, time++));               
        follow(userId, userId);        
    }
    
    public List<Integer> getNewsFeed(int userId) {//O(n); Actual O(n.klogk) where K is constant 10. 
        List<Integer> result= new ArrayList<>();
        PriorityQueue<Tweet> pq= new PriorityQueue<>((a,b)->a.createdAt-b.createdAt);
        
        //get latest 10 tweets of all the people followed by user including himself
        HashSet<Integer> followed = followedMap.get(userId);         
        
        if(followed != null) {        
            for(Integer fid: followed){
                //get tweets of each followee
                List<Tweet> tList= tweetMap.get(fid);
                if(tList!=null){
                    for(int i=0; i<tList.size(); i++){
                        if(i<=10){
                            pq.add(tList.get(i));                    
                            if(pq.size() > 10){
                                pq.poll();
                            }
                        }
                    }
                }
            }
        }        
        while(!pq.isEmpty()){
            result.add(0, pq.poll().id);
        }
        return result;
    }
    
    public void follow(int followerId, int followeeId) {//O(1)
        if(!followedMap.containsKey(followerId)){            
            followedMap.put(followerId, new HashSet<>());
        }        
        followedMap.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {//O(1)
        if(followedMap.containsKey(followerId) && followerId!=followeeId){
            followedMap.get(followerId).remove(followeeId);
        }
    }

	// Driver code to test above
	public static void main (String[] args) {
		Twitter twitter = new Twitter();
		twitter.postTweet(1, 5); // User 1 posts a new tweet (id = 5).
		System.out.println(twitter.getNewsFeed(1));  // User 1's news feed should return a list with 1 tweet id -> [5]. return [5]
		twitter.follow(1, 2);    // User 1 follows user 2.
		twitter.postTweet(2, 6); // User 2 posts a new tweet (id = 6).
		System.out.println(twitter.getNewsFeed(1));  // User 1's news feed should return a list with 2 tweet ids -> [6, 5]. Tweet id 6 should precede tweet id 5 because it is posted after tweet id 5.
		twitter.unfollow(1, 2);  // User 1 unfollows user 2.
		System.out.println(twitter.getNewsFeed(1));  // User 1's news feed should return a list with 1 tweet id -> [5], since user 1 is no longer following user 2.
	}	
}
