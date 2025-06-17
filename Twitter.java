// Space Complexity ::O(N*max(M,T))
class Twitter {
    class Tweet{
        int tid;
        int createdAt;
        public Tweet(int tid, int time){
            this.tid = tid;
            this.createdAt = time;
        }
    }
    private HashMap<Integer, HashSet<Integer>>followed; 
    private HashMap<Integer, List<Tweet>> tweets;
    private int time;
    public Twitter() {
        this.followed = new HashMap<>();
        this.tweets = new HashMap<>();
    }
    public void postTweet(int userId, int tweetId) { //O(1)
        if(!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<>());
            follow(userId, userId);
        }
        tweets.get(userId).add(new Tweet(tweetId, time));
        time++;
    }
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        HashSet<Integer> followeds = followed.get(userId); 
        if(followeds != null){ //O(N)
            for(int fid: followeds){
                List<Tweet> fTweets = tweets.get(fid);
                if(fTweets != null){
                    int K = Math.min(fTweets.size(), 11);
                    for(int i = K - 1; i >= 0 && K >= 0; i--){
                        pq.add(fTweets.get(i));
                        K--;
                        if(pq.size() > 10){
                            pq.poll();
                        }
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0, pq.poll().tid);
        }
        return result;
    }
    public void follow(int followerId, int followeeId) { //O(1)
        if(!followed.containsKey(followerId)){
            followed.put(followerId, new HashSet<>());
        }
        followed.get(followerId).add(followeeId);
    }
    public void unfollow(int followerId, int followeeId) {//O(1)
        if(followed.containsKey(followerId) && followerId != followeeId){
            followed.get(followerId).remove(followeeId);
        }
    }
}