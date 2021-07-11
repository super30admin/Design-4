class Twitter {
    class Tweet {
      int tweetId;
      int createAt;
      public Tweet(int tweetId, int createAt) {
        this.tweetId = tweetId;
        this.createAt = createAt;
      }
    }
    HashMap<Integer, Set<Integer>> followed;
    HashMap<Integer, List<Tweet>> tweets;
    int time;

    /** Initialize your data structure here. */
    public Twitter() {
      followed = new HashMap<>();
      tweets = new HashMap<>();
         
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
      follow(userId,userId);
      if(!tweets.containsKey(userId)){
        tweets.put(userId, new ArrayList<>());
      }
        tweets.get(userId).add(new Tweet(tweetId, time++));
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
     
      PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createAt - b.createAt);
       //Get the all the list of followee the given user is following
      Set<Integer> followeeList  = followed.get(userId);
      if(followeeList != null) {
        //traverse followee list
        for(Integer fid : followeeList) {
          //get the list of tweet of the follower.
          List<Tweet> tweetList = tweets.get(fid);
          if(tweetList != null) {
            //iterate list of the tweet
            for(Tweet tweet : tweetList) {
              pq.add(tweet);
              if(pq.size() > 10) { //remove tweet from pq
                pq.poll();
              }
            }
          }
        }
      }
      List<Integer> result = new ArrayList<>();
      while(!pq.isEmpty()) {
        result.add(0, pq.poll().tweetId);
      }
      return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
      //check the user exist in followed id if not add the user(follower) then add followee in the hash set
      
        if(!followed.containsKey(followerId)) {
          followed.put(followerId, new HashSet<>());
        }
      
        followed.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
      //to unfollow get the follower id and check if its not same user then get the set and remove followee from it.
         if(followed.containsKey(followerId) && followerId != followeeId) {
           Set<Integer> set = followed.get(followerId);
           set.remove(followeeId);
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