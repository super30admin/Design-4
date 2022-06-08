import java.util.*;

class Twitter {
    // Time complexity is written on each method
    // Space complexity is O(1)
    // This solution is suibmitted on leetcode with zero errors.
    class Tweet {
        int id;
        int createdAt;
        public Tweet(int id, int createdAt){
            this.id = id;
            this.createdAt = createdAt;
        }
    }
    
    // Create global usermap
    HashMap<Integer,HashSet<Integer>> usermap;
    
    // Create a global tweet map
    HashMap<Integer, List<Tweet>> tweetmap;
    
    int time;
    public Twitter() {
        this.usermap = new HashMap<>();
        this.tweetmap = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {  // TC is O(1)
        // Follow yourself to get your feeds as well in the feeds
        follow(userId,userId);
        if(!tweetmap.containsKey(userId)){
            tweetmap.put(userId, new ArrayList<>());
        }
        tweetmap.get(userId).add(new Tweet(tweetId, time++));
    }
    
    public List<Integer> getNewsFeed(int userId) { // TC is O(n) i.e the number of tweets in pq
        List<Integer> result = new ArrayList<>();
        
        // Get follower list
        HashSet<Integer> fids = usermap.get(userId);
        
        // Making priority queue to give latest ten post
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        
        if(fids!=null){
            for(int fid: fids){
                List<Tweet> fullTweets = tweetmap.get(fid);
                if(fullTweets!=null) {
                    for(Tweet t: fullTweets){
                        pq.add(t);
                        if(pq.size()>10){
                            pq.poll();
                        }
                    }
                }
            }
        }
        
        // reverse the order since peek have 10th largest time element
        while(!pq.isEmpty()){
            result.add(0,pq.poll().id);
        }
        return result;
    }
    
    public void follow(int followerId, int followeeId) { // TC is O(1)
        // create a set if user is not there
        if(!usermap.containsKey(followerId)){
            usermap.put(followerId, new HashSet<Integer>());
        }
        usermap.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) { // TC is O(1)
        // check if followeeId is there
        if(usermap.containsKey(followerId) && followerId !=followeeId){
            usermap.get(followerId).remove(followeeId);
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