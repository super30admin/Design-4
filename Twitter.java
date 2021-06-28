package Design4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/*-----------------------------------------
   Time complexity : 
   
   space complexity: o(followers* tweets) - size of priority queue
   Did this code run successfully in leetcode : yes
   problems faces : no*/
    
public class Twitter {

    private HashMap<Integer, Set<Integer>> followed;
    private HashMap<Integer, List<Tweet>> tweets;
    private int time;

    public Twitter() {
        followed = new HashMap<>();
        tweets = new HashMap<>();
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId,int tweetId)
    {
        follow(userId,userId);
        if(!tweets.containsKey(userId))
        {
            tweets.put(userId, new ArrayList<Tweet>());
        }
        
        tweets.get(userId).add(new Tweet(tweetId,time++));
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. 
     * Each item in the news feed must be posted by users who the user followed or by the user herself.
     *  Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId)
    {
        Set<Integer> followeds = followed.get(userId);
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        
        if(followeds != null)
        {
            for(int user : followeds)
            {
                List<Tweet> tweetsByuser =  tweets.get(user);
               if(tweetsByuser != null )
               {
                   for(Tweet t : tweetsByuser)
                   {
                       pq.add(t);
                       if(pq.size() >10) 
                       {
                         pq.poll();   
                       }
                   }
               }
            }
        }
        
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty())
        {
            result.add(0,pq.poll().tweetId);
        }
        
        return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId)
    {
        if(!followed.containsKey(followerId))
          {
            followed.put(followerId, new HashSet<Integer>(Arrays.asList(followeeId)));
          }
        else
        {
            followed.get(followerId).add(followeeId);
        }
        
        
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId)
    {
        if(followed.containsKey(followerId) && followerId !=followeeId)
        {
           Set<Integer> set =  followed.get(followerId);
           if(set != null && set.contains(followeeId))
           {
               set.remove(followeeId);
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
    public class Tweet {
        int tweetId;
        int createdAt;

        public Tweet(int tweetId, int createdAt) {
            this.tweetId = tweetId;
            this.createdAt = createdAt;
        }
    }

}
