class Twitter {
    Map<Integer, Set<Integer>> followers = new HashMap<>();
    Map<Integer, List<Tweet>> tweets = new HashMap<>();
    int timeStamp = 0;

    /** Initialize your data structure here. */
    public Twitter() {
    }


    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if(!tweets.containsKey(userId))
            tweets.put(userId, new ArrayList<>());
        Tweet newTweet = new Tweet(tweetId, timeStamp++);
        tweets.get(userId).add(newTweet);
    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> (a.timeStamp - b.timeStamp));
        List<Integer> res = new ArrayList<>();
        Set<Integer> fl = followers.get(userId);

        if(fl != null) {
            for(Integer follower: fl) {
                List<Tweet> allTweets = tweets.get(follower);

                if(allTweets != null) {
                    for(Tweet t: allTweets) {
                        pq.add(t);
                        if(pq.size() > 10) {
                            pq.poll();
                        }
                    }
                }
            }
        }

        while(!pq.isEmpty()) {
            res.add(0, pq.poll().tweetId);
        }

        return res;
    }

    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!followers.containsKey(followeeId))
            followers.put(followeeId, new HashSet<>());
        followers.get(followeeId).add(followerId);
    }

    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(followers.containsKey(followeeId))
            followers.get(followeeId).remove(followerId);
    }
    class Tweet {
        public int tweetId;
        public int timeStamp;

        public Tweet(int tweetId, int timeStamp) {
            this.tweetId = tweetId;
            this.timeStamp = timeStamp;
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