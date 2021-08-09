// Time Complexity :
// Space Complexity :
// Did this code successfully run on Leetcode :No
// Any problem you faced while coding this :Yes
//Could not figure out the logic to create minheap


// Your code here along with comments explaining your approach
class Twitter {
          
    
    HashMap<Integer, List<Integer>> follow;
    HashMap<Integer, List<Integer>> user;
    /** Initialize your data structure here. */
    public Twitter() {
        follow= new HashMap<>();
        user= new HashMap<>();
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        if(user.contains(userId)){
             user.get(userId).add(tweetId);
        }
        else
        {
            user.put(userId,new ArrayList<>(tweetId));
        }
            
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        return user.get(userId);
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(follow.contains(followeeId)){
            follow.get(followeeId).add(followeerId);
        }
        else
        {
            follow.put(followeeId,new ArrayList<>(followerId));
        }
        
       user.put(followerId,heapify(follow.get(followeeId)));
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        
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