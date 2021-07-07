Executed in LeetCode- Yes
Time Complexity-0(m*n)
Space Complexity-o(m*n)

class Twitter {

    /** Initialize your data structure here. */
    public Twitter() {
        
    }
    HashMap<Integer,HashSet<Integer>> followMap= new HashMap<>();
    HashMap<Integer,HashSet<Integer>> tweetsMap= new HashMap<>();
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        if(!tweetsMap.containsKey(userId))
            tweetsMap.put(userId,new HashSet<>());
        tweetsMap.get(userId).add(tweetId);
        
        if(!followMap.containsKey(userId))
            followMap.put(userId,new HashSet<>());
        followMap.get(userId).add(userId);
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Integer> tweets = new PriorityQueue<>();
        for(Integer followee :followMap.get(userId)){
            for(Integer tweet :tweetsMap.get(followee)){
                tweets.add(tweet);
                if(tweets.size()>10)
                    tweets.poll();
            }
        }
       List<Integer> result = new ArrayList<>();
        for(Integer tweet: tweets){
            result.add(0,tweet);
        }
        return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!followMap.containsKey(followerId))
            followMap.put(followerId,new HashSet<>());
        followMap.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        followMap.get(followerId).remove(followeeId);
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
