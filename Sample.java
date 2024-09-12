//Time Complexity:O(N)
//Space Complexity:O(N*max(M,T))
class Twitter {

      HashMap <Integer, Set<Integer>> followed = new HashMap<>();

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

   }



      public void postTweet(int userId, int tweetId) {


          if(!followed.containsKey(userId)) followed.put(userId, new HashSet<>());

          followed.get(userId).add(userId);

          if(!tweets.containsKey(userId)) tweets.put(userId, new ArrayList <>());

          tweets.get(userId).add(new Tweet(tweetId, timeStamp++));

      }



      public List<Integer> getNewsFeed(int userId) {

          PriorityQueue <Tweet> pTweets = new PriorityQueue<>((t1,t2) -> t1.createdAt - t2.createdAt);

          Set<Integer> followeds = followed.get(userId);

          if(followeds != null){

              for(int f : followeds){

                  List<Tweet> fTweets = tweets.get(f);

                  if(fTweets == null) continue;

                  for(Tweet t: fTweets){

                      if(pTweets.size() < feedSize){

                          pTweets.add(t);

                      } else {// where my pq is full

                          if(t.createdAt > pTweets.peek().createdAt){

                              pTweets.poll();

                              pTweets.add(t);

                          }

                      }

                  }

              }

          }

          List<Integer> res = new ArrayList<>();

          while(!pTweets.isEmpty()) res.add(0,pTweets.poll().id);

          return res;

      }


      public void follow(int followerId, int followeeId) {

         if(!followed.containsKey(followerId)) followed.put(followerId, new HashSet<>());

          followed.get(followerId).add(followeeId);

      }


      public void unfollow(int followerId, int followeeId) {

          if(followed.containsKey(followerId) && followerId != followeeId){

              followed.get(followerId).remove(followeeId);

          }

      }

  }


-------------------------------------------------------------------------------------
class SkipIterator implements Iterator<Integer> {

     private final Iterator<Integer> it;

     private final Map<Integer, Integer> count;

     private Integer nextEl;

   public SkipIterator(Iterator<Integer> it) {
this.it = it;

         this.count = new HashMap<>();

         advance();

     }

   @Override

     public boolean hasNext() {

         return nextEl != null;

     }

   @Override

     public Integer next() {

         if (!hasNext()) throw new RuntimeException("empty");

         Integer el = nextEl;

         advance();

         return el;

     }

   public void skip(int num) {

         if (!hasNext()) throw new RuntimeException("empty");

         if (nextEl == num) {

             advance();

         } else {

             count.put(num, count.getOrDefault(num, 0) + 1);

         }

     }

   private void advance() {

         nextEl = null;

         while (nextEl == null && it.hasNext()) {

             Integer el = it.next();

             if (!count.containsKey(el)) {

                 nextEl = el;

             } else {

                 count.put(el, count.get(el) - 1);

                 count.remove(el, 0);

             }

         }

     }

 }

public class Main {

         public static void main(String[] args) {

         SkipIterator it = new SkipIterator(Arrays.asList(1, 2, 3).iterator());

         System.out.println(it.hasNext());

         it.skip(2);

         it.skip(1);

         it.skip(3);

         System.out.println(it.hasNext());

     }

 }



------------------------------------------------------------------------