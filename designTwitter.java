class Twitter {
    // Inner class to represent a Tweet with a timestamp and tweetId
    class Tweet {
        int timeStamp;
        int tweetId;

        public Tweet(int timeStamp, int tweetId) {
            this.timeStamp = timeStamp;
            this.tweetId = tweetId;
        }
    }

    // Maps userId to a set of followeeIds
    HashMap<Integer, HashSet<Integer>> users;
    // Maps userId to a list of tweets
    HashMap<Integer, List<Tweet>> tweets;
    // Global timestamp counter to order tweets
    int time;

    // Constructor to initialize the Twitter object
    public Twitter() {
        this.users = new HashMap<>();
        this.tweets = new HashMap<>();
        this.time = 0; // Initialize timestamp counter
    }

    // Method to post a tweet by a user
    public void postTweet(int userId, int tweetId) {
        // Ensure that the user follows themselves (for simplicity)
        follow(userId, userId);
        
        // Initialize the list of tweets for the user if not already present
        if (!tweets.containsKey(userId)) {
            tweets.put(userId, new ArrayList<>());
        }

        // Create a new tweet with the current timestamp and increment the timestamp
        Tweet tweet = new Tweet(time++, tweetId);
        // Add the new tweet to the user's list of tweets
        tweets.get(userId).add(tweet);
    }

    // Method to get the news feed for a user
    public List<Integer> getNewsFeed(int userId) {
        // Priority queue to keep track of the most recent tweets
        PriorityQueue<Tweet> minHeap = new PriorityQueue<>((Tweet a, Tweet b) -> a.timeStamp - b.timeStamp);
        List<Integer> result = new ArrayList<>();

        // Return an empty list if the user does not exist
        if (!users.containsKey(userId)) return result;

        // Get the list of users that the user follows
        HashSet<Integer> followeeUsers = users.get(userId);

        // Add tweets from all followees to the min-heap
        for (int user : followeeUsers) {
            if (tweets.containsKey(user)) {
                 List<Tweet> tweetList=tweets.get(user);
                 int max=0;
                for (int i=tweetList.size()-1;i>=0;i--) {
                    if(max>10)break;
                    minHeap.add(tweetList.get(i));

                    // Ensure the min-heap size does not exceed 10
                    if (minHeap.size() > 10) minHeap.poll();
                    max++;
                }
            }
        }

        // Extract tweets from the min-heap and add them to the result list in reverse order
        while (!minHeap.isEmpty()) {
            result.add(0, minHeap.poll().tweetId);
        }

        return result;
    }

    // Method for a user to follow another user
    public void follow(int followerId, int followeeId) {
        // Initialize the set of followees for the follower if not already present
        if (!users.containsKey(followerId)) {
            users.put(followerId, new HashSet<>());
            users.get(followerId).add(followerId); // Add the follower to their own followees list
        }
        // Add the followee to the follower's set of followees
        users.get(followerId).add(followeeId);
    }

    // Method for a user to unfollow another user
    public void unfollow(int followerId, int followeeId) {
        // Remove the followee from the follower's set of followees if the follower exists
        if (users.containsKey(followerId)) {
            users.get(followerId).remove(followeeId);
        }
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId, tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId, followeeId);
 * obj.unfollow(followerId, followeeId);
 */
