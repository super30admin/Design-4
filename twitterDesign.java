
class Twitter {

    /** Initialize your data structure here. */
    class tweet{
        int Id;
        int createdAt;
        public tweet(int Id, int createdAt){
            this.Id = Id;
            this.createdAt = createdAt;
        }
        
    }
    HashMap<Integer, Set<Integer>> followed;
    HashMap<Integer, List<tweet>> tweets;
    int time;
    public Twitter() {
        followed = new HashMap<>();
        tweets = new HashMap<>();
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        if(!followed.containsKey(userId)){
            followed.put(userId, new HashSet<>());
        }
        followed.get(userId).add(userId);
        tweet t = new tweet(tweetId, time++); 
        if(!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(t);
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        Set<Integer> flfeed = followed.get(userId);
        if(flfeed != null){
            for(Integer feed : flfeed){
                List<tweet> newsfeed = tweets.get(feed);
                if(newsfeed != null){
                    for(tweet ftweet: newsfeed){
                        pq.add(ftweet);
                        if(pq.size() > 10){
                            pq.poll();
                        }
                    }  
                }               
            }
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0, pq.poll().Id);
        }
        return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!followed.containsKey(followerId)){
            followed.put(followerId, new HashSet<>());
        }
        followed.get(followerId).add(followeeId);
        
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
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
