// Time Complexity : O(nklog10) 
// Space Complexity : O(n) 

// Your code here along with comments explaining your approach: 
class Tweet {
  int tid;
  int creationTime;

  public Tweet(int tweetId, int time) {
    tid = tweetId;
    creationTime = time;
  }
}

class Twitter {
  HashMap<Integer, List<Tweet>> tweets;
  HashMap<Integer, HashSet<Integer>> followers;
  int time;

  /** Initialize your data structure here. */
  public Twitter() {
    tweets = new HashMap<>();
    followers = new HashMap<>();
  }

  /** Compose a new tweet. */
  public void postTweet(int userId, int tweetId) {
    if (!tweets.containsKey(userId)) {
      tweets.put(userId, new ArrayList<>());
    }
    tweets.get(userId).add(new Tweet(tweetId, time++));
  }

  /**
   * Retrieve the 10 most recent tweet ids in the user's news feed. Each item in
   * the news feed must be posted by users who the user followed or by the user
   * herself. Tweets must be ordered from most recent to least recent.
   */
  public List<Integer> getNewsFeed(int userId) { // O(nklog10)
    follow(userId, userId);
    PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.creationTime - b.creationTime);
    Set<Integer> f = followers.get(userId);
    for (int fid : f) {
      List<Tweet> twts = tweets.get(fid);
      if (twts != null) {
        for (Tweet t : twts) {
          pq.add(t);
          if (pq.size() > 10)
            pq.remove();
        }
      }
    }
    List<Integer> result = new ArrayList<>();
    while (pq.size() > 0) {

      result.add(0, pq.remove().tid);
    }
    return result;
  }

  /**
   * Follower follows a followee. If the operation is invalid, it should be a
   * no-op.
   */
  public void follow(int followerId, int followeeId) {
    if (!followers.containsKey(followerId)) {
      followers.put(followerId, new HashSet<>());
    }
    followers.get(followerId).add(followeeId);
  }

  /**
   * Follower unfollows a followee. If the operation is invalid, it should be a
   * no-op.
   */
  public void unfollow(int followerId, int followeeId) {
    if (followers.containsKey(followerId)) {
      followers.get(followerId).remove(followeeId);
    }
  }
}