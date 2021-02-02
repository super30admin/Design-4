class Twitter {

    /** Initialize your data structure here. */
    PriorityQueue<TweetPriority> pq;// for user id and value is tweets according to priority
    HashMap<Integer, List<Integer>> hm2; // for user id and value is lists of followers
    public Twitter() {
        pq = new PriorityQueue<>((a,b)->(b.tweet_priority - a.tweet_priority));
        hm2 = new HashMap<>();
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        TweetPriority tp = new TweetPriority(userId, tweetId, pq.size()+1);
        pq.add(tp);
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> res = new ArrayList<>();
        List<Integer> user = hm2.getOrDefault(userId, new ArrayList<>());
        PriorityQueue<TweetPriority> pq1 = new PriorityQueue<>(pq);
        user.add(userId);
        while(res.size()<10 && pq1.size()>0){
            TweetPriority tp = pq1.poll();
            if(user.contains(tp.user_id)){
                res.add(tp.tweet_id);
            }
        }
        return res;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(hm2.containsKey(followerId)){
            List<Integer> l = hm2.get(followerId);
            if(!l.contains(followeeId)){
                l.add(followeeId);
                hm2.put(followerId, l);
            }
        }
        else{
            List<Integer> l = new ArrayList<>();
            l.add(followeeId);
            hm2.put(followerId, l);
        }
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(hm2.containsKey(followerId)){
            List<Integer> l = hm2.get(followerId);
            if(l!=null && l.contains(followeeId)){
                l.remove(new Integer(followeeId));
                hm2.put(followerId, l);
            }
        }
    }
}

class TweetPriority {
    int user_id;
    int tweet_id;
    int tweet_priority;
    
     TweetPriority(int user_id, int tweet_id, int tweet_priority){
        this.user_id = user_id;
        this.tweet_id = tweet_id;
        this.tweet_priority = tweet_priority;
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

//Time complexity : for follow,unfollow,post a tweet -> O(1), for get news feed it is O(t) where t is the total number of tweets.
//Space complexity : O(n+t) n is the number of users + t is the number of tweets
