class Twitter {
    class Tweet
    {
        int id;
        int createdAt;
        public Tweet(int id,int timestamp)
        {
            this.id=id;
            this.createdAt=timestamp;
        }
    }
    
    HashMap<Integer,List<Tweet>> tweets;
    HashMap<Integer,HashSet<Integer>> user;
    int capacity;
    int timestamp;
    /** Initialize your data structure here. */
    public Twitter() {
        user=new HashMap<>();
        tweets=new HashMap<>();
        this.capacity=10;
    
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        follow(userId,userId);
        if(!tweets.containsKey(userId))
        {
            tweets.put(userId,new ArrayList<>());
        }   
        tweets.get(userId).add(new Tweet(tweetId,timestamp++));
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq=new PriorityQueue<>((t1,t2)->t1.createdAt-t2.createdAt);
        List<Integer> result=new ArrayList<>();
        HashSet<Integer> temp=user.get(userId);
        if(temp!=null)
        {
            for(Integer i:temp)
            {
                List<Tweet> list=tweets.get(i);
                if(list!=null)
                {
                    for(Tweet t:list)
                    {
                        pq.add(t);
                        if(pq.size()>capacity)
                        {
                            pq.poll();
                        }
                    }
                }
            }

        }
        
        while(!pq.isEmpty())
        {
            Tweet t=pq.poll();
            result.add(0,t.id);
        }
        return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!user.containsKey(followerId))
        {
            user.put(followerId,new HashSet<>());
        }
        user.get(followerId).add(followeeId);
        
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(user.containsKey(followerId) && followerId!=followeeId)
        {
            user.get(followerId).remove(followeeId);
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