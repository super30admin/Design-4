// Time Complexity : O(n)
// Space Complexity : O(n)

class Twitter {
    class Tweet{
        int tweetId;
        int time;
        
        public Tweet(int tweetId,int time){
            this.tweetId = tweetId;
            this.time = time;
        }
    }
    Map<Integer, Set<Integer>> following;
    Map<Integer, List<Tweet>> tweets;
    PriorityQueue<Tweet> pq;
    int time;

    /** Initialize your data structure here. */
    public Twitter() {
        following = new HashMap<>();
        tweets = new HashMap<>();
        pq = new PriorityQueue<>((a,b) -> a.time - b.time);
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        if(!tweets.containsKey(userId)){
            follow(userId, userId);
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId, time));
        time++;
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> news = new ArrayList<>();
        if(following.containsKey(userId)){
            Set<Integer> followingSet = following.get(userId);
            for(int following:  followingSet){
                if(tweets.containsKey(following)){
                    List<Tweet> followingTweets = tweets.get(following);
                    for(Tweet followingTweet: followingTweets){
                        pq.add(followingTweet);
                        if(pq.size() > 10)
                            pq.poll();
                    }
                }
            }
        }
        while(!pq.isEmpty()){
            news.add(0, pq.poll().tweetId);
        }
        return news;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!following.containsKey(followerId)){
            following.put(followerId, new HashSet<>());
        }
        following.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(following.containsKey(followerId) && followerId != followeeId){
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