class Twitter {
    class Tweet
    {
        int tweetId;
        int userId;
        int time;
        public Tweet(int t, int u, int time)
        {
            this.tweetId = t;
            this.userId = u;
            this.time =time;
        }
    }
    
   HashMap<Integer, List<Tweet>> userToTweets;
   HashMap<Integer, HashSet<Integer>> following; 
   int time;
    public Twitter()
    {
        userToTweets = new HashMap<>();
        following = new HashMap<>();
        time = 0;
    }
    
    public void postTweet(int userId, int tweetId) {  // O(1) time
        List<Tweet> tweets = userToTweets.getOrDefault(userId, new ArrayList<Tweet>());
        Tweet t= new Tweet(tweetId, userId,time);
        time++;
        tweets.add(t);
        userToTweets.put(userId, tweets);
        follow(userId, userId);
    }
    
    public List<Integer> getNewsFeed(int userId) {  // O(n log(k)) where k is the number of tweets needed int the news feed
        List<Integer> res= new ArrayList<>();
        if(following.containsKey(userId))
        {
        HashSet<Integer> followingList =  following.get(userId);
         if(followingList.size()>0)
         {  PriorityQueue<Tweet> pq =new PriorityQueue<>((a,b)->a.time-b.time);
             for(Integer f : followingList)
             {
                 List<Tweet> tweets = userToTweets.get(f);
                 if(tweets !=null)
                 {    
                 for(Tweet t: tweets)
                 {
                     pq.add(t);
                     if(pq.size()>10)
                         pq.poll();
                 }
                     
                }
                 
             }
          
          while(pq.size()>0)
          {
              res.add(0, pq.poll().tweetId);
          }
          
          
        }
            
        }
        
        return res;
        
    }
    
    public void follow(int followerId, int followeeId) {  // O(1) time
        HashSet<Integer> followingList = following.getOrDefault(followerId,new HashSet<Integer>());
        if(!followingList.contains(followeeId))
        {
            followingList.add(followeeId);
            following.put(followerId,followingList);
        }
    }
    
    public void unfollow(int followerId, int followeeId) {  // O(1) time
        if(following.containsKey(followerId))
        {
            HashSet<Integer> followingList = following.get(followerId);
            if(followingList.contains(followeeId))
            {
                followingList.remove(followeeId);
                following.put(followerId,followingList);
            }
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