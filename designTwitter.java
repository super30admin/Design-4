//Space Complexity:O(N*max(M,T)) where N is the number of users, M is the average number of followers and T is the average number of tweets.
//In this problem, I'll be having two separate classes, one for the tweets, which I'll be maintaining using singly linked list and the users which I'll be maintaining using hashmap and their followers list in a hashset. I'll be initializing the constructor for tweets class in such a way that every time a user posts a tweet, I'll be incrementing a corresponding time tamp, which will help me while displaying the newsfeed. I'll be having helper functions such as follow, unfollow and post tweets. The newfeed display is carried out using a max heap, which will take in the tweets of both the user's as well as the users followed by the users in the order in which they were posted. and displaying only the top 10 most recent tweets. 
//This code was executed successfully and got accepted in leetcode. 
class Twitter {
    public static int timestamp=0;
    //Tweet class
    class Tweet{
        int id;
        int time;
        public Tweet next;
        public Tweet(int id){
            this.id=id;
            time=timestamp++;
            next=null;
        }
    }
    //User class
    class User{
        int id;
        Tweet tweet_head;
        Set<Integer> followers;
        public User(int id){
            this.id=id;
            followers=new HashSet<>();
            follow(id);
            tweet_head=null;
        }
        //Function to add followers
        public void follow(int id){
            followers.add(id);
        }
        //Function to unfollow. 
        public void unfollow(int id){
            followers.remove(id);
        }
        //function to post tweets
        public void post(int id){
            Tweet t=new Tweet(id);
            t.next=tweet_head;
            tweet_head=t;
        }
    }
    Map<Integer,User> userMap;
    /** Initialize your data structure here. */
    public Twitter() {
        userMap=new HashMap<>();
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        if(!userMap.containsKey(userId)){
            User u=new User(userId);
            userMap.put(userId,u);
        }
        userMap.get(userId).post(tweetId);
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> res=new ArrayList<>();
        if(!userMap.containsKey(userId)){
            return res;
        }
        Set<Integer> users=userMap.get(userId).followers;
        PriorityQueue<Tweet> q=new PriorityQueue<>(users.size(),(a,b)->(b.time-a.time));//max heap
        for(int user:users){
            Tweet t = userMap.get(user).tweet_head;
            if(t!=null){
                q.add(t);
            }
        }
        int n=0;
        while(!q.isEmpty()&&n<10){
            Tweet t=q.poll();
            res.add(t.id);
            n++;
            if(t.next!=null){
                q.add(t.next);
            }
        }
        return res;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!userMap.containsKey(followerId)){
            User u=new User(followerId);
            userMap.put(followerId,u);
        }
        if(!userMap.containsKey(followeeId)){
            User u=new User(followeeId);
            userMap.put(followeeId,u);
        }
        userMap.get(followerId).follow(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(!userMap.containsKey(followerId)||followerId==followeeId){
            return;
        }
        userMap.get(followerId).unfollow(followeeId);
    }
}
