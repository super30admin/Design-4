// Time Complexity : O(nlogk) where n is the number of tweets posted and k is the feedsize 
// Space Complexity : O(n) where n is the number of tweets posted
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

// Your code here along with comments explaining your approach
// HashMap(User, HashSet(User)) -> K is user and V is the set of users that user follows
// HashMap(User, ArrayList(Tweet)) -> K is user and V is the list of tweets by that user

class designTwitter {
    
    HashMap<Integer, HashSet<Integer>> following;
    HashMap<Integer, List<Tweet>> tweets;
    int timeStamp;
    int feedSize;
    
    class Tweet {
        int tweetId;
        int createdAt;
        
        public Tweet(int tweetId, int createdAt) {
            this.tweetId = tweetId;
            this.createdAt = createdAt;
        }
    }

    /** Initialize your data structure here. */
    public Twitter() {
        following = new HashMap<>();
        tweets = new HashMap<>();
        timeStamp = 0;
        feedSize = 10;
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if (!tweets.containsKey(userId)) {
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId, timeStamp++));
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be 
    posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to 
    least recent. */
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);
        Set<Integer> fIds = following.get(userId);
        if (fIds != null) {
            for (Integer fId : fIds) {
                List<Tweet> fTweets = tweets.get(fId);
                if (fTweets != null) {
                    for (Tweet fTweet : fTweets) {
                        if (pq.size() < feedSize) {
                            pq.offer(fTweet);    
                        }
                        else {
                            if (fTweet.createdAt > pq.peek().createdAt) {
                                pq.poll();
                                pq.offer(fTweet);
                            }
                        }
                    }
                }
            }
        }
        List<Integer> ans = new ArrayList<>();
        while (!pq.isEmpty()) {
            ans.add(0, pq.poll().tweetId);
        }
        return ans;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if (!following.containsKey(followerId)) {
            following.put(followerId, new HashSet<>());
        }    
        following.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if (following.containsKey(followerId) && followerId != followeeId) {
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