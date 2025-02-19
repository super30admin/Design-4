class Twitter {
    class Tweet {
        int tweetId;
        int createdAt;

        public Tweet(int tweetId, int createdAt) {
            this.tweetId = tweetId;
            this.createdAt = createdAt;
        }
    }

    int tweetTime;
    HashMap<Integer, HashSet<Integer>> userMap;
    HashMap<Integer, List<Tweet>> tweetMap;

    public Twitter() {
        tweetTime = 0;
        userMap = new HashMap<>();
        tweetMap = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        if (!tweetMap.containsKey(userId)) {
            tweetMap.put(userId, new ArrayList<Tweet>());
        }
        tweetMap.get(userId).add(new Tweet(tweetId, tweetTime++));
    }

    public List<Integer> getNewsFeed(int userId) {
       
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);
        if (tweetMap.containsKey(userId)) {
            for (Tweet t : tweetMap.get(userId)) {
                pq.add(t);
                if (pq.size() > 10) {
                    pq.poll();
                }
            }
        }
        HashSet<Integer> followers = userMap.get(userId);
        if (followers!= null){
        for (int follower : followers) {
            List<Tweet> allT = tweetMap.get(follower);
            if (allT != null) {
                for (Tweet tw : allT) {
                    pq.add(tw);
                    if (pq.size() > 10) {
                        pq.poll();
                    }

                }
            }
        }}
        List<Integer> res = new ArrayList<>();
        while (!pq.isEmpty()){
            res.add (0, pq.poll().tweetId);
        }
        return res;
    }

    public void follow(int followerId, int followeeId) {
        if (!userMap.containsKey(followerId)) {
            userMap.put(followerId, new HashSet<>());
        }
        userMap.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
       if (userMap.containsKey(followerId)) {
        userMap.get(followerId).remove(followeeId);
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
 *
