// T.C -> O(1) - follow, unfollow, 
// T.C -> O(N) - post
// T.C -> O(Nklog10) - getNewsFeed
// Solved on Leetcode: Yes

class Twitter {

    class Tweet {
        int tweetId;
        int createdAt;

        Tweet(int tweetId, int createdAt) {
            this.tweetId = tweetId;
            this.createdAt = createdAt;
        }
    }

    HashMap<Integer, HashSet<Integer>> users;
    HashMap<Integer, List<Tweet>> tweets;
    int time;

    public Twitter() {
        this.users = new HashMap<>();
        this.tweets = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        if (!tweets.containsKey(userId)) {
            follow(userId, userId);
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId, time++));
    }

    public List<Integer> getNewsFeed(int userId) {
        List<Integer> result = new ArrayList<>();
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> {
            return a.createdAt - b.createdAt;
        });
        if (users.containsKey(userId)) {
            HashSet<Integer> followeds = users.get(userId);
            for (int fid : followeds) {
                List<Tweet> fTweets = tweets.get(fid);
                if (fTweets != null) {
                    int k = fTweets.size();
                    for (int j = k - 1; j >= 0 && j >= k - 12; j--) {
                        pq.add(fTweets.get(j));
                        if (pq.size() > 10) {
                            pq.poll();
                        }
                    }
                }
            }
        }

        while (!pq.isEmpty()) {
            result.add(0, pq.poll().tweetId);
        }
        return result;
    }

    public void follow(int followerId, int followeeId) {
        if (!users.containsKey(followerId)) {
            users.put(followerId, new HashSet<>());
        }
        users.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (followerId != followeeId && users.containsKey(followerId)) {
            users.get(followerId).remove(followeeId);
        }
    }
}