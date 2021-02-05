// Time Complexity : O(n) for everything but follow and unfollow that is O(1)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no


// Your code here along with comments explaining your approach

class Twitter {
    //create a tweet class
    class Tweet{
        //tweet id
        int id;
        //time
        int createdAt;
        public Tweet(int id, int createdAt){
            this.id = id;
            this.createdAt = createdAt;
        }
    } 
    //user table
    HashMap<Integer, Set<Integer>> followed;
    //tweet table
    HashMap<Integer, List<Tweet>> tweets;
    //time
    int time;
    /** Initialize your data structure here. */
    public Twitter() {
        followed = new HashMap<>();
        tweets = new HashMap<>();
        time = 0;
    }
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        //follow yourself
        follow(userId,userId);
        //check if the tweets table doesn't contain the user
        if(!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<>());
        }
        //get the userId and add the tweet id and time added
        tweets.get(userId).add(new Tweet(tweetId, time++));  
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        //make a priority quue
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        //create a set for unquie follower ids
        Set<Integer> fids = followed.get(userId);
        if(fids != null){
            //iterate over the followids
            for(int fid : fids){
                //get the list of follow ids
                List<Tweet> ftweets =  tweets.get(fid);
                //check if the list isn't null
                if(ftweets != null){
                    //go over each tweet
                    for(Tweet ftweet : ftweets){
                        //add the tweet in the pq
                        pq.add(ftweet);
                        //check the min heap size which will be greater than 10 then pop
                        if(pq.size() > 10){
                            pq.poll();
                            
                        }
                    }
                }
            }
        }
        List<Integer> res = new ArrayList<>();
        //go over the pq to get the top 10 tweets
        while(!pq.isEmpty()){
            //add to the 0th index because you want latest each time
            res.add(0, pq.poll().id);
        }
        return res;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        //if the follow table does not contains followerId
        if(!followed.containsKey(followerId)){
            followed.put(followerId, new HashSet<>());
        }
        //if the entry is there 
        followed.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        //check if it is in the follower table
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