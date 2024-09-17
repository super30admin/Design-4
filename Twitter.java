// Time Complexity :
//getNewsFeed O(tweets of all people user follows , self included)
//postTweet, follow , unfollow O(1)
// Space Complexity :O(users)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

class Twitter {
    
    
    class Tweet{
        int id;
        int createdAt;
        
        public Tweet(int id, int createdAt){
            this.id = id;
            this.createdAt = createdAt;
        }
    }

    
    HashMap<Integer,HashSet<Integer>> followed ;
    HashMap<Integer, List<Tweet>> tweets;
    int feedSize;
    int timestamp;
    
    /** Initialize your data structure here. */
    public Twitter() {
        followed = new HashMap<>();
        tweets = new HashMap<>();
        this.feedSize = 10;
    }

    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        follow(userId,userId);
        if(tweets.get(userId) == null){
            tweets.put(userId,new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId,timestamp++));
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b)->a.createdAt - b.createdAt);
        Set<Integer> fId = followed.get(userId);
        if(fId!=null){
            for(Integer f :fId){
                List<Tweet> ftweets = tweets.get(f);
                if(ftweets!=null){
                    for(Tweet a : ftweets){
                        pq.add(a);
                        if(pq.size()> feedSize){
                            pq.remove();
                        }
                    }
                }
            }
        }
        
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0,pq.remove().id);
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
        if(followed.containsKey(followerId) && followerId != followeeId ){
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