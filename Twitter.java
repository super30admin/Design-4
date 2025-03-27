import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

//Time Complexity : follow O(1), unfollow O(1), posttweet O(1), getnews O(n) n=no. of users followed
//Space Complexity : O(n)
//Did this code successfully run on Leetcode : Yes
//Any problem you faced while coding this : No

class Twitter {
    class Tweet{
        int tid;
        int time;
        Tweet(int tid, int time){
            this.tid=tid;
            this.time=time;
        }
    }
    int time=0;
    private Map<Integer,Set<Integer>> followed;
    private Map<Integer, List<Tweet>> tweets;

    public Twitter() {
        this.followed=new HashMap<>();
        this.tweets=new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) { //O(1)

        follow(userId,userId);
        if(!tweets.containsKey(userId)){
            tweets.put(userId,new ArrayList<>());
        }
        Tweet t=new Tweet(tweetId,time);
        time++;
        tweets.get(userId).add(t);
        
    }
    
    // public List<Integer> getNewsFeed(int userId) { //Nlog(10) = N, N=total number of tweets of the users I am following.
    //     PriorityQueue<Tweet> pq=new PriorityQueue<>((t1,t2)->t1.time-t2.time);
    //     Set<Integer> ifollow=followed.get(userId);
    //     if(ifollow==null){
    //         return new ArrayList<>();
    //     }
    //     for(int fid:ifollow){
    //         //get tweets
    //         List<Tweet> ft=tweets.get(fid);
    //         if(ft==null){
    //             continue;
    //         }
    //         for(Tweet t: ft){
    //             pq.add(t);
    //             if(pq.size()>10){
    //                 pq.poll();
    //             }
    //         }
    //     }
    //     List<Integer> res=new ArrayList<>();
    //     while(!pq.isEmpty()){
    //         res.add(0,pq.poll().tid);
    //     }
    //     return res;
    // }

    public List<Integer> getNewsFeed(int userId) { //O(P) -> p=number of people I follow.
        PriorityQueue<Tweet> pq=new PriorityQueue<>((t1,t2)->t1.time-t2.time);
        Set<Integer> ifollow=followed.get(userId);
        if(ifollow==null){
            return new ArrayList<>();
        }
        for(int fid:ifollow){
            //get tweets
            List<Tweet> ft=tweets.get(fid);
            if(ft==null){
                continue;
            }
            int l=ft.size();
            for(int i=l-1;i>=0 && i>l-10;i--){
                Tweet t=ft.get(i);
                pq.add(t);
                if(pq.size()>10){
                    pq.poll();
                }
            }
        }
        List<Integer> res=new ArrayList<>();
        while(!pq.isEmpty()){
            res.add(0,pq.poll().tid);
        }
        return res;
    }
    
    public void follow(int followerId, int followeeId) { //O(1)
        if(!followed.containsKey(followerId)){
            followed.put(followerId,new HashSet<>());
        }
        followed.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) { //O(1)
        if(!followed.containsKey(followerId) || followerId==followeeId){
            return;
        }
        followed.get(followerId).remove(followeeId);
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