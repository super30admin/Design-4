//Time Complexity :O(1) for posting a tweet, follow, unfollow. O(N) for getNewsFeed
//Space Complexity :O(1)
//Did this code successfully run on Leetcode :yes
//Any problem you faced while coding this :Nope


//Your code here along with comments explaining your approach

class Twitter {
    int currentTimeStamp;
    Map<Integer,Queue<Tweet>> map;
    Map<Integer,Set<Integer>> followersMap;
    /** Initialize your data structure here. */
    public Twitter() {
        map = new HashMap<>();
        followersMap = new HashMap<>();
        currentTimeStamp = 0;
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        currentTimeStamp++;
        Queue<Tweet> q = map.getOrDefault(userId,new LinkedList<>());
        if(q.size() >= 10){
            q.poll();
        }
        q.offer(new Tweet(currentTimeStamp,tweetId));
        map.put(userId,q);
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        currentTimeStamp++;
        List<Tweet> result = new ArrayList<>();
        Queue<Tweet> q = map.getOrDefault(userId, new LinkedList<>());
        getNewsFeed(q,result);
        Set<Integer> set = followersMap.getOrDefault(userId, new HashSet<>());
        Iterator<Integer> it = set.iterator();
        while(it.hasNext()){
            q = map.getOrDefault(it.next(), new LinkedList<>());
            getNewsFeed(q,result);
        }
        Collections.sort(result, new Comparator<Tweet>(){
            public int compare(Tweet t1, Tweet t2){
                return t2.timeStamp - t1.timeStamp;
            }
        });
        List<Integer> res = new ArrayList<>();
        Set<Integer> newSet = new HashSet<>();
        for(int i = 0; i < result.size() && res.size() < 10; i++){
            if(!newSet.contains(result.get(i).tweetId)){
             res.add(result.get(i).tweetId);   
            }
            newSet.add(result.get(i).tweetId);
        }
        return res;
    }
                
    public void getNewsFeed(Queue<Tweet> q, List<Tweet> result){
        int size = q.size();
        for(int i = 0; i < size; i++){
            Tweet val = q.poll();
            result.add(val);
            q.offer(val);
        }
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        Set<Integer> set = followersMap.getOrDefault(followerId, new HashSet<>());
        set.add(followeeId);
        followersMap.put(followerId,set);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        Set<Integer> set = followersMap.getOrDefault(followerId, new HashSet<>());
        set.remove(followeeId);
        followersMap.put(followerId,set);
    }
}

class Tweet{
    public int timeStamp, tweetId;
    public Tweet(int timeStamp,int tweetId){
        this.timeStamp = timeStamp;
        this.tweetId = tweetId;
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