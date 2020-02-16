
/*
 * Time complexity : getNewsFeed() --> O(n) where n is total number of tweets to fetch
 * Space complexity : O(n^2) if each user follows every other(Space required for following map)
 */
class Twitter {

    Map<Integer, List<Tweet>> tweetMap;
    Map<Integer, Set<Integer>> followMap;
    
    int feedSize = 10;
    int timeStamp = 0;
    
    class Tweet{
        int tweetId;
        int createdAt;
        String post;
        
        Tweet(int tweetId, int createdAt){
            this.tweetId = tweetId;
            this.createdAt = createdAt;
        }
        
    }
    /** Initialize your data structure here. */
    public Twitter() {
        
        tweetMap = new HashMap<>();
        followMap = new HashMap<>();
        
        
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        
        //Add to user table
        if(!followMap.containsKey(userId)){
            followMap.put(userId, new HashSet<>());
        }
        
        followMap.get(userId).add(userId);
        
        //Add to tweet table
        if(!tweetMap.containsKey(userId)){
            tweetMap.put(userId, new ArrayList<>());
        }
        
        tweetMap.get(userId).add(new Tweet(tweetId, timeStamp++));
        
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        
        PriorityQueue<Tweet> queue = new PriorityQueue<Tweet>((a, b) -> a.createdAt - b.createdAt);
        Set<Integer> followerIds = followMap.get(userId);
        if(followerIds != null){
            for(Integer fid : followerIds){
            
            List<Tweet> userTweet = tweetMap.get(fid);
            if(userTweet != null){
                for(Tweet tw : userTweet){
                
                if(queue.size() < feedSize){
                    queue.add(tw);
                }else{
                    if(tw.createdAt > queue.peek().createdAt){
                        queue.poll();
                        queue.add(tw);
                    }
                    }
                }    
            }
            
        }
    
        }
                
        List<Integer> results = new ArrayList<>();
        while(!queue.isEmpty()){
            results.add(0, queue.poll().tweetId);
        }
        
        return results;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!followMap.containsKey(followerId)){
            followMap.put(followerId, new HashSet<>());
        }
        
        if(!followMap.containsKey(followeeId)){
            followMap.put(followeeId, new HashSet<>());
        }
        
        followMap.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        
        if(!followMap.containsKey(followerId) || followerId == followeeId){
            return;
        }
        
        followMap.get(followerId).remove(followeeId);
        
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