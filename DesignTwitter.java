// Time Complexity : postTweet: O(1), follow/unfollow: O(1), getNewsFeed: O(n log k), where n = total tweets by followees (at most top 10 are maintained), k = 10
// Space Complexity : O(f + t) f = number of follows, t = number of tweets posted
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No
// Approach -
//   - Maintain a `followyessMap` to track follow relationships (user → set of followees).
//   - Maintain a `tweetsMap` to track tweets posted by each user (user → list of tweets).
//   - Each tweet has an associated global timestamp to maintain correct ordering.
//   - In `postTweet`, append tweet to user's tweet list and ensure they follow themselves.
//   - In `getNewsFeed`, check tweets of all followees, maintain a min-heap of size 10 to keep only the most recent tweets.
//   - In `follow` and `unfollow`, update the followyessMap accordingly.

import java.util.*;

public class DesignTwitter {
    class Tweet {
        int tweetId;
        int timestamp;

        public Tweet(int tweetId, int timestamp) {
            this.tweetId = tweetId;
            this.timestamp = timestamp;
        }
    }

    Map<Integer, HashSet<Integer>> followyessMap;
    Map<Integer, List<Tweet>> tweetsMap;
    int time;

    public DesignTwitter() {
        this.followyessMap = new HashMap<>();
        this.tweetsMap = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        if(!tweetsMap.containsKey(userId)) { //new user
            tweetsMap.put(userId, new ArrayList<>());
        }
        Tweet tweet = new Tweet(tweetId, time);
        time++;
        tweetsMap.get(userId).add(tweet);

        follow(userId, userId);
    }

    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.timestamp - b.timestamp);
        HashSet<Integer> followees = followyessMap.get(userId);
        if(followees != null) {
            for(Integer followee: followees) {
                List<Tweet> tweets = tweetsMap.get(followee);
                if(tweets != null) {
                    for(int i = tweets.size() - 1; i >= tweets.size() - 10 && i >= 0; i--) {//O(n)  only top 10 tweets of each followee checked
                        Tweet tweet = tweets.get(i);
                        pq.add(tweet);
                        if(pq.size() > 10) {    //if size of heap goes beyond 10 then remove min value in it
                            pq.poll();
                        }
                    }
                }
            }
        }

        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()) {
            result.add(pq.poll().tweetId);
        }

        Collections.reverse(result);

        return result;
    }

    public void follow(int followerId, int followeeId) {
        if(!followyessMap.containsKey(followerId)) {  //new user
            followyessMap.put(followerId, new HashSet<>());
        }
        followyessMap.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if(!followyessMap.containsKey(followerId)) {  //no user
            return;
        }
        followyessMap.get(followerId).remove(followeeId);
    }

    public static void main(String[] args) {
        DesignTwitter twitter = new DesignTwitter();

        twitter.postTweet(1, 5);  // User 1 posts tweet (id = 5)

        System.out.println("News Feed of user 1: " + twitter.getNewsFeed(1)); // Expected: [5]

        twitter.follow(1, 2); // User 1 follows user 2
        twitter.postTweet(2, 6); // User 2 posts tweet (id = 6)

        System.out.println("News Feed of user 1: " + twitter.getNewsFeed(1)); // Expected: [6, 5]

        twitter.unfollow(1, 2); // User 1 unfollows user 2

        System.out.println("News Feed of user 1: " + twitter.getNewsFeed(1)); // Expected: [5]
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