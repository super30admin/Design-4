import java.util.*;

/***
 1. We need userMap(key = userID(Integer), value = List of followers(HashSet<Integer>)),
 tweetMap(key = userID(Integer), value = List of tweets(List<Tweet>)), class-Tweet(tweetID, createdAt)
 2. Priority queue of type Tweet sorted by createdTime in descending order - To store most recent 10 tweets for a user in a min heap
 3. postTweet - TC : O(1), SC : O(nk) (n = total no of users, k = average no of tweets per user)
 getNewsFeed - TC : O(nklog(k)), SC : O(k) (for min heap)
 follow - TC : O(1), SC : O(n)
 unfollow - TC : O(1), SC : O(n)
 */
class Twitter {

    class Tweet{
        int tweetId;
        int createdAt;

        public Tweet(int tweetId, int createdAt) {
            this.tweetId = tweetId;
            this.createdAt = createdAt;
        }
    }

    Map<Integer, HashSet<Integer>> userMap;
    Map<Integer, List<Tweet>> tweetMap;
    int time;

    public Twitter() {
        userMap = new HashMap<>();
        tweetMap = new HashMap<>();
        time =0;
    }

    public void postTweet(int userId, int tweetId) {
        //check if userID is present in tweetMap, if not, add this userID with a new ArrayList
        if(!tweetMap.containsKey(userId)) {
            tweetMap.put(userId, new ArrayList<>());
        }

        tweetMap.get(userId).add(new Tweet(tweetId, time++));

    }

    public List<Integer> getNewsFeed(int userId) {

        //make the user follow himself
        follow(userId, userId);

        //min heap to store the most recent 10 tweets
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);

        //fetch all the tweets by the user and his followers
        Set<Integer> allUsers = userMap.get(userId);

        //fetch the tweets by all these users
        for(Integer user : allUsers) {
            List<Tweet> tweets = tweetMap.get(user);
            if(tweets != null) {
                for(Tweet tweet : tweets) {
                    pq.add(tweet);
                    if(pq.size() > 10)
                        pq.poll();
                }
            }
        }

        //return most recent tweets
        List<Integer> tweets = new ArrayList<>();
        while(!pq.isEmpty()) {
            tweets.add(0, pq.poll().tweetId);
        }

        return tweets;

    }

    public void follow(int followerId, int followeeId) {
        //check if followerId is present in userMap
        if(!userMap.containsKey(followerId))
            userMap.put(followerId, new HashSet<Integer>());

        userMap.get(followerId).add(followeeId);

    }

    public void unfollow(int followerId, int followeeId) {
        //check if followerId is present in userMap
        if(!userMap.containsKey(followerId))
            return;

        userMap.get(followerId).remove(followeeId);
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