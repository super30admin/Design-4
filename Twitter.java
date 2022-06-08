class Twitter 
{
    int time = 0;
    
    Map<Integer,Set<Integer>> follows; // stores user -> [list of users he follows]
    Map<Integer, List<Tweet>> tweets;
    

    public Twitter() 
    {
        this.follows = new HashMap<>();
        this.tweets = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) 
    {
        follow(userId,userId);
        Tweet tweet = new Tweet(userId,tweetId,time);
        
        if(!tweets.containsKey(userId))
        {
            tweets.put(userId, new ArrayList<>());
        }
        
        List<Tweet> userTweets = tweets.get(userId);
        userTweets.add(tweet);
        
        time++;
    }
    
    public List<Integer> getNewsFeed(int userId) 
    {
        List<Integer> output = new ArrayList<>();
        Set<Integer> following = follows.get(userId);
        
        if(following == null)
            following = new HashSet<>();
        
        PriorityQueue<Tweet> queue = new PriorityQueue<>((a,b)->b.time-a.time);
        
        for(int userFollowed: following)
        {
            List<Tweet> twList = tweets.get(userFollowed);
            
            if(twList == null)
                twList = new ArrayList<>();
            
            for(Tweet tw:twList)
                queue.add(tw);
        }
        
        int count = 0;
        
        while(count <10 && !queue.isEmpty())
        {
            output.add(queue.poll().tweetId);
            count++;
        }
        
        return output;
    }
    
    public void follow(int followerId, int followeeId) 
    {
        if(!follows.containsKey(followerId))
            this.follows.put(followerId, new HashSet<>());
        
        this.follows.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) 
    {
        if(!follows.containsKey(followerId))
            return;
        
        follows.get(followerId).remove(followeeId);
    }
}
class Tweet
{
    int userId;
    int tweetId;
    int time;
        
    public Tweet(int userId, int tweetId, int time)
    {
        this.userId = userId;
        this.tweetId = tweetId;
        this.time = time;
    }
}