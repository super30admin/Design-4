//Time Complexity:
// postTweet(), follow(), unfollow() -> O(1)
// getNewsFeed() -> O(mLogn) n: no of tweets & m: no of users

//Space Complexity: O(m+n) 


class Twitter {
    
    
    class Tweet
    {
        int tweetId;
        int createdAt;
        
        Tweet( int tweetId, int createdAt)
        {
            this.tweetId = tweetId;
            this.createdAt = createdAt;
        }
    }
    

    //userid ==> list of tweets
    HashMap<Integer, List<Tweet>> userTweetMap = new HashMap<>();
    
    //user Followers map
    HashMap<Integer, Set<Integer>> userFollowMap = new HashMap<>();
    
    int timeStamp;
    int feedSize;
    
    /** Initialize your data structure here. */
    public Twitter() {
        timeStamp = 0;
        feedSize = 10;
        
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        
        isFirstTime(userId);
        Tweet tweet = new Tweet(tweetId, timeStamp++);
        userTweetMap.get(userId).add(tweet);
        
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        isFirstTime(userId);
       
        List<Tweet> feeds = new ArrayList<>();
        Set<Integer> allFollowers = userFollowMap.get(userId);
        
        for(Integer users: allFollowers)
        {
            feeds.addAll(userTweetMap.get(userId)); // all the tweets from the followees
        }
        
        //sorting the list of feeds by latest tweets
        Collections.sort(feeds, (a,b) -> (b.createdAt - a.createdAt));
        
        Iterator<Tweet> itr = feeds.iterator();
        
        List<Integer> recentFeed = new ArrayList<>();
        
        int count = feedSize;
        
        while(count > 0 && itr.hasNext())
        {
            recentFeed.add(itr.next().tweetId);
            count --;
        }
        
        return recentFeed;
    }
    

    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        isFirstTime(followerId);
        isFirstTime(followeeId);
        userFollowMap.get(followeeId).add(followerId);
        
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        isFirstTime(followerId);
        isFirstTime(followeeId);
        userFollowMap.get(followeeId).remove(followerId);
        
    }
    
    
    
    //custom method 
    public void isFirstTime(int userId)
    {
        if(userTweetMap.get(userId)==null)
        {
            userTweetMap.put(userId, new ArrayList<Tweet>());
        }
        
        if(userFollowMap.get(userId)== null)
        {
            Set<Integer> followeeSet = new HashSet<>();
            followeeSet.add(userId);
            userFollowMap.put(userId, followeeSet);
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
