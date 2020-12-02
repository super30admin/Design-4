class Twitter{

  HashMap<Integer,Set<Integer>> followed = new HashMap<>();
  HashMap<Integer, List<Tweet>> tweets = new HashMap<>();
  int timeStamp;
  int feedSize=10;

  class Tweet{
    int id;
    int createdAt;
    public Tweet(int id,int createdAt){
      this.id=id;
      this.createdAt = createdAt;
    }
  }

  public Twitter(){

}

public void postTweet(int userId,int tweetId){
  //Add tweet to hashmap of tweets
  if(!followed.containsKey(userId))followed.put(userId,new HashSet<>());
  followed.get(userId).add(userId);
  if(!tweets.containsKey(userId)) tweets.put(userId,new ArrayList<>());
  tweets.get(userId).add(new Tweet(tweetId,timeStamp++));

}

public List<Integer> getNewsFeed(int userId){
  PriorityQueue<Tweet> pq = new PriorityQueue<>((t1,t2) -> t1.cretedAt - t2.createdAt);
  Set<Integer> fIds = followed.get(userId);
  if(fIds != null){
    for(int fId : fIds){
        List<Tweet> fTwwets = tweets.get(fId);
        if(fTweets == null) continue;
        for(Tweet t: fTweets){
          if(pq.size()<feedSize){
            pq.add(t);
          }else{
            if(t.createdAt > pq.peek().createdAt){
              pq.poll();
              pq.add(t);
            }
          }
        }
    }
  }
  List<Integer> result = new ArrayList<>();
  while(!pq.isEmpty()){
    result.add(0,pq.poll(),id);
  }
  return result;
}


public void follow(int followerId,int followeeId){
  if(!followed.containsKey(followerId)) followed.put(followerId,new HashSet<>());
  followed.get(followerId).add(followeeId);
}

public void unfollow(int followerId,int followeeId){
  if(followed.containsKey(followerId) && followerId !=followeeId){
    followed.get(followerId).remove(followeeId);
  }
}
}





