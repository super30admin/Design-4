class Twitter {
    HashMap<Integer, Set<Integer>> fans ;
    HashMap<Integer, List<Tweet>> tweets ;
    int feedMaxNum,timeStamp;
    /** Initialize your data structure here. */
    public Twitter() {
        fans= new HashMap<>();
        tweets = new HashMap<>();
        this.timeStamp =0;
        this.feedMaxNum = 10;
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        if(!tweets.containsKey(userId)){
            follow(userId, userId);
            tweets.put(userId, new ArrayList<>());
        }
        //add new twwet in the tweet hashmap
        tweets.get(userId).add(new Tweet(timeStamp++, tweetId));
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */

    public List<Integer> getNewsFeed(int userId) {
        //queue will keep the data in ascending order
        Queue<Tweet> queue = new PriorityQueue<>((a,b) -> a.time - b.time);
        
        //get users's fans list
        Set<Integer> userFans = fans.get(userId);
        if(userFans != null){
            //get tweets posted by each users
            for(int user : userFans){
                List<Tweet> tweetList = tweets.get(user);
                if(tweetList == null)
                    continue;
                //getting tweets of all users I follow and adding into maxheap to keep most recent tweets om
                //the top
                for(Tweet twt : tweetList){
                    if(queue.size() < feedMaxNum)
                        queue.add(twt);
                    else{
                        //we need top 10 recent tweets, so removing older one, when heap size exceeds 10
                        queue.add(twt);
                        queue.remove();
                    }
                }
            }
        }
         List<Integer> myFeed = new ArrayList<>();
         while(!queue.isEmpty())
             myFeed.add(0, queue.poll().tweetid);
    
        return myFeed;
    }
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!fans.containsKey(followerId))
            fans.put(followerId, new HashSet<>());
        fans.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        //cannot unfollow itself
         if(fans.containsKey(followerId) && followerId!= followeeId)
             fans.get(followerId).remove(followeeId);
    }
}

class Tweet{
    int time;
    int tweetid;
    public Tweet(int time, int tweetid){
        this.time = time ;
        this.tweetid= tweetid;
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
