
class Twitter {
    class Tweet {
        int tweetId;
        int createdAt;
        public Tweet(int id, int time) {
            this.tweetId = id;
            this.createdAt = time;
        }
    }

    HashMap<Integer, HashSet<Integer>> followedMap;
    HashMap<Integer, List<Tweet>> tweetsMap;
    int time;

    public Twitter() {
        this.followedMap = new HashMap<>();
        this.tweetsMap = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) { // O(1)
        // follow(userId,userId);
        Tweet newTw = new Tweet(tweetId, time);
        time++;
        if(tweetsMap.containsKey(userId))
            tweetsMap.get(userId).add(newTw);
        else {
            ArrayList<Tweet> tweets = new ArrayList<>();
            tweets.add(newTw);
            tweetsMap.put(userId, tweets);
        }
    }
    
    public List<Integer> getNewsFeed(int userId) { //Nlog(10) == O(N)
    PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);

    // Include user's own tweets
    if (tweetsMap.containsKey(userId)) {
        for (Tweet tweet : tweetsMap.get(userId)) {
            pq.add(tweet);
            if (pq.size() > 10) {
                pq.poll(); 
            }
        }
    }

    if (followedMap.containsKey(userId)) {
        for (int followee : followedMap.get(userId)) {
            if (tweetsMap.containsKey(followee)) {
                for (Tweet tweet : tweetsMap.get(followee)) {
                    pq.add(tweet);
                    if (pq.size() > 10) {
                        pq.poll();
                    }
                }
            }
        }
    }

    // Collect tweets from priority queue, reverse order (most recent first)
    List<Integer> result = new ArrayList<>();
    while (!pq.isEmpty()) {
        result.add(0, pq.poll().tweetId); 
    }

    return result;
}

    
    public void follow(int followerId, int followeeId) { //O(1)
        if(followedMap.containsKey(followerId))
            followedMap.get(followerId).add(followeeId);
        else {
            HashSet<Integer> set = new HashSet<>();
            set.add(followeeId);
            followedMap.put(followerId,set);
        }
    }
    
    public void unfollow(int followerId, int followeeId) { //O(1)
        if(followedMap.get(followerId)!= null)
            followedMap.get(followerId).remove(followeeId);
    }
}
