// Time Complexity : O(1) for everything else except getNewsFeed() for O(mn)
// Space Complexity : O(mn)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no

class Twitter {

    class Tweet {
        int time;
        int tweetId;
        
        Tweet(int tweetId, int time) {
            this.tweetId = tweetId;
            this.time = time;
        }
    }
    
    Map<Integer, Set<Integer>> userMap;
    Map<Integer, List<Tweet>> tweetMap;
    int time;
    public Twitter() {
        userMap = new HashMap<>();
        tweetMap = new HashMap<>();
        time = 0;
    }
    
    public void postTweet(int userId, int tweetId) {
        
        if(!tweetMap.containsKey(userId)) {
            tweetMap.put(userId, new ArrayList<>());
        }
        
        Tweet tweet = new Tweet(tweetId, time++);
        
        tweetMap.get(userId).add(tweet);
    }
    
    public List<Integer> getNewsFeed(int userId) {
        follow(userId, userId);
        
        Set<Integer> followers = userMap.get(userId);
        
        PriorityQueue<Tweet> minHeap = new PriorityQueue<>((a, b) -> a.time - b.time);
        
        for(Integer follower: followers) {
            
            List<Tweet> tweets = tweetMap.get(follower);
            
            if(tweets != null) {
               
                for(Tweet tweet: tweets) {
                    minHeap.add(tweet);
                    
                    if(minHeap.size() > 10) {
                        minHeap.poll();
                    }
                }
                
                
            }
        }
        
        List<Integer> result = new ArrayList<>();
        
        while(!minHeap.isEmpty()) {
            result.add(0, minHeap.poll().tweetId);
        }
        
        return result;
    }
    
    public void follow(int followerId, int followeeId) {
        
        if(!userMap.containsKey(followerId)) {
            userMap.put(followerId, new HashSet<>());
        }
        
        userMap.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        
        if(userMap.containsKey(followerId) && followerId != followeeId) {
            Set<Integer> followers = userMap.get(followerId);
            
            followers.remove(followeeId);
            userMap.put(followerId, followers);
        }
    }
}