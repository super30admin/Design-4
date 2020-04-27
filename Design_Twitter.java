import java.util.*;

class Twitter {
    int feedSize = 10;
    int timeStamp = 0;
    
    class Tweet {
        int id;
        int createdAt;
        
        public Tweet(int id, int createdAt)
        {
            this.id=id;
            this.createdAt = createdAt;
        }
    }
    Map<Integer, Set<Integer>> followers;
    Map<Integer, List<Tweet>> tweetMap;

    /** Initialize your data structure here. */
    public Twitter() {
        followers = new HashMap<>();
        tweetMap = new HashMap<>();      
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if(!tweetMap.containsKey(userId))
        {
            tweetMap.put(userId, new ArrayList<>());
        }
        
        tweetMap.get(userId).add(new Tweet(tweetId, timeStamp++));
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);
        Set<Integer> fIds = followers.get(userId);
        if(fIds != null)
        {
            for(int fId: fIds)
            {
                List<Tweet> fTweets = tweetMap.get(fId);
                if(fTweets != null)
                {
                    for(Tweet fTweet: fTweets)
                    {
                        if(pq.size() < feedSize)
                            pq.offer(fTweet);
                        else
                        {
                            if(pq.peek().createdAt < fTweet.createdAt)
                            {
                                pq.poll();
                                pq.offer(fTweet);
                            }
                        }
                    }
                }
            } 
        }
        
        List<Integer> result=new ArrayList<>();
        while(!pq.isEmpty())
        {
            result.add(0, pq.poll().id);
        }
        return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!followers.containsKey(followerId))
        {
            followers.put(followerId, new HashSet<>());
        }
        followers.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(followers.containsKey(followerId) && followerId != followeeId)
        {
            followers.get(followerId).remove(followeeId);
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