class Twitter {
    // follower -> list of followee
    Map<Integer, Set<Integer>> followMap;
    // user -> list of tweet in reverse chronological order
    Map<Integer, List<Pair<Integer, Integer>>> tweetMap;
    int timestamp = 0;
    public Twitter() {
        this.followMap = new HashMap<>();
        this.tweetMap = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        timestamp++;
        if(!tweetMap.containsKey(userId)) {
            tweetMap.put(userId, new Stack<>());
        }
        tweetMap.get(userId).add(new Pair<>(tweetId, timestamp));
    }
    
    public List<Integer> getNewsFeed(int userId) {
        Set<Integer> followeeList = followMap.getOrDefault(userId, new HashSet<>());
        followeeList.add(userId); // also show users tweets
        return generateNewsFeed(followeeList);
    }

    private List<Integer> generateNewsFeed(Set<Integer> followeeList) {
        List<Integer> newsFeed = new ArrayList<>();
        PriorityQueue<Pair<Integer, Integer>> minHeap = new PriorityQueue<>(new Comparator<Pair<Integer, Integer>>(){
            public int compare(Pair<Integer, Integer> a, Pair<Integer, Integer> b) {
                return Integer.compare(a.getValue(), b.getValue());
            }
        });

        for(Integer followee : followeeList) {
            List<Pair<Integer, Integer>> tweetList = tweetMap.getOrDefault(followee, new ArrayList<>());
            int len = tweetList.size();
            for(int i = len - 1; i >= 0 && i >= len - 10; i--) { // only take recent 10 tweets from each followers
                minHeap.add(tweetList.get(i));
                if(minHeap.size() > 10) {
                    minHeap.poll();
                }
            }
        }
        while(!minHeap.isEmpty()) {
            newsFeed.add(minHeap.poll().getKey());
        }
        return newsFeed.reversed();
    }
    
    public void follow(int followerId, int followeeId) {
        if(!followMap.containsKey(followerId)) {
            followMap.put(followerId, new HashSet<>());
        }
        followMap.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(followMap.containsKey(followerId)) {
            followMap.get(followerId).remove(followeeId);
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