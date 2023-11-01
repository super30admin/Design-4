class Twitter{

    class Tweet{
        int tid;
        int createdAt;
        public Tweet(int id, int createdAt){
            this.tid = id
            this.createdAt = createdAt;
        }
    }

    HashMap<Integer , Set<Integer>> follwed;
    HashMap<Integer, List<Integer>> tweets;
    int time;

    public Twitter() {
        this.follwed = new HashMap<>();
        this.tweets = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) { //O(1)
        follow(userId, userId);
        if(!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<Integer>)
        }
        tweets.get(userId).add(new Tweet(tweetId, time++));
    }

    public List<Integer> getNewsFeed(int userId) { //O()
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt-b.createdAt);
        Set<Integer> fo = follwed.get(userId);
        if(fo != null){
            for(int fid: fo){
                List<Tweet> ftweet = tweets.get(fid);
                if(ftweet != null){
                    for(Tweet ft: ftweet){
                        pq.add(ft);
                        if(pq.size()>10){
                            pq.poll();
                        }
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0,pq.poll().tid);
        }
        return result;
    }

    public void follow(int followerId, int followeeId) { //O(1)
        if(!follwed.containsKey(followerId)){
            follwed.put(followerId, new HashSet<>());
        }
        follwed.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) { //O(1)
        if(follwed.containsKey(followerId) && followerId != followeeId){
            follwed.get(followerId).remove(followeeId);
        }
    }
}