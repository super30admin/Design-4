import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

//Time Complexity : O(N) hashmap
//Space Complexity : O(10) -> 10 most recent tweet IDs in the user's news feed
class Twitter {
    class Tweet{
        int id;
        int createdAt;
        public Tweet( int id,int createdAt){
            this.id = id;
            this.createdAt = createdAt;
        }
    }
    HashMap<Integer, Set<Integer>> followed;
    HashMap<Integer, List<Tweet>> tweets;
    int time;
    public Twitter() {
        this.followed = new HashMap<>();
        this.tweets = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if(!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId, time++));
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        Set<Integer> fIds = followed.get(userId);
        if(fIds != null){
            for(int fId : fIds){
            List<Tweet> ftweets = tweets.get(fId);
            if(ftweets != null){
            for(Tweet fTweet : ftweets){
                pq.add(fTweet);
                if(pq.size() > 10){
                    pq.poll();
                    }
                }
            }
            }
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0,pq.poll().id);
        }
        return result;
    }
    
    public void follow(int followerId, int followeeId) {
        if(!followed.containsKey(followerId)){
            followed.put(followerId, new HashSet<>());
        }
        followed.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(followed.containsKey(followerId) &&followerId != followeeId){
            followed.get(followerId).remove(followeeId);
        }
    }



public static void main(String args[]){
  Twitter obj = new Twitter();
  int userId = 10;
  int tweetId = 10;
  int followerId = 30;
  int followeeId = 10;
obj.postTweet(userId,tweetId);
  List<Integer> a = obj.getNewsFeed(userId);
  obj.follow(followerId,followeeId);
  obj.unfollow(followerId,followeeId);
}
}