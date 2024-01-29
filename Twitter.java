import java.util.*;
//TC O(1) for each user operation
//SC O(T+F) T -> number of tweets F -> Followings

//Use HashMap where get op needs to be O(1), to display NewsFeed use PQ to get the tweets from followings and sort them in
//reverse chronological order limiting num of tweets to 10.
class Twitter {

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

    public Twitter(){
        this.tweetsMap = new HashMap();
        this.followingsMap = new HashMap();
        this.timeStamp = 0;
    }


    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        Tweets tweet = new Tweets(tweetId, timeStamp++);
        if(!tweetsMap.containsKey(userId)){
            tweetsMap.put(userId, new ArrayList());
        }
        tweetsMap.get(userId).add(tweet);
    }

    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweets> pq = new PriorityQueue<>((a, b)->a.timeStamp-b.timeStamp);
        HashSet<Integer> followings = followingsMap.get(userId);
        if(followings!=null){
            for(int following: followings){
                List<Tweets> tweetsList = tweetsMap.get(following);
                if(tweetsList!=null){
                    int startIndex = 0;
                    if(tweetsList.size()>10)
                        startIndex = tweetsList.size() - 10;

                    for(int i=startIndex; i<tweetsList.size(); i++){
                        pq.add(tweetsList.get(i));
                        if(pq.size()>10)
                            pq.poll();
                    }
                }

            }
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0,pq.poll().tweetId);
        }
        return result;
    }

    public void follow(int followerId, int followeeId) {
        if(!followingsMap.containsKey(followerId)){
            followingsMap.put(followerId, new HashSet());
        }
        followingsMap.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if(followingsMap.containsKey(followerId))
            followingsMap.get(followerId).remove(followeeId);
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