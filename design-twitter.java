//approach- using usermap to keep track of all users a user is following, tweetMap to store all tweets a user post. follow and unfollow we just add or remove the user being followed/unfollowed from the map.same thing with the tweetMap along with each tweet having a unique timeStamp.we are getting the last 10 tweets of all the users the current user is following and we are putting them in a minHeap based on the timeStamp and restricting its size to 10 in order to get the latest tweets.
//time complexity are mention on top of each method
//space Complexity->O(f+t+k) fbeing the number total no. of followers all the users have and t being the total number od tweets in the tweetMap. and k is a constant here being 10.
class Twitter {
    int time;
    HashMap<Integer, HashSet<Integer>> userMap;

    class Tweet {
        int id;
        int timeStamp;

        public Tweet(int id, int t) {
            this.id = id;
            this.timeStamp = t;
        }
    }

    HashMap<Integer, List<Tweet>> tweetMap;

    public Twitter() {
        this.userMap = new HashMap<>();
        this.tweetMap = new HashMap<>();
        this.time = 0;
    }

    //O(1)
    public void postTweet(int userId, int tweetId) {
        if (!tweetMap.containsKey(userId)) {
            tweetMap.put(userId, new ArrayList<Tweet>());
            if (!userMap.containsKey(userId)) {
                userMap.put(userId, new HashSet<>());

            }
            var list = userMap.get(userId);
            list.add(userId);

        }
        tweetMap.get(userId).add(new Tweet(tweetId, time++));
    }
    //O(f)xO(logk)xk==> o(f), f being the no.of followers and k being a constant(10 here)
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> result = new ArrayList<>();
        HashSet<Integer> followers = userMap.get(userId);
        PriorityQueue<Tweet> minHeap = new PriorityQueue<>((a, b) -> a.timeStamp - b.timeStamp);
        if (followers == null)
            return result;
        for (Integer id : followers) {
            List<Tweet> tweets = tweetMap.get(id);
            if (tweets != null && tweets.size() != 0) {
                for (int i = tweets.size() - 1; i >= 0; i--) {
                    minHeap.add(tweets.get(i));

                    if (minHeap.size() > 10)
                        minHeap.poll();

                }
            }
        }

        while (!minHeap.isEmpty()) {
            result.add(minHeap.poll().id);
        }
        Collections.reverse(result);
        return result;

    }
    //O(1)
    public void follow(int followerId, int followeeId) {
        if (!userMap.containsKey(followerId)) {
            userMap.put(followerId, new HashSet<>());

        }
        userMap.get(followerId).add(followeeId);

    }
    //O(1)
    public void unfollow(int followerId, int followeeId) {
        userMap.get(followerId).remove(followeeId);
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
