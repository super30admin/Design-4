// Time Complexity : postTweet-O(1), getNewsFeed-O(Nlog10) where N is the number of all tweets from all followers, follow-O(1), unfollow-O(1)
// Space Complexity : O(u+f+t) where u is the numbers of users, f is the number of followers, t is the number of tweets
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no


// Your code here along with comments explaining your approach

/**
 * Backtracking approach with for loop to create a substring at each pivot and i combination. Check if the string is palindrom.
 */
class Twitter {
    Map<Integer, Set<Integer>> map;
    Map<Integer, List<Tweet>> tweets;
    int counter = 0;

    public Twitter() {
        this.map = new HashMap<>();
        this.tweets = new HashMap<>();
    }

    record Tweet(int tweetId, int timestamp) {
    }


    public void postTweet(int userId, int tweetId) {
        Tweet tweet = new Tweet(tweetId, counter++);

        if (!tweets.containsKey(userId)) {
            tweets.put(userId, new ArrayList<>());
        }

        tweets.get(userId).add(tweet);
    }

    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.timestamp - b.timestamp);
        List<Tweet> selfTweets = tweets.get(userId);
        if (selfTweets != null) {
            for (Tweet tweet : selfTweets) {
                pq.add(tweet);
                if (pq.size() > 10)
                    pq.poll();
            }
        }

        Set<Integer> followingIds = map.get(userId);
        if (followingIds != null) {
            for (int followingId : followingIds) {
                List<Tweet> fTweets = tweets.get(followingId);
                if (fTweets != null) {
                    for (Tweet tweet : fTweets) {
                        pq.add(tweet);
                        if (pq.size() > 10)
                            pq.poll();
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

    public void follow(int followerId, int followeeId) {
        if (followerId == followeeId)
            return;

        if (!map.containsKey(followerId)) {
            map.put(followerId, new HashSet<>());
        }

        map.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (!map.containsKey(followerId))
            return;

        map.get(followerId).remove(followeeId);
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