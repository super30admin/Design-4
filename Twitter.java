import java.util.*;

//intermediate solution using priority queue
public class Twitter {
    HashMap<Integer, HashSet<Integer>> followedMap;
    HashMap<Integer, List<Tweet>> tweetsMap;
    int time;

    public Twitter() {
        this.followedMap = new HashMap<>();
        this.tweetsMap = new HashMap<>();
        this.time = 0;
    }

    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if (!tweetsMap.containsKey(userId)) {
            tweetsMap.put(userId, new ArrayList<>());
        }

        tweetsMap.get(userId).add(new Tweet(tweetId, time));
        time++;
    }

    public List<Integer> getNewsFeed(int userId) { // O(N) where N is the number of tweets from followed users
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.time - b.time);
        HashSet<Integer> followers = followedMap.get(userId);
        if (followers != null) {
            for (int fid : followers) {
                List<Tweet> fTweets = tweetsMap.get(fid);
                if (fTweets != null) {
                    for (Tweet fTweet : fTweets) {
                        pq.add(fTweet);
                        if (pq.size() > 10) {
                            pq.poll();
                        }
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while (!pq.isEmpty()) {
            result.add(0, pq.poll().id); // pushing at front since we want recent 10 tweets
        }
        return result;
    }

    // optimize the getNewsFeed function with size
    public List<Integer> getNewsFeedWithSize(int userId) { // O(n) where n is the number of k tweets from followed users

        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.time - b.time);
        HashSet<Integer> followers = followedMap.get(userId);
        if (followers != null) {
            for (int fid : followers) {
                List<Tweet> fTweets = tweetsMap.get(fid);
                if (fTweets != null) {
                    int size = fTweets.size();
                    for (int k = size - 1; k >= 0 && k >= size - 11; k--) {
                        Tweet fTweet = fTweets.get(k);
                        pq.add(fTweet);
                        if (pq.size() > 10) {
                            pq.poll();
                        }
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while (!pq.isEmpty()) {
            result.add(0, pq.poll().id); // pushing at front since we want recent 10 tweets
        }
        return result;
    }

    public void follow(int followerId, int followeeId) { // O(1)
        if (!followedMap.containsKey(followerId)) {
            followedMap.put(followerId, new HashSet<>());
        }
        followedMap.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) { // O(1)
        if (followedMap.containsKey(followerId) && followerId != followeeId) {
            followedMap.get(followerId).remove(followeeId);
        }
    }
}

class Tweet {
    int id;
    int time;
    String content;

    public Tweet(int id, int time) {
        this.id = id;
        this.time = time;
        this.content = "";
    }
}
