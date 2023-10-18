import java.util.*;

class Twitter {
    int time = 1;

    class Tweet {
        int tweetId;
        int userId;
        int timeStamp;

        public Tweet(int userId, int tweetId) {
            this.tweetId = tweetId;
            this.userId = userId;
            this.timeStamp = time++;
        }
    }

    class User {
        int userId;
        java.util.HashSet<Integer> followers;

        public User(int userId, java.util.HashSet<Integer> followers) {
            this.userId = userId;
            this.followers = followers;
        }
    }

    HashMap<Integer, java.util.HashSet<Integer>> followerMapping;
    HashMap<Integer, List<Tweet>> tweetMapping;
    PriorityQueue<Tweet> news = new PriorityQueue<>((a, b) -> a.timeStamp - b.timeStamp);

    public Twitter() {
        followerMapping = new HashMap<>();
        tweetMapping = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        Tweet tweet = new Tweet(userId, tweetId);
        if (!tweetMapping.containsKey(userId)) {
            tweetMapping.put(userId, new ArrayList<>());
        }
        tweetMapping.get(userId).add(tweet);
    }

    public List<Integer> getNewsFeed(int userId) {
        follow(userId, userId);
        java.util.HashSet<Integer> followers = followerMapping.getOrDefault(userId, new java.util.HashSet<>());
        for (Integer followerId : followers) {
            List<Tweet> tweets = tweetMapping.getOrDefault(followerId, new ArrayList<>());
            for (Tweet tweet : tweets) {
                if (news.size() >= 10) {
                    news.poll();
                }
                news.add(tweet);
            }
        }
        List<Integer> result = new ArrayList<>();
        while (!news.isEmpty()) {
            result.add(0, news.poll().tweetId);
        }
        return result;
    }

    public void follow(int followerId, int followeeId) {
        if (!followerMapping.containsKey(followerId)) {
            followerMapping.put(followerId, new java.util.HashSet<>());
        }
        followerMapping.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (followerMapping.containsKey(followerId)) {
            followerMapping.get(followerId).remove(followeeId);
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