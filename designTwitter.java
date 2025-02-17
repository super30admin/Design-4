import java.util.*;

public class designTwitter {
}
class Twitter {
    class tweet {
        int tweetId;
        int createdAt;

        public tweet(int tweetId, int time) {
            this.tweetId = tweetId;
            this.createdAt = time;
        }
    }

    HashMap<Integer, HashSet<Integer>> userMap;
    HashMap<Integer, List<tweet>> tweetMap;
    int time;

    public Twitter() {
        userMap = new HashMap<>();
        tweetMap = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        if (!tweetMap.containsKey(userId)) {
            tweetMap.put(userId, new ArrayList<>());
        }
        tweetMap.get(userId).add(new tweet(tweetId, time++));
    }
    //TC: O(nlogk)
//SC : O(n) Using a list for all tweets
//Need to get the tweets of latest 10 for a paticular user so we need to get all tweets of the user and his followers
    public List<Integer> getNewsFeed(int userId) {
        // if(!userMap.containsKey(userId)){
        //     return new Arra
        // }

        PriorityQueue<tweet> pq = new PriorityQueue<> ((a, b) -> a.createdAt - b.createdAt);
        if(tweetMap.containsKey(userId)){
            //getting tweets for the user
            for(tweet tw : tweetMap.get(userId)){
                pq.add(tw);
                if(pq.size() > 10){
                    pq.poll();
                }

            }
        }
        //getting tweets for his followers
        HashSet<Integer> followers = userMap.get(userId);
        if(followers != null){
            for(int follower : followers){
                List<tweet> allTweets = tweetMap.get(follower);
                if(allTweets != null){
                    for(tweet tw :allTweets){
                        pq.add(tw);
                        if(pq.size() > 10){
                            pq.poll();
                        }
                    }

                }

            }

        }
        List<Integer> result = new ArrayList<> ();
        while(!pq.isEmpty()){
            result.add(0,pq.poll().tweetId);
        }
        return result;

    }

    public void follow(int followerId, int followeeId) {
        if (!userMap.containsKey(followerId)) {
            userMap.put(followerId, new HashSet<>());
        }
        userMap.get(followerId).add(followeeId);

    }

    public void unfollow(int followerId, int followeeId) {
        if (!userMap.containsKey(followerId)) {
            return;
        }
        if (userMap.get(followerId).contains(followeeId)) {
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