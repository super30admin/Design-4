import java.util.*;

class DesignTwitter {
    Map<Integer, Set<Integer>> followingMap;
    Map<Integer, List<Tweet>> userTweets;
    int time = 0;
    public DesignTwitter() {
        followingMap = new HashMap<>();
        userTweets = new HashMap<>();
    }

    // TC: O(1)
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        userTweets.putIfAbsent(userId, new ArrayList<>());
        userTweets.get(userId).add(new Tweet(tweetId, time++));
    }

    // TC: O(NTlog K) where N is number of people the user is following, T is average number of tweets by a user and K is 10
    public List<Integer> getNewsFeed(int userId) {
        Set<Integer> following = followingMap.get(userId);
        if (following == null) return new ArrayList<>();
        PriorityQueue<Tweet> maxHeap = new PriorityQueue<>((a, b) -> b.tweetedTime - a.tweetedTime);
        for (int p : following) {
            List<Tweet> tweetsOfP = userTweets.get(p);
            if (tweetsOfP == null) continue;
            int size = tweetsOfP.size();
            if (size <= 10) {
                maxHeap.addAll(tweetsOfP);
            } else {
                for (int i = size - 1; i >= size - 10; i--) {
                    maxHeap.add(tweetsOfP.get(i));
                }
            }
        }
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < 10 && !maxHeap.isEmpty(); i++) {
            res.add(maxHeap.poll().tweetId);
        }
        return res;
    }

    // TC: O(1)
    public void follow(int followerId, int followeeId) {
        followingMap.putIfAbsent(followerId, new HashSet<>());
        followingMap.get(followerId).add(followeeId);
    }
    
    // TC: O(1)
    public void unfollow(int followerId, int followeeId) {
        if (followingMap.containsKey(followerId)) {
            followingMap.get(followerId).remove(followeeId);
        }
    }
}

class Tweet {
    int tweetId;
    int tweetedTime;
    public Tweet(int tweetId, int tweetedTime) {
        this.tweetId = tweetId;
        this.tweetedTime = tweetedTime;
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