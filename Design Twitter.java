class Twitter {

    HashMap<Integer,Set<Integer>> map;
    HashMap<Integer,List<TweetNode>> tweetmap;
    int timestamp = 0;

    /** Initialize your data structure here. */
    public Twitter() {

        map = new HashMap<>();
        tweetmap = new HashMap<>();
    }

    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {

        follow(userId,userId);

        if(!tweetmap.containsKey(userId)) tweetmap.put(userId , new ArrayList<TweetNode>());

        TweetNode tweet = new TweetNode(tweetId,timestamp++);
        tweetmap.get(userId).add(tweet);

    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId)
    {
        PriorityQueue<TweetNode> q = new PriorityQueue<>((t1,t2) -> t1.created - t2.created);

        Set<Integer> followers = map.get(userId);

        if(followers!=null)
        {
             for(int fid : followers)
             {
                 List<TweetNode> tweets = tweetmap.get(fid);

                 if(tweets!=null)
                 {
                     for(TweetNode tweet : tweets)
                     {
                         q.add(tweet);

                         if(q.size()>10) q.poll();
                     }
                 }
             }
        }

        List<Integer> res = new ArrayList<>();

        while(!q.isEmpty())
        {
            res.add(0,q.poll().tweetid);
        }

        return res;
    }

    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {

        if(!map.containsKey(followerId)) map.put(followerId,new HashSet<>());
        map.get(followerId).add(followeeId);
    }

    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {

         if(map.containsKey(followerId) && followerId!=followeeId) map.get(followerId).remove(followeeId);
    }
}

class TweetNode
{
    int tweetid;
    int created;

    public TweetNode(int tweetid , int created)
    {
        this.tweetid = tweetid;
        this.created = created;
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
