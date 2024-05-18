// Time Complexity : given at each function.
// Space Complexity : given at each function.
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no

/*
 * Variables needed: 
 * We dont need to maintain the list of followers & following... for posting a tweet to all its followers.
 * we need to maintain a global time with which we can sort the latest feed
 * List of tweets.
 */

import java.util.*;

public class Twitter {
    Map<Integer, Set<Integer>> listOfFollowees;
    Map<Integer, List<Tweet>> tweets;
    int time; // time post globally

    class Tweet {
        int tweetId;
        int timeStamp;

        public Tweet(int tweetId, int timeStamp) {
            this.tweetId = tweetId;
            this.timeStamp = timeStamp;
        }
    }

    public Twitter() {
        this.listOfFollowees = new HashMap<>();
        this.tweets = new HashMap<>();
    }

    // Timecomplexity - O(1)
    // Space complexity- O(n)
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId); // when ever you post a tweet follow yourself
        if (!this.tweets.containsKey(userId)) {
            this.tweets.put(userId, new ArrayList<>());
        }
        Tweet tweet = new Tweet(tweetId, time++);
        this.tweets.get(userId).add(tweet);
    }

    /*
     * Things to remember:
     * min heap will have min value on surface, so all the max values are at the
     * bottom
     * max heap will have max value on surface(the first to pop out), so all the min
     * values are at the bottom.
     * 
     */
    // Timecomplexity - O(n) -> where n is the number of followers
    // Space complexity- O(1) since the count of feed is constant
    public List<Integer> getNewsFeed(int userId) {

        PriorityQueue<Tweet> newsFeed = new PriorityQueue<>((a, b) -> a.timeStamp - b.timeStamp); // by default its min
                                                                                                  // heap ; a < b

        Set<Integer> followees = this.listOfFollowees.get(userId);
        if (followees != null) {
            for (Integer followee : followees) {
                List<Tweet> ts = tweets.get(followee);
                if (ts != null) {
                    int startIndex = 0;
                    if (ts.size() > 10) {
                        startIndex = ts.size() - 10;
                    }
                    for (int i = startIndex; i < ts.size(); i++) {
                        newsFeed.add(ts.get(i));
                        if (newsFeed.size() > 10) {
                            newsFeed.poll();
                        }
                    }
                }
            }
        }

        List<Integer> result = new ArrayList<>();
        while (!newsFeed.isEmpty()) {
            result.add(0, newsFeed.poll().tweetId); // adding at 0 will shift the previously added elements to the next
                                                    // index.
        }
        return result;
    }

    // Timecomplexity - O(1)
    // Space complexity- O(n)
    public void follow(int followerId, int followeeId) {
        if (listOfFollowees.get(followerId) == null) {
            listOfFollowees.put(followerId, new HashSet<>());
            // listOfFollowees.get(followerId).add(followerId); // adding this here won't
            // work. We are adding the user to
            // himself, when someone follows, what if no one follows,
            // user should be able to see his/her posts.
        }

        listOfFollowees.get(followerId).add(followeeId);
    }

    // Timecomplexity - O(1)
    // Space complexity- O(1)
    public void unfollow(int followerId, int followeeId) {
        if (!listOfFollowees.containsKey(followerId))
            return;
        listOfFollowees.get(followerId).remove(followeeId);
    }
}
