// Time Complexity : O(nk) for. o(1) for other functions
// Space Complexity : O(n) - maps of users and tweets
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach
class Twitter {

    class Tweet{
        int tweetId;
        int createdAt;
        public Tweet(int tweetId, int createdAt){
            this.tweetId = tweetId;
            this.createdAt = createdAt;
        }
    }
    HashMap<Integer, HashSet<Integer>> userMap;
    HashMap<Integer, List<Tweet>> tweetMap;
    int time;

    public Twitter() {
        userMap = new HashMap<>();
        tweetMap = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) { // O(1)
        if(!tweetMap.containsKey(userId)){
            tweetMap.put(userId, new ArrayList<>());
        }
        tweetMap.get(userId).add(new Tweet(tweetId, time++));
    }
    
    public List<Integer> getNewsFeed(int userId) { // O(nK) - n - users, k - tweets
        follow(userId, userId);
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        HashSet<Integer> allUsers = userMap.get(userId); 

        for(Integer user : allUsers){
            List<Tweet> allTweets = tweetMap.get(user);
            if(allTweets != null){
                for(Tweet tw : allTweets){
                    pq.add(tw);  // Ignoring constant time to insert tweet in list of 10 constant tweets
                    if(pq.size() > 10){
                        pq.poll();
                    }
                }
            }
        }
        List<Integer> res = new ArrayList<>();
        while(!pq.isEmpty()){
            res.add(0, pq.poll().tweetId); //save most recent tweet from start index 
        }
        return res;
    }
    
    public void follow(int followerId, int followeeId) { // O(1)
        if(!userMap.containsKey(followerId)){
            userMap.put(followerId, new HashSet<>());
        }
        userMap.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) { // O(1)
        if(!userMap.containsKey(followerId) || followerId == followeeId){
            return;
        }
        if(userMap.get(followerId).contains(followeeId)){
            userMap.get(followerId).remove(followeeId);
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