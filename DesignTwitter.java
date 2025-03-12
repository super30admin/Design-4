// Time Complexity : written below for all the methods.
// Space Complexity : O(n) if all the meetings are in conflict.
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no


import java.util.*;
class Twitter {
    class Tweet{
        int tid;
        int time;
        Tweet(int tid, int time)
        {
            this.tid = tid;
            this.time = time;
        }
    }
    HashMap<Integer, Set<Integer>> follows;
    HashMap<Integer, List<Tweet>> userTweets;
    int time;
    public Twitter() {
        follows = new HashMap<>();
        userTweets = new HashMap<>();
    }
    
    //Time Complexity : O(1)
    public void postTweet(int userId, int tweetId) { 
        //user should follow himself
        follows.putIfAbsent(userId, new HashSet<>());
        follows.get(userId).add(userId);

        //create new tweet and add it to the users tweets list
        Tweet newTweet = new Tweet(tweetId, ++time);
        userTweets.putIfAbsent(userId, new ArrayList<>());
        userTweets.get(userId).add(newTweet);
    }
    
    //Time Complexity : O(k) where K is the list of users that user follows.
    public List<Integer> getNewsFeed(int userId) {
        //get the list of users that the current user is following
        Set<Integer> followsList = follows.getOrDefault(userId, new HashSet<>());
        //using a minheap
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> (a.time - b.time));
        for(int fid : followsList)
        {
            List<Tweet> ftweets = userTweets.getOrDefault(fid, new ArrayList<>());
            
            int size = ftweets.size();
            for(int i=size-1; i>=0 && i>=size-11; i--)
            {
                pq.offer(ftweets.get(i));
                if(pq.size() > 10)
                    pq.poll();
            }
        }
        List<Integer> recentTweets = new ArrayList<>();
        while(!pq.isEmpty())
            recentTweets.add(pq.poll().tid);
        Collections.reverse(recentTweets);
        return recentTweets;
    }
    
    //Time Complexity : O(1)
    public void follow(int followerId, int followeeId) {
        follows.putIfAbsent(followerId, new HashSet<>());
        follows.get(followerId).add(followeeId);
    }
    
    //Time Complexity : O(1)
    public void unfollow(int followerId, int followeeId) { 
        if(!follows.containsKey(followerId)) return;
        follows.get(followerId).remove(followeeId);
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