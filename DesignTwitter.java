import java.util.*;

class DesignTwitter {

    class Tweet{
        int tId;
        int time;
        Tweet(int tId, int time){
            this.tId=tId;
            this.time=time;
        }
    }

    HashMap<Integer, List<Tweet>> tweetMap;
    HashMap<Integer, HashSet<Integer>> followerMap;
    int time=0;

    public DesignTwitter() {
        this.tweetMap=new HashMap<>();
        this.followerMap=new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        if(!tweetMap.containsKey(userId)){
            tweetMap.put(userId, new ArrayList<>());
            follow(userId,userId);
        }
        tweetMap.get(userId).add(new Tweet(tweetId,time));
        time++;

    }

    public List<Integer> getNewsFeed(int userId) {
        List<Integer> result=new ArrayList<>();
        PriorityQueue<Tweet> tweets=new PriorityQueue<>((a,b)->a.time- b.time);
        if(followerMap.containsKey(userId)) {
            HashSet<Integer> followerList = followerMap.get(userId);
            for(int follower: followerList){
                List<Tweet> tweetList=tweetMap.get(follower);
                if(tweetList!=null){
                    for(Tweet tweet: tweetList){
                        tweets.add(tweet);
                        if(tweets.size()>11)
                            tweets.poll();
                    }
                }
            }
        }
        while(!tweets.isEmpty()){
            result.add(0,tweets.poll().tId);
        }
        return result;
    }

    public void follow(int followerId, int followeeId) {
        if(!followerMap.containsKey(followerId)){
            followerMap.put(followerId, new HashSet<>());
        }
        followerMap.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if(followerMap.containsKey(followerId)){
            followerMap.get(followerId).remove(followeeId);
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