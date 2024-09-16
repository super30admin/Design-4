class Twitter {
    int currtime;
    int feedSize;
    Map<Integer, List<Tweet>> userTweetMap;
    Map<Integer, Set<Integer>> userFollowerMap;
    
    public Twitter() {
        userTweetMap = new  HashMap<Integer, List<Tweet>>();
        userFollowerMap = new HashMap<Integer, Set<Integer>>();
        feedSize = 10;
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        isFirstTime(userId);
        List<Tweet> tweets = userTweetMap.get(userId);
        tweets.add(new Tweet(tweetId, ++currtime));
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        isFirstTime(userId);
        Set<Integer> followees = userFollowerMap.get(userId);
        List<Tweet> allTweets = new ArrayList<>();
        for(Integer id: followees){
            allTweets.addAll(userTweetMap.get(id));
        }

        Collections.sort(allTweets, (a, b) -> b.timestamp - a.timestamp);

        List<Integer> feeds = new ArrayList<>();
        int count = feedSize;
        Iterator<Tweet> iterator = allTweets.iterator();
        while(count > 0 && iterator.hasNext()){
            feeds.add(iterator.next().tweetId);
            count--;
        }
        
        return feeds;
    
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        isFirstTime(followerId);
        isFirstTime(followeeId);
        Set<Integer> followees = userFollowerMap.get(followerId);
        followees.add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        // user can't unfollow himself
        if(followerId == followeeId) return;
        
        isFirstTime(followerId);
        isFirstTime(followeeId);
        Set<Integer> followees = userFollowerMap.get(followerId);
        followees.remove(followeeId);
    }
    
    /** Adds a user if it doesnt exist-Avoids explicit null checks */
    private void isFirstTime(int userId){
        List<Tweet> tweets = userTweetMap.get(userId);
        if(tweets == null){
            userTweetMap.put(userId, new LinkedList<Tweet>());
        }
        Set<Integer> followees = userFollowerMap.get(userId);
        if(followees == null){
            followees = new HashSet<Integer> ();
            userFollowerMap.put(userId, followees);
            followees.add(userId);
        }
        
    }
}

class Tweet{
    int tweetId;
    int timestamp;
    Tweet(int tweetId, int timestamp){
        this.tweetId = tweetId;
        this.timestamp = timestamp;
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