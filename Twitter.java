import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

class Twitter {
    class Tweet {
        int id;
        int time;

        public Tweet(int id, int time) {
            this.id = id;
            this.time = time;
        }
    }

    private HashMap<Integer, HashSet<Integer>> followedMap;
    private HashMap<Integer, List<Tweet>> tweetsMap;
    private int time;

    public Twitter() {
        this.followedMap = new HashMap<>();
        this.tweetsMap = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        if (!tweetsMap.containsKey(userId)) {
            tweetsMap.put(userId, new ArrayList<>());
            follow(userId, userId);
        }
        tweetsMap.get(userId).add(new Tweet(tweetId, time));
        time++;
    }

    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.time - b.time);
        HashSet<Integer> peopleUserFollows = followedMap.get(userId);
        if (peopleUserFollows != null) {
            for (int fid : peopleUserFollows) {
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
            result.add(0, pq.poll().id);
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

    public static void main(String[] args) {
        Twitter twitter = new Twitter();

        twitter.postTweet(1, 101);

        // User 1's news feed should contain the tweet with ID 101
        List<Integer> newsFeed1 = twitter.getNewsFeed(1);
        System.out.println("User 1's news feed: " + newsFeed1); // Output: [101]

        // User 2 follows User 1
        twitter.follow(2, 1);

        // User 2's news feed should contain the tweet with ID 101 (from User 1)
        List<Integer> newsFeed2 = twitter.getNewsFeed(2);
        System.out.println("User 2's news feed: " + newsFeed2); // Output: [101]

        // User 1 posts another tweet
        twitter.postTweet(1, 102);

        // Both User 1 and User 2's news feed should contain the new tweet with ID 102
        newsFeed1 = twitter.getNewsFeed(1);
        System.out.println("User 1's news feed: " + newsFeed1); // Output: [102, 101]
        newsFeed2 = twitter.getNewsFeed(2);
        System.out.println("User 2's news feed: " + newsFeed2); // Output: [102, 101]

        // User 2 unfollows User 1
        twitter.unfollow(2, 1);

        // User 2's news feed should now only contain the tweet with ID 102
        newsFeed2 = twitter.getNewsFeed(2);
        System.out.println("User 2's news feed: " + newsFeed2); // Output: [102]
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
/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */