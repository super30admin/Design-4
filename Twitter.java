class Twitter {
    HashMap<Integer, User> users;
    int time;
    public Twitter() {
        users = new HashMap<>();
        time = 0;
    }

    public void postTweet(int userId, int tweetId) {
        User u = users.getOrDefault(userId, new User(userId));
        u.addTweet(tweetId, time++);
        users.put(userId, u);
    }

    public List<Integer> getNewsFeed(int userId) {
        List<Integer> feed = new ArrayList<>();
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b)->b.timestamp-a.timestamp);
        User u = users.getOrDefault(userId, new User(userId));
        for(User u1:u.follows)
            if(u1.latest!=null)
                pq.add(u1.latest);

        Tweet t;
        while(!pq.isEmpty()){
            t = pq.poll();
            if(t.next!=null)
                pq.add(t.next);
            feed.add(t.id);
            if(feed.size()==10)
                break;
        }
        users.put(userId, u);
        return feed;
    }

    public void follow(int followerId, int followeeId) {
        User u = users.getOrDefault(followerId, new User(followerId));
        User u2 = users.getOrDefault(followeeId, new User(followeeId));
        u.followUser(u2);
        users.put(followerId, u);
        users.put(followeeId, u2);
    }

    public void unfollow(int followerId, int followeeId) {
        User u = users.getOrDefault(followerId, new User(followerId));
        User u2 = users.getOrDefault(followeeId, new User(followeeId));
        u.unfollowUser(u2);
        users.put(followerId, u);
        users.put(followeeId, u2);
    }

    class Tweet{
        public int id;
        public int timestamp;
        public Tweet next;
        Tweet(int id, int timestamp, Tweet next){
            this.id = id;
            this.timestamp = timestamp;
            this.next = next;
        }
    }

    class User{
        public int userId;
        public HashSet<User> follows;
        public Tweet latest;

        User(int userId){
            this.userId = userId;
            follows = new HashSet<>();
            follows.add(this);
        }

        void addTweet(int tweetId, int time){
            latest = new Tweet(tweetId, time, latest);
        }

        void followUser(User s){
            follows.add(s);
        }
        void unfollowUser(User s){
            if(follows.contains(s))
                follows.remove(s);
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