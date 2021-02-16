package Design-4;

public class DesignTwitter {
    
class Twitter {
    
    class Tweet
    {
        int id;
        int createdAt;
        public Tweet(int id,int createdAt)
        {
            this.id = id;
            this.createdAt = createdAt;
        }
    }
    /** Initialize your data structure here. */
    HashMap<Integer,Set<Integer>> following;
    HashMap<Integer,List<Tweet>> tweets;
    int time;
    public Twitter() {
        following = new HashMap<>();
        tweets = new HashMap<>();
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {  //O(1)
        follow(userId,userId);
        List<Tweet> tweetList = tweets.getOrDefault(userId,new ArrayList<>());
        tweetList.add(new Tweet(tweetId,time++));
        tweets.put(userId,tweetList);
        
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) { //O(n*mlogk) n is number of users m is number of tweets per user k is 10 in our case
        // System.out.println(following);
        Set<Integer> users = following.get(userId);
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b)-> a.createdAt - b.createdAt);
        if(users!=null)
        for(int user : users)
        {
            //System.out.println(tweets);
            List<Tweet> tweetList = tweets.get(user);
            if(tweetList!=null)
            {
                for(Tweet tweet : tweetList)
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
            Tweet tweet = pq.poll();
            result.add(0,tweet.id);
        }
        return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) { // O(1)
        
        Set<Integer> list = following.getOrDefault(followerId,new HashSet<Integer>());
        list.add(followeeId);
        following.put(followerId,list);
        
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) { // O(1)
        Set<Integer> list = following.get(followerId);
        if(followerId!=followeeId && list!=null)
        {
        list.remove(followeeId);    
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
    
}
