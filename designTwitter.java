    /*  Explanation
    # Leetcode problem link : https://leetcode.com/problems/design-twitter/
    Time Complexity for operators : o(n) .. traversal for news feed.
    Extra Space Complexity for operators : o(n) for (3 heashmap with priority queue) without recursive stack
    Did this code successfully run on Leetcode : NA
    Any problem you faced while coding this : No
# Your code here along with comments explaining your approach
        # Basic approach : 
        # Optimized approach: 
                              
            # 1. 
                    A) Starting from Post a tweet. We need to main a hashmap to store the userd with its postid.
                    B) Also, we need to maintain posted tweet with timer aligned with it. Timer will be like a counter value.
                    C) This timer value, we will use to form a Max Heap that will give recent 10 tweets.
                    D) For follow and un follow with will use hasmap added with haset that will maintain the followee list.
                    E) At the ewnd, newsfeed, as said we will form a priority queue if self tweets and the tweets posted by
                       followers for last 10 tweets. We will return this list.
    */

class Twitter {
    
    // hashmap top store follow and unfollow list
    HashMap<Integer,Set<Integer>> hmFollow;
    
    // hashmap to store userid and tweetid combo
    HashMap<Integer,List<Integer>> hmpostTweet;
    
    // hashmap to store tweetid with timer
    HashMap<Integer, Integer> hmTweetTimer;

    //timer
    int time;
    /** Initialize your data structure here. */
    public Twitter() {
        hmFollow = new HashMap<>();
        hmpostTweet = new HashMap<>();
        hmTweetTimer = new HashMap<>();
        time = 0;
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        if(!hmpostTweet.containsKey(userId)){
            hmpostTweet.put(userId,new ArrayList<>());
        }
        
        hmpostTweet.get(userId).add(0,tweetId);
        
        if(!hmTweetTimer.containsKey(tweetId)){
            hmTweetTimer.put(tweetId,time);
        }
        time++;
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        // now as we have userID then get the list of followers of this users and then get the
        // list of tweets
        // but that tweets needs to be most recent tweet that means we need to use Max Heap here.
        
        
        if (!hmpostTweet.containsKey(userId)) {
            hmpostTweet.put(userId, new ArrayList<>());
        }
        
        if (!hmFollow.containsKey(userId)) {
            hmFollow.put(userId, new HashSet<>());
        }
        
        // priority queue will create a max heap according to the timer value
        // this will make sure that recent tweet gets added on the top
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> hmTweetTimer.get(b) - hmTweetTimer.get(a));

        for (int tweet : hmpostTweet.get(userId)) {
            pq.offer(tweet);
        }
        
        for (int followee : hmFollow.get(userId)) {
            List<Integer> lst = hmpostTweet.getOrDefault(followee, new ArrayList<>());
            for (int num : lst) {
                pq.offer(num);
            }
        }
        
        List<Integer> res = new ArrayList<>();
        while (!pq.isEmpty()) {
            res.add(pq.poll());
        }
        if (res.size() > 10) {
            return res.subList(0, 10);
        }
        
        return res;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        
        if(followerId == followeeId)
            return;
        
        if(!hmFollow.containsKey(followerId)){
            hmFollow.put(followerId,new HashSet<>());
        }
        
        hmFollow.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        
        if(followerId == followeeId)
            return;
        
        if(!hmFollow.containsKey(followerId)){
            hmFollow.put(followerId,new HashSet<>());
        }
        
        Set<Integer> set = hmFollow.get(followerId);
        
        if(set.contains(followeeId)){
            set.remove(followeeId);
        }
        
        hmFollow.put(followerId,set);
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