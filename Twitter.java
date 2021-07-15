import java.util.*;

/**
 * @author Vishal Puri
 * // Time Complexity : O(1)
 * // Space Complexity : O(n)
 * // Did this code successfully run on Leetcode : Yes
 * // Any problem you faced while coding this :
 */

public class Twitter {

    Map<Integer,User> users;
    Map<Integer, Set<Integer>> follows;
    int timeStamp;

    class User {
        int userId;
        TreeMap<Integer,Tweet> newspeed;
        List<Tweet> tweets;
        User(int userId){
            this.userId = userId;
            newspeed = new TreeMap<Integer,Tweet>((s1,s2)->s2-s1);
            tweets = new ArrayList<>();
        }
    }

    class Tweet {
        int tweetId;
        int userId;
        int timeStamp;
        Tweet(int tweetId,int userId,int timeStamp){
            this.tweetId = tweetId;
            this.userId = userId;
            this.timeStamp = timeStamp;
        }
    }

    /** Initialize your data structure here. */
    public Twitter() {
        users = new HashMap<>();
        follows = new HashMap<>();
        timeStamp = 0;
    }

    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        User user = users.getOrDefault(userId,new User(userId));
        Tweet tweet = new Tweet(tweetId,userId,++timeStamp);
        user.tweets.add(tweet);
        user.newspeed.put(timeStamp,tweet);
        users.put(userId,user);
        if(follows.containsKey(userId)){
            Set<Integer> followers = follows.get(userId);
            for(int followerId:followers)
                users.get(followerId).newspeed.put(timeStamp,tweet);
        }
    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> res = new ArrayList<>();
        if(!users.containsKey(userId))
            return res;
        User user = users.get(userId);
        Map<Integer,Tweet> newspeed = user.newspeed;
        int n = 0;
        for(int key:newspeed.keySet()){
            if(n++==10)
                break;
            res.add(newspeed.get(key).tweetId);
        }
        return res;
    }

    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!users.containsKey(followerId))
            users.put(followerId,new User(followerId));
        if(!users.containsKey(followeeId))
            users.put(followeeId,new User(followeeId));
        User follower = users.getOrDefault(followerId,new User(followerId));
        User followee = users.getOrDefault(followeeId,new User(followeeId));

        if(!follows.containsKey(followeeId))
            follows.put(followeeId,new HashSet<>());
        Set<Integer> followers = follows.get(followeeId);
        followers.add(followerId);

        Map<Integer,Tweet> newspeed = follower.newspeed;
        for(Tweet tweet:followee.tweets)
            newspeed.put(tweet.timeStamp,tweet);
    }

    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        User follower = users.get(followerId);
        User followee = users.get(followeeId);

        if(!follows.containsKey(followeeId))
            follows.put(followeeId,new HashSet<>());
        Set<Integer> followers = follows.get(followeeId);
        followers.remove(followerId);

        Map<Integer,Tweet> newspeed = follower.newspeed;
        for(Tweet tweet:followee.tweets)
            newspeed.remove(tweet.timeStamp);
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