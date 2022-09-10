//Main class
class Twitter {
    
    //tweet class
    class Tweet{
        int tId;
        int createdAt;
        public Tweet(int id, int time){
            this.tId = id;
            this.createdAt = time;
        }
    }
    
    HashMap<Integer, HashSet<Integer>> followed;
    HashMap<Integer, List<Tweet>> tweets;
    int time;
    public Twitter() {
        this.followed = new HashMap<>();
        this.tweets = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId); //O(1)
        
     
        if(!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId, time++)); //??
       
    }
    
    public List<Integer> getNewsFeed(int userId) {
        //Custom comparators
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) ->a.createdAt - b.createdAt); // min Heap
        HashSet<Integer> followeds = followed.get(userId); //get user followers
        
        if(followeds != null ){
            for(int followedId : followeds){
                
                List<Tweet> fTweets = tweets.get(followedId);
                
                System.out.println("fTweets" +" : " +fTweets);
                if(fTweets != null){
                    int size = fTweets.size();
                    for(int k = size-1; k>=0 && k > size-11; k--){
                        Tweet fTweet = fTweets.get(k);
                        pq.add(fTweet);
                        if(pq.size() > 10){
                            pq.poll();
                        }
                    }
                }
                
            }
        }
        
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0, pq.poll().tId); //??
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


class Twitter {
    
    //tweet class
    class Tweet{
        int tId;
        int createdAt;
        public Tweet(int id, int time){
            this.tId = id;
            this.createdAt = time;
        }
    }
    
    HashMap<Integer, HashSet<Integer>> followed;
    HashMap<Integer, List<Tweet>> tweets;
    int time;
    public Twitter() {
        this.followed = new HashMap<>();
        this.tweets = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId); //O(1)
        
     
        if(!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId, time++)); //??
       
    }
    
    public List<Integer> getNewsFeed(int userId) {
        //Custom comparators
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) ->a.createdAt - b.createdAt); // min Heap
        HashSet<Integer> followeds = followed.get(userId); //get user followers
        
        if(followeds != null ){
            for(int followedId : followeds){
                
                List<Tweet> fTweets = tweets.get(followedId);
                
                System.out.println("fTweets" +" : " +fTweets);
                if(fTweets != null){
                    for(Tweet fTweet: fTweets){
                        pq.add(fTweet);
                        if(pq.size() > 10){
                            pq.poll();
                        }
                    }
                }
                
            }
        }
        
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0, pq.poll().tId); //??
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

