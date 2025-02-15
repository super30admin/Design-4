// In this design problem, we are using 2 hashmaps, one for user and its followers, other for user and tweets posted by them. For
// posttweet we are just checking if that user has entry in tweetmap, if yes than just add the new tweet to that list else create
// new entry for that tweet. For follow and unfollow, just check the usermap, and the hashset associated to userid. For getfeed,
// we have declared a tweet class which maintains tweetid along with time, so that we can get 10 latest tweets. Also, in tweet map,
// we are storing tweet objects.

// Time Complexity : O(1) for follow, unfollow and post tweet. If m followers and on average each follower has made n tweets, then 
// O(mnlogk) but k is size of heap that is constant so we can say O(mn)
// Space Complexity : O(1) 
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no

class Twitter {

    class Tweet {
        int tweetId, createdAt;

        public Tweet(int tweetId, int createdAt) {
            this.tweetId = tweetId;
            this.createdAt = createdAt;
        }
    }

    int time;
    HashMap<Integer, HashSet<Integer>> usermap;
    HashMap<Integer, List<Tweet>> tweetmap;

    public Twitter() {
        this.usermap = new HashMap<>();
        this.tweetmap = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        if (!tweetmap.containsKey(userId)) {
            tweetmap.put(userId, new ArrayList<>());
        }
        tweetmap.get(userId).add(new Tweet(tweetId, time++));
    }

    public List<Integer> getNewsFeed(int userId) {
        HashSet<Integer> followers = usermap.get(userId);
        List<Tweet> tweets = tweetmap.get(userId);
        List<Integer> result = new ArrayList<>();
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);
        if (tweets != null) {
            for (Tweet tw : tweets) {
                pq.add(tw);
                if (pq.size() > 10) {
                    pq.poll();
                }
            }
        }

        if (followers != null) {
            for (int f : followers) {
                List<Tweet> tt = tweetmap.get(f);
                if (tt != null) {
                    for (Tweet tw : tt) {
                        pq.add(tw);
                        if (pq.size() > 10) {
                            pq.poll();
                        }
                    }
                }

            }
        }

        while (!pq.isEmpty()) {
            result.add(0, pq.poll().tweetId);
        }

        return result;
    }

    public void follow(int followerId, int followeeId) {
        if (!usermap.containsKey(followerId)) {
            usermap.put(followerId, new HashSet<>());
        }
        usermap.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (!usermap.containsKey(followerId)) {
            return;
        }
        usermap.get(followerId).remove(followeeId);
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