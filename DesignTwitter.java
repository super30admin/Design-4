/*

2 separate classes -> one for tweets and other for newsfeed

Class tweets
 -> will initialize a tweet with id and timestamp
 -> create tweet object 

 users -> userMap -> consist of HashMap who it follows
 pq -> to get the tweets

SC: Space Complexity:O(N*max(M,T)) where N is the number of users, 
M is the average number of followers and T is the average number of tweets.

*/

class Twitter {

  HashMap<Integer, HashSet<Integer>> usersMap;
  HashMap<Integer, List<Tweet>> tweets;
  int time;

  class Tweet {

    int tid;
    int createdAt;

    public Tweet(int id, int time) {
      this.tid = id;
      this.createdAt = time;
    }
  }

  public Twitter() {
    this.usersMap = new HashMap<>();
    this.tweets = new HashMap<>();
  }

  public void postTweet(int userId, int tweetId) {
    if (!tweets.containsKey(userId)) {
      follow(userId, userId);
      tweets.put(userId, new ArrayList<>());
    }
    Tweet tweet = new Tweet(tweetId, time++);
    tweets.get(userId).add(tweet);
  }

  public List<Integer> getNewsFeed(int userId) {
    //get top 10 tweets
    PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) ->
      a.createdAt - b.createdAt
    );
    List<Integer> result = new ArrayList<>();
    if (usersMap.containsKey(userId)) {
      HashSet<Integer> followers = usersMap.get(userId);
      for (int fid : followers) {
        List<Tweet> ftweets = tweets.get(fid);
        if (ftweets != null) {
          for (Tweet t : ftweets) {
            pq.add(t);
            if (pq.size() > 10) {
              pq.poll();
            }
          }
        }
      }
    }
    while (!pq.isEmpty()) {
      result.add(0, pq.poll().tid);
    }
    return result;
  }

  // TC: O(1)
  public void follow(int followerId, int followeeId) {
    if (!usersMap.containsKey(followerId)) {
      usersMap.put(followerId, new HashSet<>());
    }

    usersMap.get(followerId).add(followeeId);
  }

  //TC: O(1)
  public void unfollow(int followerId, int followeeId) {
    if (followerId != followeeId && usersMap.containsKey(followerId)) {
      usersMap.get(followerId).remove(followeeId);
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
