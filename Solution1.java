import java.util.*;

// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this :No 


// Your code here along with comments explaining your approach
class Twitter {
    class tweet {
        int tweetid;
        int createdAt;
        
       public tweet(int tid,int createdAt){
            this.tweetid=tid;
            this.createdAt=createdAt;
        }
        
    }
    
    HashMap<Integer,HashSet<Integer>> users;
    HashMap<Integer,List<tweet>> tweets;
    int time;
    public Twitter() {
        users=new HashMap<>();
        tweets=new HashMap<>();
        time=0;
    }
    
// Time Complexity : O(1)
// Space Complexity :O(1) no extra space required

    public void postTweet(int userId, int tweetId) {
        tweet tw= new tweet(tweetId,time++);
        if(!tweets.containsKey(userId)){
             tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(tw);
    }
    
// Time Complexity : O(mnlogk) +O(k) 
//m being total users including the one he is following,
//n being the total number of tweets of all users
//since we need 10 latest tweets k is 10 here 
// traversering through mn to form a PQ of size k and laso taking k elements out of the PQ to enter in result list
// Space Complexity :O(k)  extra space required for Priority queue
    public List<Integer> getNewsFeed(int userId) {
        follow(userId,userId);
        HashSet<Integer> allUsers=users.get(userId);
        PriorityQueue<tweet> pq= new PriorityQueue<>((a,b)->a.createdAt-b.createdAt);
        for(Integer user:allUsers){
            List<tweet> alltweets=tweets.get(user);
            if(alltweets!=null){
            for(tweet tw:alltweets){
                pq.add(tw);
                if(pq.size()>10){
                    pq.poll();
                }
            }
            }
        }
        List<Integer> result= new ArrayList<>();
       while(!pq.isEmpty()){
            result.add(0,pq.poll().tweetid);
        }
        
        return result;
    }
    
// Time Complexity : O(1)
// Space Complexity :O(1) no extra space required
    public void follow(int followerId, int followeeId) {
        if(!users.containsKey(followerId)){
            users.put(followerId, new HashSet<>());
        }
        users.get(followerId).add(followeeId);
    }

// Time Complexity : O(1)
// Space Complexity :O(1) no extra space required
    public void unfollow(int followerId, int followeeId) {
         if(users.containsKey(followerId) && followerId!=followeeId){
             users.get(followerId).remove(followeeId);
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