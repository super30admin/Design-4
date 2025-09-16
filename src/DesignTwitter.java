import java.util.*;
public class DesignTwitter {
    HashMap<Integer, HashSet<Integer>> userMap;  // users to followees
    HashMap<Integer, List<Tweet>> tweetMap; // users to tweets
    int time;

    class Tweet {
        int tweetId;
        int timeStamp;

        public Tweet(int id, int time) {
            this.tweetId = id;
            this.timeStamp = time;
        }
    }
    public DesignTwitter() {
        this.userMap = new HashMap<>();
        this.tweetMap = new HashMap<>();
        this.time = 0;
    }
    public void postTweet(int userId, int tweetId) {
        Tweet tweet = new Tweet(tweetId, time++);
        if (!tweetMap.containsKey(userId)) {
            tweetMap.put(userId, new ArrayList<>());
        }
        tweetMap.get(userId).add(tweet);
        follow(userId, userId);
    }
    public List<Integer> getNewsFeed(int userId) {
        List<Tweet> list = new ArrayList<>();
        HashSet<Integer> followees = userMap.get(userId);
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.timeStamp - b.timeStamp);
        if (followees != null) {
            for (int followee : followees) {
                List<Tweet> tweets = tweetMap.get(followee);
                if (tweets != null) {
                    for (Tweet tweet : tweets) {
                        list.add(tweet);
                    }
                }
            }
        }
        Collections.sort(list, (a, b) -> a.timeStamp - b.timeStamp);
        List<Integer> result = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0 && i >= list.size() - 10; i--) {
            result.add(list.get(i).tweetId);
        }
        return result;
    }

    public void follow(int followerId, int followeeId) {
        if (!userMap.containsKey(followerId)) {
            userMap.put(followerId, new HashSet<>());
        }
        userMap.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (!userMap.containsKey(followerId)) {
            return;
        }
        userMap.get(followerId).remove(followeeId);
    }
    public static void main(String[] args) {
        //["Twitter", "postTweet", "getNewsFeed", "follow", "postTweet", "getNewsFeed", "unfollow", "getNewsFeed"]
        //[[],        [1, 5],       [1],          [1, 2],    [2, 6],       [1],            [1, 2],  [1]]
        DesignTwitter d1 = new DesignTwitter();
        d1.postTweet(1,5);
        System.out.println(d1.getNewsFeed(1));
        d1.follow(1,2);
        d1.postTweet(2,6);
        System.out.println(d1.getNewsFeed(1));
        d1.unfollow(1,2);
        System.out.println(d1.getNewsFeed(1));
    }
}
