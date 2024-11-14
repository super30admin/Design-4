import java.time.Instant;
class Twitter {
    class Tweet{
        int tweetid;
        Instant createdAt;
        public Tweet(int id, Instant time){
            tweetid=id;
            createdAt=time;
        }
    }
    class User{
        int userid;
        HashSet<User> following;
        List<Tweet> tweets;
        public User(int id){
            userid=id;
            following=new HashSet<>();
            tweets = new ArrayList<>();
        }
    }
    HashMap<Integer,User> users;
    public Twitter() {
        users=new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        User user1 = getUser(userId);

        Instant timestamp = Instant.now();
        if(user1.tweets.size()==0) user1.following.add(user1);
        user1.tweets.add(new Tweet(tweetId,timestamp));
    }
    
    public List<Integer> getNewsFeed(int userId) {
        User user1 = getUser(userId);
        PriorityQueue<Tweet> q=new PriorityQueue<>((a,b) -> a.createdAt.compareTo(b.createdAt));
        List<Integer> res = new LinkedList<>();

        for(User user2:user1.following){
            for(Tweet tweet1:user2.tweets){
                q.add(tweet1);
                if(q.size()>10) q.poll();
            }
        }

        while(!q.isEmpty()){
            res.add(0,q.poll().tweetid);
        }
        return res;
    }
    
    public void follow(int followerId, int followeeId) {
        User follower=getUser(followerId);
        User followee=getUser(followeeId);

        follower.following.add(followee);
    }
    
    public void unfollow(int followerId, int followeeId) {
        User follower = getUser(followerId);
        follower.following.remove(users.get(followeeId));
    }
    private User getUser(int userId){
        if(!users.containsKey(userId)){
            users.put(userId, new User(userId));
        }
        return users.get(userId);
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
