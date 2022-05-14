import java.util.*;

public class Problem1 {
    class Twitter {
        int time;

        public class Tweet {
            int id, time;

            Tweet(int id, int createdAt) {
                this.id = id;
                this.time = createdAt;
            }
        }

        HashMap<Integer, HashSet<Integer>> followerMap;
        HashMap<Integer, List<Tweet>> tweets;

        public Twitter() {
            followerMap = new HashMap<>();
            tweets = new HashMap<>();
        }

        // TC : O (1)
        // SC : O (1)
        public void postTweet(int userId, int tweetId) {
            if (!tweets.containsKey(userId)) {
                tweets.put(userId, new ArrayList<>());
            }
            tweets.get(userId).add(new Tweet(tweetId, time++));
        }

        // TC : O (m*t*log10)
        // SC : O (1)
        // where m = user , t = tweets
        public List<Integer> getNewsFeed(int userId) {
            follow(userId, userId);
            PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.time - b.time);
            HashSet<Integer> users = followerMap.get(userId);
            for (int user : users) {
                List<Tweet> allTweet = tweets.get(user);
                if (allTweet != null) {
                    for (Tweet tw : allTweet) {
                        pq.add(tw);
                        if (pq.size() > 10) {
                            pq.poll();
                        }
                    }
                }
            }
            List<Integer> result = new ArrayList<>();
            while (!pq.isEmpty()) {
                result.add(0, pq.poll().id);
            }
            return result;
        }

        // TC : O (1)
        // SC : O (1)
        public void follow(int followerId, int followeeId) {
            if (followerMap.containsKey(followerId)) {
                followerMap.put(followerId, new HashSet<>());
            }
            followerMap.get(followerId).add(followeeId);
        }

        // TC : O (1)
        // SC : O (1)
        public void unfollow(int followerId, int followeeId) {
            if (followerId != followeeId && followerMap.containsKey(followerId)) {
                followerMap.get(followerId).remove(followeeId);
            }
        }
    }
}
