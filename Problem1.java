class Twitter {

    class PriorityQueueComparatorHelper {
        int index;
        List<Tweet> list;

        public PriorityQueueComparatorHelper(int index, List<Tweet> l) {
            this.index = index;
            this.list = new ArrayList<Tweet>(l);
        }
    }

    class Tweet {
        int id;
        int time;

        public Tweet(int id, int time) {
            this.id = id;
            this.time = time;
        }
    }

    HashMap<Integer, Set> user_following;
    HashMap<Integer, List> user_tweets;
    int time_global;

    /** Initialize your data structure here. */
    public Twitter() {
        user_following = new HashMap<>();
        user_tweets = new HashMap<>();
    }

    //Time complexity : O(1)
    public void postTweet(int userId, int tweetId) {
        List<Tweet> tweets = user_tweets.get(userId);
        if (tweets == null) {
            tweets = new ArrayList<Tweet>();
        }
        tweets.add(new Tweet(tweetId, time_global++));
        user_tweets.put(userId, tweets);
    }

    //Time complexity : O(number of followeres * log(number of followers))
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<PriorityQueueComparatorHelper> q = new PriorityQueue<>((a, b) -> {
            int aIndex = a.index;
            int bIndex = b.index;
            Tweet aTweet = a.list.get(aIndex);
            Tweet bTweet = b.list.get(bIndex);
            return bTweet.time - aTweet.time;
        });

        List<Integer> feed = new ArrayList<>();

        Set<Integer> following = user_following.get(userId);
        if (following == null) {
            following = new HashSet<>();
        }

        following.add(userId);
        for (Integer followingId : following) {
            List<Tweet> tweets = user_tweets.get(followingId);
            if (tweets != null) {
                PriorityQueueComparatorHelper h = new PriorityQueueComparatorHelper(tweets.size() - 1, tweets);
                q.add(h);
            }
        }

        while (!q.isEmpty() && feed.size() < 10) {
            PriorityQueueComparatorHelper h = q.poll();
            feed.add(h.list.get(h.index).id);
            if (--h.index >= 0) {
                q.add(h);
            }
        }
        return feed;

    }

    //Time complexity : O(1)
    public void follow(int followerId, int followeeId) {
        Set<Integer> following = user_following.get(followerId);
        if (following == null) {
            following = new HashSet<>();
        }
        following.add(followeeId);
        user_following.put(followerId, following);
    }

    //Time complexity : O(1)
    public void unfollow(int followerId, int followeeId) {
        Set<Integer> following = user_following.get(followerId);
        if (following != null) {
            following.remove(followeeId);
            user_following.put(followerId, following);
        }

    }
}
