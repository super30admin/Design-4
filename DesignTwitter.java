import java.util.*;

// TC will be O(N log M)
// SC will be O(1)


class DesignTwitter {

    class Tweet {
        int id;
        int createdAt;
        public Tweet(int tweetId, int time){
            this.id = tweetId;
            this.createdAt = time;
        }
    }

    int time;
    private HashMap<Integer, HashSet<Integer>> followers;
    private HashMap<Integer, List<Tweet>> tweetMap;


    public Twitter(){
        followers = new HashMap<>();
        tweetMap = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {  //TC will be O(1)
        if(!tweetMap.containsKey(userId)){
            tweetMap.put(userId, new ArrayList<>());
        }
        tweetMap.get(userId).add(new Tweet(tweetId, time++));
    }


    public List<Integer> getNewsFeed(int userId) {
        follow(userId, userId);
        HashSet<Integer> allfollowers = followers.get(userId);
        if(allfollowers == null){
            return new ArrayList<>();
        }
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);
        for(Integer follower : allfollowers){
            List<Tweet> alltweets = tweetMap.get(follower);
            if(alltweets == null){
                continue;
            }
            for(Tweet tw : alltweets){
                pq.add(tw);
                if(pq.size()>10){
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
        if(!followers.containsKey(followerId)){
            followers.put(followerId, new HashSet<>());
        }
        followers.get(followerId).add(followeeId);
    }


    public void unfollow(int followerId, int followeeId) {
        if(followers.containsKey(followerId) && followerId != followeeId){
            followers.get(followerId).remove(followeeId);
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