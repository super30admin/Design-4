import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

class Twitter {
    int time;
    Map<Integer, HashSet<Integer>> userToFolloweesMap;
    Map<Integer, ArrayList<Tweet>> userToTweetMap;
    int tweetLimit;

    class Tweet {
        int timeStamp;
        int id;

        public Tweet(int id) {
            this.id = id;
            this.timeStamp = time;
        }
    }

    public Twitter() {
        this.time = 0;
        this.userToFolloweesMap = new HashMap();
        this.userToTweetMap = new HashMap();
        this.tweetLimit = 10;
    }

    // O(1)
    public void postTweet(int userId, int tweetId) {
        if (!userToTweetMap.containsKey(userId)) {
            userToTweetMap.put(userId, new ArrayList<Tweet>());
        }

        // follow yourself if you post any tweets
        follow(userId, userId);

        userToTweetMap.get(userId).add(new Tweet(tweetId));
        time++;
    }

    // knlog(k) k = tweets required; n = number of followees
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> res = new ArrayList();
        if (!userToFolloweesMap.containsKey(userId)) {
            return res;
        }

        HashSet<Integer> followees = userToFolloweesMap.get(userId);

        Queue<Tweet> heap = new PriorityQueue<Tweet>((t1, t2) -> t2.timeStamp - t1.timeStamp);

        for (int user : followees) {
            // no tweets by the followee
            if (!userToTweetMap.containsKey(user)) {
                continue;
            }
            // tweets by this particular user
            List<Tweet> tweets = userToTweetMap.get(user);
            if (tweets == null)
                continue;
            for (int i = tweets.size() - 1; i >= 0 && i >= tweets.size() - tweetLimit; i--) {
                heap.add(tweets.get(i));
            }
        }

        int tweetCount = 0;

        while (!heap.isEmpty() && tweetCount < 10) {
            res.add(heap.poll().id);
            tweetCount++;
        }

        return res;
    }

    // O(1)
    public void follow(int followerId, int followeeId) {
        if (!userToFolloweesMap.containsKey(followerId)) {
            userToFolloweesMap.put(followerId, new HashSet());
        }
        userToFolloweesMap.get(followerId).add(followeeId);
    }

    // O(1)
    public void unfollow(int followerId, int followeeId) {
        if (!userToFolloweesMap.containsKey(followerId) || !userToFolloweesMap.get(followerId).contains(followeeId)) {
            return;
        }
        userToFolloweesMap.get(followerId).remove(followeeId);
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */