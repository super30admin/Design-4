
class User {

    int id;
    Set<User> following;
    Tweet head;

    public User(int id) {
        this.id = id;
        this.following = new HashSet<>();
        this.following.add(this);

    }

    public void post(int id, int time) {
        Tweet tweet = new Tweet(id, time);
        tweet.next = this.head;
        this.head = tweet;

    }

}

class Tweet {

    int id;
    int time;
    Tweet next;

    public Tweet(int id, int time) {
        this.id = id;
        this.time = time;
        this.next = null;
    }
}

class Twitter {

    HashMap<Integer, User> userData;
    int time;

    public Twitter() {
        userData = new HashMap<>();
        this.time = 0;
        //this.pq = new PriorityQueue<>((a,b)->a.time-b.time);

    }

    public void postTweet(int userId, int tweetId) {
        if (!userData.containsKey(userId)) {
            User user = new User(userId);
            userData.put(userId, user);
        }
        userData.get(userId).post(tweetId, this.time);
        this.time++;

    }

    public List<Integer> getNewsFeed(int userId) {
        List<Integer> result = new ArrayList<>();
        PriorityQueue<Tweet> heap = new PriorityQueue<>((a, b) -> b.time - a.time);
        if (userData.containsKey(userId)) {
            User currentUser = userData.get(userId);
            for (User user : currentUser.following) {
                if (user.head != null) {
                    heap.add(user.head);
                }
            }

            while (!heap.isEmpty()) {
                Tweet top = heap.poll();
                result.add(top.id);
                if (top.next != null) {
                    heap.offer(top.next);
                }
                if (result.size() == 10) {
                    break;
                }
            }

        }
        return result;
    }

    public void follow(int followerId, int followeeId) {
        if (!userData.containsKey(followerId)) {
            User user = new User(followerId);
            userData.put(followerId, user);

        }
        if (!userData.containsKey(followeeId)) {
            User user = new User(followeeId);
            userData.put(followeeId, user);

        }

        userData.get(followerId).following.add(userData.get(followeeId));
    }

    public void unfollow(int followerId, int followeeId) {

        if (userData.containsKey(followerId) && userData.containsKey(followeeId)) {
            User follower = userData.get(followerId);
            User followee = userData.get(followeeId);
            if (follower.following.contains(followee)) {
                follower.following.remove(followee);
            }
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
