package s30Coding;
import java.util.*;

class Twitter {
    class Tweet{
        int id;
        int createdAt;
        public Tweet(int id, int timeStamp){
            this.id = id;
            this.createdAt = timeStamp;
        }
    }
    
    HashMap<Integer, Set<Integer>> followed;
    HashMap<Integer, List<Tweet>> tweets;
    int feedSize;
    int timeStamp;
    /** Initialize your data structure here. */
    public Twitter() {
        this.feedSize = 10;
        followed = new HashMap<>();
        tweets = new HashMap<>();
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        if(!followed.containsKey(userId)){
           followed.put(userId, new HashSet<>()); 
        }
        followed.get(userId).add(userId);
        if(!tweets.containsKey(userId)){
            tweets.put(userId,new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId,timeStamp++));
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
      PriorityQueue<Tweet> pq = new PriorityQueue<>((t1,t2) -> t1.createdAt - t2.createdAt);
      Set<Integer> fIds = followed.get(userId);
        if(fIds != null){
            for(Integer fId : fIds){
                List<Tweet> fTweets = tweets.get(fId);
                if(fTweets != null){
                    for(Tweet fTweet : fTweets){
                        if(pq.size() < feedSize){
                            pq.add(fTweet);
                        }
                        else{
                            if(fTweet.createdAt > pq.peek().createdAt){
                                pq.poll();
                                pq.add(fTweet);
                            }
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
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!followed.containsKey(followerId)){
           followed.put(followerId, new HashSet<>()); 
        }
        if(!followed.containsKey(followeeId)){
           followed.put(followeeId, new HashSet<>()); 
        }
        followed.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(followed.containsKey(followerId) && followerId != followeeId){
            followed.get(followerId).remove(followeeId);
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
