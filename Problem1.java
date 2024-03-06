//# Design-4

//## Problem 1: Design Twitter (https://leetcode.com/problems/design-twitter/)

class Twitter {
    class Tweet{
        public int id;
        public int timestamp;

        public Tweet(int id, int time){
            this.id=id;
            this.timestamp=time;
        } 
    }

    // Map to store the ids of users, current user is following
    private Map<Integer, Set<Integer>> following;
    // Map to store the tweets of a certain user
    private Map<Integer, List<Tweet>> tweets;
    // Keep track of global time
    private int time;

    public Twitter() {
        this.following=new HashMap<>();
        this.tweets=new HashMap<>();
        this.time=0;
    }
    
    // Time O(1)
    public void postTweet(int userId, int tweetId) {
        // Follow self to get self-tweets
        follow(userId, userId);
        if(!tweets.containsKey(userId)){
            // First tweet of a user
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId, this.time));
        this.time=this.time+1;
    }
    
    // Time: O(n log(k)), where n is the total number of following of a user and k=10
    // Space: O(k) for the priority queue
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> result=new ArrayList<>();

        //1. Get all the followings of current user --- O(1)
        if(!following.containsKey(userId)){
            // User doesn't follow anyone and has no tweets of their own
            return result;
        }
        Set<Integer> userFollowing = following.get(userId);

        //2. Get the most recent k tweets from the users, current user is following
        // Put them one by one in a min-heap of size 10
        // Time - O(nk logk), where n is the total number of following of a user and k=10
        PriorityQueue<Tweet> pq=new PriorityQueue<>((a,b) -> a.timestamp - b.timestamp);
        for(Integer fId: userFollowing){
            List<Tweet> fTweets = tweets.getOrDefault(fId, new ArrayList<>());
            
            for(int i=fTweets.size()-1;i>=0 && i>=(fTweets.size()-10);i--){
                pq.add(fTweets.get(i));
                if(pq.size()>10){
                    pq.poll();
                }
            }

        }

        //3. Pop elements from heap one by one and add to result
        // Since we are using min-heap, the top will not represent oldest of the 10 tweets
        // We need to store the result in reverse
        // Time - O(k), where k is 10
        while(!pq.isEmpty()){
            result.add(0, pq.poll().id);
        }

        return result;
    }
    
    // Time O(1)
    // Space O(1)
    public void follow(int followerId, int followeeId) {
        if(!following.containsKey(followerId)){
            // First time following someone
            following.put(followerId, new HashSet<>());
        }
        following.get(followerId).add(followeeId);
    }
    
    // Time O(1)
    // Space O(1)
    public void unfollow(int followerId, int followeeId) {
        if(!following.containsKey(followerId)){
            // User doesn't follow anyone
            return;
        }
        following.get(followerId).remove(followeeId);
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