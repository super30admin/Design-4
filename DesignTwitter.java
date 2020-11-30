class Twitter {
    private final int FEED_LEN = 10;
    class Tweet
    {
        int tweetId;
        int timestamp;
        
        Tweet(int t, int ts)
        {
            tweetId = t;
            timestamp = ts;
        }
        
        public String toString()
        {
            return "<" + tweetId + ", " + timestamp + ">";
        }
    }
    
    class User
    {
        List<Tweet> tweets = new ArrayList<>();
        Set<Integer> following = new HashSet<>();
        
        public String toString()
        {
            return "Tweets: " + tweets.toString() + " " + "Following: " + following.toString();
        }
    }
    
    // User id -> user object
    Map<Integer, User> users;
    int currentTime = 1;
    
    /** Initialize your data structure here. */
    public Twitter() {
        users = new HashMap<>();
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        if (!users.containsKey(userId))
            users.put(userId, new User());
        
        // Append new tweet at index 0
        User u = users.get(userId);
        u.tweets.add(0, new Tweet(tweetId, currentTime++));
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
                
        PriorityQueue<Tweet> pq = new PriorityQueue<Tweet>((e1, e2) -> {
           return e2.timestamp - e1.timestamp;
        });
        
        User u = users.get(userId);
        if (u == null)
            return new ArrayList();
        
        // Add my own tweets to the priority queue; cap tweets to 10
        int len = Math.min(FEED_LEN, u.tweets.size());
        for (int i=0; i < len; i++)
            pq.add(u.tweets.get(i));

        // Add tweets of users I am following
        for (int fid: u.following)
        {
            u = users.get(fid);
            if (u == null)
                continue;
            
            len = Math.min(FEED_LEN, u.tweets.size());
            for (int i=0; i < len; i++)
                pq.add(u.tweets.get(i));
        }
        
        //System.out.println("pq.size() = " + pq.size() + " " + pq.toString());
        List<Integer> out = new ArrayList<>();
        
        int outLen = Math.min(FEED_LEN, pq.size());
        
        for (int i=0; i<outLen;  i++)
            out.add(pq.poll().tweetId);
        
        return out;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        
        if (followerId == followeeId)
            return;
        
        User u = users.get(followerId);
        if (u == null)
            users.put(followerId, new User());
        
        u = users.get(followerId);
        u.following.add(followeeId);        
        //System.out.println("follow(" + followerId + ", " + followeeId + ") " + u.toString());        
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if (followerId == followeeId)
            return;

        User u = users.get(followerId);
        if (u != null)
            u.following.remove(followeeId);        
    }
}