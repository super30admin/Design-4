// Time Complexity :
// Space Complexity : 
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this :


// Your code here along with comments explaining your approach

class Twitter {

    HashMap<Integer,Set<Integer>> followers;
    HashMap<Integer,List<Tweet>> tweets;
    int feedSize = 10;
    int timeStamp = 0; 
    
    private class Tweet{
        int id;
        int createdAt;
        
        public Tweet(int id, int createdAt){
            this.id = id;
            this.createdAt = createdAt;
        }
    }
    /** Initialize your data structure here. */
    public Twitter() {
        followers = new HashMap<>();
        tweets = new HashMap<>();
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        if(!followers.containsKey(userId)){
            followers.put(userId,new HashSet<>());
        }
        followers.get(userId).add(userId); 
        if(!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId,timeStamp++));
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b)->a.createdAt - b.createdAt);
        Set<Integer> fIds = followers.get(userId);
        if(fIds != null){
            for(Integer fId : fIds){
                List<Tweet> fTweets = tweets.get(fId);
                if(fTweets != null){
                    for(Tweet fTweet: fTweets){
                        if(pq.size() < feedSize){
                            pq.add(fTweet);
                        }
                        else{
                            if(fTweet.createdAt > pq.peek().createdAt){
                                pq.poll();
                                pq.add(fTweet);
                            }
                        }
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0,pq.poll().id);
        }
        return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
       if(!followers.containsKey(followerId)){
            followers.put(followerId,new HashSet<>());
        }
        followers.get(followerId).add(followeeId); 
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(followers.containsKey(followerId) && followerId != followeeId){
            followers.get(followerId).remove(followeeId);
        }
    }
}
