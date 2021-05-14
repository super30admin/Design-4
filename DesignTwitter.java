//Space complexity: O(n) for each of the hashtables
class Twitter {
    
    class Tweet{
        int tweetId;
        int time;
        public Tweet(int tweetId){
            this.tweetId = tweetId;
            this.time = timestamp++;
        }
    }
    
    public int timestamp=0;

    Map<Integer, Set<Integer>> followers;
    Map<Integer,Deque<Tweet>> tweets;
    /** Initialize your data structure here. */
    public Twitter() {
        followers = new HashMap();
        tweets = new HashMap();
    }
    
    /** Compose a new tweet. */
    //TC: O(1)
    public void postTweet(int userId, int tweetId) {
        Tweet t= new Tweet(tweetId);
        Deque<Tweet> tweetsQueue = tweets.getOrDefault(userId, new ArrayDeque());
        tweetsQueue.addFirst(t);
        tweets.put(userId, tweetsQueue);
        followers.putIfAbsent(userId,new HashSet());
        Set<Integer> set = followers.get(userId);
        if(!set.contains(userId)){
            set.add(userId);
            followers.put(userId, set);
        }
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    //TC: O(nlogn) n is the total number of  followees for userId; PQ will be of size n*10 and there will be logn operation i.e. insert in pq n*10 times ==> n*10 * logn
    public List<Integer> getNewsFeed(int userId) {
        if(!followers.containsKey(userId)) return new ArrayList();
        PriorityQueue<Tweet> pq = new PriorityQueue<Tweet>((a,b) -> b.time - a.time);
        for(int followee : followers.get(userId)){
            if(!tweets.containsKey(followee)) continue;
            Deque<Tweet> tweetsqueue = new ArrayDeque(tweets.get(followee));
            int i=11;
            while(!tweetsqueue.isEmpty() && i>=0) {
                pq.add(tweetsqueue.removeFirst());
                i--;
            }
        }
        List<Integer> newsfeed=new ArrayList();
        int k=10;
        while(!pq.isEmpty() && k>0){
            newsfeed.add(pq.poll().tweetId);
            k--;
        }
        return newsfeed;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    //TC: O(1)
    public void follow(int followerId, int followeeId) {
        Set<Integer> set = followers.getOrDefault(followerId, new HashSet());
        set.add(followeeId);
        followers.put(followerId, set);
        /*
        followers.putIfAbsent(followeeId,new HashSet());
        Set<Integer> set = followers.get(followeeId);
        set.add(followerId);
        followers.put(followeeId, set);
        */
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    //TC: O(1)
    public void unfollow(int followerId, int followeeId) {
        Set<Integer> set = followers.get(followerId);
        set.remove(followeeId);
        followers.put(followerId, set);
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