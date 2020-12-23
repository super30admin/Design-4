class Twitter {
    
    class Tweet{
        int id;
        int timeStamp;
        Tweet(int id, int timeStamp){
            this.id = id;
            this.timeStamp = timeStamp;
        }
    }
    /** Initialize your data structure here. */
    HashMap<Integer,Set<Integer>> followed;
    HashMap<Integer,List<Tweet>> tweets;
    int time;
    public Twitter() {
        followed = new HashMap<>();
        tweets = new HashMap<>();
        time = 0;
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        follow(userId,userId);
        Tweet t = new Tweet(tweetId,time++);
        if(!tweets.containsKey(userId)){
            tweets.put(userId,new ArrayList<Tweet>());
        }
        tweets.get(userId).add(t);
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> res = new ArrayList<>();
        if(!followed.containsKey(userId)) return res;
        Set<Integer> userList= followed.get(userId);
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.timeStamp - b.timeStamp);
        if(userList == null) return res;
        for(int user: userList){
            List<Tweet> tweetList = tweets.get(user);
            if(tweetList == null) return res;
            for(Tweet t: tweetList){
                pq.add(t);
                if(pq.size() > 10){
                    pq.poll();
                }
            }
        }
        while(!pq.isEmpty()){
            int x = pq.poll().id;
            res.add(0,x);
        }
        // for(int i : tweets.keySet()){
        //     System.out.print(i+" : ");
        //     for(Tweet k: tweets.get(i)){
        //         System.out.print(k.id+",");
        //     }
        //     System.out.println();
        // }
        // for(int i : followed.keySet()){
        //     System.out.print(i+" : ");
        //     for(int k: followed.get(i)){
        //         System.out.print(k+",");
        //     }
        //     System.out.println();
        // }
        return res;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!followed.containsKey(followerId)){
            followed.put(followerId,new HashSet<>());
            // followed.get(followerId).add(followerId);
        }
        followed.get(followerId).add(followeeId);   
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(followerId == followeeId) return;
        if(!followed.containsKey(followerId)) return;
        followed.get(followerId).remove(followeeId);
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