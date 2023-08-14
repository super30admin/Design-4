class Twitter {

    ArrayList<Tweet> tweets;
    HashMap<Integer, User> users; 

    public Twitter() {
        tweets = new ArrayList<>();
        users = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        if(!users.containsKey(userId))
            users.put(userId, new User(userId));
        tweets.add(new Tweet(tweetId, userId));
    }
    
    public List<Integer> getNewsFeed(int userId) {
        if(!users.containsKey(userId))
            users.put(userId, new User(userId));
        List<Integer> ans = new ArrayList<Integer>();
        int i = tweets.size()-1;
        while(ans.size()<10 && i>=0) {
            if(tweets.get(i).userId==userId || users.get(userId).follows.contains(tweets.get(i).userId))
                ans.add(tweets.get(i).tweetId);
            i--;
        }
        return ans;
    }
    
    public void follow(int followerId, int followeeId) {
        if(!users.containsKey(followerId))
            users.put(followerId, new User(followerId));
        if(!users.containsKey(followeeId))
            users.put(followeeId, new User(followeeId));
        User user = users.get(followerId);
        user.follows.add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(!users.containsKey(followerId))
            users.put(followerId, new User(followerId));
        if(!users.containsKey(followeeId))
            users.put(followeeId, new User(followeeId));
        User user = users.get(followerId);
        user.follows.remove(followeeId);
    }
}

class Tweet {
    int tweetId;
    int userId;
    Tweet(int t, int u) {
        tweetId = t;
        userId = u;
    }
}

class User {
    int userId;
    HashSet<Integer> follows;
    User(int u) {
        userId = u;
        follows = new HashSet<>();
    }
}

