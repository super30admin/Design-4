import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

class Twitter {
    class Tweet{
        int id; int createdAt;
        public Tweet(int tId, int time){
            this.id = tId;
            this.createdAt = time;
        }
    }
    private HashMap<Integer, HashSet<Integer>> followed;
    private HashMap<Integer, List<Tweet>> tweets;
    private int time;
    public Twitter() {
        this.followed = new HashMap<>();
        this.tweets = new HashMap<>();
        this.time = 0;
    }
    
    public void postTweet(int userId, int tweetId) {
        //first, we need to have the user in both followed and tweets hashmaps
        follow(userId, userId); //this adds the user to followed if doesn't exist and adds to hashset of following as well
        if(!tweets.containsKey(userId)){ // adding user to tweets table if doesn't exists
            tweets.put(userId, new ArrayList<>());
        }
        Tweet newTweet = new Tweet(tweetId, time);
        time++;
        tweets.get(userId).add(newTweet); //adding new tweet to the list of tweet objects for a user
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt); //minHeap for tweets to get top 10 latest tweets among followers and self
        HashSet<Integer> followedIds = followed.get(userId);
        if(followedIds == null) return new ArrayList<>();
        for(Integer fid: followedIds){
            //get tweets for each follower
            List<Tweet> fTweets = tweets.get(fid);
            if(fTweets == null) continue;
            int l = fTweets.size();
            for(int i = l-1; i>=0 && i >= l-11; i--){
                Tweet fTweet = fTweets.get(i);
                pq.add(fTweet);
                if(pq.size() > 10){
                    pq.poll();
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0, pq.poll().id);
        }
        return result;

    }
    
    public void follow(int followerId, int followeeId) {
        if(!followed.containsKey(followerId)){ // checking if the user exists
            followed.put(followerId, new HashSet<>());
        }
        followed.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(!followed.containsKey(followerId) || followerId == followeeId ) return; //don't want to unfollow self or someone who doesn't exist
        followed.get(followerId).remove(followeeId);
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