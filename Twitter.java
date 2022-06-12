// Time Complexity :postTweet->constant,follow()->constant,unfollow->constant,
//getNewsFeed->O(average size of usersMap*average size of tweets lists in map)+O(result)
// Space Complexity :only in getNewsFeed a priorityQueue of O(10)===constant is used
// Did this code successfully run on Leetcode :Yes
// Any problem you faced while coding this :No
class Twitter {

    private static int time;

    class Tweet {
        private int tweetId;
        private int tweetTime;

        public Tweet(int tweetId, int ttime) {
            this.tweetId = tweetId;
            this.tweetTime = ttime;
        }
    }

    private HashMap<Integer, Set<Integer>> userMap;
    private HashMap<Integer, List<Tweet>> tweetMap;

    public Twitter() {
        this.userMap = new HashMap<>();
        this.tweetMap = new HashMap<>();

    }

    // making a new tweet object and if user is new, add in tweetMap and add tweet
    // in map also
    // make user follow himself
    public void postTweet(int userId, int tweetId) {

        follow(userId, userId);

        if (!tweetMap.containsKey(userId)) {
            tweetMap.put(userId, new ArrayList<>());
        }

        tweetMap.get(userId).add(new Tweet(tweetId, time++));

    }

    // get all the followees from userhashMap and get all tweets of users and remove
    // old tweets using
    // priorityQueue
    public List<Integer> getNewsFeed(int userId) {
        Set<Integer> users = userMap.get(userId);
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> (a.tweetTime - b.tweetTime));
        if (users != null) {
            for (int user : users) {
                List<Tweet> tweets = tweetMap.get(user);
                if (tweets != null) {
                    for (Tweet tweet : tweets) {

                        pq.add(tweet);

                        if (pq.size() > 10) {
                            pq.poll();
                        }
                    }
                }

            }
        }

        List<Integer> result = new ArrayList<>();
        while (!pq.isEmpty()) {

            result.add(0, pq.poll().tweetId);
        }
        return result;
    }

    // if user is new add in userMap and add followee in hashSet of followees
    public void follow(int followerId, int followeeId) {
        if (!userMap.containsKey(followerId)) {
            userMap.put(followerId, new HashSet<>());

        }
        userMap.get(followerId).add(followeeId);
    }

    // remove followee from followee hashset of follower
    public void unfollow(int followerId, int followeeId) {

        if (userMap.containsKey(followerId) && followerId != followeeId) {
            userMap.get(followerId).remove(followeeId);
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