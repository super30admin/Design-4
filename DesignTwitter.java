import java.util.*;

// Time Complexity:
// postTweet: O(1) follow / unfollow: O(1) getNewsFeed: O(N log 10) -> O(N), where N is total tweets from all followees.

// Space Complexity:
// O(t+f+u) where t is the number of tweets, f is the number of followers and u is the number of users.

class Twitter {

    Map<Integer, List<Tweet>> tweetsMap;
    Map<Integer, Set<Integer>> followeesMap;
    int time; // to create new tweets in increasing manner

    class Tweet {
        int tweetId;
        int timestamp;

        public Tweet(int tweetId, int timestamp) {
            this.tweetId = tweetId;
            this.timestamp = timestamp;
        }
    }

    public Twitter() {
        this.tweetsMap = new HashMap<>();
        this.followeesMap = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {        
        Tweet tweet = new Tweet(tweetId, time);
        time++;

        if (!tweetsMap.containsKey(userId)) {
            tweetsMap.put(userId, new ArrayList<>());
        }
        tweetsMap.get(userId).add(tweet);
    }
    
    public List<Integer> getNewsFeed(int userId) {
        Set<Integer> followees = followeesMap.get(userId);

        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> Integer.compare(a.timestamp, b.timestamp)); // min heap

        if (followees != null) {
            for (Integer followee : followees) {
                List<Tweet> tweets = tweetsMap.get(followee);

                if (tweets != null) {
                    for (Tweet tweet : tweets) {
                        pq.offer(tweet);

                        if (pq.size() > 10) { // only maintain top 10
                            pq.poll();
                        }
                    }
                }
            }
        }

        List<Tweet> tweets = tweetsMap.get(userId); // we want the user's own tweets too
        if (tweets != null) {
            for (Tweet tweet : tweets) {
                pq.offer(tweet);

                if (pq.size() > 10) { // only maintain top 10
                    pq.poll();
                }
            }
        }

        List<Integer> ans = new ArrayList<>();
        while (!pq.isEmpty()) {
            int tweetId = pq.poll().tweetId;
            ans.add(tweetId);
        }
        Collections.reverse(ans);
        return ans;
    }
    
    public void follow(int followerId, int followeeId) {
        if (!followeesMap.containsKey(followerId)) {
            followeesMap.put(followerId, new HashSet<>());
        }
        followeesMap.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if (followeesMap.containsKey(followerId)) {
            followeesMap.get(followerId).remove(followeeId);
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