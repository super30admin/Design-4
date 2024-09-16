/**
 * Time: O(m*nlogk) m-no of followers and n-tweets k =10  for getFeed function and the rest O(1)
 * Space: O(m*n)
 */
class Twitter {
    class Tweet{
        int tid;
        int tstamp;
        Tweet(int tid,int tstamp){
            this.tid = tid;
            this.tstamp = tstamp;
        }
    }
    HashMap<Integer,List<Tweet>> tweets;
    HashMap<Integer, HashSet<Integer>> user;
    int cnt;
    /** Initialize your data structure here. */
    public Twitter() {
        tweets = new HashMap<>();
        user = new HashMap<>();
    }

    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {         //O(1)
        follow(userId,userId);
        if(tweets.getOrDefault(userId,null) == null)
            tweets.put(userId,new ArrayList<Tweet>());
        tweets.get(userId).add(new Tweet(tweetId,cnt++));
    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
     ["Twitter","postTweet","unfollow","getNewsFeed"]
     [[],[1,5],[1,1],[1]]
     */

    public List<Integer> getNewsFeed(int userId) {                  //O(m*nlogk) m-o of followers and n-tweets k =10
        //min heap - nlogk
        PriorityQueue<Tweet> pq = new PriorityQueue<>(
                (Tweet t1, Tweet t2) -> t1.tstamp-t2.tstamp);
        List<Integer> res = new ArrayList<>();
        if(user.containsKey(userId)){
            HashSet<Integer> foll = user.getOrDefault(userId,null);
            if(foll != null){
                for(int no : foll){
                    List<Tweet> twts = tweets.getOrDefault(no,null);
                    if(twts!=null){
                        for(Tweet i:twts){
                            pq.add(i);
                            if(pq.size() == 11) pq.poll();
                        }
                    }
                }
            }
        }
        int size = pq.size();
        while(size>0){
            res.add(0,pq.poll().tid);
            size--;
        }
        return res;
    }

    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {        //O(1)
        if(user.getOrDefault(followerId,null) == null) user.put(followerId,new HashSet<Integer>());
        user.get(followerId).add(followeeId);
    }

    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {         //O(1)
        if(followerId == followeeId) return;
        if(user.getOrDefault(followerId,null) != null)
            user.get(followerId).remove(followeeId);
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