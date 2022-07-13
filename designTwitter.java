// Time Complexity : O(n) 
// Space Complexity : O(l)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this: No


class Twitter {
    class Tweet{ //tweet object
        int tid;
        int createdAt;
        public Tweet(int tid, int createdAt){
            this.tid = tid;
            this.createdAt = createdAt;
        }
    }
    HashMap<Integer, List<Tweet>> tweets;
    HashMap<Integer, Set<Integer>> followed;
    int time;
    public Twitter() { //twitter object
        this.tweets = new HashMap<>(); //initialize tweets
        this.followed = new HashMap<>(); //initialize followed table to map
    }
    
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if(!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId, time++));
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        Set<Integer> followeds = followed.get(userId);
        if(followeds != null){
            for(int uid: followeds){
                List<Tweet> fTweets = tweets.get(uid);
                if(fTweets != null){
                   for(Tweet fTweet: fTweets){
                        pq.add(fTweet);
                        if(pq.size() > 10){
                            pq.poll();
                        }
                    } 
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0, pq.poll().tid);
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
        if(followed.containsKey(followerId) && followerId != followeeId){
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