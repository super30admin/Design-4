//Time Complexity: O(n)
//Space Complexity: O(n)

class Twitter {   
    class Tweet {
        int id;
        int createdAt;
        public Tweet(int id, int time) {
            this.id = id;
            this.createdAt = time;
        }
    }

    HashMap<Integer, HashSet<Integer>> map; 
    HashMap<Integer, ArrayList<Tweet>> tweets; 
    int time = 0;
    public Twitter() {
        map = new HashMap();
        tweets = new HashMap();
    }
    
    public void postTweet(int userId, int tweetId) {
        if(!tweets.containsKey(userId)) {
            tweets.put(userId, new ArrayList());
        }
        tweets.get(userId).add(new Tweet(tweetId, time++));
    }
    
    public List<Integer> getNewsFeed(int userId) {
        follow(userId, userId);
        HashSet<Integer> allFollowers = map.get(userId);
        if(allFollowers == null) {
            return new ArrayList();
        }
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);
        for(int follower : allFollowers) {
            List<Tweet> followerTweets = tweets.get(follower);
                if(followerTweets != null) {
                    for(Tweet followerTweet: followerTweets) {
                            pq.add(followerTweet);
                            if(pq.size() > 10) {
                                pq.poll();
                            }
                    }
                }
            }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()) {
            Tweet t = pq.poll();
            result.add(0, t.id);
        }
        return result;
    }
    
    public void follow(int followerId, int followeeId) {
      if(!map.containsKey(followerId)) {
          map.put(followerId, new HashSet<>());
      }

      map.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
      if(map.containsKey(followerId) && followerId != followeeId) {
            map.get(followerId).remove(followeeId);
      }
    }
}