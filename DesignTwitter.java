// TC : follow -> O(1) , unfollow -> O(1), post -> O(1), getNewsFeed -> 0(n*log10)
// Sc: O(N)
class Twitter {
    int time;
    HashMap<Integer, HashSet<Integer>> userMap;
    HashMap<Integer, List<Tweets>> tweets;
    class Tweets {
        int id;
        int createdAt;
        public Tweets(int id, int time) {
            this.id = id;
            this.createdAt = time;
        }
    }

    public Twitter() {
        userMap = new HashMap<>();
        tweets = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        if (!tweets.containsKey(userId)) {
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweets(tweetId, time++));
    }

    public List<Integer> getNewsFeed(int userId) {
        follow(userId, userId);
        PriorityQueue<Tweets> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);

        HashSet<Integer> users = userMap.get(userId);

        for (int user: users) {
            List<Tweets> allTweets = tweets.get(user);
            if (allTweets != null) {
                for (Tweets tw : allTweets) {
                    pq.add(tw);
                    if (pq.size() > 10) {
                        pq.poll();
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
        if(!userMap.containsKey(followerId)) {
            userMap.put(followerId, new HashSet<>());
        }
        userMap.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (userMap.containsKey(followerId) && followerId != followeeId) {
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
