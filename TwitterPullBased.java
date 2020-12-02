import java.util.*;
import java.util.stream.Collectors;

/**
 *  This is the solution implemented in class. Kinda .
 *
 *  We have 3 classes to implement this design .
 *
 *  - Twitter
 *  - User
 *  - Tweet
 *
 *  Timestamps are implemented using a global Integer counter Twitter.timestamp
 *
 *  For this implementation all the other classes are implementedd as sinner classes of the main class.
 *  This limited me from restricting visibility of certain attributes and functions.
 *
 *  Also I have not made any effort to make this thread safe. though that should be achievable by making the users
 *  collection attributes synchronized.
 */
class Twitter {

  private static Integer timestamp = 0; // global timestamp counter

  /**
   * Tweet class : Every tweet is defined by 2 attributes a tweet id and a timestamp.
   * A tweet is immutable. i.e once created its attributes will not change.
   */
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
    HashSet<User> followees;
    List<Tweet> tweets;
    List<Tweet> feeds;

    User(int id) {
      this.id = id;
      this.followees = new HashSet<>();
      this.tweets = new ArrayList<>();
      this.feeds = new ArrayList<>();
    }

    void addFollowee(User u) {
      if (u != this) {
        this.followees.add(u);
      }
    }

    void removeFollowee(User u) {
      this.followees.remove(u);
    }


    /**
     * Method to tweet something
     * @param tweet something tweeted by the user
     */

    void tweet(Integer id) {

      Tweet tweet = new Tweet(id);
      if (tweets.size() == MAX_FEED_SIZE) {
        tweets.remove(MAX_FEED_SIZE -1);
      }
      tweets.add(0,tweet);
      this.updateFeed(tweet);
    }


    /**
     * Update feed of a user with a specific tweet. if feed is full remove an element and add a new tweet
     * @param tweet Tweet to be added
     */
    void updateFeed(Tweet tweet) {
      if (feeds.size() == 10) {
        feeds.remove(MAX_FEED_SIZE -1);
      }
      feeds.add(0,tweet);
    }


    /**
     * @return return the feed list of the user
     */
    List<Integer> feed() {
      getKLatestFeeds();
      return feeds.stream().map(i -> i.tweetId).collect(Collectors.toList());
    }

    /**
     * Recomputes the entire feed of user from her/his followers
     */
    private void getKLatestFeeds() {
      // using simple array list sort here . this can obviously be optimized by using a heap.
      ArrayList<Tweet> allTweets = new ArrayList<>(this.tweets);
      for (User u : this.followees){
        allTweets.addAll(u.tweets);
      }
      Collections.sort(allTweets); // this should be done using heap
      this.feeds = new ArrayList<>(allTweets.subList(0, Math.min(MAX_FEED_SIZE, allTweets.size())));

    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      User user = (User) o;
      return id == user.id &&
          followees.equals(user.followees) &&
          tweets.equals(user.tweets) &&
          feeds.equals(user.feeds);
    }

    @Override
    public int hashCode() {
      return id;
    }


  }

  // lookup to search for a partiuclar users object given user_id
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
    User user = getOrAddUser(userId); // create user if does not exist else
    user.tweet(tweetId);
  }

  /**
   * get user from lookup . if user does not exist create user.
   * @param userId user id
   * @return user object
   */
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
    }
  }



}

