// Time Complexity : postTweet,getNewsFeed O(1) , follow, unfollow O(n) n: number of followee
// Space Complexity : O(n2)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : yes, Took little time to think about data structure. has little issue with edge cases.


// Your code here along with comments explaining your approach
// map for all the userdId, user is following
// map of all the tweets for user. LinkedList has constant time add remove but linear time to find.
// priorityqueue will get all the latest tweets.

class Twitter {
    int timestamp=0;
    class Tweet{
        int id;
        int timestamp;
        Tweet next;
        Tweet(){};
        Tweet(int id, int timestamp){
            this.id = id;
            this.timestamp = timestamp;
        }
    }
    /** Initialize your data structure here. */
    Map<Integer,LinkedList<Integer>> follow;
    Map<Integer,Tweet> tweets;
    public Twitter() {
        follow = new HashMap<>();
        tweets = new HashMap<>();
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        if(!tweets.containsKey(userId)){
            tweets.put(userId, new Tweet());
        }
        Tweet next = tweets.get(userId).next;
        Tweet newTweet = new Tweet(tweetId, timestamp++);
        tweets.get(userId).next = newTweet;
        newTweet.next = next;
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        LinkedList<Integer> users = follow.getOrDefault(userId,new LinkedList<>());
        PriorityQueue<Tweet> q = new PriorityQueue<>((a,b) -> b.timestamp - a.timestamp);
        for(int user : users){
            //System.out.println(user);
            if(tweets.containsKey(user)) q.add(tweets.get(user).next);
        }
        if(tweets.containsKey(userId)) q.add(tweets.get(userId).next);
        List<Integer> res = new ArrayList<>();
        
        for(int i = 0; i<10;i++){
            if(!q.isEmpty()){
                Tweet t = q.poll();
                res.add(t.id);
                if(t.next != null) q.add(t.next);
            } else break;
        }
        return res;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(followerId == followeeId) return;
        if(!follow.containsKey(followerId)){
            follow.put(followerId,new LinkedList<>());
        }
        if(!follow.get(followerId).contains(followeeId)){
            follow.get(followerId).add(followeeId);
        }
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(follow.containsKey(followerId)){
            follow.get(followerId).remove((Integer)followeeId);
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