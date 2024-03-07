class Twitter {
    HashMap<Integer, HashSet<Integer>> followedMap;
    HashMap<Integer, List<Tweet>> tweetMap;
    int time;
    class Tweet {
        int id;
        int createdAt;
        Tweet(int id, int time) {
            this.id = id;
            this.createdAt = time;
        }
    }
    public Twitter() {
        this.followedMap = new HashMap<>();
        this.tweetMap = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        follow(userId,userId);
        if(!tweetMap.containsKey(userId)) {
           tweetMap.put(userId,new ArrayList<>());
        }
        tweetMap.get(userId).add(new Tweet(tweetId, time++));
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b)-> a.createdAt - b.createdAt);
        HashSet<Integer> followedIds = followedMap.get(userId);
        List<Integer> result = new ArrayList<>();
        if(followedIds != null) {
          for(Integer fid : followedIds) {
              List<Tweet> fTweets = tweetMap.get(fid);
              if(fTweets != null) {
                  for(Tweet fTweet : fTweets) {

                      pq.add(fTweet);
                      if(pq.size() > 10) {
                          pq.poll();
                      }
                  }
              }
          }
        }
        while(!pq.isEmpty()) {
            result.add(0,pq.poll().id);
        }
        return result;
    }
    
    public void follow(int followerId, int followeeId) {
        if(!followedMap.containsKey(followerId)) {
            followedMap.put(followerId, new HashSet<>());
        }
        followedMap.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(followedMap.containsKey(followerId)) {
            followedMap.get(followerId).remove(followeeId);
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