// Time Complexity :
// Space Complexity :
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no


// Your code here along with comments explaining your approach

//Method 2 - Modified for getNewFeed method to get only last k tweets and then use them in heap tp get latest k tweets(here k = 10)
public List<Integer> getNewsFeed(int userId) {
        //Heap to retrieve top 10 tweets
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);

        //get all the users list followed by userId
        HashSet<Integer> followed_set = followed.get(userId);
        if(followed_set != null){
        for(int followedId : followed_set){
        List<Tweet> f_tweets = tweets.get(followedId);
        if(f_tweets != null){
        int size = f_tweets.size();
        for(int k = size-1; k >= 0 && k>size-11 ; k--){
        Tweet f_tweet = f_tweets.get(k);
        pq.add(f_tweet);
        if(pq.size() > 10){
        pq.poll();
        }
        }
        }
        }
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
        result.add(0,pq.poll().tid);
        }
        return result;
        }


// Method - 1
// Here , we get all the tweets from the followers list of userID and then use heap to get latest 10 tweets
class Twitter {
    class Tweet{
        int tid;
        int createdAt;
        public Tweet(int id, int time){
            this.tid = id;
            this.createdAt = time;
        }
    }

    HashMap<Integer,HashSet<Integer>> followed;
    HashMap<Integer,List<Tweet>> tweets;

    int time;

    public Twitter() {
        this.followed = new HashMap<>();
        this.tweets = new HashMap<>();
    }

    //TC: O(1)
    public void postTweet(int userId, int tweetId) {
        //follow yourself first
        follow(userId,userId);
        if(!tweets.containsKey(userId)){
            tweets.put(userId,new ArrayList<>());
        }
        //list of tweet objects - so create a new pbject and add tweetid and increase time by 1
        tweets.get(userId).add(new Tweet(tweetId , time++));
    }
    //TC: O(n) where n is the number of users
    public List<Integer> getNewsFeed(int userId) {
        //Heap to retrieve top 10 tweets
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);

        //get all the users list followed by userId
        HashSet<Integer> followed_set = followed.get(userId);
        if(followed_set != null){
            for(int followedId : followed_set){
                List<Tweet> f_tweets = tweets.get(followedId);
                if(f_tweets != null){
                    for(Tweet f_tweet : f_tweets){
                        pq.add(f_tweet);
                        //check if heap size > 10, pop it out
                        if(pq.size() > 10){
                            pq.poll();
                        }
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0,pq.poll().tid);
        }
        return result;
    }
    //TC: O(1)
    public void follow(int followerId, int followeeId) {
        if(!followed.containsKey(followerId)){
            followed.put(followerId,new HashSet<>());
        }
        followed.get(followerId).add(followeeId);
    }
    //TC: O(1)
    public void unfollow(int followerId, int followeeId) {
        if(followed.containsKey(followerId) && (followerId != followeeId)){
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
