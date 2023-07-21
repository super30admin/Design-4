class Twitter {
    HashMap<Integer, HashSet<Integer>> followed;
    HashMap<Integer, List<Tweet>> tweets;
    int time;

    class Tweet {
        int id;
        int createdAt;

        public Tweet(int id, int time) {
            this.id = id;
            this.createdAt = time;
        }

    }

    public Twitter() {
        this.followed = new HashMap<>();
        this.tweets = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if (!tweets.containsKey(userId)) {
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId, time));
        time++;
    }

    public List<Integer> getNewsFeed(int userId) {
        HashSet<Integer> following = followed.get(userId);
        List<Integer> result = new ArrayList<>();
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);
        if (following != null) {
            for (int id : following) {
                List<Tweet> ftweets = tweets.get(id);
                if (ftweets != null) {
                    for (Tweet tw : ftweets) {
                        pq.add(tw);
                        if (pq.size() > 10) {
                            pq.poll();
                        }
                    }
                }
            }
        }
        while (!pq.isEmpty()) {
            result.add(0, pq.poll().id);
        }
        return result;
    }

    public void follow(int followerId, int followeeId) {
        if (!followed.containsKey(followerId)) {
            followed.put(followerId, new HashSet<>());

        }
        followed.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (followed.containsKey(followerId) && followerId != followeeId) {
            followed.get(followerId).remove(followeeId);
        }
    }
}