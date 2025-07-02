/*
  Time complexity: Post tweet:O(1); Follow:O(1); Unfollow:O(1); NewsFeed: O(n)
  Space complexity: O(u*n) where u is the number of users and n is the number of tweets

*/

public class Tweet
{
    public int tid;
    public int createdAt;

    public Tweet(int id, int time)
    {
        tid = id;
        createdAt = time;
    }
}

public class Twitter {
    Dictionary<int, HashSet<int>> userFollow;
    Dictionary<int, List<Tweet>> tweets;
    int time;

    public Twitter() {
        userFollow = new();
        tweets = new();
    }
    
    public void PostTweet(int userId, int tweetId) {
        Tweet tweet = new Tweet(tweetId,time);
        if(!tweets.ContainsKey(userId))
        {
            tweets.Add(userId, new List<Tweet>());
        }
        tweets[userId].Add(tweet);
        time++;
    }
    
    public IList<int> GetNewsFeed(int userId) {
        HashSet<int> users = new();
        List<int> result = new();
        PriorityQueue<int,int> topTweets = new();

        if(userFollow.ContainsKey(userId))
            users = userFollow[userId];

        users.Add(userId);

        foreach(int user in users)
        {
            if(tweets.ContainsKey(user))
            {
                int count = tweets[user].Count-1;
                for(int i = count; i>=0 && i>=count-10;i--)
                {
                    topTweets.Enqueue(tweets[user][i].tid, tweets[user][i].createdAt);
                    if(topTweets.Count>10)
                        topTweets.Dequeue();
                }
            }
        }

        while(topTweets.Count>0)
        {
            result.Insert(0,topTweets.Dequeue());
        }
        
        return result;
    }
    
    public void Follow(int followerId, int followeeId) {
        if(!userFollow.ContainsKey(followerId))
        {
            userFollow.Add(followerId, new HashSet<int>());
        }
        userFollow[followerId].Add(followeeId);
    }
    
    public void Unfollow(int followerId, int followeeId) {
        if(userFollow.ContainsKey(followerId))
            userFollow[followerId].Remove(followeeId);
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
