class Problem1 {

    HashMap<Integer, HashSet<Integer>> followeesMap;
    HashMap<Integer, List<Tweet>> tweetsMap;
    int time;

    class Tweet {
        int tweetId;
        int timeStamp;

        public Tweet(int tId, int time) {
            this.tweetId = tId;
            this.timeStamp = time;
        }
    }

    public Twitter() {
        this.followeesMap = new HashMap<>();
        this.tweetsMap = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        if (!tweetsMap.containsKey(userId)) {
            tweetsMap.put(userId, new ArrayList<>());
        }
        Tweet tweet = new Tweet(tweetId, time);
        time++;
        tweetsMap.get(userId).add(tweet);

        follow(userId, userId);
    }

    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.timeStamp - b.timeStamp);

        HashSet<Integer> followees = followeesMap.get(userId);
        if (followees != null) {
            for (Integer followee : followees) {
                List<Tweet> tweets = tweetsMap.get(followee);
                if (tweets != null) {
                    for (int i = tweets.size() - 1; i >= tweets.size() - 10 && i >= 0; i--) {
                        Tweet tweet = tweets.get(i);
                        pq.add(tweet);
                        if (pq.size() > 10) {
                            pq.poll();
                        }
                    }
                }
            }
        }

        List<Integer> result = new ArrayList<>();
        while (!pq.isEmpty()) {
            result.add(pq.poll().tweetId);
        }

        Collections.reverse(result);
        return result;
    }

    public void follow(int followerId, int followeeId) {
        if (!followeesMap.containsKey(followerId)) {
            followeesMap.put(followerId, new HashSet<>());
        }
        followeesMap.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (!followeesMap.containsKey(followerId)) {
            return;
        }
        followeesMap.get(followerId).remove(followeeId);
    }
}
