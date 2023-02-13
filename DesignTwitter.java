// Time Complexity : follow(), unfollow(), postTweet() -> O(1); getNewsFeed() -> O(Nlogk)
// Space Complexity : follow(), unfollow(), postTweet() -> O(1); getNewsFeed() -> O(k)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

class Twitter {
    int global_time;

    class Tweet {
        int tweetId;
        int timePosted;

        Tweet(int id) {
            this.tweetId = id;
            this.timePosted = global_time;
            global_time++;
        }
    }

    HashMap<Integer, HashSet<Integer>> followedMap;
    HashMap<Integer, List<Tweet>> tweetMap;

    public Twitter() {
        global_time = 0;
        followedMap = new HashMap<>();
        tweetMap = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        if (!tweetMap.containsKey(userId)) {
            tweetMap.put(userId, new ArrayList<>());
        }
        Tweet newTweet = new Tweet(tweetId);
        tweetMap.get(userId).add(newTweet);
        follow(userId, userId);
    }

    public List<Integer> getNewsFeed(int userId) {
        List<Integer> result = new ArrayList<>();
        HashSet<Integer> followed = followedMap.get(userId);
        if (followed == null) {
            return result;
        }
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> (a.timePosted - b.timePosted));
        for (int i : followed) {
            List<Tweet> tweets = tweetMap.get(i);
            if (tweets == null){
                continue;
            }
            for (Tweet t : tweets) {
                pq.add(t);
                if (pq.size() > 10){
                    pq.poll();
                }
            }
        }
        while (!pq.isEmpty()) {
            Tweet temp = pq.poll();
            result.add(0, temp.tweetId);
        }
        return result;
    }

    public void follow(int followerId, int followeeId) {
        if (!followedMap.containsKey(followerId)) {
            followedMap.put(followerId, new HashSet<>());
        }
        followedMap.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (!followedMap.containsKey(followerId)) {
            return;
        }
        if (followedMap.get(followerId).contains(followeeId)) {
            followedMap.get(followerId).remove(followeeId);
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