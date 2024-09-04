// Problem 355. Design Twitter
// Time Complexity :  O(k*n), where k is the number of followed users and n is the number of tweets per user.
// Space Complexity : O(T+F), where: T is the total number of tweets, F is the total number of follow relationships.
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this :

// Your code here along with comments explaining your approach
class Twitter {
    private static int timeStamp = 0;
    private Map<Integer, Set<Integer>> followedMap;  // Map from user to set of followed users
    private Map<Integer, List<Tweet>> tweetsMap;     // Map from user to list of tweets
    private PriorityQueue<Tweet> pq;                 // Priority queue to sort tweets by timestamp
    // A tweet object to store tweet ID and timestamp
    private class Tweet {
        public int id;
        public int createdAt;
        public Tweet(int id, int createdAt) {
            this.id = id;
            this.createdAt = createdAt;
        }
    }
    public Twitter() {
        followedMap = new HashMap<>();
        tweetsMap = new HashMap<>();
    }
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);  // Follow yourself if not already followed
        if (!tweetsMap.containsKey(userId)) {
            tweetsMap.put(userId, new ArrayList<>());
        }
        tweetsMap.get(userId).add(new Tweet(tweetId, timeStamp++));
    }
    public List<Integer> getNewsFeed(int userId) {
        pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);  // Min heap to store the latest 10 tweets
        Set<Integer> followedUsers = followedMap.get(userId);
        if (followedUsers != null) {
            for (int followeeId : followedUsers) {
                List<Tweet> tweets = tweetsMap.get(followeeId);
                if (tweets != null) {
                    for (Tweet tweet : tweets) {
                        pq.add(tweet);
                        if (pq.size() > 10) {  // Only keep the latest 10 tweets
                            pq.poll();
                        }
                    }
                }
            }
        }

        // Extract the tweets from the priority queue
        List<Integer> result = new ArrayList<>();
        while (!pq.isEmpty()) {
            result.add(0, pq.poll().id);  // Reverse the order to show the most recent first
        }
        return result;
    }
    public void follow(int followerId, int followeeId) {
        if (!followedMap.containsKey(followerId)) {
            followedMap.put(followerId, new HashSet<>());
        }
        followedMap.get(followerId).add(followeeId);
    }
    public void unfollow(int followerId, int followeeId) {
        if (followedMap.containsKey(followerId) && followerId != followeeId) {
            followedMap.get(followerId).remove(followeeId);
        }
    }
}
