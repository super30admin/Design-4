import java.util.*;

class Twitter {
    private Map<Integer, List<Tweet>> tweets;
    private Map<Integer, Set<Integer>> followers;
    private int timestamp;

    public Twitter() {
        tweets = new HashMap<>();
        followers = new HashMap<>();
        timestamp = 0;
    }

    public void postTweet(int userId, int tweetId) {
        if (!tweets.containsKey(userId)) {
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId, timestamp++));
    }

    public List<Integer> getNewsFeed(int userId) {
        List<Integer> feed = new ArrayList<>();
        PriorityQueue<Tweet> pq = new PriorityQueue<>((t1, t2) -> t2.timestamp - t1.timestamp);
        if (tweets.containsKey(userId)) {
            for (Tweet tweet : tweets.get(userId)) {
                pq.offer(tweet);
            }
        }
        if (followers.containsKey(userId)) {
            for (int followeeId : followers.get(userId)) {
                if (tweets.containsKey(followeeId)) {
                    for (Tweet tweet : tweets.get(followeeId)) {
                        pq.offer(tweet);
                    }
                }
            }
        }
        int count = 0;
        while (!pq.isEmpty() && count < 10) {
            feed.add(pq.poll().tweetId);
            count++;
        }
        return feed;
    }

    public void follow(int followerId, int followeeId) {
        if (!followers.containsKey(followerId)) {
            followers.put(followerId, new HashSet<>());
        }
        followers.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (followers.containsKey(followerId)) {
            followers.get(followerId).remove(followeeId);
        }
    }

    private class Tweet implements Comparable<Tweet> {
        int tweetId;
        int timestamp;

        public Tweet(int tweetId, int timestamp) {
            this.tweetId = tweetId;
            this.timestamp = timestamp;
        }

        public int compareTo(Tweet other) {
            return other.timestamp - this.timestamp;
        }
    }
    public static void main(String[] args) {
        Twitter twitter = new Twitter();

        // User 1 posts tweet 1
        twitter.postTweet(1, 1);

        // User 1's news feed should only contain tweet 1
        System.out.println(twitter.getNewsFeed(1)); // prints [1]

        // User 2 follows user 1
        twitter.follow(2, 1);

        // User 2's news feed should contain tweet 1 from user 1
        System.out.println(twitter.getNewsFeed(2)); // prints [1]

        // User 1 posts tweet 2
        twitter.postTweet(1, 2);

        // User 2's news feed should now contain tweet 2 from user 1 and tweet 1 from user 1
        System.out.println(twitter.getNewsFeed(2)); // prints [2, 1]

        // User 2 unfollows user 1
        twitter.unfollow(2, 1);

        // User 2's news feed should now be empty
        System.out.println(twitter.getNewsFeed(2)); // prints []
    }

}