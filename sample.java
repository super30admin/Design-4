class Twitter {
    class tweet {
        int tweetid;
        int createdtime;
        public tweet(int tweetId, int time) {
            tweetid = tweetId;
            createdtime = time;
        }
    }
    Map<Integer, Set<Integer>> usermap;
    Map<Integer, List<tweet>> tweetmap;
    int time;

    public Twitter() {
        usermap = new HashMap<>();
        tweetmap = new HashMap<>();
    }
    public void postTweet(int userId, int tweetId) {
        if(!tweetmap.containsKey(userId)) {
            tweetmap.put(userId, new ArrayList<>());
        }
        tweetmap.get(userId).add(new tweet(tweetId, time++));
    }
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<tweet> q = new PriorityQueue<>((a, b) -> (a.createdtime - b.createdtime));
        if (tweetmap.containsKey(userId)) {
            List<tweet> tw = tweetmap.get(userId);
            for(tweet list:tw) {
                if(list != null) {
                    q.add(list);
                    if (q.size() > 10) {
                        q.poll();
                    }
                }
            }
        }
        Set<Integer> fwlist = usermap.get(userId);
        if(fwlist != null) {
            for ( int fw: fwlist) {
                List<tweet> tw = tweetmap.get(fw);
                for(tweet list:tw) {
                    if(list != null) {
                        q.add(list);
                        if (q.size() > 10) {
                            q.poll();
                        }
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while (!q.isEmpty()) {
            result.add(0, q.poll().tweetid);
        }
        return result;

    }
    public void follow(int followerId, int followeeId) {
        if (!usermap.containsKey(followerId)) {
            usermap.put(followerId, new HashSet<>());
        }
        usermap.get(followerId).add(followeeId);
    }
    public void unfollow(int followerId, int followeeId) {
        if (!usermap.containsKey(followerId)) {
            return;
        }
        if (usermap.get(followerId).contains(followeeId)) {
            usermap.get(followerId).remove(followeeId);
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
