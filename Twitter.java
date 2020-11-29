import java.util.*;

public class Twitter {

    class Tweet{
        int id;
        int time;

        public Tweet(int id, int time) {
            this.id = id;
            this.time = time;
        }
    }

    Map<Integer, Set<Integer>> userFollowsMap;
    Map<Integer, List<Tweet>> userTweetsMap;
    int time;

    /** Initialize your data structure here. */
    public Twitter() {
        userFollowsMap = new HashMap<>();
        userTweetsMap = new HashMap<>();

    }

    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);

        if(!userTweetsMap.containsKey(userId)){
            userTweetsMap.put(userId, new ArrayList<>());
        }

        Tweet tweet = new Tweet(tweetId, time);
        userTweetsMap.get(userId).add(tweet);
        time++;
    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> result = new ArrayList<>();

        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.time-b.time);
        Set<Integer> userFollowSet = userFollowsMap.get(userId);
        if(userFollowSet!= null && !userFollowSet.isEmpty()){
            for(int user : userFollowSet){
                if(userTweetsMap.containsKey(user)){
                    List<Tweet> userTweetList = userTweetsMap.get(user);
                    if(userTweetList!= null && !userTweetList.isEmpty()){
                        for(Tweet tweet : userTweetList){
                            pq.add(tweet);
                            if(pq.size()>10){
                                pq.poll();
                            }
                        }
                    }
                }
            }
        }

        while (!pq.isEmpty()){
            result.add(0,pq.poll().id);
        }
        return result;
    }

    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!userFollowsMap.containsKey(followerId)){
            userFollowsMap.put(followerId, new HashSet<>());
        }
        userFollowsMap.get(followerId).add(followeeId);
    }

    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(followeeId==followerId) return;
        if(userFollowsMap.containsKey(followerId)){
            userFollowsMap.get(followerId).remove(followeeId);
        }
    }
}
