import java.util.*;

public class DesignTwitter {
    HashMap<Integer, HashSet<Integer>> followed;
    HashMap<Integer, List<Tweet>> tweets;
    int time;
    class Tweet{
        int id;
        int createdAt;
        public Tweet(int tweetId, int time) {
            this.id = tweetId;
            this.createdAt = time;
        }
    }
    public Twitter(){
        this.followed = new HashMap<>();
        this.tweets = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if(!tweets.containsKey(userId)) {
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId, time));
        time++;
    }

    public List<Integer> getNewsFeed(int userId) {
        List<Integer> result = new ArrayList<>();
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);
        HashSet<Integer> following = followed.get(userId);
        if(following != null) {
            for(int fId : following) {
                List<Tweet> fTweets = tweets.get(fId);
                if(fTweets != null) {
                    for(Tweet tw : fTweets){
                        pq.add(tw);
                        if(pq.size() >10){
                            pq.poll();
                        }
                    }
                }
            }
        }
        while(!pq.isEmpty()) {
            result.add(0, pq.poll().id);
        }
        return result;
    }

    public void follow(int followerId, int followeeId) {
        if(!followed.containsKey(followerId)) {
            followed.put(followerId, new HashSet<>());
        }
        followed.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if(followed.containsKey(followerId) && followerId != followeeId) {
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
}
