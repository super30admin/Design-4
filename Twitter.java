/*
 * Time Complexity: postTweet() - O(1), getNewsFeed() - O(n log k), follow() - O(1), unfollow() - O(1)
 * Space Complexity: Overall: O(u ^ 2), O (n x k); postTweet() - O(1), getNewsFeed() - O(t) where t is the total tweets by user and their followees, follow() - O(1), unfollow() - O(1)
 * Where n is the number of users and k is the number of tweets
 */
import java.util.*;

public class Twitter {

    // represents a Tweet in a linked list of tweets
    static class Tweet {
        int id;
        int timestamp;
        Tweet next;

        Tweet() {}

        Tweet(int tweetId, int timestamp) {
            this.id = tweetId;
            this.timestamp = timestamp;
        }
    }

    // maps a user to their followees
    Map<Integer, Set<Integer>> followMap;

    // maps a user to a list of their tweets
    Map<Integer, Tweet> tweetMap;

    int timestamp;

    public Twitter() {
        followMap = new HashMap<>();
        tweetMap = new HashMap<>();
        timestamp = 1;
    }

    public void postTweet(int userId, int tweetId) {
        // create a new tweet and update timestamp
        Tweet tweet = new Tweet(tweetId, timestamp++);

        // get the head of the tweetList for the user
        tweetMap.putIfAbsent(userId, new Tweet());
        Tweet tweetHead = tweetMap.get(userId);

        // add the new tweet to the front of the list
        tweet.next = tweetHead.next;
        tweetHead.next = tweet;
    }

    public List<Integer> getNewsFeed(int userId) {
        // heap to store all tweets in descending order of timestamp
        PriorityQueue<Tweet> heap = new PriorityQueue<>((a, b) -> Integer.compare(b.timestamp, a.timestamp));

        // add all tweets of the user to the heap
        if (tweetMap.containsKey(userId)) heap.add(tweetMap.get(userId).next);

        // add all tweets of the user's followees to the heap
        for(int user: followMap.getOrDefault(userId, new HashSet<>())) {
            if(tweetMap.containsKey(user)) heap.add(tweetMap.get(user).next);
        }

        List<Integer> newsFeed = new ArrayList<>();
        int k = 10;
        // "merge" the tweets into a single list based on timestamp
        // do until a max of 10 tweets are added to the feed
        while(!heap.isEmpty() && k > 0) {
            Tweet tweet = heap.poll();
            newsFeed.add(tweet.id);
            k--;
            if(tweet.next != null) {
                heap.add(tweet.next);
            }
        }

        return newsFeed;
    }

    public void follow(int followerId, int followeeId) {
        // put followee in follower's set
        followMap.putIfAbsent(followerId, new HashSet<>());
        followMap.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        // remove followee from follower's set
        if(followMap.containsKey(followerId)) {
            followMap.get(followerId).remove(followeeId);
        }
    }
}

/*
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */
