import java.util.*;

class Twitter {

    Map<Integer, List<Tweet>> userTweetMap;
    Map<Integer, Set<Integer>> followerFolloweeMap;

    int tweetTime;

    public Twitter() {
        userTweetMap = new HashMap<>();
        followerFolloweeMap = new HashMap<>();
    }
    // Time: O(1)
    public void postTweet(int userId, int tweetId) {
        Tweet tweet = new Tweet(tweetId, tweetTime);
        tweetTime++;
        if (!userTweetMap.containsKey(userId)) {
            userTweetMap.put(userId, new ArrayList<>());
        }
        userTweetMap.get(userId).add(tweet);
    }
    // Time: O(n.t), where n is number of people use follow and t is number of top tweets, 10 here, so constant.
    // = ~O(n)
    public List<Integer> getNewsFeed(int userId) {
        Set<Integer> following = followerFolloweeMap.get(userId);
        if(following == null){
            following = new HashSet<>();
        }
        // To get users own tweets
        following.add(userId);

        PriorityQueue<Tweet> tweets = new PriorityQueue<>((a, b) -> a.createdTimestamp - b.createdTimestamp);
        for (Integer followedUser : following) {
            List<Tweet> tempTweets = userTweetMap.get(followedUser);
            if(tempTweets == null) continue;
            int min = Math.min(tempTweets.size()-1, 10);
            for (int i = min; i >= 0; i--) {
                tweets.add(tempTweets.get(i));
                // To maintain extra space constant allow only 10 tweets in the heap
                if(tweets.size() > 10){
                    tweets.poll();
                }
            }
        }

        List<Integer> topTweets = new ArrayList<>();
        while (!tweets.isEmpty()) {
            // Need to insert at the first because they need the top tweet first.
            topTweets.add(0, tweets.remove().tweetId);
        }
        return topTweets;
    }
    // Time: O(1)
    public void follow(int followerId, int followeeId) {
        if (!followerFolloweeMap.containsKey(followerId)) {
            followerFolloweeMap.put(followerId, new HashSet<>());
        }

        followerFolloweeMap.get(followerId).add(followeeId);
    }
    // Time: O(1)
    public void unfollow(int followerId, int followeeId) {
        if (!(followerFolloweeMap.containsKey(followerId))) {
            return;
        }

        followerFolloweeMap.get(followerId).remove(followeeId);
    }

    class Tweet {
        int tweetId;
        int createdTimestamp;

        Tweet(int tweetId, int createdTimestamp) {
            this.tweetId = tweetId;
            this.createdTimestamp = createdTimestamp;
        }
    }
}

