class Tweet {
    int id;
    int timestamp;
        
    public Tweet(int id, int timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }
}

class Twitter {

    HashMap<Integer, HashSet<Integer>> follows = null;
    HashMap<Integer, List<Tweet>> userTweets = null;
    PriorityQueue<Tweet> mostRecentTweets = null;
    int timestamp;
    
    public Twitter() {
        timestamp = 0;
        follows = new HashMap<>();
        userTweets = new HashMap<>();
        mostRecentTweets = new PriorityQueue<Tweet>((t1, t2) -> t1.timestamp - t2.timestamp);
    }
    
    // TC: O(1)
    public void postTweet(int userId, int tweetId) {
        ++timestamp;
        if (!userTweets.containsKey(userId)) {
            userTweets.put(userId, new LinkedList<>());
        }
        userTweets.get(userId).add(new Tweet(tweetId, timestamp));
        // TODO: Cap at 10
    }
    
    // TC: O(n * m * log 10) => O(n * m) where m = max number of tweets.
    public List<Integer> getNewsFeed(int userId) {
        
        mostRecentTweets.clear();
        
        // We need to add tweets of the user itself
        addToMostRecentTweets(userId);
        
        // Add tweets of the user's followers
        HashSet<Integer> followers = follows.get(userId);
        if (followers != null) { 
            for (int follower : followers) {
                addToMostRecentTweets(follower);
            }
        }
        
        List<Integer> result = new ArrayList<>();
        while (!mostRecentTweets.isEmpty()) {
            result.add(0, mostRecentTweets.poll().id);
        }
        return result;
    }
    
    // TC: O(1)
    public void follow(int followerId, int followeeId) {
        if (!follows.containsKey(followerId)) {
            follows.put(followerId, new HashSet<>());
        }
        follows.get(followerId).add(followeeId);
    }
    
    // TC: O(1)
    public void unfollow(int followerId, int followeeId) {
        if (follows.containsKey(followerId)) {
            follows.get(followerId).remove(followeeId);
        }
    }
    
    private void addToMostRecentTweets(int userId) {
        List<Tweet> tweets = userTweets.get(userId);
        
        if (tweets != null) {
            for (Tweet t : tweets) {
                mostRecentTweets.offer(t);
                if (mostRecentTweets.size() > 10) {
                    mostRecentTweets.poll();
                }
            }
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