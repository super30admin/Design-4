using System;
namespace Algorithms
{
    public class DesignTwitter
    {
        public class Twitter
        {
            HashMap<Integer, Set<Integer>> followed;
            HashMap<Integer, List<Tweet>> tweets;
            int time = 0;
            public class Tweet
            {
                Integer tweetId;
                Integer createdAt;
                public Tweet(Integer tweetId, Integer createdAt)
                {
                    this.tweetId = tweetId;
                    this.createdAt = createdAt;
                }
            }
            public Twitter()
            {
                this.followed = new HashMap<>();
                this.tweets = new HashMap<>();
            }

            public void postTweet(int userId, int tweetId)
            {
                follow(userId, userId);
                if (!tweets.containsKey(userId))
                {
                    tweets.put(userId, new ArrayList<>());
                }
                tweets.get(userId).add(new Tweet(tweetId, time++));
            }

            public List<Integer> getNewsFeed(int userId)
            {
                PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b)->a.createdAt - b.createdAt);
                Set<Integer> fIds = followed.get(userId);
                if (fIds != null)
                {
                    for (int fId: fIds)
                    {
                        List<Tweet> fTweets = tweets.get(fId);
                        if (fTweets != null)
                        {
                            for (Tweet fTweet: fTweets)
                            {
                                pq.add(fTweet);
                                if (pq.size() > 10)
                                {
                                    pq.poll();
                                }
                            }
                        }
                    }
                }
                List<Integer> result = new ArrayList<>();
                while (!pq.isEmpty())
                {
                    result.add(0, pq.poll().tweetId);
                }
                return result;
            }

            public void follow(int followerId, int followeeId)
            {
                if (!followed.containsKey(followerId))
                {
                    followed.put(followerId, new HashSet<>());
                }
                followed.get(followerId).add(followeeId);
            }

            public void unfollow(int followerId, int followeeId)
            {
                if (followed.containsKey(followerId) && followerId != followeeId)
                {
                    followed.get(followerId).remove(followeeId);
                }
            }
        }
    }
}
