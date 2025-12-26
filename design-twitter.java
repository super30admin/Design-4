class Twitter {
    class Tweet{
        //variables
        int tweetId;
        int createdAt;
        //constructor
        public Tweet(int tweetId, int time){
            this.tweetId = tweetId;
            this.createdAt = time;

        }
    }
    //maps
    //followed map will keep user & its followers, he should not have repeated followers so hashset.
    HashMap<Integer, HashSet<Integer>> followed;
    //tweetsMap - key - user, value - tweets done by that person at diff timestamp
    HashMap<Integer, List<Tweet>> tweetsMap;
    int time;
    public Twitter() {
        this.followed = new HashMap<>();
        this.tweetsMap = new HashMap<>();

    }
    //making tweetsMap
    public void postTweet(int userId, int tweetId) {
        //person should follow himself bcz he sees his tweet & his follower's tweets
        follow(userId, userId);
        if(!tweetsMap.containsKey(userId)){
            tweetsMap.put(userId, new ArrayList<>());
        }
        tweetsMap.get(userId).add(new Tweet(tweetId, time));
        time++;
    }
    //get most recent tweets in feed that
    public List<Integer> getNewsFeed(int userId) {
        //we need higher timestamp tweets, so minHeap
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        HashSet<Integer> followeds = followed.get(userId); //followers including himself
        if(followeds!=null){
            for(int fId: followeds){
                List<Tweet> tweets = tweetsMap.get(fId);
                if(tweets!=null){
                    for(Tweet tweet:tweets){
                        pq.add(tweet);
                        if(pq.size()>10){
                            pq.poll();
                        }
                    }
                }


            }
        }


        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0, pq.poll().tweetId); //desc order of heap
        }
        return result;

    }
    //making followed map
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