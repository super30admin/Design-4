class Twitter {

    HashMap<Integer, HashSet<Integer>> followeesMap;
    HashMap<Integer, List<Tweet>> tweetMap;
    int time;

    class Tweet {
        int tweetId;
        int timestamp;

        public Tweet(int tweetId, int timestamp) {
            this.tweetId = tweetId;
            this.timestamp = timestamp;
        }
    }

    public Twitter() {
        this.followeesMap = new HashMap<>();
        this.tweetMap = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        follow(userId,userId);
        if(!tweetMap.containsKey(userId)){
            tweetMap.put(userId, new ArrayList<>());
        }
        tweetMap.get(userId).add(new Tweet(tweetId, time++));
    }

    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.timestamp - b.timestamp);
        HashSet<Integer> followees = followeesMap.get(userId);

        if(followees != null) {
            for(Integer followee : followees) {
                List<Tweet> tweets = tweetMap.get(followee);
                if(tweets != null) {
                    for(int i=0;i< tweets.size();i++){ //O(n)
                        pq.add(tweets.get(i));
                        if(pq.size() > 10) {
                            pq.poll();
                        }
                    }
                }
            }
        }

        List<Integer> result = new ArrayList<>();

        while(!pq.isEmpty()){
            result.add(0, pq.poll().tweetId);
        }

        return result;
    }

    public void follow(int followerId, int followeeId) {
        if(!followeesMap.containsKey(followerId)) {
            followeesMap.put(followerId, new HashSet<>());
        }
        followeesMap.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if(followeesMap.containsKey(followerId)) {
            followeesMap.get(followerId).remove(followeeId);
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