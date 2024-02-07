class Twitter {
//TC: O(m Log k)   --- m no. of users, k-- maximum tweets of the users
//SC: O(m) 
    private HashMap<Integer, HashSet<Integer>> followed;
    private HashMap<Integer, List<Tweet>> tweets;
    int time = 0;
    class Tweet{
        int id;
        int createdAt;
        public Tweet(int tid,  int time){
            this.id = tid;
            this.createdAt = time;
        }
    }
    public Twitter() {
        this.followed = new HashMap<>();
        this.tweets = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        follow(userId,userId);  //O(1);
        if(!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId,time++));
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        HashSet<Integer> follwedIds = followed.get(userId);
        if(follwedIds != null){
            for(int fId : follwedIds){
                List<Tweet> fTweets = tweets.get(fId);
                if(fTweets == null) continue;
                int size = fTweets.size();
                for(int i =size - 1; i >= size - 10 && i >=0; i--){
                        pq.add(fTweets.get(i));
                        if(pq.size() > 10){
                            pq.poll();
                        }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0,pq.poll().id);
        }
        return result;
    }
    
    public void follow(int followerId, int followeeId) {
        if(!followed.containsKey(followerId)){
            followed.put(followerId, new HashSet<>());
        }
        followed.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(followed.containsKey(followerId) && followerId != followeeId){
            followed.get(followerId).remove(followeeId);
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