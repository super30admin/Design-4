class Twitter {
    int newsFeedCap;
    int curTimeStamp;
    class Tweet{
        int tweetId;
        int timeStamp;
        
        public Tweet(int tweetId, int time){
            this.tweetId = tweetId;
            this.timeStamp = time;
        }
    }
 

    /** Initialize your data structure here. */
    //followred = usertable
    HashMap<Integer, Set<Integer>> userTable; // this map has a mapping of userid and user that this user is following
    HashMap<Integer, List<Tweet>> tweetTable;
    public Twitter() {
        userTable = new HashMap<>();
        tweetTable = new HashMap<>();
        newsFeedCap = 10;
        // curTimeStamp = 0;
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        
        if(!userTable.containsKey(userId)){
            userTable.put(userId, new HashSet<>()); //making sure before user post, it's id is created
        }
        userTable.get(userId).add(userId); // making this user follow itself
        
        if(!tweetTable.containsKey(userId)){
            tweetTable.put(userId, new ArrayList<>());
        }
        tweetTable.get(userId).add(new Tweet(tweetId,curTimeStamp++));
        
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> q = new PriorityQueue<>((a,b) -> a.timeStamp - b.timeStamp);
        
        if(userTable.containsKey(userId)){
            Set<Integer> followingMap = userTable.get(userId);
            
            for(Integer id: followingMap){
                
                List<Tweet> foloweeTweets = tweetTable.get(id);
            if(foloweeTweets != null){
                for(Tweet tweet: foloweeTweets){
                    if(q.size() < newsFeedCap){
                        q.add(tweet);
                    }else{
                        if(tweet.timeStamp > q.peek().timeStamp){
                            q.poll();
                            q.add(tweet);
                        }
                    }
                }
            }
        }
     }
        
        List<Integer> result = new ArrayList<>();
        while(!q.isEmpty()){
            result.add(0, q.poll().tweetId);
        }
        return result;
        
}
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!userTable.containsKey(followerId)){
            userTable.put(followerId, new HashSet<>());
        }
        
        if(!userTable.containsKey(followeeId)){
            userTable.put(followeeId, new HashSet<>());
        }
        
        userTable.get(followerId).add(followeeId);
        
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(userTable.containsKey(followerId) && followerId != followeeId){
            userTable.get(followerId).remove(followeeId);
        }
    }
}


 */
