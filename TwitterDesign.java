class Twitter {

  class User {
      int userId;
      Set<User> following;
      List<Tweet> tweets;
      User(int userId) {
          this.userId = userId;
          this.following = new HashSet<>();
          this.tweets = new ArrayList<>();
          this.following.add(this); // User can follow themself.
      }
  }
  
  class Tweet {
      int tweetId;
      int userId;
      int time;
      Tweet (int userId, int tweetId, int time) {
          this.tweetId = tweetId;
          this.userId = userId;
          this.time = time;
      }
  }
  
  Map<Integer, User> userMap;
  int time;

  public Twitter() {
     userMap = new HashMap<>();
     time = 1;
  }
  
  public void postTweet(int userId, int tweetId) {
      Tweet newTweet = new Tweet(userId, tweetId, time);
      time++;
  
      if(!userMap.containsKey(userId)){
          User newUser = new User(userId);
          userMap.put(userId, newUser);
      }
      
      User currUser = userMap.get(userId);
      currUser.tweets.add(newTweet);
  }
  
  public List<Integer> getNewsFeed(int userId) {
      User currUser = userMap.get(userId);
      
      List<Integer> result = new ArrayList<>();
      
      if(currUser == null) { return result; }
      PriorityQueue<Tweet> pq = new PriorityQueue<>((t1, t2) -> (t1.time - t2.time));
      
      for(User user : currUser.following){
          for(Tweet tweet : user.tweets){
              pq.add(tweet);
              
              // remove the oldest Tweet whenever the size is > 10
              if(pq.size() > 10){
                  pq.remove();
              }
          }
      }
      
      while(!pq.isEmpty()){
          Tweet t = pq.remove();
          result.add(t.tweetId);
      }
      
      // the priority queue would have the most recent tweets, but in reverse order.
      Collections.reverse(result);
      return result;
  }
  
  public void follow(int followerId, int followeeId) {

      if(!userMap.containsKey(followerId)){
          userMap.put(followerId, new User(followerId));
      }
      
      if(!userMap.containsKey(followeeId)){
          userMap.put(followeeId, new User(followeeId));
      }
      
      User follower = userMap.get(followerId);
      User followee = userMap.get(followeeId);
      
      follower.following.add(followee);
  }
  
  public void unfollow(int followerId, int followeeId) {
      User follower = userMap.get(followerId);
      
      User followee = userMap.get(followeeId);
      
      follower.following.remove(followee);
  }
}
