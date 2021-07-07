class Twitter {

    class Tweet{
        int tId;
        int timestamp;
        public Tweet(int tId, int timestamp) {
            this.tId = tId;
            this.timestamp = timestamp;
        }
    }
    /** Initialize your data structure here. */
    HashMap<Integer, HashSet<Integer>> followers;
    HashMap<Integer, List<Tweet>> tweetMap;
    int feedSize;
    int timestamp;
    public Twitter() {
        followers = new HashMap<>();
        tweetMap = new HashMap<>();
        this.feedSize = 10;
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        if(!followers.containsKey(userId)) {
            followers.put(userId, new HashSet<>());
        }
        followers.get(userId).add(userId);
        
        Tweet t = new Tweet(tweetId, timestamp++);
        if(!tweetMap.containsKey(userId)) {
            tweetMap.put(userId, new ArrayList<>());
        }
        tweetMap.get(userId).add(t);
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        
        PriorityQueue<Tweet> pq = new PriorityQueue<>((t1,t2) -> t1.timestamp - t2.timestamp);
        if(followers.containsKey(userId)) {
            HashSet<Integer> set = followers.get(userId);
            if(set !=null) {
                    for(Integer user: set) {
                    List<Tweet> userTweets = tweetMap.get(user);
                    if(userTweets != null) {
                            for(Tweet t1 : userTweets) {
                            if(pq.size() < feedSize) {
                                pq.add(t1);
                            }
                            else {
                                if(pq.peek().timestamp < t1.timestamp)
                                {
                                    pq.poll();
                                    pq.add(t1);
                                }
                            }
                        }
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty())
        {
            result.add(0, pq.poll().tId);
        }
        return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!followers.containsKey(followerId)) {
            followers.put(followerId, new HashSet<>());
        }
        followers.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        
        if(followers.containsKey(followerId) && followerId != followeeId) {
            followers.get(followerId).remove(followeeId);
        }
    }
}