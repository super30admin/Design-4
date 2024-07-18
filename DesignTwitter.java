// Time Complexity : O(n) n -tweets
// Space Complexity : O(1)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach
class Twitter {

  class Tweet {
    int tweetID;
    int createdAt;
    Tweet(int tweetID, int createdAt) {
      this.tweetID = tweetID;
      this.createdAt = createdAt;
    }
  }

  Map<Integer, Set<Integer>> users; // UserID, list of userIds being followed
  Map<Integer, List<Tweet>> posts; // UserID, list of tweets tweeted by that user
  int time;

  public Twitter() {
    users = new HashMap<>();
    posts = new HashMap<>();
  }

  public void postTweet(int userId, int tweetId) { // Adding a tweet for a user with tweetID and creation time(global)
    posts.computeIfAbsent(userId, k -> new ArrayList<>()).add(new Tweet(tweetId, time++));
  }

  public List<Integer> getNewsFeed(int userId) {
    // MinHeap
    PriorityQueue<Tweet> newsFeed = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
    // Result array
    List<Integer> answer = new ArrayList<>();
    // User's own tweets are counted as well
    follow(userId, userId);
    // Get the set of users that user follows and can view
    Set<Integer> following = users.get(userId);
    // Loop through the following
    for(int followee: following) {
      // get the list of tweets for each user
      List<Tweet> tweetList = posts.get(followee);
      // Check if the list is empty
      if(tweetList!=null) {
        // Loop through the list of tweets for each user
        for(Tweet tweet: tweetList) {
          // Add the tweets to the minHeap
          newsFeed.add(tweet);
          // to maintain O(1), only 10 tweets can reside inside the minHeap at any time
          if(newsFeed.size() > 10) {
            // Will remove the oldest tweet
            newsFeed.poll();
          }
        }
      }
    }
    int count = 0;
    // Adding 10 tweets to answer list
    while(!newsFeed.isEmpty() && count<10) {
      // Since PQ is a minHeap, it will return the oldest tweet. Thus, insert in reverse order.
      answer.add(0, newsFeed.poll().tweetID);
      count++;
    }
    return answer;
  }

  // Adding an id that user is following
  public void follow(int followerId, int followeeId) {
    users.computeIfAbsent(followerId, k -> new HashSet<>()).add(followeeId);
  }

  // removing an id that user stopped following
  public void unfollow(int followerId, int followeeId) {
    if(followerId!=followeeId && users.containsKey(followerId)) {
      users.get(followerId).remove(followeeId);
    }
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