
class DesignTwitter {

    class Tweet {
        int tweetId;
        int timeStamp;

        public Tweet(int tId, int time) {
            this.tweetId = tId;
            this.timeStamp = time;
        }
    }

    Map<Integer, HashSet<Integer>> followeesMap;
    Map<Integer, List<Tweet>> tweetMap;
    int time;

    public Twitter() {
        this.followeesMap = new HashMap();
        this.tweetMap = new HashMap();
    }

    public void postTweet(int userId, int tweetId) {
        Tweet tweet = new Tweet(tweetId, time);
        tweetMap.putIfAbsent(userId, new ArrayList<>());
        tweetMap.get(userId).add(tweet);
        time++;
        follow(userId, userId);

    }

    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<Tweet>((a, b) -> (a.timeStamp) - (b.timeStamp));
        HashSet<Integer> followees = followeesMap.get(userId);
        if (followees != null) {
            for (int followee : followees) {
                List<Tweet> tweets = tweetMap.get(followee);
                if (tweets != null) {
                    for (Tweet t : tweets) {
                        pq.add(t);
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
        followeesMap.putIfAbsent(followerId, new HashSet<Integer>());
        followeesMap.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (!followeesMap.containsKey(followerId)) {
            return;
        }
        followeesMap.get(followerId).remove(followeeId);
    }
}