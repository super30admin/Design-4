class Solution1 {
    private HashMap<Integer, HashSet<Integer>> users;
    private HashMap<Integer, List<Tweets>> tweets;
    private int time;

    class Tweets {
        int id;
        int createdAt;

        public Tweets(int id, int createdAt) {
            this.id = id;
            this.createdAt = createdAt;
        }
    }

    public Twitter() {
        this.users = new HashMap<>();
        this.tweets = new HashMap<>();
        this.time = 0;
    }

    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if( !tweets.containsKey(userId)) {
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweets(tweetId, time));
        time++;
    }

    public List<Integer> getNewsFeed(int userId) {
        List<Integer> result = new ArrayList<>();
        HashSet<Integer> set = users.get(userId);
        PriorityQueue<Tweets> pq = new PriorityQueue<>( (a,b) -> a.createdAt - b.createdAt);
        if( set == null ) {
            return result;
        }
        for(int fid: set) {
            List<Tweets> li = tweets.get(fid);
            if( li != null) {
                for(Tweets tw: li) {
                    pq.add(tw);
                    if( pq.size() > 10 ) {
                        pq.poll();
                    }
                }
            }
        }
        while( !pq.isEmpty() ) {
            result.add(0, pq.poll().id);
        }
        return result;
    }

    public void follow(int followerId, int followeeId) {
        if( !users.containsKey(followerId)) {
            users.put(followerId, new HashSet<>());
        }
        users.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if( users.containsKey(followerId) && followerId != followeeId) {
            users.get(followerId).remove(followeeId);
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