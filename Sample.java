// Problem 1 - Design Twitter

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

/** Compose a new tweet. */

  public void postTweet(int userId, int tweetId) {

      // if user is not there in followed table add him

      if(!followed.containsKey(userId)) followed.put(userId, new HashSet<>());

      followed.get(userId).add(userId);

      if(!tweets.containsKey(userId)) tweets.put(userId, new ArrayList <>());

      tweets.get(userId).add(new Tweet(tweetId, timeStamp++));

  }

/** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */

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

      while(!pTweets.isEmpty()) res.add(0, pTweets.poll().id);

      return res;

  }

/** Follower follows a followee. If the operation is invalid, it should be a no-op. */

  public void follow(int followerId, int followeeId) {

      if(!followed.containsKey(followerId)) followed.put(followerId, new HashSet<>());

      followed.get(followerId).add(followeeId);

  }

/** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */

  public void unfollow(int followerId, int followeeId) {

      if(followed.containsKey(followerId) && followerId != followeeId){

          followed.get(followerId).remove(followeeId);

      }

  }

}

// Problem 2 - Skip Iterator
// Time Complexity: O(1)
// Space Complexity: O(1)

class SkipIterator implements Iterator<Integer> {
    private final Iterator<Integer> iterator;
    private final Map<Integer, Integer> map;
    private Integer curr;

    public SkipIterator(Iterator<Integer> it) {
        this.iterator = it;
        this.map = new HashMap<>();
        advance();
    }

    @Override
    public boolean hasNext() {
        return curr != null;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            return null;
        }
        Integer temp = curr;
        advance();
        return temp;
    }

    public void skip(int num) {
        if (!hasNext()) {
            return;
        }
        
        if (curr == num) {
            advance();
        } else {
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1);
            } else {
                map.put(num, 1);
            }
        }
    }

    private void advance() {
        curr = null;

        while (curr == null && iterator.hasNext()) {
            Integer el = iterator.next();
            if (!map.containsKey(el)) {
             curr = el;
            } else {
             map.put(el, map.get(el) - 1);
             map.remove(el, 0);
            }
        }

    }
}

public class Main {
    public static void main(String[] args) {
        SkipIterator it = new SkipIterator(Arrays.asList(1, 2, 3).iterator());
        System.out.println(it.hasNext());
        it.skip(2);
        System.out.println(it.next());
        it.skip(1);
        it.skip(3);
        System.out.println(it.hasNext());
    }
}
