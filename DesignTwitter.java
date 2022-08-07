
//Time complexity :follow,unfollow,post tweets and get tweets = O(1)
//Space complexity: O(N*max(M,T)) where N is the number of users, M is the average number of followers and T is the average number of tweets.
//In this problem, I'll be having two separate classes, one for the tweets, which I'll be maintaining using singly linked list and the users which I'll be maintaining using hashmap and their followers list in a hashset. I'll be initializing the constructor for tweets class in such a way that every time a user posts a tweet, I'll be incrementing a corresponding time tamp, which will help me while displaying the newsfeed. I'll be having helper functions such as follow, unfollow and post tweets. The newfeed display is carried out using a max heap, which will take in the tweets of both the user's as well as the users followed by the users in the order in which they were posted. and displaying only the top 10 most recent tweets.
class Twitter {
      HashMap <Integer, HashSet<Integer>> users = new HashMap<>();
      HashMap <Integer, List<Tweet>> tweets = new HashMap<>();

      int timeStamp;
      int feedSize = 10;

      private static class Tweet {
          int id;
          int createdAt;
          public Tweet(int id, int createdAt){
              this.id = id;
              this.createdAt = createdAt;
          }
      }

   /** Initialize your data structure here. */
      public Twitter() {
          users = new HashMap<>();
          tweets = new HashMap<>();
   }

   /** Compose a new tweet. */
      public void postTweet(int userId, int tweetId) {
          // if user is not there in followed table add him
          if(!tweets.containsKey(userId)){
           tweets.put(userId, new ArrayList <>());   
          } 
          tweets.get(userId).add(new Tweet(tweetId, timeStamp++));
      }

   /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */

      public List<Integer> getNewsFeed(int userId) {
          follow(userId,userId);
          PriorityQueue <Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
          
          HashSet<Integer> followedPpl = users.get(userId);
          for(int user : followedPpl){
              List<Tweet> alltweets = tweets.get(user);
              if(alltweets != null){
                  for(Tweet tw : alltweets){
                      pq.add(tw);
                      if(pq.size()>10){
                          pq.poll();
                      }
                  }
              }
          }
          List<Integer> result = new ArrayList<>();
          while(!pq.isEmpty()){
              result.add(0,pq.poll().id);
          }
          return result;
      }

   /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
      public void follow(int followerId, int followeeId) {
          if(!users.containsKey(followerId)) users.put(followerId, new HashSet<>());
          users.get(followerId).add(followeeId);
      }

   /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
      public void unfollow(int followerId, int followeeId) {
          if(users.containsKey(followerId) && followerId != followeeId){
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