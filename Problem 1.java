//Time Complexity: postTweet() : O(1), getNewsFeed : O(N log K) where N is the total number of tweets and K is the number
//of tweets that we want to display, follow() : O(1), unfollow() : O(1)

//Space Complexity: O(K) for priority queue

//Successfully runs on leetcode: 40 ms

//Approach: Maintain two hashmaps: One of User -> User, second for User -> Tweets. Maintain a priority queue to give the K
//most recent tweets.


class Twitter {

    /** Initialize your data structure here. */
    class Tweet
    {
        int tid;
        int createdAt;
        
        public Tweet(int tid, int createdAt)
        {
            this.tid = tid;
            this.createdAt = createdAt;
        }
    }
    
    HashMap<Integer, Set<Integer>> followerMap;
    HashMap<Integer, List<Tweet>> tweetMap;
    int time;
    public Twitter() {
        followerMap = new HashMap<>();
        tweetMap = new HashMap<>();
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        follow(userId,userId);
        Tweet t = new Tweet(tweetId,time);
        time++;
        if(!tweetMap.containsKey(userId))
        {
            tweetMap.put(userId, new ArrayList<>());
        }
        tweetMap.get(userId).add(t);
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> result = new ArrayList<>();
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        Set<Integer> set = followerMap.get(userId);
        if(set != null)
        {
            for(Integer fid: set)
            {
                List<Tweet> ts = tweetMap.get(fid);
                if(ts != null)
                {
                    for(Tweet tsi: ts)
                    {
                        pq.add(tsi);
                        if(pq.size() > 10)
                            pq.poll();
                    }
                        
                }
            }
        }
        while(!pq.isEmpty())
            result.add(0, pq.poll().tid);
        return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!followerMap.containsKey(followerId))
        {
            followerMap.put(followerId, new HashSet<>());
        }
        followerMap.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(followerMap.containsKey(followerId) && followerId != followeeId)
            followerMap.get(followerId).remove(followeeId);            
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