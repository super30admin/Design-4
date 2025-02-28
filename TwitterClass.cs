// SC => O(m) where m is total number of users
public class Twitter
{
    public class Tweet
    {
        public int tweetId;
        public int timestamp;
        public Tweet(int tId, int ts)
        {
            tweetId = tId;
            timestamp = ts;
        }
    }

    public class MaxComparer : IComparer<int>
    {
        public int Compare(int x, int y) => y.CompareTo(x);
    }
    Dictionary<int, HashSet<int>> userMap;
    Dictionary<int, List<Tweet>> tweetMap;
    int timer;
    PriorityQueue<Tweet, int> pQueue;
    int feedCapacity = 10;
    public Twitter()
    {
        userMap = new Dictionary<int, HashSet<int>>();
        tweetMap = new Dictionary<int, List<Tweet>>();
        pQueue = new PriorityQueue<Tweet, int>();
        timer = 0;
    }

    //TC ==> O(1)
    public void PostTweet(int userId, int tweetId)
    {
        tweetMap.TryAdd(userId, new List<Tweet>());
        tweetMap[userId].Add(new Tweet(tweetId, timer));
        timer++;
    }

    //TC ==> O(nklogk) where n is total followers and k is feedCapacity
    public IList<int> GetNewsFeed(int userId)
    {
        IList<int> result = new List<int>();
        HashSet<int> followers = new HashSet<int>();
        if (userMap.ContainsKey(userId))
        {
            followers = userMap[userId];
        }
        if (tweetMap.ContainsKey(userId))
        {
            foreach (var tweet in tweetMap[userId])
            {
                pQueue.Enqueue(tweet, tweet.timestamp);
                if (pQueue.Count > feedCapacity)
                {
                    pQueue.Dequeue();
                }
            }
        }

        foreach (var user in followers)
        {
            if (!tweetMap.ContainsKey(user))
            {
                continue;
            }
            foreach (var tweet in tweetMap[user])
            {
                pQueue.Enqueue(tweet, tweet.timestamp);
                if (pQueue.Count > feedCapacity)
                {
                    pQueue.Dequeue();
                }
            }
        }

        while (pQueue.Count > 0)
        {
            result.Add(pQueue.Dequeue().tweetId);
        }
        return result.Reverse().ToList();
    }

    //TC ==> O(1)
    public void Follow(int followerId, int followeeId)
    {
        userMap.TryAdd(followerId, new HashSet<int>());
        if (userMap[followerId].Contains(followeeId))
        {
            return;
        }
        userMap[followerId].Add(followeeId);
    }

    //TC ==> O(1)
    public void Unfollow(int followerId, int followeeId)
    {
        if (!userMap.ContainsKey(followerId) || !userMap[followerId].Contains(followeeId))
        {
            return;
        }
        userMap[followerId].Remove(followeeId);
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