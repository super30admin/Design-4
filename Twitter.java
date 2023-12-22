import java.util.*;

public class Twitter {

    HashMap<Integer, List<Tweets>> tweetsMap;
    HashMap<Integer, HashSet<Integer>> followingsMap;
    int timeStamp;

    class Tweets{
        int tweetId;
        int timeStamp;

        public Tweets(int tweetId, int timeStamp){
            this.tweetId = tweetId;
            this.timeStamp = timeStamp;
        }
    }
    public Twitter() {
        this.tweetsMap = new HashMap<>();
        this.followingsMap = new HashMap<>();
        this.timeStamp = 0;
    }

    public void postTweet(int userId, int tweetId) {
        follow(userId,userId);
        Tweets tweet = new Tweets(tweetId, timeStamp++);
        if(!tweetsMap.containsKey(userId)){
            tweetsMap.put(userId, new ArrayList<>());
        }
        tweetsMap.get(userId).add(tweet);

    }

    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweets> pq = new PriorityQueue<>((a,b)->a.timeStamp-b.timeStamp);
        HashSet<Integer> followings = followingsMap.get(userId);
        if(followings != null){
            for(int following: followings){
                List<Tweets> tweetsList = tweetsMap.get(following);
                if(tweetsList != null){
                    for(Tweets tweet : tweetsList){
                        pq.add(tweet);
                        if(pq.size() > 10){
                            pq.poll();
                        }
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0, pq.poll().tweetId);
        }
        return result;
    }

    public void follow(int followerId, int followeeId) {
        if(!followingsMap.containsKey(followerId)){
            followingsMap.put(followerId, new HashSet<>());
        }
        followingsMap.get(followerId).add(followeeId);

    }

    public void unfollow(int followerId, int followeeId) {
        if(followingsMap.containsKey(followerId)){
            followingsMap.get(followerId).remove(followeeId);
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

//Time Complexity:
//        postTweet(int userId, int tweetId):
//        O(1)

//        getNewsFeed(int userId):
//        O(N log K), where N is the total number of tweets, and K is a constant.

//        follow(int followerId, int followeeId):
//        O(1)

//        unfollow(int followerId, int followeeId):
//        O(1)

// Space Complexity:
//        HashMap<Integer, List<Tweets>> tweetsMap://
//        O(N)

//        HashMap<Integer, HashSet<Integer>> followingsMap:
//        O(N + M), where M is the total number of follow relationships.

//        PriorityQueue<Tweets> pq:
//        O(K), where K is a constant.

//        Tweets class:
//        O(N)
