# TC:
# postTweet: O(1)
# getNewsFeed: O(n)
# follow: O(1)
# unfollow: O(1)

public class Twitter
{
    Dictionary<int, IList<(int TweetId, int Time)>> userMap;
    Dictionary<int, HashSet<int>> followerMap;

    private int time;

    public Twitter()
    {
        time = 0;
        userMap = new Dictionary<int, IList<(int TweetId, int Time)>>();
        followerMap = new Dictionary<int, HashSet<int>>();
    }

    public void PostTweet(int userId, int tweetId)
    {
        if (!userMap.ContainsKey(userId))
        {
            userMap[userId] = new List<(int TweetId, int Time)>();
        }
        userMap[userId].Add((tweetId, time));
        time++;
    }

    public IList<int> GetNewsFeed(int userId)
    {
        var tweetIdList = new List<int>();
        var userIdListToRetrieve = new List<int>();
        var pq = new PriorityQueue<int, int>(Comparer<int>.Create((x, y) => y - x));

        if (!userMap.ContainsKey(userId)) return tweetIdList;

        if (followerMap.ContainsKey(userId))
        {
            userIdListToRetrieve.AddRange(followerMap[userId]);
        }
        userIdListToRetrieve.Add(userId);

        foreach (var id in userIdListToRetrieve)
        {
            for (int i = Math.Max(0, userMap[id].Count - 10); i < userMap[id].Count; ++i)
            {
                pq.Enqueue(userMap[id][i].TweetId, userMap[id][i].Time);
            }
        }

        while (tweetIdList.Count < 10 && pq.Count > 0)
        {
            tweetIdList.Add(pq.Dequeue());
        }
        return tweetIdList;
    }

    public void Follow(int followerId, int followeeId)
    {
        if (!userMap.ContainsKey(followerId))
        {
            userMap[followerId] = new List<(int TweetId, int Time)>();
        }
        if (!userMap.ContainsKey(followeeId))
        {
            userMap[followeeId] = new List<(int TweetId, int Time)>();
        }
        if (!followerMap.ContainsKey(followerId))
        {
            followerMap[followerId] = new HashSet<int>();
        }

        followerMap[followerId].Add(followeeId);
    }

    public void Unfollow(int followerId, int followeeId)
    {
        if (followerMap.ContainsKey(followerId))
        {
            followerMap[followerId].Remove(followeeId);
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
