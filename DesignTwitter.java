// TC : postTweet - O(1), follow - O(1), unfollow - O(1), getNewsFeed - O(f log f + k log f)
// f = no. of following of a user, k = tweetFeedLimit
// SC : O(f)

package S30_Codes.Design_4;
import java.util.*;

public class DesignTwitter {
}

class Twitter {
    Map<Integer, User> userMap;
    static final int tweetFeedLimit = 10;

    Twitter() {
        userMap = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        Tweet tweet = new Tweet(tweetId);
        createUserIfNotExist(userId);
        userMap.get(userId).postTweet(tweet);
    }

    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> b.priority - a.priority);

        User user = userMap.get(userId);
        if(user != null){
            for(Integer followeeId : user.followingSet){
                User followee = userMap.get(followeeId);
                if(followee.tweetHead != null)
                    pq.add(followee.tweetHead);
            }
        }

        List<Integer> feeds = new ArrayList<>();

        while(!pq.isEmpty() && feeds.size() < tweetFeedLimit){
            Tweet tweet = pq.remove();
            feeds.add(tweet.tweetId);

            if(tweet.next != null)
                pq.add(tweet.next);
        }
        return feeds;
    }

    public void follow(int followerId, int followeeId) {
        createUserIfNotExist(followeeId);
        createUserIfNotExist(followerId);
        userMap.get(followerId).follow(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        userMap.get(followerId).unfollow(followeeId);
    }

    private void createUserIfNotExist(int userId){
        if(!userMap.containsKey(userId))
            userMap.put(userId, new User(userId));
    }
}

class User{
    int userId;
    Set<Integer> followingSet;
    Tweet tweetHead;

    User(int id){
        userId = userId;
        followingSet = new HashSet<>();
        followingSet.add(id);
    }

    public void follow(int followeeId) {
        followingSet.add(followeeId);
    }

    public void unfollow(int followeeId) {
        followingSet.remove(followeeId);
    }

    public void postTweet(Tweet tweet) {
        tweet.next = tweetHead;
        tweetHead = tweet;
    }
}

class Tweet{
    static int tweetCount = 0;
    int tweetId;
    int priority;
    Tweet next;

    Tweet(int tweetId){
        this.tweetId = tweetId;
        this.priority = ++tweetCount;
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