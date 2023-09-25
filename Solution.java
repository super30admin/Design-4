import java.util.*;

//SC:  O(N + N * m + k) where 
//N is the number of users, 
//m is the average number of tweets per user, and 
//k is the number of users the given user follows.
class Twitter {
    private static int timeStamp = 0;

    private Map<Integer, User> userMap;

    // Tweet link to next Tweet so that we can save a lot of time
    // when we execute getNewsFeed(userId)
    private class Tweet {
        public int id;
        public int time;
        public Tweet next;

        public Tweet(int id) {
            this.id = id;
            time = timeStamp++;
            next = null;
        }
    }

    public class User {
        public int id;
        public Set<Integer> followed;
        public Tweet tweet_head;

        public User(int id) {
            this.id = id;
            followed = new HashSet<>();
            follow(id); // first follow itself
            tweet_head = null;
        }

        public void follow(int id) {
            followed.add(id);
        }

        public void unfollow(int id) {
            followed.remove(id);
        }

        // everytime user post a new tweet, add it to the head of tweet list.
        public void post(int id) {
            Tweet t = new Tweet(id);
            t.next = tweet_head;
            tweet_head = t;
        }
    }

    public Twitter() {
        userMap = new HashMap<Integer, User>();
    }

    // TC: O(1)
    public void postTweet(int userId, int tweetId) {
        if (!userMap.containsKey(userId)) {
            User u = new User(userId);
            userMap.put(userId, u);
        }
        userMap.get(userId).post(tweetId);

    }

    // TC: O(k * log k), where 'k' is the number of users the given user follows.
    // Working flow
    // first get all tweets lists from one user including itself and all people it
    // followed.
    // Second add all heads into a max heap. Every time we poll a tweet with
    // largest time stamp from the heap, then we add its next tweet into the heap.
    // So after adding all heads we only need to add 9 tweets at most into this
    // heap before we get the 10 most recent tweet.
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> res = new LinkedList<>();

        if (!userMap.containsKey(userId))
            return res;

        Set<Integer> users = userMap.get(userId).followed;
        PriorityQueue<Tweet> q = new PriorityQueue<Tweet>(users.size(), (a, b) -> (b.time - a.time));
        for (int user : users) {
            Tweet t = userMap.get(user).tweet_head;
            if (t != null) {
                q.add(t);
            }
        }
        int n = 0;
        while (!q.isEmpty() && n < 10) {
            Tweet t = q.poll();
            res.add(t.id);
            n++;
            if (t.next != null)
                q.add(t.next);
        }

        return res;

    }

    // TC: O(1)
    public void follow(int followerId, int followeeId) {
        if (!userMap.containsKey(followerId)) {
            User u = new User(followerId);
            userMap.put(followerId, u);
        }
        if (!userMap.containsKey(followeeId)) {
            User u = new User(followeeId);
            userMap.put(followeeId, u);
        }
        userMap.get(followerId).follow(followeeId);
    }

    // TC: O(1)
    public void unfollow(int followerId, int followeeId) {
        if (!userMap.containsKey(followerId) || followerId == followeeId)
            return;
        userMap.get(followerId).unfollow(followeeId);
    }
}

class SkipIterator implements Iterator<Integer> {
    HashMap<Integer, Integer> skipMap;
    Integer nextEle;
    Iterator<Integer> nIt;

    // TC: O(1) SC:O(1)
    public SkipIterator(Iterator<Integer> it) {
        this.skipMap = new HashMap<>();
        this.nextEle = null;
        this.nIt = it;
        advance();
    }

    // TC: O(n) SC:O(1)
    private void advance() {
        nextEle = null;
        while (nIt.hasNext() && nextEle == null) {
            Integer el = nIt.next();
            if (skipMap.containsKey(el)) {
                skipMap.put(el, skipMap.get(el) - 1);
                skipMap.remove(el, 0);
            } else {
                nextEle = el;
            }
        }
    }

    // TC: O(1) SC:O(1)
    public boolean hasNext() {
        return nextEle != null;
    }

    // TC: O(1) SC:O(1)
    public Integer next() {
        Integer result = nextEle;
        advance();
        return result;
    }

    // TC: O(1) SC:O(1)
    public void skip(int val) {
        if (val == nextEle) {
            advance();
        } else {
            skipMap.put(val, skipMap.getOrDefault(val, 0) + 1);
        }
    }

}

public class Solution {

    /**
     * Your Twitter object will be instantiated and called as such:
     * Twitter obj = new Twitter();
     */
    public static void main(String[] args) {
        int userId = 01;
        int tweetId = 001;
        int followerId = 002;
        int followeeId = 003;
        Twitter obj = new Twitter();
        obj.postTweet(userId, tweetId);
        List<Integer> param_2 = obj.getNewsFeed(userId);
        obj.follow(followerId, followeeId);
        obj.unfollow(followerId, followeeId);
    }

}