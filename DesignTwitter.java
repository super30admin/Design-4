class Twitter {
    class Tweet
    {
        int tweetId;
        int createdAt;
        Tweet(int tweetId, int createdAt)
        {
            this.tweetId = tweetId;
            this.createdAt = createdAt;
        }
    }

    HashMap<Integer, HashSet<Integer>> followed;
    HashMap<Integer, List<Tweet>> tweets;
    int time;
    
    /** Initialize your data structure here. */
    public Twitter() {
        followed = new HashMap();
        tweets = new HashMap();
        time = 0;
    }
     //TC: O(1)
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        // make user follow himself/herself so that while getNewsFeed is called, his tweets are also included
         follow(userId, userId);
        
        if (!tweets.containsKey(userId))
        {
            tweets.put(userId, new ArrayList());
        }
        tweets.get(userId).add(new Tweet(tweetId, time));
        time++;
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> result = new ArrayList();
        PriorityQueue<Tweet> minHeap = new  PriorityQueue<Tweet>((o1,o2) -> o1.createdAt - o2.createdAt);
            HashSet<Integer> followees = followed.get(userId);
            if (followees != null )
            {
                for (Integer followee : followees)
                {
                   List<Tweet> followeeTweetsList = tweets.get(followee);
                    if (followeeTweetsList != null)
                    {
                        for ( Tweet followeeTweet : followeeTweetsList)
                        {
                            minHeap.add(followeeTweet);
                            if ( minHeap.size() > 10)
                                minHeap.poll();
                        }
                    }
                }
            }
        
        while ( !minHeap.isEmpty())
            result.add(0, minHeap.poll().tweetId);
        return result;
    }
   //TC: O(1) 
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if (!followed.containsKey(followerId))
        {
            followed.put(followerId, new HashSet());
        }
        followed.get(followerId).add(followeeId);
    }
    
     //TC: O(1)
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if (followed.containsKey(followerId) && followerId != followeeId)
        {
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
