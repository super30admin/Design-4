/**
 * Data Structures required 
 *      1) HashMap - To store which people who are followed by the user. It will contain userId as Key
 *      and a set to store all the userId of the followed user
 *      2) HashMap - To store the user tweets, where key will be userId and value will be LinkedList
 *      3) Custom Tweet class - to store tweet ID and timestamp of the tweet 
 */

class Twitter {
    
    class Tweet {
        int time;
        int id;

        public Tweet(int tweetTime, int tweetId) {
            time = tweetTime;
            id = tweetId;
        }
    }
    
    HashMap<Integer, Set<Integer>> usersFollowers;
    HashMap<Integer, LinkedList<Tweet>> usersTweets;
    
    int timeCounter = 0;
    
    /** Initialize your data structure here. */
    public Twitter() {
        usersFollowers  =  new  HashMap<Integer, Set<Integer>>();
        usersTweets = new HashMap<Integer, LinkedList<Tweet>>();
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        
        //check if user exist, if not create user and add a empty tweet list
        if(!usersTweets.containsKey(userId))
            usersTweets.put(userId, new LinkedList<>());
        
        //create tweet and increament the tweet timestamp
        Tweet userTweet = new Tweet(timeCounter++, tweetId);  
            
        LinkedList tweets = usersTweets.get(userId);
        
        //Only keep latest 10 tweets
        if(tweets.size()==10)
            tweets.remove();
        
        //add tweet 
        tweets.add(userTweet);
        
        usersTweets.put(userId, tweets);
        
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        
        Set<Integer> followers =  usersFollowers.get(userId);
        
        PriorityQueue<Tweet> pQueue = new PriorityQueue<>((t1, t2) -> t2.time - t1.time);
        
        // loop on each follower, extract their tweets and add it to priority queue
        if(followers != null){
            for(int follower:  followers){
                if(usersTweets.containsKey(follower)){
                    for(Tweet tweet : usersTweets.get(follower)){
                        pQueue.add(tweet);
                    }
                }
            }
        }
        
        //Also add users tweets to priority queue
        if(usersTweets.containsKey(userId)){
            for(Tweet tweet : usersTweets.get(userId)){
                pQueue.add(tweet);
            }
        }
        
        ArrayList<Integer> userFeed = new ArrayList<>();
        
        //Extract latest 10 tweetsfrom the  pq and return
        while (pQueue.size() > 0 && userFeed.size() < 10) 
            userFeed.add(pQueue.poll().id);
                
        return userFeed;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(followerId == followeeId) return;
        
        if(!usersFollowers.containsKey(followerId))
            usersFollowers.put(followerId, new HashSet<>());
        
        usersFollowers.get(followerId).add(followeeId);
      
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(followerId == followeeId) return;
        
        if(!usersFollowers.containsKey(followerId))
            return;
        
        if(usersFollowers.get(followerId).contains(followeeId)){
            usersFollowers.get(followerId).remove(followeeId);
        }
        
    }
}