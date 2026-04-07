
// Ran successfully on Leetcode: yes

class Twitter {
    int timeStamp;
    HashMap<Integer, List<Tweet>> tweetsMap;
    HashMap<Integer, HashSet<Integer>> followeesMap;

    class Tweet {
        int tweetId;
        int time;

        public Tweet(int tweetId, int time) {
            this.tweetId = tweetId;
            this.time = time;
        }
    }
    // lazy loading
    public Twitter() {
        tweetsMap = new HashMap<>();
        followeesMap = new HashMap<>();
    }

    // TimeComplexity: O(1) add the new tweet
    public void postTweet(int userId, int tweetId) {
        Tweet tweet = new Tweet(tweetId, timeStamp++);

        if (!tweetsMap.containsKey(userId)) {
            tweetsMap.put(userId, new ArrayList<>());
        }
        tweetsMap.get(userId).add(tweet);
    }
    // TimeComplexity: O(10 log10)
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> result = new ArrayList<>();
        HashSet<Integer> followees = null;
        // use min heap to get the 10 most recent tweets
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.time, b.time));
        if (!followeesMap.containsKey(userId) && !tweetsMap.containsKey(userId)) return result;
        if (followeesMap.containsKey(userId)) followees = followeesMap.get(userId);
        // since user cannnot follow himself go though his tweets first and add to the queue
        List<Tweet> tweets = tweetsMap.get(userId);
        if (tweets!=null) {
            for (int i = tweets.size() - 1; i >= tweets.size() - 10 && i >= 0; i--) {
                Tweet tweet = tweets.get(i);
                pq.add(tweet);
                if (pq.size() > 10) {
                    pq.poll();
                }
            }
        }


        if (followees != null) {
            for (Integer followee : followees) {
                tweets = tweetsMap.get(followee);
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
        // add tweets in reverse order
        while (!pq.isEmpty()) {
            result.add(0, pq.poll().tweetId);
        }
        return result;
    }

    public void follow(int followerId, int followeeId) {
        if (followeesMap.containsKey(followerId)) {
            if(!followeesMap.get(followerId).contains(followerId)) {
                followeesMap.get(followerId).add(followeeId);
            }
        } else {
            HashSet<Integer> set = new HashSet<>();
            set.add(followeeId);
            followeesMap.put(followerId, set);
        }

    }

    public void unfollow(int followerId, int followeeId) {

        if (followeesMap.containsKey(followerId)) {
            if(followeesMap.get(followerId).contains(followeeId)) {
                followeesMap.get(followerId).remove(followeeId);
            }
        }

    }
}
