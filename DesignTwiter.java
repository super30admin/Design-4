import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Twitter {
    int tId;
    int createdAt;

    public Twitter(int tId, int time) {
        this.tId = tId;
        this.createdAt = time;
    }

    HashMap<Integer, Set<Integer>> followeeId;
    HashMap<Integer, List<Tweet>> twitter;
    int time;

    public Twitter() {
        this.followeeId = new HashMap<>();
        this.twitter = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        follow(userId , userId);
        if(!userId.contains(tweetId)){
            userId.put(tweetId , new ArrayList<>());
        }
        userId.get(userId). new ;
    }

    public List<Integer> getNewsFeed(int userId) {

    }

    public void follow(int followerId, int followeeId) {
        if (!followeeId.containsKey(followerId)) {
            followeeId.put(followerId, new HashSet<>());
        }
        followeeId.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (followeeId.containsKey(followerId) && followerId != followeeId) {
            followeeId.get(followerId).remove(followeeId);
        }

    }
}
