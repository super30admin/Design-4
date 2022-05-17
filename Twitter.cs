using System.Collections.Generic;
using System.Collections;
namespace Design
{
    public class Twitter
    {
        /*
         * T.C: O(m*nLog10) where m is number of users , n is number of tweets and log 10 is becuase used the Min heap of 10 size
         * 
         */
        public class Tweet
        {
            public int Id;
            public int time;
            public Tweet(int id, int time)
            {
                this.Id = id;
                this.time = time;
            }
        }

        Dictionary<int, List<Tweet>> tweets;
        Dictionary<int, HashSet<int>> map;
        int time;

        PriorityQueue<Tweet, int> pq;

        public Twitter()
        {
            tweets = new Dictionary<int, List<Tweet>>();
            map = new Dictionary<int, HashSet<int>>();
            pq = new PriorityQueue<Tweet, int>();
        }

        public void PostTweet(int userId, int tweetId)
        {
            Tweet newtweet = new Tweet(tweetId, time++);
            if (!tweets.ContainsKey(userId))
            {
                tweets.Add(userId, new List<Tweet>());
            }
            tweets[userId].Add(newtweet);
        }

        public IList<int> GetNewsFeed(int userId)
        {
            Follow(userId, userId);
            HashSet<int> users = map[userId];
            foreach (int user in users)
            {
                if (tweets.ContainsKey(user))
                {
                    List<Tweet> allTweets = tweets[user];

                    foreach (Tweet twt in allTweets)
                    {
                        pq.Enqueue(twt, twt.time);

                        if (pq.Count > 10)
                        {
                            pq.Dequeue();
                        }
                    }
                }
            }

            IList<int> result = new List<int>();
            while (pq.Count != 0)
            {
                Tweet feed = pq.Dequeue();
                result.Insert(0, feed.Id);
            }

            return result;
        }

        public void Follow(int followerId, int followeeId)
        {
            if (!map.ContainsKey(followerId))
            {
                map.Add(followerId, new HashSet<int>());
            }

            map[followerId].Add(followeeId);
        }

        public void Unfollow(int followerId, int followeeId)
        {
            if (map.ContainsKey(followerId))
            {
                map[followerId].Remove(followeeId);
            }
        }
    }
}
