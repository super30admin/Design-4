class TweetDetails{
    int Id;
    int tweetTime;

    TweetDetails(int Id, int tweetTime){
        
        this.Id = Id;
        this.tweetTime = tweetTime;
        
    }
}


class Twitter {
    
    HashMap<Integer,Set<Integer>> followerData = new HashMap<>();
    HashMap<Integer, List<TweetDetails>> userData = new HashMap<>();
    int timeStamp;
    /** Initialize your data structure here. */
    public Twitter() {
        this.followerData = new HashMap<>();
        this.userData = new HashMap<>();
        
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        follow(userId,userId);
        
        if(!userData.containsKey(userId)){
            userData.put(userId, new ArrayList<>());
        }
        
        userData.get(userId).add(new TweetDetails(tweetId,timeStamp));
        timeStamp++;
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        
        PriorityQueue<TweetDetails> pq = new PriorityQueue<>((a,b) -> a.tweetTime-b.tweetTime);
        
            
            //get the list of follwers 
             Set<Integer> followersList = followerData.get(userId);
            
                if(followersList != null){
                    for(int follower : followersList){
                
                //for each follower in list get the tweets
                        List<TweetDetails> details = userData.get(follower);
                               if(details != null){
                                   for(TweetDetails tweet : details){
                                       pq.add(tweet);
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
        if(!followerData.containsKey(followerId)){
            followerData.put(followerId,new HashSet<>());
        }
        
         followerData.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        
        if(followerData.containsKey(followerId) && followerId != followeeId){
           followerData.get(followerId).remove(followeeId);    
           
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