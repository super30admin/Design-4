// Time Complexity :
// Space Complexity :
// Did this code successfully run on Leetcode :No
// Any problem you faced while coding this :


// Your code here along with comments explaining your approach

class Twitter {
  int timestamp;
  int feedsize;
 HashMap< Integer,List<Tweet>> tweetsmap;
  HashMap<Integer,Set<Integer>> followersmap;
  List<Tweet> getfeed;
  /** Initialize your data structure here. */
  public Twitter() {
      timestamp=0;
      feedsize =10;
      tweetsmap=new HashMap<>();
      followersmap=new HashMap<>();

  }
  
  /** Compose a new tweet. */
  public void postTweet(int userId, int tweetId) {
      isFirsttime(userId);
      List<Tweet> t=tweetsmap.get(userId);
      t.add(new Tweet(tweetId,timestamp++));
  }
  
  /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
  public List<Integer> getNewsFeed(int userId) {
       isFirsttime(userId);
      getfeed= new ArrayList<>();
      Set<Integer> users=followersmap.get(userId);
      for(Integer i:users){
          getfeed.addAll(tweetsmap.get(i));
      }
      Collections.sort(getfeed,(a,b) ->(b.CreatedAt -a.CreatedAt));
      
      List<Integer> recentfeed =new ArrayList<>();
      Iterator<Tweet> itr=getfeed.iterator();
      int count=feedsize;
      while(count>0 && itr.hasNext()){
          recentfeed.add(itr.next().TweetId);
          count--;
      }
      
      
      return recentfeed;
  }
  
  /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
  public void follow(int followerId, int followeeId) {
     isFirsttime(followerId);
       isFirsttime(followeeId);
      Set<Integer> f=followersmap.get(followerId);
      f.add(followeeId);
  }
  
  /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
  public void unfollow(int followerId, int followeeId) {
        isFirsttime(followerId);
       isFirsttime(followeeId);
      if(followerId != followeeId){
           followersmap.remove(followerId);
      }
  }
  
  public void isFirsttime(int userId){
  Set<Integer> fmap=followersmap.get(userId);
      if(fmap ==null){
          fmap=new HashSet<>();
          fmap.add(userId);
          followersmap.put(userId,fmap);
      }
  List<Tweet> tmap=tweetsmap.get(userId);
      if(tmap==null){
         tmap =new LinkedList<>();
          tweetsmap.put(userId,tmap);
      }
  }
  
  //  public void isFirsttime(int element){
  //  if(!tweetsmap.containsKey(element)) {
  //      List<Tweet> tweets=new LinkedList<>();
  //      tweetsmap.put(element,tweets);
  //  }   
  //  if(!followersmap.containsKey(element)) {
  //      Set<Integer> followers= new HashSet<>();
  //      followers.add(element);
  //      followersmap.put(element,followers);
  //  }   
  // }
  
class Tweet{
  int TweetId;
  int CreatedAt;
  Tweet(int TweetId,int CreatedAt){
      this.TweetId=TweetId;
      this.CreatedAt =CreatedAt;
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