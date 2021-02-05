Time Complexity: o(n log(k))
Space Complexity: o(n)
class Twitter {
    HashMap<Integer, Set<Integer>> ftable;
    HashMap<Integer, List<tweets>> ptable;
    int count;
    class tweets {
        int id;
        int time;
        public tweets(int id, int time)
        {
            this.id = id;
            this.time = time;
        }
    }
    /** Initialize your data structure here. */
    public Twitter() {
        ftable = new HashMap<>();
        ptable = new HashMap<>();      
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        if(!ptable.containsKey(userId))
        {
            if(!ftable.containsKey(userId)) ftable.put(userId, new HashSet<>());
            ptable.put(userId, new ArrayList<>());
            ptable.get(userId).add(new tweets(tweetId, count++));
        }
        else{
            ptable.get(userId).add(new tweets(tweetId, count++));
        }
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> res = new ArrayList<>();
        if(!ftable.containsKey(userId)) return res;
        Set<Integer> followees = ftable.get(userId);
        follow(userId, userId);
        PriorityQueue<tweets> pq = new PriorityQueue<>((a,b)->a.time-b.time);
        for(int i: followees)
        {
            List<tweets> posts = ptable.get(i);
            if(posts == null) continue;
            for(tweets val: posts)
            {
                int tweetid = val.id;
                int time = val.time;
                pq.add(val);
                if(pq.size() > 10)
                {
                    pq.poll();
                }
            }
        }
        while(!pq.isEmpty())
        {
            res.add(0,pq.poll().id);
        }
        unfollow(userId, userId);
        
        return res;
        
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!ftable.containsKey(followerId))
        {
            ftable.put(followerId, new HashSet<>());
            ftable.get(followerId).add(followeeId);
        }
        else
        {
            Set<Integer> fetable = ftable.get(followerId);
            if(!fetable.contains(followeeId))
            {
                fetable.add(followeeId);
            }
        }
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(!ftable.containsKey(followerId) || !ftable.get(followerId).contains(followeeId))
        {
            return;
        }
        else
        {
            Set<Integer> fetable = ftable.get(followerId);
            fetable.remove(followeeId);
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