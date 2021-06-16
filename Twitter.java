// Time Complexity : O(1)
// Space Complexity : O(1)(O(10000))
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this :


// Your code here along with comments explaining your approach
// I am taking 2 hashes on any given key value. The first hash is to find out the bucket where the (k,v) is to be placed and the second hash is to find the location within the bucket where the element is to be placed.
class Tweet{
    int tweetId;
    int createdAt;
    public Tweet(int tweetId,int createdAt){
        this.tweetId = tweetId;
        this.createdAt = createdAt;
    }
    
}
class Twitter {
    HashMap<Integer,HashSet<Integer>> followers;
    HashMap<Integer,List<Tweet>> tweets;
    int time;
    /** Initialize your data structure here. */
    public Twitter() {
        this.followers = new HashMap<>();
        this.tweets = new HashMap<>();
        this.time = 0;
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        follow(userId,userId);
        Tweet tweet = new Tweet(tweetId,this.time++);
        if(!tweets.containsKey(userId)){
            tweets.put(userId,new ArrayList<>());
        }
        tweets.get(userId).add(tweet);
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        HashSet<Integer> users = followers.get(userId);
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b)->a.createdAt - b.createdAt);
        
        if(users != null){
            for(int user:users){
                
                List<Tweet> tweetsList = tweets.get(user); 
                if(tweetsList != null){
                    for(Tweet twt: tweetsList){
                        pq.add(twt);
                        if(pq.size()>10) pq.poll();
                    }                    
                }
            }
        }
        List<Integer> result = new LinkedList<>();
        while (!pq.isEmpty()) result.add(0,pq.poll().tweetId);
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
        if(followers.containsKey(followerId))
            if(followers.get(followerId).contains(followeeId))
                followers.get(followerId).remove(followeeId);
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
