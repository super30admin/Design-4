/**
LeetCode Submitted : YES
Space Complexity : hashmap(users and followees) and tweets object (hashMap of integers and list)
                   O(number of users * Max number of followers) , O(number of users *number of tweets).
Time Complexity :  O(1) for accessing maps and using priority queue will be O(10)

**/


class Twitter {
    
    class Tweet{
        int tweetId;
        int lastUpdated;
        
        public Tweet(int tweetId, int lastUpdated){
            this.tweetId     = tweetId;
            this.lastUpdated = lastUpdated;  
        }
    }
    
    
    class TopTweetsComparator implements Comparator<Tweet>{
            
        public int compare(Tweet t1, Tweet t2){
            if(t1.lastUpdated > t2.lastUpdated)
                return 1;
            else if(t1.lastUpdated < t2.lastUpdated)
                return -1;
                
                return 0;
        }
        
    }
    
    Map<Integer,Set<Integer>> followMap; 
    Map<Integer,List<Tweet>> tweetsMap;
    int timestamp = 0;
    
    /** Initialize your data structure here. */
    public Twitter() {
        this.followMap = new HashMap<>();
        this.tweetsMap = new HashMap<>();
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        //check user exists in follow map
        if(!followMap.containsKey(userId)){
            Set<Integer> s = new HashSet<>();
            s.add(userId);
            followMap.put(userId,s);
        }else{
            Set<Integer> s = followMap.get(userId);
            if(!s.contains(userId))
            {
                s.add(userId);
                followMap.put(userId,s);
            }
        }
        
        //Add this tweet to the tweets table/map
        //if a new tweet
        Tweet tweetObject = new Tweet(tweetId,timestamp);
        if(!tweetsMap.containsKey(userId)){
            List<Tweet> t = new ArrayList<>();
            t.add(tweetObject);
            tweetsMap.put(userId,t);
        }else{
            List<Tweet> t = tweetsMap.get(userId);
            t.add(tweetObject);
            tweetsMap.put(userId,t);
        }
        
        timestamp++;
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<Tweet>(new TopTweetsComparator());
        List<Integer> results = new ArrayList<>();
        Set<Integer> users = followMap.get(userId);
        if(users != null){
            for(Integer user : users){
                //System.out.println(user);
                List<Tweet> tweets = tweetsMap.get(user);
                if(tweets != null){
                    for(Tweet tweet : tweets){
                            //System.out.println(tweet.tweetId);
                            if(pq.size() < 10)
                                pq.add(tweet);
                            else if(pq.peek().lastUpdated < tweet.lastUpdated)
                            {
                                pq.poll();
                                pq.add(tweet);
                            }
                    }
                }
            }
        }
        //System.out.println(pq.size());
        while(!pq.isEmpty()){
            results.add(0,pq.poll().tweetId);
        }
        
        return results;
        
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(followerId == followeeId)
            return;
        
        if(!followMap.containsKey(followerId)){
            Set<Integer> s = new HashSet<>();
            s.add(followeeId);
            followMap.put(followerId,s);
        }else{
            Set<Integer> s = followMap.get(followerId);
            s.add(followeeId);
            followMap.put(followerId,s);
        }
        
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(followerId == followeeId)
            return;
        
        if(followMap.containsKey(followerId)){
            Set<Integer> s = followMap.get(followerId);
            //if(s.contains(followeeId))
            s.remove(followeeId);
            followMap.put(followerId,s);
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
