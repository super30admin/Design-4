class Twitter {
    
    class Tweet {
        int timestamp;  
        int tweetId;

        public Tweet(int tweetId,int timestamp) {
            this.tweetId = tweetId;                     
            this.timestamp = timestamp;             // to update recent feed 
        }
    }
    
    int timestamp, feedsize;
    Map<Integer, List<Tweet>> tweetMap;             // {user: {List of tweets}}
    Map<Integer, Set<Integer>> followeeMap;         //{user: {followee set}}
    
    public Twitter() {
        timestamp = 0;
        feedsize= 10;
        tweetMap = new HashMap<>();
        followeeMap = new HashMap<>();
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {            
        firstTimeCheck(userId);
        tweetMap.get(userId).add(new Tweet(tweetId, timestamp++));      // update timestamp after every tweet
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        firstTimeCheck(userId);
        List<Tweet> feed = new ArrayList<>();
        Set<Integer> followees = followeeMap.get(userId);               // Get followees of the user
        //sort based on timestamp
        for(int followee : followees) {
            feed.addAll(tweetMap.get(followee));                    // Add all their tweets in a list
        }       
        
        Collections.sort(feed, (a,b) -> (b.timestamp - a.timestamp));       //sort the tweets in descending order of time
        
        List<Integer> resultFeed = new ArrayList<>();
        
        for(int i = 0; i < feed.size() && i < feedsize; i++) {              //return the 10 most recent tweets
            resultFeed.add(feed.get(i).tweetId);
        }
        return resultFeed;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        firstTimeCheck(followerId);
        firstTimeCheck(followeeId);                         
        followeeMap.get(followerId).add(followeeId);                    // follow a user
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        firstTimeCheck(followerId);
        firstTimeCheck(followeeId);
        if(followeeId != followerId)    
            followeeMap.get(followerId).remove(followeeId);             // unfollow a user
    }
    
    private void firstTimeCheck(int userId) {
        if(tweetMap.get(userId) == null) {  // no tweets from this user yet, so create  mapping {userId:{}}
            tweetMap.put(userId, new ArrayList<>());
        }
        if(followeeMap.get(userId) == null) { //new user, so create mapping {userId: {userId}}
            followeeMap.put(userId, new HashSet<>());
            followeeMap.get(userId).add(userId);
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