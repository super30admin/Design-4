import java.util.*;

class Twitter {
    private int timestamp = 0;
    private HashMap<Integer, HashSet<Integer>> usersList;
    private HashMap<Integer, List<Tweet>> postsList;

    class Tweet {
        int timestamp;
        int tweetId;

        Tweet(int timestamp, int tweetId) {
            this.timestamp = timestamp;
            this.tweetId = tweetId;
        }
    }

    public Twitter() {
        this.usersList = new HashMap<>();
        this.postsList = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        // Ensure user exists before posting
        addUser(userId);
        
        Tweet t = new Tweet(++timestamp, tweetId);
        postsList.get(userId).add(t);
    }

    public List<Integer> getNewsFeed(int userId) {
        List<Integer> result = new ArrayList<>();
        
        addUser(userId);
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> b.timestamp - a.timestamp);
        HashSet<Integer> followees = usersList.get(userId);
        if (followees == null) {
            return result;
        }
        for (Integer followeeId : followees) {
            List<Tweet> tweets = postsList.get(followeeId);
            if (tweets != null) {
                for (Tweet tweet : tweets) {
                    pq.add(tweet);
                }
            }
        }
        
        int count = 0;
        while (!pq.isEmpty() && count < 10) {
            result.add(pq.poll().tweetId);
            count++;
        }
        
        return result;
    }

    public void follow(int followerId, int followeeId) {
        addUser(followerId);
        addUser(followeeId);
        usersList.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (usersList.containsKey(followerId)) {
            usersList.get(followerId).remove(followeeId);
        }
    }
    
    private void addUser(int userId) {
        if (!usersList.containsKey(userId)) {
            usersList.put(userId, new HashSet<>());
            usersList.get(userId).add(userId);
            postsList.put(userId, new ArrayList<>());
        }
    }
}