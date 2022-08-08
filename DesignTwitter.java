import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

//Time complexity- O(1) for follow, onfollow and postTweet methods. O(mn) where m is the users and n is the number of tweets is for getfeed method
//Successfully ran on leetcode

class Twitter {
 class Tweet{
     int id;
     int createdAt;
     public Tweet(int id, int time){
         this.id= id;
         this.createdAt= time;            
     }
 }
 
 HashMap<Integer, HashSet<Integer>> user;
 HashMap<Integer, List<Tweet>> tweets;
 int time;
 
 public Twitter() {
     user= new HashMap();
     tweets= new HashMap();
 }
 
 public void postTweet(int userId, int tweetId) {
     
     if(!tweets.containsKey(userId)){
         tweets.put(userId, new ArrayList());
     }
     tweets.get(userId).add(new Tweet(tweetId, time++));
     
 }
 
 public List<Integer> getNewsFeed(int userId) {
     follow(userId, userId);
     PriorityQueue<Tweet> pq= new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
     HashSet<Integer> followdppl =user.get(userId);
     
     for(int users: followdppl){
          List<Tweet> allTweets = tweets.get(users);
         if(allTweets != null){
             for(Tweet tw : allTweets){
                 pq.add(tw);
                 if(pq.size() > 10 ){
                     pq.poll();
                 }
             }
         }
     }
     List<Integer> result= new ArrayList<>();
     while(!pq.isEmpty()){
         result.add(0, pq.poll().id);
     }
     return result;
 }
 //O(1)
 public void follow(int followerId, int followeeId) {
     if(!user.containsKey(followerId)){
         user.put(followerId, new HashSet<>());
     }
     user.get(followerId).add(followeeId);
 }
 
 public void unfollow(int followerId, int followeeId) {
     if(user.containsKey(followerId) && followerId != followeeId){
         user.get(followerId).remove(followeeId);
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