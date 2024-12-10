// Time Complexity:
// postTweet = O(1)
//getNewsFeed - O(F.N. log 10) where F is number if followees and N is average number of tweets per followee
// follow / unfollow : O(1)

// Space Complexity: O(n)

import java.util.*;

class Tweet {
    int tweetId;
    int timestamp;

    public Tweet(int tweetId, int timestamp) {
        this.tweetId = tweetId;
        this.timestamp = timestamp;
    }
}

class Twitter {
    private static int timestamp = 0;
    private Map<Integer, List<Tweet>> tweets; // userId -> list of tweets
    private Map<Integer, Set<Integer>> followees; // userId -> set of followeeIds

    public Twitter() {
        tweets = new HashMap<>();
        followees = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        tweets.putIfAbsent(userId, new ArrayList<>());
        tweets.get(userId).add(new Tweet(tweetId, timestamp++));

        // Ensure user is following themselves to see their own tweets
        follow(userId, userId);
    }

    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> minHeap = new PriorityQueue<>(Comparator.comparingInt(t -> t.timestamp));

        // Add tweets from all followees, including the user themselves
        if (followees.containsKey(userId)) {
            for (int followeeId : followees.get(userId)) {
                List<Tweet> followeeTweets = tweets.getOrDefault(followeeId, new ArrayList<>());
                for (Tweet tweet : followeeTweets) {
                    minHeap.offer(tweet);
                    if (minHeap.size() > 10) {
                        minHeap.poll(); // Remove the oldest tweet
                    }
                }
            }
        }

        // Collect the 10 most recent tweets
        List<Integer> result = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            result.add(0, minHeap.poll().tweetId); // Add in reverse order
        }
        return result;
    }

    public void follow(int followerId, int followeeId) {
        followees.putIfAbsent(followerId, new HashSet<>());
        followees.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        // Ensure users cannot unfollow themselves
        if (followerId != followeeId && followees.containsKey(followerId)) {
            followees.get(followerId).remove(followeeId);
        }
    }

    public static void main(String[] args) {
        Twitter twitter = new Twitter();
        twitter.postTweet(1, 5); // User 1 posts tweet with ID 5
        System.out.println(twitter.getNewsFeed(1)); // [5]

        twitter.follow(1, 2); // User 1 follows User 2
        twitter.postTweet(2, 6); // User 2 posts tweet with ID 6
        System.out.println(twitter.getNewsFeed(1)); // [6, 5]

        twitter.unfollow(1, 2); // User 1 unfollows User 2
        System.out.println(twitter.getNewsFeed(1)); // [5]
    }
}
