class Twitter {

    // timestamp, feedsize
    int timestamp, feedSize;

    // userfollowsMap, userTweetsMap
    Map<Integer, List<Tweet>> userTweetMap;
    Map<Integer, Set<Integer>> userFollowsMap;

    /** Initialize your data structure here. */
    public Twitter() {
        // init
        timestamp = 0;
        feedSize = 10;

        userTweetMap = new HashMap<>();
        userFollowsMap = new HashMap<>();

    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        // check
        isFirstTime(userId);
        // post it

        List<Tweet> tweet = userTweetMap.get(userId);
        tweet.add(new Tweet(tweetId, timestamp++));
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        // check
        isFirstTime(userId);
        // get feeds
        List<Tweet> feeds = new ArrayList<>();

        Set<Integer> followees = userFollowsMap.get(userId);

        for(Integer followee: followees) {
            feeds.addAll(userTweetMap.get(followee));
        }

        // order by most recent
        // 3, 5
        // a, b
        Collections.sort(feeds, (a,b) -> (b.createdAt - a.createdAt));

        // get feedSize most recent tweets
        Iterator<Tweet> iterator = feeds.iterator();

        List<Integer> recentFeed = new ArrayList<>();
        int count = feedSize;

        while(count > 0 && iterator.hasNext()) {
            recentFeed.add(iterator.next().tweetId);
            count--;
        }

        return recentFeed;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        // check
        isFirstTime(followerId);
        isFirstTime(followeeId);
        // add followee
        Set<Integer> followees = userfollowsMap.get(followerId);
        followees.add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        // check
        isFirstTime(followerId);
        isFirstTime(followeeId);
        // add followee
        Set<Integer> followees = userfollowsMap.get(followerId);
        if(followerId != followeeId) {
            followees.remove(followeeId);
        }
    }

    private void isFirstTime(int userId) {
        Set<Integer> followees = userFollowsMap.get(userId);
        if(followees == null) {
            followees = new HashSet<>();
            followees.add(userId);
            userFollowsMap.put(userId, followees);
        }

        List<Tweet> tweets = userTweetMap.get(userId);
        if(tweets == null) {
            tweets = new LinkedList<>();
            userTweetMap.put(userId, tweets);
        }
    }
}

class Tweet {
    int tweetId;
    int createAt;

    Tweet(int tweetId, int createAt) {
        this.tweetId = tweetId;
        this.createAt = createAt;
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