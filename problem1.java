import java.util.*;

public class problem1 {

    class Twitter {

        private HashMap<Integer, HashSet<Integer>> followeesMap; //O(u⋅m), where u is the number of users and m is the maximum number of followees per user.
        private HashMap<Integer, List<Tweet>> tweetsMap;//  O(n), where n is the total number of tweets.
        private int timestamp;

        class Tweet {
            int tweetId;
            int createdAt;

            public Tweet(int tweetId, int createdAt) {
                this.tweetId = tweetId;
                this.createdAt = createdAt;
            }
        }

        public Twitter() {
            this.followeesMap = new HashMap<>();
            this.tweetsMap = new HashMap<>();
            this.timestamp = 0; // Initialize timestamp
        }

        public void postTweet(int userId, int tweetId) { //O(1)
            follow(userId, userId); // Ensure user follows themselves
            if (!tweetsMap.containsKey(userId)) {
                tweetsMap.put(userId, new ArrayList<>());
            }
            tweetsMap.get(userId).add(new Tweet(tweetId, timestamp++));
        }

        public List<Integer> getNewsFeed(int userId) {   // O(k.t)where k is the number of followees and t is the average number of tweets per followee.
            PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt); // Min-Heap , O(1)
            HashSet<Integer> followees = followeesMap.get(userId);

            if (followees != null) {
                for (Integer followee : followees) {
                    List<Tweet> tweets = tweetsMap.get(followee);
                    if (tweets != null) {
                        for (Tweet tw : tweets) {
                            pq.add(tw);
                            if (pq.size() > 10) { // Keep only the top 10 most recent tweets
                                pq.poll();
                            }
                        }
                    }
                }
            }

            List<Integer> result = new ArrayList<>();
            while (!pq.isEmpty()) {
                result.add(0, pq.poll().tweetId); // Add to the front to maintain descending order
            }

            return result;
        }

        public void follow(int followerId, int followeeId) {
            followeesMap.putIfAbsent(followerId, new HashSet<>());
            followeesMap.get(followerId).add(followeeId);
        }

        public void unfollow(int followerId, int followeeId) {
            if (followeesMap.containsKey(followerId) && followerId != followeeId) { // Cannot unfollow oneself
                followeesMap.get(followerId).remove(followeeId);
            }
        }
    }

}
