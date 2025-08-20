//Time Complexity: postTweet: O(1) (append to list, assign timestamp), follow / unfollow: O(1) (HashSet add/remove), getNewsFeed: O(F · 10) = O(F), where F = #followees (scan up to last 10 tweets per followee; heap kept at size ≤10 so each offer/poll is O(1)).
//Space Complexity: O(U + E + T) where U = users, E = total follow edges (sizes of all followee sets), T = total tweets kept.

//Each user stores a HashSet of followees and a list of their tweets; each tweet has an increasing timestamp.
//postTweet appends a timestamped tweet; follow/unfollow update the followees set.
//getNewsFeed scans up to the last 10 tweets of each followee, pushes them into a min-heap capped at 10 by time, then pops to produce the 10 most recent tweet IDs.

class Twitter {
    int timeStamp;
    HashMap<Integer, User> userMap;

    class User {
        int UserId;
        HashSet<Integer> followees;
        List<Tweet> tweets;

        public User(int userId) {
            this.UserId = userId;
            this.followees = new HashSet<>();
            this.tweets = new ArrayList<>();
        }
    }

    class Tweet {
        int tweetId;
        int time;

        public Tweet(int tweetId, int time) {
            this.tweetId = tweetId;
            this.time = time;
        }
    }

    public Twitter() {
        userMap = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        Tweet newTweet = new Tweet(tweetId, timeStamp++);
        if (userMap.containsKey(userId)) {
            userMap.get(userId).tweets.add(newTweet);
        } else {
            User newUser = new User(userId);
            newUser.followees.add(userId);
            newUser.tweets.add(newTweet);
            userMap.put(userId, newUser);
        }
    }

    public List<Integer> getNewsFeed(int userId) {
        List<Integer> result = new ArrayList<>();
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.time - b.time);
        if (!userMap.containsKey(userId)) return result;
        User user = userMap.get(userId);
        HashSet<Integer> followees = user.followees;
        if (followees != null) {
            for (Integer followee : followees) {
                List<Tweet> tweets = userMap.get(followee).tweets;
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
        while (!pq.isEmpty()) {
            result.add(0, pq.poll().tweetId);
        }
        return result;
    }

    public void follow(int followerId, int followeeId) {
        if (!userMap.containsKey(followerId)) {
            User newUser = new User(followerId);
            newUser.followees.add(followeeId);
            userMap.put(followerId, newUser);
        } else {
            userMap.get(followerId).followees.add(followerId);
            userMap.get(followerId).followees.add(followeeId);
        }

        if (!userMap.containsKey(followeeId)) {
            User newUser = new User(followeeId);
            userMap.put(followeeId, newUser);
        }
    }

    public void unfollow(int followerId, int followeeId) {
        if (userMap.containsKey(followerId)) {
            userMap.get(followerId).followees.remove(followeeId);
        }
    }
}
