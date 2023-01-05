// Design Twitter

// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : -


// Your code here along with comments explaining your approach

class Twitter {

    class Tweet{
        int id;
        int createdAt;
        
        public Tweet(int tweetId, int createdAt){
            this.id = tweetId;
            this.createdAt = createdAt;
        }
    }

    HashMap<Integer, HashSet<Integer>> followed; // User: List of all the users this user follows, including self
    HashMap<Integer, List<Tweet>> tweets;
    int time;

    public Twitter() {
        this.followed = new HashMap<>();
        this.tweets = new HashMap<>();
    }
    
    // Time Complexity : O(1)
    // Space Complexity : O(1)
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId); //Follow self
        if(!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<>());
        }
        Tweet newTweet = new Tweet(tweetId, time);
        time++;
        tweets.get(userId).add(newTweet);
    }
    
    // Time Complexity : O(m)
    // Space Complexity : O(m)
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        List<Integer> result = new ArrayList<>();
        HashSet<Integer> followeds = followed.get(userId);
        if(followeds == null)
            return result;
        for(int fid: followeds){
            List<Tweet> ftweets = tweets.get(fid);
            if(ftweets != null){
                int size = ftweets.size();
                int min = Math.min(size, 10);
                for(int i = size - 1; i >= size - min; i--){
                    pq.add(ftweets.get(i));
                    if(pq.size() > 10){
                        pq.poll();
                    }
                }
            }
        }
        while(!pq.isEmpty()){
            result.add(0, pq.poll().id);
        }
        return result;
    }
    
    // Time Complexity : O(1)
    // Space Complexity : O(1)
    public void follow(int followerId, int followeeId) {
        if(!followed.containsKey(followerId)){
            followed.put(followerId, new HashSet<>());
        }
        followed.get(followerId).add(followeeId);
    }
    
    // Time Complexity : O(1)
    // Space Complexity : O(1)
    public void unfollow(int followerId, int followeeId) {
        if(!followed.containsKey(followerId) || followerId == followeeId)
            return;
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