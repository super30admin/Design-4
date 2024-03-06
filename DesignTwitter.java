import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class DesignTwitter {

    class Twitter {
        private Map<Integer, Set<Integer>> followingMap;
        private Map<Integer, List<Tweet>> tweetsMap;
        private int time = 0;

        class Tweet {
            private int id;
            private int timeStamp;

            public Tweet(int id, int timeStamp) {
                this.id = id;
                this.timeStamp = timeStamp;
                time++;
            }
        }

        public Twitter() {
            this.followingMap = new HashMap<>();
            this.tweetsMap = new HashMap<>();
        }

        public void postTweet(int userId, int tweetId) { // O(1)
            follow(userId, userId); // Self Follow
            Tweet tweet = new Tweet(tweetId, time);
            if (!tweetsMap.containsKey(userId)) {
                tweetsMap.put(userId, new ArrayList<>());
            }
            tweetsMap.get(userId).add(tweet);
        }

        public List<Integer> getNewsFeed(int userId) {
            Set<Integer> followingSet = followingMap.getOrDefault(userId, new HashSet<>());
            Queue<Tweet> latestTweetsMinHeap = new PriorityQueue<>((a, b) -> a.timeStamp - b.timeStamp);
            for (int followee : followingSet) { // O(Size of following set)
                List<Tweet> tweetsByFolloweeList = tweetsMap.getOrDefault(followee, new ArrayList<>()); // O(1)
                for (int i = tweetsByFolloweeList.size() - 1; i >= 0 && i > tweetsByFolloweeList.size() - 1 - 10; i--) {
                    latestTweetsMinHeap.add(tweetsByFolloweeList.get(i));
                    if (latestTweetsMinHeap.size() > 10) { // O(k)
                        latestTweetsMinHeap.poll();
                    }
                }
            }

            List<Integer> latestTweetIdList = new ArrayList<>();
            while (latestTweetsMinHeap.size() > 0) {
                latestTweetIdList.add(0, latestTweetsMinHeap.poll().id);
            }

            return latestTweetIdList;
        }

        public void follow(int followerId, int followeeId) { // O(1)
            if (!followingMap.containsKey(followerId)) {
                followingMap.put(followerId, new HashSet<>());
            }
            followingMap.get(followerId).add(followeeId);
        }

        public void unfollow(int followerId, int followeeId) { // O(1)
            if (!followingMap.containsKey(followerId)) {
                return;
            }
            followingMap.get(followerId).remove(followeeId);
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
}