import java.util.*;

public class Twitter {
    class Tweet{
        int id;
        int createdAt;
        Tweet(int id, int createdAt){
            this.id = id;
            this.createdAt = createdAt;
        }
    }
    HashMap<Integer, List<Tweet>> tweets;
    HashMap<Integer, Set<Integer>> followed;
    int time = 0;
    public Twitter() {
        this.tweets = new HashMap<>();
        this.followed = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if(!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId, time++));
    }

    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);
        Set<Integer> followers = followed.get(userId);
        if(followers!=null){
            for(int user: followers){
                List<Tweet> userTweets = tweets.get(user);
                if(userTweets!=null){
                    for(Tweet userTweet: userTweets){
                        pq.add(userTweet);
                        if(pq.size() > 10)
                            pq.poll();
                    }
                }

            }
        }

        List<Integer> result = new LinkedList<>();
        while(!pq.isEmpty()){
            result.add(0, pq.poll().id);
        }
        return result;
    }

    public void follow(int followerId, int followeeId) {
        if(!followed.containsKey(followerId)){
            followed.put(followerId, new HashSet<>());
        }
        followed.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if(followed.containsKey(followerId) && followerId!=followeeId){
            followed.get(followerId).remove(followeeId);
        }

    }
}
