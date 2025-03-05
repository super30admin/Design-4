class Twitter {
    class Tweet
    {
        int tid;
        int createdAt;
        public Tweet(int tid, int time)
        {
                this.tid=tid;
                this.createdAt = time;
        }
    }
private HashMap<Integer, HashSet<Integer>> followed;
private HashMap<Integer, List<Tweet>> tweets;
int time;
    public Twitter() {
        this.followed = new HashMap<>();
        this.tweets = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
       follow(userId, userId);
       if(!tweets.containsKey(userId))
       {
            tweets.put(userId, new ArrayList<>());
       }
       Tweet newTweet = new Tweet(tweetId, time);
       time++;
       tweets.get(userId).add(newTweet);
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt -b.createdAt);
        HashSet<Integer> followedIds = followed.get(userId);
        if(followedIds == null)
        return new ArrayList<>();
        for(Integer fId: followedIds)
        {
            //get the tweets of fids
            List<Tweet> fTweets = tweets.get(fId);
            if(fTweets==null)
            continue;
            for(Tweet fTweet: fTweets)
            {
                pq.add(fTweet);
                if(pq.size() > 10)
                {
                    pq.poll();
                }
            }  
        }
        List<Integer> res  = new ArrayList<>();
        while(!pq.isEmpty())
            {
                res.add(0,pq.poll().tid);
            }
        return res;
    }
    
    public void follow(int followerId, int followeeId) {
        if(!followed.containsKey(followerId))
        {
                followed.put(followerId, new HashSet<>());
        }
        followed.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(!followed.containsKey(followerId) || followerId == followeeId )
        {
                return;
        }
        else{
            followed.get(followerId).remove(followeeId);
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




/* 
Time Complexity:
postTweet: O(1)

getNewsFeed: O(f×t), where 
f is the number of followed users and 
t is the number of tweets per user. The number of tweets and users can vary,
but the process of adding them to the priority queue is effectively constant because we only keep the 10 most recent tweets.

follow: O(1)

unfollow: O(1)


Space Complexity:

postTweet: O(1) for the single tweet, but in total space for all tweets, it grows with the number of tweets.

getNewsFeed: O(1) for the priority queue and result list.

follow: O(1) for the new entry in the HashSet.

unfollow:  O(1) for the removal operation.
*/