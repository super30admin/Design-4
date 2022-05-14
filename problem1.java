class Twitter {

    class Tweet{
        int id, createdAt;
        public Tweet(int id, int time){
            this.id = id;
            this.createdAt = time;
        }
    }

    HashMap<Integer, HashSet<Integer>> map;
    HashMap<Integer, List<Tweet>> tweets;
    int time;

    public Twitter() {
        map = new HashMap<>();
        tweets = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        if(!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId, time++));
    }
    
    public List<Integer> getNewsFeed(int userId) {
        follow(userId, userId);
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        HashSet<Integer> users = map.get(userId);
        for(Integer user : users){
            List<Tweet> allTweets = tweets.get(user);
            if(allTweets != null){
                for(Tweet t : allTweets){
                    pq.add(t);
                    if(pq.size() > 10){
                        pq.poll();
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0, pq.poll().id);
        }
        return result;
    }
    
    public void follow(int followerId, int followeeId) {
        if(!map.containsKey(followerId)){
            map.put(followerId, new HashSet<>());
        }
        map.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(map.containsKey(followerId) && followerId != followeeId){
            map.get(followerId).remove(followeeId);
        }
    }
}

//time complexity O(mn lon 10) where m is number user follow, n is number
// of tweets of user and then log 10 maintaining priorityQueue for 10 size
//space complexity O(n*m) where n is the size of map where we store user
//who follow whom and m is map where we store all tweets for user 