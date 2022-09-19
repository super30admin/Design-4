import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

class Twitter {

    
    class Tweet{
        int tid;
        int createdAt;
        Tweet(int tid,int createdAt)
        {
            this.tid=tid;
            this.createdAt=createdAt;
        }
    }
    /*class user{
        int uid;
        user(int uid)
        {
            this.uid=uid;
            
        }
    }*/
    int time;
    //U1:[U2,U3] user1 follows u2 and u2
    //show post of u2 and u3 will appear on the feed
    //when you want to show the feed, ones you follow those ids needs to be retrieved
    Map<Integer,Set<Integer>> userAndUserFollowingMap;
    Map<Integer,List<Tweet>> tweets;
    /** Initialize your data structure here. */
    public Twitter() {
        userAndUserFollowingMap=new HashMap<>();
        tweets=new HashMap<>();
        time=0;
    }
  
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        follow(userId,userId);
        tweets.putIfAbsent(userId,new ArrayList<>());
        tweets.get(userId).add(new Tweet(tweetId,time++));
        
    }
      //In news feed we will show our own feed also so we add our own id to userAndUserFollowingMap;
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> list=new ArrayList<>();
        Set<Integer> followedUsers=userAndUserFollowingMap.get(userId);
        PriorityQueue<Tweet> pQueue=new PriorityQueue<>((a,b)->a.createdAt-b.createdAt);
        if(followedUsers!=null)
        {
            for(int followedUserID:followedUsers)
            {
                List<Tweet> fTweets=tweets.get(followedUserID);
                if(fTweets!=null)
                {
                    for(Tweet tweet:fTweets)
                    {
                        pQueue.add(tweet);
                        if(pQueue.size()>10)
                            pQueue.poll();
                    }
                }
               
            }
            
          
            while(!pQueue.isEmpty())
                list.add(0,pQueue.poll().tid);
        }
        return list;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
         userAndUserFollowingMap.putIfAbsent(followerId,new HashSet<>());
         userAndUserFollowingMap.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        //we will make us follow ourselves
        if(userAndUserFollowingMap.containsKey(followerId) && followerId!=followeeId)
        userAndUserFollowingMap.get(followerId).remove(followeeId);
    }
}