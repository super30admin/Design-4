
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Time Complexity: 
 * 1. postTweet - O(1)
 * 2. follow/unfollow - O(1)
 * 3. getNewsFeed - O(Nlog10) where N is the total number of tweets from the user and followees
 *  
 * Did this code successfully run on Leetcode: Yes
 * Any problem you faced while coding this: None
 *
 * Approach:
 * 1. postTweet: Adds a new Tweet object (containing tweetId and time) to the tweetRelations map for the given userId.
 * 2. getNewsFeed: Uses a min-heap (PriorityQueue) to keep track of the 10 most recent tweets.
 * Retrieves tweets from the user and their followees.
 * Extracts and returns the most recent tweets in descending order of time.
 * 3. follow/unfollow - Updates the userRelations map to add/remove followee relationships. 
 */

public class Twitter {
    class Tweet {
        int tweetId;
        int time;

        Tweet(int tweetId, int time) {
            this.tweetId = tweetId;
            this.time = time;
        }
    }

    HashMap<Integer, HashSet<Integer>> userRelations;
    HashMap<Integer, List<Tweet>> tweetRelations;
    int time;

    public Twitter() {
        this.userRelations = new HashMap<>();
        this.tweetRelations = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        if (!tweetRelations.containsKey(userId)) {
            tweetRelations.put(userId, new ArrayList<>());
        }

        Tweet newTweet = new Tweet(tweetId, time++);
        tweetRelations.get(userId).add(newTweet);
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.time - b.time);
        addToPq(userId, pq);

        HashSet<Integer> followers = userRelations.get(userId);
        if (followers != null) {
             for (int user: followers) {
                addToPq(user, pq);
            }
        }
        List<Integer> result = new ArrayList<>();

        while (!pq.isEmpty()) {
            Tweet t = pq.poll();
            result.add(0, t.tweetId);
        }

        return result;
    }

    private void addToPq(int userId, PriorityQueue<Tweet> pq) {
        List<Tweet> tweetsByUser = tweetRelations.get(userId);
        if (tweetsByUser == null)
            return;
        for (Tweet tweet: tweetsByUser) {
            pq.add(tweet);
            if (pq.size() > 10) {
                pq.poll();
            }
        }
    }
    
    public void follow(int followerId, int followeeId) {
        if (!userRelations.containsKey(followerId)) {
            userRelations.put(followerId, new HashSet<>());
        }

        userRelations.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if (userRelations.containsKey(followerId)) {
            userRelations.get(followerId).remove(followeeId);
        }
    }

    public static void main(String[] args) {
        // Your code here
        // Your Twitter object will be instantiated and called as such:
        Twitter obj = new Twitter();
        obj.postTweet(1,10);
        List<Integer> param_2 = obj.getNewsFeed(1);
        System.out.println(param_2);
        obj.follow(1,5);
        obj.unfollow(1,2);
        obj.postTweet(5,10);
        List<Integer> param_3 = obj.getNewsFeed(1);
        System.out.println(param_3);
    }
}
