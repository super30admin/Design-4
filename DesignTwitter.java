
/**
 * Time Complexity : For each tweet, postTweet = O(1), follow = O(1), unfollow = O(1),
 * getNewsFeed = O(nm +  mlog k)  where m is the number of tweets and n is the number of users, k is the size of heap
 * Space Complexity : O(n + n) for Users Hashmap and O(n + m) where n is the number of users and m is number of tweet objects for tweet HashMap.
 */


class Twitter {

    class Tweet{
        int id;
        int createdAt;

        public Tweet(int tweetId, int timestamp){
            this.id = tweetId;
            this.createdAt = timestamp;
        }

    }

    HashMap<Integer, HashSet<Integer>> following;
    HashMap<Integer, List<Tweet>> tweets;
    int feedsize, timestamp;

    /** Initialize your data structure here. */
    public Twitter() {
        following = new HashMap<>();
        tweets = new HashMap<>();
        this.feedsize = 10;

    }

    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if(!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId, timestamp++));
    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((t1, t2) -> t1.createdAt - t2.createdAt);
        Set<Integer> fIds = following.get(userId);
        if(fIds!=null){
            for(Integer fId : fIds){
                List<Tweet> fTweets = tweets.get(fId);
                if(fTweets != null){
                    for(Tweet tweet : fTweets){
                        pq.add(tweet);
                        if(pq.size() > feedsize){
                            pq.poll();
                        }
                    }
                }
            }
        }

        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            Tweet tweet = pq.poll();
            result.add(0, tweet.id);
        }
        return result;
    }

    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!following.containsKey(followerId)){
            following.put(followerId, new HashSet<>());
        }
        following.get(followerId).add(followeeId);
    }

    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {

        if(following.containsKey(followerId) && followerId!=followeeId){
            following.get(followerId).remove(followeeId);
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
