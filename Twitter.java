import java.util.*;
//https://leetcode.com/problems/design-twitter/submissions/
class Twitter {
    class tweet{
        int id;
        int createdAt;
        public tweet(int id,int createdAt){
            this.id = id;
            this.createdAt = createdAt;
        }
    }
    HashMap<Integer,HashSet<Integer>> followed;
    HashMap<Integer,List<tweet>> tweet;
    int time;
    public Twitter() {
        this.followed = new HashMap<>();
        this.tweet = new HashMap<>();
        this.time =0;
    }
    //time complexity : 1
    // space complexity : 1
    // did it run on leetcode : yes 
    // any doubts :  no
    
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
         if(!tweet.containsKey(userId)){
            tweet.put(userId,new ArrayList<>());
        }
        tweet.get(userId).add(new tweet(tweetId,time++));
        
    }
    //time complexity : max number of people followed by a person
    // space complexity : max number of people followed by a person (N)
    // did it run on leetcode : yes 
    // any doubts :  no
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue <tweet> pTweets = new PriorityQueue<>((t1,t2) -> t1.createdAt - t2.createdAt);
          Set<Integer> followeds = followed.get(userId);
          if(followeds != null){
              for(int f : followeds){
                  List<tweet> fTweets = tweet.get(f);
                  if(fTweets == null) continue;
                  for(tweet t: fTweets){

                      if(pTweets.size() < 10){

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
    
  //time complexity : 1
    // space complexity : 1
    // did it run on leetcode : yes 
    // any doubts :  no
    public void follow(int followerId, int followeeId) {
        if(!followed.containsKey(followerId)){
            followed.put(followerId,new HashSet<>());
        }
        followed.get(followerId).add(followeeId);
    }
    //time complexity : 1
    // space complexity : 1
    // did it run on leetcode : yes 
    // any doubts :  no
    public void unfollow(int followerId, int followeeId) {
          if(followed.containsKey(followerId) && followerId != followeeId ){
            followed.get(followerId).remove(followeeId);
        }
    }
}
