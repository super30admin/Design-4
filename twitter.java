import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
class Twitter {

    class Tweet{
        int tid;
        int createdAt;
        public Tweet(int tid, int time){
            this.tid = tid;
            this.createdAt = time;
        }
    }
    private HashMap<Integer, HashSet<Integer>>usermap; 
    private HashMap<Integer, List<Tweet>> tweets;
    private int time;

    public Twitter() {
        this.usermap = new HashMap<>();
        this.tweets = new HashMap<>();
    }
    public void postTweet(int userId, int tweetId) { //O(1)
        if(!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<>());
            
        }
        tweets.get(userId).add(new Tweet(tweetId, time++));
    }
    public List<Integer> getNewsFeed(int userId) {
        follow(userId, userId);
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        HashSet<Integer> followeds = usermap.get(userId);  //O(N)
            for(int fid: followeds){
                List<Tweet> fTweets = tweets.get(fid);
                if(fTweets != null){
                    for(Tweet f:fTweets){
                        pq.add(f);
                        if(pq.size() > 10){
                            pq.poll();
                        }
                    }
                }
            }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0, pq.poll().tid);
        }
        // reverse l and right
        return result;    
    }

    public void follow(int followerId, int followeeId) { //O(1)
        if(!usermap.containsKey(followerId)){
            usermap.put(followerId, new HashSet<>());
        }
        usermap.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {//O(1)
        if(usermap.containsKey(followerId) && followerId != followeeId){
            usermap.get(followerId).remove(followeeId);
        }
    }
}