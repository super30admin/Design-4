import java.util.*;

class Twitter {
 
    class Tweet {
       int tid;
       String message;
       int createdAt;

       Tweet(int tid, String message, int createAt) {
           this.tid = tid;
           this.message = message;
           this.createdAt = createAt;
       }
   }

   HashMap<Integer, List<Integer>> followers;
   HashMap<Integer, List<Tweet>> tweets;
   int currentTime = 0;

   public Twitter() {
       followers = new HashMap<>();
       tweets = new HashMap<>();
   }

   public void postTweet(int userId, int tweetId) {
       Tweet tweet = new Tweet(tweetId, "Test", currentTime ++);
       follow(userId, userId);
       if (!tweets.containsKey(userId)) {
           tweets.put(userId, new ArrayList<>());
       }
       tweets.get(userId).add(tweet);
   }

   public List<Integer> getNewsFeed(int userId) { 
       List<Integer> result = new ArrayList();
       List<Integer> followees = followers.get(userId);
       PriorityQueue<Tweet> pq = new PriorityQueue<Tweet>((a, b) -> a.createdAt - b.createdAt);
       if (followees != null) {
           for (Integer followee : followees) {
               List<Tweet> listOfTweets = tweets.get(followee);
               if (listOfTweets != null) {
                   for (Tweet t : listOfTweets) {
                       pq.add(t);
                       if (pq.size() > 10) {
                           pq.poll();
                       }
                   }

               }
           }
       }

       System.out.println("The size is" + pq.size());
       while (!pq.isEmpty()) {
           result.add(0, pq.poll().tid);
       }
       return result;

   }

   public void follow(int followerId, int followeeId) {
       if (!followers.containsKey(followerId)) {
           followers.put(followerId, new ArrayList<>());
       }
       if (!followers.get(followerId).contains(followeeId)) {
           followers.get(followerId).add(followeeId);
       }
   }

   public void unfollow(int followerId, int followeeId) {
       if (followers.containsKey(followerId) && followeeId != followerId) {
           List<Integer> result = followers.get(followerId);
           List<Integer> temp = new ArrayList(result);
           if (result.contains(followeeId)) {
               for (int i = 0; i < result.size(); i++) {
                   if (result.get(i) == followeeId) {
                       temp.remove(result.get(i));
                   }
               }
               followers.put(followerId, temp);
           }
       }
   }

   public static void main(String[] args) {
       Twitter obj = new Twitter();
       List<Integer> param_2 = obj.getNewsFeed(1);
       // obj.postTweet(1, 1);
       // List<Integer> param_2 = obj.getNewsFeed(1);
       // obj.follow(1,2);
       // obj.postTweet(2, 1);
       // List<Integer> param_3 = obj.getNewsFeed(2);
       // obj.unfollow(2,1);
       // List<Integer> param_4 = obj.getNewsFeed(2);
       System.out.println("The Result is" + param_2);
   }

}