class DesignTwitter {

    class Tweet {
        int tweetId;
        int time;
        public Tweet(int tweetId, int time){
            this.tweetId = tweetId;
            this.time = time;
        }
    }
    Map<Integer, List<Integer>> followerMap = new HashMap<>();
    Map<Integer, List<Tweet>> tweetMap = new HashMap<>();
    int counter;

    public Twitter() {

    }

    public void postTweet(int userId, int tweetId) {
        Tweet tweet = new Tweet(tweetId, counter++);
        if(!tweetMap.containsKey(userId)){
            tweetMap.put(userId, new ArrayList<>());
        }
        tweetMap.get(userId).add(tweet);
    }

    public List<Integer> getNewsFeed(int userId) {
        Set<Integer> users = new HashSet<>();
        if(followerMap.get(userId) != null){
            users.addAll(followerMap.get(userId));
        }
        users.add(userId);
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.time - b.time);
        for(int id: users){
            List<Tweet> tweets = tweetMap.get(id);
            if(tweets!=null){
                for(Tweet tweet: tweets){
                    pq.add(tweet);
                    if(pq.size() > 10){
                        pq.poll();
                    }
                }
            }
        }
        List<Integer> tweetIds = new ArrayList<>();
        while(!pq.isEmpty()){
            Tweet tweet = pq.poll();
            tweetIds.add(0,tweet.tweetId);
        }
        return tweetIds;
    }

    public void follow(int followerId, int followeeId) {
        if(!followerMap.containsKey(followerId)){
            followerMap.put(followerId, new ArrayList<>());
        }
        followerMap.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if(!followerMap.containsKey(followerId)){
            return;
        }
        List<Integer> followees = followerMap.get(followerId);
        if(followees!=null){
            for(int i=0; i<followees.size(); i++){
                followerMap.get(followerId).remove(i);
            }
        }
    }
}
