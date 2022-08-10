class Twitter {
    class Tweet
    {
        int id;
        int createdAt;
        public Tweet(int id, int time)
        {
            this.id=id;
            this.createdAt=time;
        }
    }
    HashMap<Integer,HashSet<Integer>> users;
    HashMap<Integer,List<Tweet>> tweets;
    int time;



    public Twitter() {
        users=new HashMap<>();
        tweets=new HashMap<>();

    }
    // 0(1)
    public void postTweet(int userId, int tweetId) {
        if(!tweets.containsKey(userId))
        {
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId, time++));
    }
    //0(N) where N IS LENGTH OF TWEETS
    public List<Integer> getNewsFeed(int userId) {
        follow(userId,userId);
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b)->a.createdAt-b.createdAt);
        HashSet <Integer> followedUser = users.get(userId);
        for(int people:followedUser)
        {
            List<Tweet> allTweets = tweets.get(people);
            if(allTweets!=null)
            {
                for(Tweet tweet : allTweets)
                {
                    pq.add(tweet);
                    if(pq.size()>10)
                    {
                        pq.poll();
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty())
        {
            result.add(0,pq.poll().id);
        }
        return result;



    }
    // 0(1)
    public void follow(int followerId, int followeeId) {
        if(!users.containsKey(followerId))
        {
            users.put(followerId, new HashSet<>());
        }
        users.get(followerId).add(followeeId);
    }
    // 0(1)
    public void unfollow(int followerId, int followeeId) {
        if(users.containsKey(followerId) && followerId!=followeeId)
        {
            users.get(followerId).remove(followeeId);
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