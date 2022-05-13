import java.util.*;

public class Twitter {

    class Tweet {
        int id, createdAt;

        public Tweet(int id, int createdAt) {
            this.id = id;
            this.createdAt = createdAt;
        }
    }

    Map<Integer, HashSet<Integer>> map;
    Map<Integer, List<Tweet>> tweets;
    int time; // time at which a tweet is posted

    public Twitter() {
        map = new HashMap<>();
        tweets = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        if(!tweets.containsKey(userId)) {
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId, time++));
    }

    // TC: m * n * log 10
        // m - number os users a given user is following
        // n - number of tweets for these users
        // 10 because pq size is capped at 10
    public List<Integer> getNewsFeed(int userId) {
        follow(userId, userId); // makes it simple to get the tweets posted by this user and from the users he is following

        // min heap of size 10
        // Having max heap will miss out the recent tweets
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        HashSet<Integer> users = map.get(userId);

        // For each user, get the tweets
        // add each tweet to the size restricted priority queue
        for(Integer user : users) {
            List<Tweet> allTweets = tweets.get(user);
            if(allTweets != null) {
                for(Tweet tweet : allTweets) {
                    pq.add(tweet);
                    if(pq.size() > 10){
                        pq.poll();
                    }
                }
            }
        }

        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()) {
            result.add(0, pq.poll().id);
        }

        return result;
    }

    // TC: O(1)
    public void follow(int followerId, int followeeId) {
        if(!map.containsKey(followerId)) {
            map.put(followerId, new HashSet<>());
        }
        map.get(followerId).add(followeeId);
    }

    // TC: O(1)
    public void unfollow(int followerId, int followeeId) {
        // followerId != followeeId just because we added that check in getNewsFeed method
        if(map.containsKey(followerId) && followerId != followeeId) {
            map.get(followerId).remove(followeeId);
        }
    }
}
