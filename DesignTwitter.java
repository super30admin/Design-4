// Time Complexity : O(n), n is number of tweets of user and following users
// Space Complexity : O(k), k is number of users
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this :


class Twitter {
    class Tweet {
        int tid;
        int tweettimeStamp;
        public Tweet (int tid, int timeStamp) {
            this.tid = tid;
            this.tweettimeStamp = timeStamp;
        }
    }
    
    HashMap<Integer, HashSet<Integer>> following;
    HashMap<Integer, List<Tweet>> tweets;
    int feedSize;
    int timeStamp;

    /** Initialize your data structure here. */
    public Twitter() {
        following = new HashMap<>();
        tweets = new HashMap<>();
        this.feedSize = 10; 
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if(!tweets.containsKey(userId)) {
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId, timeStamp++));
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.tweettimeStamp - b.tweettimeStamp);
        Set<Integer> followIds = following.get(userId);
        if(followIds != null) {
            for(Integer followId : followIds) {
                List<Tweet> followTweets = tweets.get(followId);
                if(followTweets != null){
                    for(Tweet followTweet : followTweets) {
                        pq.add(followTweet);
                        if(pq.size() > feedSize) {
                            pq.poll();
                        }
                    }                 
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()) {
            Tweet t = pq.poll();
            result.add(0, t.tid);
        }
        return result; 
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!following.containsKey(followerId)) {
            following.put(followerId, new HashSet<>());
        }
        following.get(followerId).add(followeeId);
        if(!following.containsKey(followeeId)) {
            following.put(followeeId, new HashSet<>());
        }
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(following.containsKey(followerId) && followerId != followeeId) {
            following.get(followerId).remove(followeeId);
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


/*
//Output
Finished in N/A
true
2
true
3
true
6
5
7
-1
10
false

java.lang.NullPointerException
  at line 66, SkipIterator.next
  at line 111, Main.main

*/
