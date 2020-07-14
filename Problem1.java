class Twitter {
    
    //Create a class Tweet containing TweetID and TimeStamp
    class Tweet {
        int id;
        int createdAt;
        public Tweet(int id, int createdAt) {
            this.id = id;
            this.createdAt = createdAt;
        }
    }
    int timeStamp ;
    int feedSize ;
    Map<Integer,Set<Integer>> followedBy;//set of followees for an user
    Map<Integer,List<Tweet>> tweets;//user associateed with tweets
    /** Initialize your data structure here. */
    public Twitter() {
        followedBy = new HashMap<>();
        tweets = new HashMap<>();
        this.feedSize = 10;
        this.timeStamp = 0;
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        //user follows self by default
        follow(userId,userId);
        //check if user is already in tweets map and map user -> tweet
        if(!tweets.containsKey(userId)) {
            tweets.put(userId,new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId,timeStamp++));
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        //do priority queue and get the top 10 recent
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        //get the user followees
        Set<Integer> users= followedBy.get(userId);
        if (users != null){
            //for each followee geet thee tweet list
            for (Integer user : users) {
                List<Tweet> twets = tweets.get(user);
                if(twets != null) {
                    //for every tweet check the recent 10 or not criteria
                for (Tweet tweet: twets) {
                    pq.add(tweet);
                    if(pq.size() > this.feedSize) {
                        pq.poll();
                    }
                }
                }
            }
        }
        //add it to the result
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()) {
            Tweet tt = pq.poll();
            result.add(0,tt.id);
        }
        return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        // follower -> seet of followees map 
        if(!followedBy.containsKey(followerId)) {
            followedBy.put(followerId,new HashSet<>());
        }
        
        followedBy.get(followerId).add(followeeId);
            
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        //check if followerId is already in map and followeer cannot unfollow herself by any chance
        if(followedBy.containsKey(followerId) && followerId != followeeId) {
            followedBy.get(followerId).remove(followeeId);
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
