/**
Design a simplified version of Twitter where users can post tweets, follow/unfollow another user and 
is able to see the 10 most recent tweets in the user's news feed. Your design should support the following methods:

postTweet(userId, tweetId): Compose a new tweet.

getNewsFeed(userId): Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.

follow(followerId, followeeId): Follower follows a followee.

unfollow(followerId, followeeId): Follower unfollows a followee.

Collabedit: http://collabedit.com/awdwh
**/
class Twitter 
{

    // maps the userId to its followers
    Map<Integer, Set<Integer>> userFollowerMap;
    
    Map<Integer, List<Tweet>> tweetMapByUser;
    
    int timeIndex;
    
    
    /** Initialize your data structure here. */
    public Twitter() 
    {
        this.userFollowerMap = new HashMap<>();
        this.tweetMapByUser = new HashMap<>();
        this.timeIndex = 0;
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) 
    {
        if( !tweetMapByUser.containsKey(userId) )
        {
            tweetMapByUser.put( userId, new ArrayList<>());
        }
        
        List<Tweet> lastTweets = this.tweetMapByUser.get(userId);
        
        this.timeIndex++;
        
        Tweet tweet = new Tweet(tweetId, timeIndex);
        
        lastTweets.add(tweet);
        
        tweetMapByUser.put( userId, lastTweets);
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) 
    {
        // this is the output to be returned by from this function
        List<Integer> tweetList = new ArrayList<>();
        
        
        List<Integer> followers = this.userFollowerMap.get(userId) == null ? new ArrayList<>() : new ArrayList<>(this.userFollowerMap.get(userId));
        
        followers.add(userId);
        
        PriorityQueue<Tweet> pq = new PriorityQueue<>((t1,t2)-> t2.tweetIndex - t1.tweetIndex);
        
        for( int follower: followers)
        {
            if(tweetMapByUser.containsKey(follower))
                pq.addAll(tweetMapByUser.get(follower));
        }
        
        
        for(int i =0; i< 10; i++)
        {
            if( !pq.isEmpty())
            {
                tweetList.add( pq.poll().tweetId);
            }
        }
        
        return tweetList;
        
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) 
    {
        if(followerId == followeeId)
            return;
        
        if( !this.userFollowerMap.containsKey(followerId))
        {
            this.userFollowerMap.put( followerId, new HashSet<>());
        }
        
        this.userFollowerMap.get( followerId).add( followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) 
    {
        if(followerId == followeeId)
            return;
        
        if( !this.userFollowerMap.containsKey(followerId))
        {
            return;
        }
        
        this.userFollowerMap.get( followerId).remove( new Integer(followeeId));        
    }
}

class Tweet
{
    public int tweetId;
    public int tweetIndex;
    
    public Tweet(int tweetId, int tweetIndex)
    {
        this.tweetId = tweetId;
        this.tweetIndex = tweetIndex;
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