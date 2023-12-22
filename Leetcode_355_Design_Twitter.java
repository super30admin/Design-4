/*
 * approach
 * define followers map - userMap
 * define tweets class - with timestamp, where time is a global variable running
 * Deine a tweets map along with userid as key, list of tweets as value
 * 
 * user should follow himself, when asks for the newFeed; to include his/her own feeds if lates
 * use Min Heap, as we are asked the most recent 10 items
 *  make sure to add extracted tweets id into the list, and also that even at th 0th index, as it leads to themost recent tweet 1st in the least,
 * as remeber, when we extract from q that's the oldest tweet right, ans we want the latest value in result first, so just for reordering/ 
 * 
 * TC: follow, unfollow - hashset - constant
 * TC: postTweet - O(1)
 * SC: for userMap - N
 * SC for tweets - M ; M = NK; T users, T Tweets
 * 
 * TC: for getNewsFeed - M log 10; log 10 can be negligible, so M = NK
 * SC: using only 10 feeds space in priority Queue, again negligible, so constant/. 
 * 
 */

class Twitter {
    // Map for follow, followee, userid as key and hashset of userid as values
    // class for tweets with timestamp

    class Tweet {
        int id;
        int timeStamp;

        public Tweet(int id, int timeStamp) {
            this.id = id;
            this.timeStamp = timeStamp;
        }
    }

    // map for tweets - user -> tweets
    HashMap<Integer, List<Tweet>> tweets_map;
    // map for user - followee
    HashMap<Integer, HashSet<Integer>> userMap;
    int time;

    public Twitter() {
        tweets_map = new HashMap<>();
        userMap = new HashMap<>();
        time = 0;
    }

    public void postTweet(int userId, int tweetId) {

        if (!tweets_map.containsKey(userId)) {
            tweets_map.put(userId, new ArrayList<>());
        }
        tweets_map.get(userId).add(new Tweet(tweetId, time++));
    }

    public List<Integer> getNewsFeed(int userId) {
        // arraylist for newFeeds
        List<Integer> newsFeed = new ArrayList<>();

        // 10 most recent feed, use MinHeap
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> (a.timeStamp - b.timeStamp));

        // make sure user follows himself, so that self feeds can appear if latest.

        follow(userId, userId);

        // get all the users
        HashSet<Integer> followees = userMap.get(userId);
        for (int followee : followees) {
            // get tweets for that particular user
            List<Tweet> all_tweets = tweets_map.get(followee);

            if (all_tweets != null) {
                for (Tweet twt : all_tweets) {
                    pq.add(twt);

                    // size check
                    if (pq.size() > 10) {
                        pq.poll();
                    }
                }
            }

        }

        // so now we have all 10 tweets in our minheap, lets catch it to newFeed list
        while (!pq.isEmpty()) {
            newsFeed.add(0, pq.poll().id);
        }

        return newsFeed;
    }

    public void follow(int followerId, int followeeId) {
        // check if user entry exist
        if (!userMap.containsKey(followerId)) {
            userMap.put(followerId, new HashSet<>());
        }
        userMap.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        // check if follower exist in map.
        if (userMap.containsKey(followerId)) {
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