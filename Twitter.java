// This solution uses two maps - one for followees and the other for list of tweets for each user. 
// We maintain a priority queue to find top 10 latest posts
class Twitter {

    class Tweet {
        int id;
        int timestamp;

        public Tweet(int id, int timestamp) {
            this.id = id;
            this.timestamp = timestamp;
        }
    }

    Map<Integer, Set<Integer>> followees;
    Map<Integer, List<Tweet>> tweets;
    int time;

    public Twitter() {
        this.followees = new HashMap();
        this.tweets = new HashMap();
        time=0;
    }
    
    public void postTweet(int userId, int tweetId) {
        if(!tweets.containsKey(userId)) {
            tweets.put(userId, new ArrayList());
        }
        tweets.get(userId).add(new Tweet(tweetId, time++));
        follow(userId, userId);
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<Tweet>((a,b) -> a.timestamp-b.timestamp);
        if(!followees.containsKey(userId)) return new ArrayList();
        for(int followee:followees.get(userId)) {
            if(tweets.containsKey(followee)) {
                List<Tweet> list = tweets.get(followee);
                for(Tweet tweet:list) {
                    pq.add(tweet);
                    if(pq.size()>10) {
                        pq.poll();
                    }
                }
            }
        }
        List<Tweet> tweets = new ArrayList();
        while(!pq.isEmpty()) {
            tweets.add(pq.poll());
        }
        List<Integer> result = new ArrayList<>();
        for (int i = tweets.size() - 1; i >= 0; i--) {
            result.add(tweets.get(i).id);
        }
        return result;
    }
    
    public void follow(int followerId, int followeeId) {
        if(!followees.containsKey(followerId)) {
            followees.put(followerId, new HashSet());
        }
        followees.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
         if(!followees.containsKey(followerId)) {
            return;
        }
        followees.get(followerId).remove(followeeId);
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
