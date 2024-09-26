// Time Complexity : 1. For adding user : O(1) 2. For follow() : O(1) 3. For unfollow() : O(1), 4. To post message: O(1), 5. Newsfeed: O(N) where N is the total number of feeds the user's all followees posted.
// Space Complexity : 1. tweetMap : O(N) where N is all tweets in the entire system. 2. userMap : O(N) where N is total number of users.
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

/* Each user, there is a user ID and List of people he/she is following */
class User{
    int userId;
    List<User> followees = new ArrayList<>();
    User(int userId){ this.userId=userId;
        this.followees.add(this);}
}

/* For each tweet, there is a tweet ID, text and */
class Tweet{
    int tweetId;
    int timeStamp; //This is the timestamp of each tweet
    Tweet(int tweetId){
        this.tweetId= tweetId;
        this.timeStamp= Twitter.timestampCounter++; //The timestamp is incremented for each tweet and is unique.
    }
}

class Twitter {
    static int timestampCounter=0;
    /** Initialize your data structure here. */
    HashMap<Integer, List<Tweet>> tweetMap = new HashMap<>();
    HashMap<Integer, User> usersMap = new HashMap<>();
    public Twitter() {
    }

    private void addUser(int userId){
        if(!usersMap.containsKey(userId)){
            usersMap.put(userId, new User(userId));
        }
    }
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        addUser(userId);
        if(!tweetMap.containsKey(userId)){
            tweetMap.put(userId, new ArrayList<Tweet>());
        }
        Tweet tweet= new Tweet(tweetId);
        List<Tweet> tempTweets = tweetMap.get(userId);
        tempTweets.add(tweet);
        tweetMap.put(userId, tempTweets);
        System.out.println(userId + " posted this tweet --> " + tweetId);
    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> result = new ArrayList<>();
        if(!usersMap.containsKey(userId)){
            return result;
        }
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.timeStamp - b.timeStamp); //Min heap
        List<User> followees = usersMap.get(userId).followees;
        for(User followee: followees) {
            if (tweetMap.get(followee.userId) != null){
                List<Tweet> followeeAllTweets = tweetMap.get(followee.userId);
                if (followeeAllTweets != null) {
                    for (Tweet tweet : followeeAllTweets){
                        if(pq.size()==0){
                            pq.add(tweet);
                        }
                        else if (pq.size() > 0) {
                            if (pq.size() < 10) { //size of the news feed
                                if(pq.peek().timeStamp < tweet.timeStamp){
                                    Tweet temp = pq.poll();
                                    //pq.remove(pq.peek());
                                    pq.add(tweet);
                                    pq.add(temp);
                                } else{
                                    pq.add(tweet);
                                }

                            } else {
                                if(pq.peek().timeStamp < tweet.timeStamp){
                                    pq.remove(pq.peek());
                                    pq.add(tweet);
                                }
                            }
                        }
                    }
                }
            }
        }
        while(pq.size()!=0){
            result.add(0, pq.peek().tweetId);
            pq.remove();
        }
        System.out.println("The news feed for "+ userId + ":");
        for(int tweet: result){
            System.out.println(tweet);
        }
        return result;
    }

    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        addUser(followerId);
        addUser(followeeId);

        if(!usersMap.get(followerId).followees.contains(usersMap.get(followeeId))){
            (usersMap.get(followerId)).followees.add(usersMap.get(followeeId));
        }
        System.out.println(followerId + " successfully is following " + followeeId);
    }

    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        addUser(followerId);
        addUser(followeeId);

        if(usersMap.get(followerId).followees.contains(usersMap.get(followeeId)) && followeeId!=followerId){
            (usersMap.get(followerId)).followees.remove(usersMap.get(followeeId));
        }
        System.out.println(followerId + " has successfully stopped following " + followeeId);

    }

    public static void main(String... args){
        Twitter obj = new Twitter();
        obj.postTweet(1,101);
        List<Integer> param_2 = obj.getNewsFeed(1);
        obj.follow(1,1);
        obj.postTweet(2,201);
        List<Integer> param_3 = obj.getNewsFeed(1);
        obj.unfollow(1,2);
        List<Integer> param_4 = obj.getNewsFeed(1);
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
