class Twitter {
    class Tweet {
        int tid;
        int createdAt;
        
        public Tweet(int tid, int createdAt)
        {
            this.tid = tid;
            this.createdAt = createdAt;
        }
    }
    
    int time;
    // User ID, Followers
    Map<Integer, Set<Integer>> followers;
    // User ID, Tweets
    Map<Integer, List<Tweet>> tweets;
    
    public Twitter() {
    
        followers = new HashMap<>();
        tweets = new HashMap<>();
        
    }
    
    // O(1)
    public void postTweet(int userId, int tweetId) {
         if(!tweets.containsKey(userId))
        {
            tweets.put(userId, new ArrayList<>());
        }
        Tweet twt = new Tweet(tweetId, time++);
        tweets.get(userId).add(twt);
    }
    
    // O(nm) n no of user, no of tweets
    public List<Integer> getNewsFeed(int userId) 
    {
       
       PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> {
            return a.createdAt - b.createdAt;
        });
    
        // add userId to set to get all tweets
        follow(userId, userId); // O(1)
        // get followers
        Set<Integer> users = followers.get(userId); // O(1)
       
        for(Integer user : users) // O(n)
        {
            List<Tweet> usersTweets = tweets.get(user); // O(m)
            if(usersTweets != null)
            {
                for(Tweet twt : usersTweets)
                {
                    pq.offer(twt); // O(mn10)
                    if(pq.size() > 10)
                    {
                        pq.poll();
                    }
                }
            }
        }
        
         // get all top 10s
        List<Integer>result = new ArrayList<>();
        while(!pq.isEmpty()) // O(10)
        {
            result.add(0, pq.poll().tid);
        }
    
        return result;    
    }
    
    // O(1)
    public void follow(int followerId, int followeeId) {
        if(!followers.containsKey(followerId))
        {
            followers.put(followerId, new HashSet<>());
        }
        followers.get(followerId).add(followeeId);
    }
    
    //O(1)
    public void unfollow(int followerId, int followeeId) {
         if(followers.containsKey(followerId) && followerId != followeeId)
        {
            Set<Integer> followees = followers.get(followerId);
            if(followees.contains(followeeId))
            {
                followees.remove(followeeId);
            }
        }
    }
}