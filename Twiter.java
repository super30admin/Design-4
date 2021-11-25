class Twitter {

    class Tweet{
        int id;
        int timeStamp;
        public Tweet(int tweetID, int time){
            this.id = tweetID;
            this.timeStamp = time;
        }
    }
    
    // (follower) : (follows these followees)
    // userID  : [userIds of all the people who he/she follows ]
    HashMap<Integer, HashSet<Integer>> followed;
    
    // UserID : list of Tweet objects mapped to this user
    HashMap<Integer, List<Tweet>> tweets;
    
    // timeStamp
    int time;
        
    public Twitter() {
        followed = new HashMap<>();
        tweets =  new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        // Add the same ID in the hash set to use it while fetching tweets  
        follow(userId, userId);
        
        // If user ID dont exist, create a new entry in the hashmap
        if(!tweets.containsKey(userId)){
            tweets.put(userId , new ArrayList<>());
        }
        
        // If userId already exists , add the tweet into the list
        tweets.get(userId).add(new Tweet(tweetId, time++));   
    }
    
    public List<Integer> getNewsFeed(int userId) {
        // Fetch all the followers of this userID and find the tweets corresponding to these followers
        HashSet<Integer> followerIDs = followed.get(userId);
        
        // Create a priority queue to find the latest 10 tweets
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.timeStamp - b.timeStamp); 
        
        if(followerIDs != null){
             for(int fid : followerIDs){
             
                // Fetch the tweets mapped to this userID
                List<Tweet> ftweets =  tweets.get(fid);

                if(ftweets != null){
                    // add these tweets in the min heap 
                    for(Tweet tweet : ftweets){
                        pq.add(tweet);
                        if (pq.size() > 10){
                            pq.poll();
                        }
                    }
                }
            }
        }
        
        // Output the latest 10 tweet Ids present in the heap into the result 
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0, pq.poll().id);
        }
       
        return result;
    }
    
    public void follow(int followerId, int followeeId) {
        
        // not present then create a new Hash Set and add the followeeId
        if(!followed.containsKey(followerId)){
            followed.put(followerId, new HashSet<>());
        }
        
        // If the followerID given is present in the followed Hashmap, 
        // then add the followeeID to the hashSet
        followed.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        
        // If the followerID is present , remove the followeeID in the corresponding hashSet
        if(followed.containsKey(followerId)  && followerId != followeeId){
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