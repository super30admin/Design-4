// Time Complexity : unfollow : O(1), follow : O(1), getNewsFeed : O(NlogK), postTweet : O(1)
// Space Complexity : O(1) from user perspective, otherwise O(N)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No
public class DesignTwitter {
    class Twitter {
        class Tweet {
            int id;
            int createdAt;

            public Tweet(int id, int createdAt) {
                this.id = id;
                this.createdAt = createdAt;
            }
        }

        Map<Integer, Set<Integer>> userFollowings;
        Map<Integer, List<Tweet>> userTweets;
        int time;

        public Twitter() {
            this.userFollowings = new HashMap<>();
            this.userTweets = new HashMap<>();
        }

        public void postTweet(int userId, int tweetId) {
            follow(userId, userId);
            if(!userTweets.containsKey(userId)) {
                userTweets.put(userId, new ArrayList<>());
            }
            userTweets.get(userId).add(new Tweet(tweetId, time++));
        }

        public List<Integer> getNewsFeed(int userId) {
            PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);
            Set<Integer> followeeIds = userFollowings.get(userId);
            if(followeeIds !=null) {
                for(Integer followeeId : followeeIds) {
                    List<Tweet> fTweets= userTweets.get(followeeId);
                    if(fTweets != null){
                        for(Tweet fTweet : fTweets) {
                            pq.add(fTweet);
                            if(pq.size() > 10) {
                                pq.poll();
                            }
                        }
                    }
                }
            }

            List<Integer> result = new ArrayList<>();
            while(!pq.isEmpty()) {
                result.add(0, pq.poll().id);
            }
            return result;
        }

        public void follow(int followerId, int followeeId) {
            if(!userFollowings.containsKey(followerId)) {
                userFollowings.put(followerId, new HashSet<>());
            }
            userFollowings.get(followerId).add(followeeId);

        }

        public void unfollow(int followerId, int followeeId) {
            if(userFollowings.containsKey(followeeId) &&  followerId != followeeId) {
                userFollowings.get(followerId).remove(followeeId);
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
}
