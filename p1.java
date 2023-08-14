// Time Complexity :
//postTweet O(1)
//getNewsFeed O(n*k) where n is average number of followed ID and k is average number of tweets per ID
//follow O(1)
//unfollow O(1)

// Space Complexity :
//postTweet O(1)
//getNewsFeed O(1) constant space since we would only be requiring priority queue of size 10
//follow O(1)
//unfollow O(1)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach


class Twitter {
    
    int time;
    HashMap<Integer, User> userList;
    //User class to get user objects
    class User{
        int userId;
        HashSet<Integer> following;
        HashSet<Integer[]> tweets;
        public User(int userId){
            this.userId = userId;
            this.following = new HashSet<>();
            this.tweets = new HashSet<>();
            this.following.add(userId);
        }
    }
    public Twitter() {
        this.userList = new HashMap<>();
    }
    
    //create user if user is not there and add tweets to the user
    public void postTweet(int userId, int tweetId) {
        if(!userList.containsKey(userId)){
            User user = new User(userId);
            this.userList.put(userId, user);
        }
        this.userList.get(userId).tweets.add(new Integer[]{this.time, tweetId});
        time++;
    }
    
    //Get the user from userID, then get the followed users from user. Get all the tweets from the users and add it to the min priority queue. The size of the queue would be 10 since we need only 10 tweets
    public List<Integer> getNewsFeed(int userId) {
        if(!userList.containsKey(userId)) return new ArrayList<>();
        HashSet<Integer> temp = userList.get(userId).following;
        PriorityQueue<Integer[]> pq = new PriorityQueue<>((a,b) -> (
            a[0]-b[0]
        ));
        for(Integer i: temp){
            User u = userList.get(i);
            
            if(u.tweets != null){
                HashSet<Integer[]> tweets = u.tweets;
                for(Integer[] t: tweets){
                pq.add(t);
                if(pq.size() == 11){
                    pq.poll();
                }
            }
            }
            
            


        }

        List<Integer> result = new ArrayList<>();
        while(pq.peek()!=null){
            result.add(0, pq.poll()[1]);
        }
        return result;
    }
    
    //Create the users if users are not there and then add the followeeId to the followed of user
    public void follow(int followerId, int followeeId) {
        if(!userList.containsKey(followerId)){
            User user = new User(followerId);
            this.userList.put(followerId, user);
        }
        if(!userList.containsKey(followeeId)){
            User user = new User(followeeId);
            this.userList.put(followeeId, user);
        }
        userList.get(followerId).following.add(followeeId);
    }
    
    //Remove the followeeId from followed of user
    public void unfollow(int followerId, int followeeId) {
        userList.get(followerId).following.remove(followeeId);
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