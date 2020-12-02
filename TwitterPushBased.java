package com.shopkick.core.data.messaging.common;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Please read the push based approach first to understand the differences.
 * The differences are highlighted in comments below.
 *
 * The major difference in the approach is the way we implement the tweet function.
 * In the pull based approach we would have to recompute the entire list of feeds every time because
 * we do not know what tweets the user's followees have produced. If we maintain both the followees and followers
 * then we can update all the followers stack given a particular user tweets something. The only time we would have to
 * recompute a user's feed is when he / she adds or removes a followee.
 *
 *
 * This method works on leet code
 */
class Twitter {

  private static Integer timestamp = 0;
  static class Tweet implements Comparable< Tweet >{

    Integer timestamp;
    Integer tweetId;

    Tweet(Integer tweetId) {
      this.timestamp = ++Twitter.timestamp;
      this.tweetId = tweetId;
    }

    @Override
    public int compareTo(Tweet o) {
      return o.timestamp.compareTo(this.timestamp);
    }
  }


  static class User {

    static final int MAX_FEED_SIZE = 10;
    int id;
    HashSet<User> followers; /* we store the followers as well as the followees in this case */
    HashSet<User> followees;
    List<Tweet> tweets;
    List<Tweet> feeds;
    boolean shouldRecomputeFeed; /* flag to indicate if we have to recompute */

    User(int id) {
      this.id = id;
      this.followers = new HashSet<>();
      this.followees = new HashSet<>();
      this.tweets = new ArrayList<>();
      this.feeds = new ArrayList<>();
    }


    /**
     * Add a new follower
     * @param u user
     */
    void addFollower(User u) {
      if (u != this) {
        this.followers.add(u);
      }
    }

    /**
     * Remove a follower
     * @param u user
     */
    void removeFollower(User u) {
      this.followers.remove(u);
    }


    void addFollowee(User u) {
      if (u != this) {
        this.followees.add(u);
      }
    }

    void removeFollowee(User u) {
      this.followees.remove(u);
    }


    void tweet(Integer id) {

      Tweet tweet = new Tweet(id);
      if (tweets.size() == MAX_FEED_SIZE) {
        tweets.remove(MAX_FEED_SIZE -1);
      }
      tweets.add(0,tweet); // this is same as pull based
      // push tweet for all users
      for (User u : followers) {  // propogate the tweet to all the followers of the user once.
        u.updateFeed(tweet);
      }

      this.updateFeed(tweet); // update feed of user as well
    }


    void updateFeed(Tweet tweet) {
      if (feeds.size() == 10) {
        feeds.remove(MAX_FEED_SIZE -1);
      }
      feeds.add(0,tweet);
    }

    void markForFeedRecompute() {
      shouldRecomputeFeed = true;  // mark to recompute
    }


    List<Integer> feed() {
      if (shouldRecomputeFeed) {
        getKLatestFeeds();
        shouldRecomputeFeed = false;
      }
      return feeds.stream().map(i -> i.tweetId).collect(Collectors.toList());
    }

    private void getKLatestFeeds() { // this can be optimized using heaps . I wanted to just indicate the idea here
      ArrayList<Tweet> allTweets = new ArrayList<>(this.tweets);
      for (User u : this.followees){
        allTweets.addAll(u.tweets);
      }
      Collections.sort(allTweets);
      this.feeds = new ArrayList<>(allTweets.subList(0, Math.min(MAX_FEED_SIZE, allTweets.size())));

    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      User user = (User) o;
      return id == user.id &&
          shouldRecomputeFeed == user.shouldRecomputeFeed &&
          followees.equals(user.followees) &&
          tweets.equals(user.tweets) &&
          feeds.equals(user.feeds);
    }

    @Override
    public int hashCode() {
      return id;
    }


  }

  private HashMap<Integer, User> userLookUp;

  /**
   * Initialize your data structure here.
   */
  public Twitter() {

    userLookUp = new HashMap<>();
  }


  /**
   * Compose a new tweet.
   */
  public void postTweet(int userId, int tweetId) {
    User user = getOrAddUser(userId);
    user.tweet(tweetId);
  }

  private User getOrAddUser(int userId) {
    User user = userLookUp.get(userId);
    if (user == null) {
      user = new User(userId);
      userLookUp.put(userId, user);
    }
    return user;
  }

  /**
   * Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
   */
  public List<Integer> getNewsFeed(int userId) {

    User user = getOrAddUser(userId);
    return user.feed();

  }

  /**
   * Follower follows a followee. If the operation is invalid, it should be a no-op.
   */
  public void follow(int followerId, int followeeId) {
    User follower = getOrAddUser(followerId);
    User followee = getOrAddUser(followeeId);
    if (! follower.followees.contains(followee)) {
      follower.addFollowee(followee);
      followee.addFollower(follower);
      follower.markForFeedRecompute();
    }
  }

  /**
   * Follower unfollows a followee. If the operation is invalid, it should be a no-op.
   */
  public void unfollow(int followerId, int followeeId) {  // followerId : 1 ; followeeId : 2
    User follower = getOrAddUser(followerId);  // User(1) u1 ; User(2) u2
    User followee = getOrAddUser(followeeId);
    if (follower.followees.contains(followee)) { // u1 = {id:1, followers: {}} ; u2 = {id:2, followers: {}} then skip
      // u1 = {id:1, followers: {u2}} ; u2 = {id:2, followees: {u1}}
      follower.removeFollowee(followee);
      followee.removeFollower(follower);
      follower.markForFeedRecompute();
    }
  }


  public static void main(String args[]){

    Twitter twitter = new Twitter();
    twitter.postTweet(1,5);
    twitter.postTweet(1,7);
    System.out.println(twitter.getNewsFeed(1));
    twitter.follow(1,2);
    twitter.postTweet(2,6);
    System.out.println(twitter.getNewsFeed(1));
    twitter.postTweet(2,9);
    System.out.println(twitter.getNewsFeed(1));
    twitter.unfollow(1,2);
    System.out.println(twitter.getNewsFeed(1));
  }
}

