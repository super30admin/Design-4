/** Design a simplified version of Twitter where users can post tweets, follow/unfollow another user and is able to see the 10 most recent tweets in the user's news feed.
* TC O(nklog10) - All operations except the getNewsFeed is O(1).  SC O(max (2 hashmaps))
*/
class Twitter {

    class Tweet {
        int tweetId;
        int creationTime;
        public Tweet(int tweetId, int creationTime){
            this.tweetId = tweetId;
            this.creationTime = creationTime;
        }
    }
        
    int time;
    HashMap<Integer, List<Tweet>> tweets;
    HashMap<Integer, HashSet<Integer>> followers;
    /** Initialize your data structure here. */
    public Twitter() {
        tweets = new HashMap<>();
        followers = new HashMap<>();
        //time = 0;
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        if (!tweets.containsKey(userId)) {
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId, time++));
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        follow(userId, userId);
        Set<Integer> followerIds = followers.get(userId);
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.creationTime - b.creationTime);
        if (followerIds != null) {
            for(Integer followerId : followerIds){
                List<Tweet> tweetList = tweets.get(followerId);
                if (tweetList != null) {
                    for (Tweet tweet : tweetList) {
                        pq.add(tweet);
                        if (pq.size() > 10) {
                            pq.remove();
                        }
                    }   
                }
            }
        }
      
        List<Integer> latest = new ArrayList<>();
        while(!pq.isEmpty()) {
            latest.add(0, pq.poll().tweetId);
        }
        return latest;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if (!followers.containsKey(followerId)) {
            followers.put(followerId, new HashSet<>());
        }
        followers.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
         if (followers.containsKey(followerId)) {
            followers.get(followerId).remove(followeeId);
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
