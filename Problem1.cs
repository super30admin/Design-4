// Time Complexity : Follow - O(1), Unfollow - O(1), GetNewsFeed - O(nlog10) where n is the top 10 tweets of each user(if a user has posted atleast 10 tweets or all tweets of the user)
// Space Complexity : O(t+f+u) where t = tweets, f = followers, u = users
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

public class Tweet
{
    public int tweetId;

    public DateTime timestamp;

    public Tweet(int tweetId)
    {
        this.tweetId = tweetId;
        this.timestamp = DateTime.UtcNow;
    }
}

public class Twitter {

    public Dictionary<int, HashSet<int>> followMap;

    public Dictionary<int, List<Tweet>> tweetMap;

    public Twitter() {
        followMap = new();
        tweetMap = new();
    }
    
    public void PostTweet(int userId, int tweetId) {
        Tweet userTweet = new Tweet(tweetId);
        
        if(!tweetMap.ContainsKey(userId))
        {
            tweetMap[userId] = new List<Tweet>();
            Follow(userId, userId);
        }

        tweetMap[userId].Add(userTweet);
    }
    
    public IList<int> GetNewsFeed(int userId) {
        List<int> newsFeed = new();
        
        if(!followMap.ContainsKey(userId))
        {
            return newsFeed;
        }

        HashSet<int> followers = followMap[userId];
        PriorityQueue<int,DateTime> minHeap = new();

        foreach(int follower in followers)
        {
            if(tweetMap.ContainsKey(follower))
            {
                List<Tweet> followerTweets = tweetMap[follower];
                int size = followerTweets.Count;
                
                for(int i = size-1; i>=0 && i>=size-10; i--)
                {
                    minHeap.Enqueue(followerTweets[i].tweetId, followerTweets[i].timestamp);
                    
                    if(minHeap.Count>10)
                    {
                        minHeap.Dequeue();
                    }
                }
            }
        }

        while(minHeap.Count>0)
        {
            newsFeed.Add(minHeap.Dequeue());
        }

        newsFeed.Reverse();
        return newsFeed;
    }
    
    public void Follow(int followerId, int followeeId) {
        if(!followMap.ContainsKey(followerId))
        {
            followMap[followerId] = new HashSet<int>();
        }

        followMap[followerId].Add(followeeId);
    }
    
    public void Unfollow(int followerId, int followeeId) {
        if(followMap.ContainsKey(followerId))
        {
            HashSet<int> followers = followMap[followerId];

            if(followers.Contains(followeeId))
            {
                followers.Remove(followeeId);
            }
        }
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.PostTweet(userId,tweetId);
 * IList<int> param_2 = obj.GetNewsFeed(userId);
 * obj.Follow(followerId,followeeId);
 * obj.Unfollow(followerId,followeeId);
 */