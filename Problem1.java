// Time Complexity : O(1)-post tweet,o(1)-follow,o(1)-unfollow,o(n)- no.of tweets - getNewsFeed
// Space Complexity : O(n)-no.of users
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

// Your code here along with comments explaining your approach

class Twitter {
    //tweet class with id and created at
    class Tweet{
        int id;
        int createdAt;
        public Tweet(int id, int createdAt){
            this.id = id;
            this.createdAt = createdAt;
        }
    }
    //here we store the follower and followee where follower follows followee
    HashMap<Integer,HashSet<Integer>> followed;
    //here we store for user and his tweets ate different times
    HashMap<Integer,List<Tweet>> tweets;
    int time;
    public Twitter() {
         this.followed = new HashMap<>();
         this.tweets = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        //as the follower needs to see his own tweets we need to make him follow himself by adding id to hashmap
        follow(userId,userId);
        //if the tweet hashmap doesnt have user id we put that user id with a new list
        if(!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<>());
        }
        //then add the tweet
        tweets.get(userId).add(new Tweet(tweetId,time++));
    }
    
    public List<Integer> getNewsFeed(int userId) {
        //min to high by using min heap based on created at
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b)->(a.createdAt-b.createdAt));
        //we need to get all ids that the user follows
        HashSet<Integer> fIds = followed.get(userId);
        if(fIds != null){
            //for each id we need to get list of tweets that he posted
            for(Integer fid : fIds)
            {
                List<Tweet> twee = tweets.get(fid);
                if(twee != null){
                    //iterate through those tweets and place in pq
                    for(Tweet t : twee)
                    {
                       pq.add(t);
                       if(pq.size()>10)
                           pq.poll();
                    } 
                }
            } 
        }
       
        List<Integer> result = new ArrayList<>();
        //as placed in pq from min to max we need to first give out max
        while(!pq.isEmpty()){
            //always adding the highest at 0 index
            result.add(0,pq.poll().id);
        }
        return result;
    }
    
    public void follow(int followerId, int followeeId) {
        if(!followed.containsKey(followerId)){
            followed.put(followerId,new HashSet<>());
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