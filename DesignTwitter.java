class Twitter {
    /*
            F = number of users followed by a user
            postTweet()
                TC: O(1)
            getNewsFeed()
                TC: O(F)
            Heap stores at most 10 tweets.
            follow() / unfollow()
                TC: O(1) average
            SC - O(U + T) - U = number of users and follow relationships,  T = total number of tweets stored
            Use a HashMap to store:
                Each user's follow list (userId -> users they follow)
                Each user's tweets (userId -> list of tweets with timestamps)
            postTweet()
                Store the tweet with a timestamp.
                Make the user follow themselves so their own tweets appear in their feed.
            getNewsFeed()
                Collect tweets from the user and all users they follow.
                Use a min heap to keep only the 10 most recent tweets.
                Return the tweets in newest-to-oldest order.
            follow() / unfollow() - Add or remove users from the follow list.
    */

    class Tweet {
        int tweetId;
        int createdAt;
        public Tweet(int id, int time) {
            this.tweetId = id;
            this.createdAt = time;
        }
    }

    HashMap<Integer, Set<Integer>> followed;
    HashMap<Integer, ArrayList<Tweet>> tweetsMap;
    int time;

    public Twitter() {
        this.followed = new HashMap<>();
        this.tweetsMap = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if (!tweetsMap.containsKey(userId)) {
            tweetsMap.put(userId, new ArrayList<>());
        }
        tweetsMap.get(userId).add(new Tweet(tweetId, time));
        time++;
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);
        Set<Integer> follows = followed.get(userId);
        if (follows != null) {
            for (int fid : follows) {
                List<Tweet> tweets = tweetsMap.get(fid);
                if (tweets != null) {
                    int length = tweets.size();
                    for (int i = length - 1; i >= 0 && i >= length - 10; i--) {
                        pq.add(tweets.get(i));
                        if (pq.size() > 10) {
                            pq.poll();
                        }
                    }
                }
            }
        }

        List<Integer> result = new ArrayList<>();
        while (!pq.isEmpty()) {
            result.add(0, pq.poll().tweetId);
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
        if (followed.containsKey(followerId)) {
            followed.get(followerId).remove(followeeId);
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