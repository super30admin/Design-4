//  Time Complexity: O(m*n) where m is the number of users and n is the corresponding tweets
//  Space Complexity: O(m*n)

import java.util.*;

public class DesignTwitter {
    class Tweet {
        int tweetId;
        int createdAt;

        public Tweet(int tweetId, int createdAt) {
            this.tweetId = tweetId;
            this.createdAt = time;
        }
    }

    Map<Integer, HashSet<Integer>> followedMap;
    Map<Integer, List<Tweet>> tweetsMap;
    int time;

    public DesignTwitter() {
        followedMap = new HashMap<>();
        tweetsMap = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);

        if (!tweetsMap.containsKey(userId)) {
            tweetsMap.put(userId, new ArrayList<>());
        }

        tweetsMap.get(userId).add(new Tweet(tweetId, time++));
    }

    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);
        HashSet<Integer> followedIdsList = followedMap.get(userId);

        if (followedIdsList != null) {
            for (int followedId : followedIdsList) {
                //  get the tweets of that followedId
                List<Tweet> tweetsList = tweetsMap.get(followedId);

                if (tweetsList != null) {
                    for (Tweet tweet : tweetsList) {
                        pq.add(tweet);

                        if (pq.size() > 10) {
                            pq.poll();
                        }
                    }
                }
            }
        }

        List<Integer> result = new ArrayList<>();
        while (!pq.isEmpty()) {
            result.add(0, pq.poll().tweetId);
        }
        return result;
    }

    public void follow(int followerId, int followeeId) {
        if (!followedMap.containsKey(followerId)) {
            followedMap.put(followerId, new HashSet<>());
        }

        followedMap.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (followedMap.containsKey(followerId) && followerId != followeeId) {
            followedMap.get(followerId).remove(followeeId);
        }
    }
}
