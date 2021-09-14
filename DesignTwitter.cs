using System;
using System.Collections.Generic;
using System.Text;

namespace Design
{
    class DesignTwitter
    {
        class Twitter
        {
            class Tweet
            {
                internal int tid;
                internal int createdAt;
                public Tweet(int tid, int createdAt)
                {
                    this.tid = tid;
                    this.createdAt = createdAt;
                }
            }

            Dictionary<int, HashSet<int>> followers; //follow unfollow
            Dictionary<int, List<Tweet>> tweet; // Tweets
            int time;
            //Initialize your datastrucure here
            public Twitter()
            {
                followers = new Dictionary<int, HashSet<int>>();
                tweet = new Dictionary<int, List<Tweet>>();
            }
            //Compose a new tweet
            public void PostTweet(int userId, int tweetId) //TC O(1)
            {
                Follow(userId, userId);
                if (!tweet.ContainsKey(userId))
                {
                    tweet.Add(userId, new List<Tweet>());
                }
                tweet[userId].Add(new Tweet(tweetId, time++));
            }

            //Retrieve the 10 most recent tweet ids in the user's new feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be orderd from most recent to least recent
            public List<int> GetNewsFeed(int userId)//TC: O(nk) SC: (U+T) users + tweets
            {
                PriorityQueue<Tweet> pq = new PriorityQueue<Tweet>();
                //C# do not have the implementation of PriorityQueue
                //PriorityQueue<Tweet> pq = new PriorityQueue<Tweet>((a, b) => a.createdAt - b.createdAt);
                HashSet<int> set = followers[userId];
                if (set != null)
                {
                    foreach (int user in set)
                    {
                        List <Tweet> tweets = tweet[user];
                        if (tweets != null)
                        {
                            foreach (Tweet tw in tweets)
                            {
                                pq.Enqueue(tw);
                                if (pq.Count > 10)
                                {
                                    pq.Dequeue();
                                }
                            }
                        }
                    }
                }
                List<int> result = new List<int>();
                while (pq.Count != 0)
                {
                    result.Insert(0, pq.Dequeue().tid);
                }
                return result;
            }

            //Follower follows a followee. If the operation is invalid, it should be a no-op
            public void Follow(int followerId, int followeeId) //TC O(1)
            {
                if (!followers.ContainsKey(followerId))
                {
                    followers.Add(followerId, new HashSet<int>());
                }
                followers[followerId].Add(followeeId);
            }
            //Follower unfollows a followee. If the operation is invalid, it should be a no-op
            public void UnFollow(int followerId, int followeeId) //TC O(1)
            {
                if (!followers.ContainsKey(followerId))
                {
                    if (followers[followerId].Contains(followeeId))
                    {
                        followers[followerId].Remove(followeeId);
                    }
                }
            }
        }
    }
}
