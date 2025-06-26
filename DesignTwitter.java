// Time Complexity O( Tweets ) for get newsfeed
// Space Complexity O(N)

class Twitter {

    class Tweet {
        Integer id;
        Integer createdAt;

        Tweet(int id, int createdAt) {
            this.id = id;
            this.createdAt = createdAt;
        }
    }

    int time = 0;
    HashMap<Integer, Set<Integer>> userMap;
    HashMap<Integer, List<Tweet>> tweetMap;

    public Twitter() {
        userMap = new HashMap<Integer, Set<Integer>>();
        tweetMap = new HashMap<Integer, List<Tweet>>();
    }

    public void postTweet(int userId, int tweetId) {
        if (!userMap.containsKey(userId)) {
            userMap.put(userId, new HashSet<Integer>());
        }

        if (tweetMap.containsKey(userId)) {
            tweetMap.get(userId).add(new Tweet(tweetId, time));
        } else {
            List<Tweet> list = new ArrayList<Tweet>();
            list.add(new Tweet(tweetId, time));
            tweetMap.put(userId, list);
        }
        time++;
    }

    public List<Integer> getNewsFeed(int userId) {
        follow(userId, userId);
        List<Integer> result = new ArrayList<Integer>();

        PriorityQueue<Tweet> queue = new PriorityQueue<Tweet>((a, b) -> a.createdAt - b.createdAt);
        Set<Integer> following = userMap.get(userId);

        for (Integer id : following) {
            List<Tweet> tweets = tweetMap.get(id);
            if (tweets != null) {
                for (Tweet t : tweets) {
                    queue.add(t);
                    if (queue.size() > 10) {
                        queue.poll();
                    }
                }
            }
        }
        while(queue.size() != 0){
            result.add(0,queue.poll().id);
        }
        return result;
    }

    public void follow(int followerId, int followeeId) {
        if(!userMap.containsKey(followerId)){
            userMap.put(followerId, new HashSet<Integer>());
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