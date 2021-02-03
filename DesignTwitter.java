// Time Complexity : O(n * m * log(k)) --> where n is number of followers of a current user and m is the average number of tweets by each user being followed and k is the feedsize
// Space Complexity : O(k) --> where m is number of tweets
// Did this code successfully run on Leetcode (355): Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach

class Twitter {
    
    class Tweet{
        int id;
        int createdAt;
        public Tweet(int id, int timeStamp) {
            this.id = id;
            this.createdAt = timeStamp;
        }
    }
    
    HashMap<Integer, HashSet<Integer>> followed;
    HashMap<Integer, List<Tweet>> tweets;
    int feedSize;
    int timeStamp;
    
    /** Initialize your data structure here. */
    public Twitter() {
        followed = new HashMap<>();
        tweets = new HashMap<>();
        feedSize = 10;
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        // follow myself
        follow(userId, userId);
        if (tweets.get(userId) == null) tweets.put(userId, new ArrayList<>());
        tweets.get(userId).add(new Tweet(tweetId, timeStamp++));
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((t1, t2) -> t1.createdAt - t2.createdAt);
        Set<Integer> fIds = followed.get(userId);   
        if (fIds != null) {
            for (Integer fId : fIds) {
                List<Tweet> fTweets = tweets.get(fId);
                if (fTweets != null) {
                    for (Tweet fTweet : fTweets) {
                        pq.add(fTweet);
                        // remove the lowest timestamp tweet if size > feedSize
                        if (pq.size() > feedSize) pq.poll();
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while (!pq.isEmpty()) {
            Tweet t = pq.poll();
            result.add(0, t.id);
        }
        return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        // userId --> to people I am following
        if (!followed.containsKey(followerId)) followed.put(followerId, new HashSet<>());
        followed.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if (followed.containsKey(followerId) && followerId != followeeId) {
            // get the follower and remove followee from its set 
            followed.get(followerId).remove(followeeId);
        }
    }
}