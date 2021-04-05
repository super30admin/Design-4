// Time Complexity : O(1) for follow(), unfollow() and postTweet() and O(N * K * log10) for getNewsFeed() 
// Space Complexity : O(N * K)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : NA


// Your code here along with comments explaining your approach

class Twitter {
    
    class Tweet {
        
        int tweetId;
        int creationTime;
        
        public Tweet(int tweetId, int creationTime) {
            
            this.tweetId = tweetId;
            this.creationTime = creationTime;
        }
        }
        
    HashMap<Integer, HashSet<Integer>> followed;
    HashMap<Integer, List<Tweet>> tweets;
    int time;
    

    /** Initialize your data structure here. */
    public Twitter() {
        
        followed = new HashMap<>();
        tweets = new HashMap<>();
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        
        if(!tweets.containsKey(userId)) {
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId, time++));
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        
        follow(userId, userId);
        
        PriorityQueue<Tweet> pq = new PriorityQueue<>( (a, b) -> a.creationTime - b.creationTime);
        Set<Integer> fids = followed.get(userId);
        if(fids != null) {
            for(int fid: fids) {
                List<Tweet> tws = tweets.get(fid);
                if(tws != null) {
                    for(Tweet tw : tws) {
                        pq.add(tw);
                        if(pq.size() > 10) pq.remove();
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        
        while(!pq.isEmpty()) {
            result.add(0, pq.poll().tweetId);
        }
        return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        
        if(!followed.containsKey(followerId)) {
            followed.put(followerId, new HashSet<>());
        }
        followed.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        
        if(followed.containsKey(followerId)) {
            followed.get(followerId).remove(followeeId);
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