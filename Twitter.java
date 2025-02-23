lass Twitter {
    class Tweet {
        private int tweetId;
        private int createdAt;

        public Tweet(int id, int time) {
            this.tweetId = id;
            this.createdAt = time;
        }
    }

    int time;
    HashMap<Integer, HashSet<Integer>> followedMap;
    HashMap<Integer, List<Tweet>> tweetMap;

    public Twitter() {
        this.followedMap = new HashMap<>();
        this.tweetMap = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        
        if (!tweetMap.containsKey(userId)) {
            tweetMap.put(userId, new ArrayList<>());
            follow(userId, userId);
        }
        Tweet newTweet = new Tweet(tweetId, time);
        time++;
        tweetMap.get(userId).add(newTweet);
    }

    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        // get users that userId is following
        HashSet<Integer> followeds = followedMap.get(userId);
        if (followeds != null) { // NlogK
            for (Integer fo : followeds) {
                List<Tweet> ftweets = tweetMap.get(fo);
                if (ftweets != null) {
                    int len = ftweets.size();
                    for (int i = len - 1; i >= 0 && i >= len - 11; i--) { // loop through top 10 tweets for each
                        Tweet curr = ftweets.get(i);
                        pq.add(curr);
                        if (pq.size() > 10) {
                            pq.poll();
                        }
                    }
                }

            }
        }
        List<Integer> result = new ArrayList<>();
        while (!pq.isEmpty()) { //reverse order
            result.add(0, pq.poll().tweetId);
        }
        return result;
    }

    public void follow(int followerId, int followeeId) {

        if (!followedMap.containsKey(followerId)) {
            this.followedMap.put(followerId, new HashSet<Integer>());
        }
        this.followedMap.get(followerId).add(followeeId);

    }

    public void unfollow(int followerId, int followeeId) {
        if (followedMap.containsKey(followerId) && followeeId != followerId
                && this.followedMap.get(followerId).contains(followeeId)) {
            this.followedMap.get(followerId).remove(followeeId);
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