// Time Complexity : O(1) for all the opertaion
// Space Complexity : O(n) for tweets table and O(1) for all other operations
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach
public class Twitter {
    
    class Tweet
    {
        int id;
        int createdAt;
        Tweet(int id, int createdAt)
        {
            this.id = id;
            this.createdAt = createdAt;
        }
    }
    int timestamp;
    int feedSize;
    HashMap<Integer,HashSet<Integer>> network;
    HashMap<Integer,List<Tweet>> tweets;

    /** Initialize your data structure here. */
    public Twitter() {
        timestamp = 0;
        feedSize=10;
        network = new HashMap<>();
        tweets = new HashMap<>();
        
        
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        
        if(!network.containsKey(userId))
        {
            network.put(userId, new HashSet<Integer>());
        }
        network.get(userId).add(userId);
        if(!tweets.containsKey(userId))
        {
            tweets.put(userId,new ArrayList<Tweet>() );
        }
        tweets.get(userId).add(new Tweet(tweetId,++timestamp));
        
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((t1,t2)->t1.createdAt-t2.createdAt);
        HashSet<Integer> uids = network.get(userId);
        if(uids!=null)
        {
            for(Integer id:uids)
            {
                List<Tweet> t = tweets.get(id);
                if(t!=null)
                {
                    for(Tweet tw:t)
                    {
                        if(pq.size()<feedSize)
                        {
                            pq.add(tw);
                        }
                        else
                        {
                            if(pq.peek().createdAt < tw.createdAt)
                            {
                                pq.poll();
                                pq.add(tw);
                            }
                        }
                    }
                }
            }
        }
        List<Integer> output = new ArrayList<>();
        while(!pq.isEmpty())
        {
            output.add(0,pq.poll().id);
        }
        return output;
        
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        
        if(!network.containsKey(followerId))
        {
            network.put(followerId, new HashSet<Integer>());
        }
        network.get(followerId).add(followerId);
         network.get(followerId).add(followeeId);
        
        
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        
        if(network.containsKey(followerId) && followerId!=followeeId)
        {
            network.get(followerId).remove(followeeId);
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
 */class Twitter {
    
}