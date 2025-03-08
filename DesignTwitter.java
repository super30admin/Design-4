import java.util.*;

public class Twitter {

    private static int timestamp = 0; // Global timestamp to keep tweets in chronological order
    private final Map<Integer, Set<Integer>> following; // Map of users and the users they follow
    private final Map<Integer, List<Tweet>> tweets; // Map of users and their tweets
    
    // Nested class to represent a Tweet
    private static class Tweet {
        int id;
        int time;
        
        public Tweet(int id, int time) {
            this.id = id;
            this.time = time;
        }
    }
    
    public Twitter() {
        following = new HashMap<>();
        tweets = new HashMap<>();
    }
    
    // Post a tweet for a given user
    public void postTweet(int userId, int tweetId) {
        tweets.putIfAbsent(userId, new ArrayList<>());
        tweets.get(userId).add(new Tweet(tweetId, timestamp++));
    }
    
    // Retrieve the 10 most recent tweets from the user and their followees
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.time - b.time);
        // Add user's own tweets to the priority queue
        if (tweets.containsKey(userId)) {
            for (Tweet tweet : tweets.get(userId)) {
                pq.offer(tweet);
                if (pq.size() > 10) pq.poll(); // Maintain only top 10 tweets
            }
        }
        // Add followees' tweets to the priority queue
        if (following.containsKey(userId)) {
            for (int followeeId : following.get(userId)) {
                if (tweets.containsKey(followeeId)) {
                    for (Tweet tweet : tweets.get(followeeId)) {
                        pq.offer(tweet);
                        if (pq.size() > 10) pq.poll();
                    }
                }
            }
        }
        // Extract tweets from the priority queue and return in reverse order
        List<Integer> res = new LinkedList<>();
        while (!pq.isEmpty()) {
            res.add(0, pq.poll().id);
        }
        return res;
    }
    
    // Follow a user
    public void follow(int followerId, int followeeId) {
        if (followerId == followeeId) return; // Avoid self-follow
        following.putIfAbsent(followerId, new HashSet<>());
        following.get(followerId).add(followeeId);
    }
    
    // Unfollow a user
    public void unfollow(int followerId, int followeeId) {
        if (following.containsKey(followerId)) {
            following.get(followerId).remove(followeeId);
        }
    }
}

/*
 * Time Complexity:
 * 1. postTweet(int userId, int tweetId): O(1)
 *    - Adding a tweet to the user's list is an O(1) operation.
 * 
 * 2. getNewsFeed(int userId): O(n + m log 10) ≈ O(n + m)
 *    - n = total tweets by the user and their followees.
 *    - m = total followees of the user.
 *    - Adding to the priority queue takes O(log 10) = O(1) since the max size is 10.
 *    - In the worst case, all tweets from the user and their followees are processed, so O(n) for iteration.
 *    - Extracting top 10 elements from the heap is O(10 log 10) = O(1).
 * 
 * 3. follow(int followerId, int followeeId): O(1)
 *    - Adding to a set is an O(1) operation.
 * 
 * 4. unfollow(int followerId, int followeeId): O(1)
 *    - Removing from a set is an O(1) operation.
 * 
 * Space Complexity:
 * 1. following Map: O(f) where f is the total number of follow relationships.
 *    - Each user can follow up to all other users, leading to O(u^2) in the worst case (u = total users).
 * 
 * 2. tweets Map: O(t) where t is the total number of tweets.
 *    - Each user can have an unlimited number of tweets, so worst case O(u * n) where n is max tweets per user.
 * 
 * 3. Priority Queue: O(10) = O(1)
 *    - The priority queue is bounded to hold at most 10 elements at a time.
 * 
 * Overall:
 * - Time Complexity: 
 *      postTweet: O(1)
 *      getNewsFeed: O(n + m)
 *      follow: O(1)
 *      unfollow: O(1)
 * - Space Complexity: O(f + t) where f = total follow relationships, t = total tweets.
 */
