// Time Complexity : O(n) getTweets for  O(1) for remaining
// Space Complexity : O(n) for storing users and tweets on avrg
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach
class Twitter {
    
    class Tweet{
        int id;
        int createdAt;
        public Tweet(int id, int time){
            this.id = id;
            this.createdAt = time;
        }
    }

    HashMap<Integer, HashSet<Integer>> userMap ;
    HashMap<Integer, List<Tweet>> tweetMap;
    int time;

    public Twitter() {
        this.userMap = new HashMap<>();
        this.tweetMap = new HashMap<>();

    }
    
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);   // for Newsfeed, getting curr user latestfeeds also
        if(!tweetMap.containsKey(userId)){
            tweetMap.put(userId, new ArrayList<>());
        }
        tweetMap.get(userId).add(new Tweet(tweetId, time++));
    }
    
    public List<Integer> getNewsFeed(int userId) {
        
        Set<Integer> followers = userMap.get(userId); // get followers list
        PriorityQueue<Tweet> minHeap = new PriorityQueue<>((a,b)-> a.createdAt-b.createdAt);   // final 10 max tweet
        if(followers!=null){
            for(int follower : followers){ // for each follower
                List<Tweet> tweets = tweetMap.get(follower); //get tweets 
                if(tweets!=null){
                    int size = tweets.size(); 
                    for(int i= size-1; i>=0 && i>size-10; i-- ){  // get only top 10 tweets from end
                        minHeap.add(tweets.get(i));   // add them to minHeap
                        if(minHeap.size()>10)
                            minHeap.poll();     // pop all old  tweets as they less value (time)
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>(); //recent 10 with max time values are left in minHeap
        while(!minHeap.isEmpty())
            result.add(0,minHeap.poll().id);  // since minHeap add max elements to starting of list
            return result;
        }
        
    
    
    public void follow(int followerId, int followeeId) {
        if(!userMap.containsKey(followerId)){
            userMap.put(followerId, new HashSet<>());
        }
        userMap.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(userMap.containsKey(followerId) && followerId!=followeeId){
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