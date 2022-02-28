import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

// TC O(Nlogk)
// SC O(N)
public class Twitter {
    class Tweet {
        int id;
        int createdAt;

        public Tweet(int id, int createdAt) {
            this.id = id;
            this.createdAt = createdAt;
        }
    }

    HashMap<Integer, HashSet<Integer>> followed;
    HashMap<Integer, List<Tweet>> tweets;
    int time;

    public Twitter() {
        followed = new HashMap<>();
        tweets = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if (!tweets.containsKey(userId)) {
            tweets.put(userId, new ArrayList<Tweet>());
        }
        tweets.get(userId).add(new Tweet(tweetId, time++));
    }

    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<Tweet>((a, b) -> a.createdAt - b.createdAt);
        HashSet<Integer> set = followed.get(userId);
        if (set != null) {
            for (Integer follower : set) {
                List<Tweet> tweetList = tweets.get(follower);
                if (tweetList != null) {
                    for (Tweet tweet : tweetList) {
                        pq.add(tweet);
                        if (pq.size() > 10) {
                            pq.poll();
                        }
                    }
                }
            }
        }
        List<Integer> res = new ArrayList<>();
        while (!pq.isEmpty()) {
            res.add(0, pq.poll().id);
        }
        return res;
    }

    public void follow(int followerId, int followeeId) {
        if (!followed.containsKey(followerId)) {
            followed.put(followerId, new HashSet<>());
        }
        followed.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (followed.containsKey(followerId) && followerId != followeeId) {
            followed.get(followerId).remove(followeeId);
        }

    }
}
