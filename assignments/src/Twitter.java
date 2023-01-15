import java.util.*;

class Twitter {

    class Tweet {
        int id;
        int createdAt;
        public Tweet(int tweetId, int createdAt) {
            this.id = tweetId;
            this.createdAt = createdAt;
        }
    }

    Map<Integer, HashSet<Integer>> followed;
    Map<Integer, List<Tweet>> tweets;
    int time;

    public Twitter() {
        this.followed = new HashMap();
        this.tweets = new HashMap();
    }

    // O(1)
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if (!tweets.containsKey(userId)) {
            tweets.put(userId, new ArrayList());
        }
        Tweet newTweet = new Tweet(tweetId, time);
        time++;
        tweets.get(userId).add(newTweet);
    }

    // O(nlogk) k = constant 10, thus O(n) asymptotically
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        HashSet<Integer> followeds = followed.get(userId);
        List<Integer> result = new ArrayList();
        if (followeds == null) return result;
        for (int fId : followeds) {
            List<Tweet> ftweets = tweets.get(fId);   // get list of tweets of all ppl I follow
            if (ftweets != null) {
                int size = ftweets.size();
                int min = Math.min(size, 10);       // to show max 10 tweets per user I follow
                for (int i = size-1; i >= size-min; i--) {      // to show latest 10 tweets only
                    pq.add(ftweets.get(i));
                    if (pq.size() > 10) pq.poll();              // pq will store 10 tweets only at a time
                }
            }
        }

        while (!pq.isEmpty()) {
            result.add(0, pq.poll().id);
        }
        return result;
    }

    // O(1)
    public void follow(int followerId, int followeeId) {
        if (!followed.containsKey(followerId)) {
            followed.put(followerId, new HashSet());
        }
        followed.get(followerId).add(followeeId);
    }

    // O(1)
    public void unfollow(int followerId, int followeeId) {
        if (followed.containsKey(followerId) && followerId != followeeId) {
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
