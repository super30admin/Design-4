/**
Time Complexity: postTweet()-O(1), follow()-O(1), unfollow()-O(1),
                getNewsFeed()-O(f*t)
                where f = avg num of followee for the userId
                      t = avg num of tweets for each followee
Space Complexity: O(max(m*f, f*t))
                where m=avg num of userId,
                      f=avg num of followee for each userId,
                      t= avg num of tweets for each followee
Did it run successfully on leetcode: yes
**/
class Twitter {
    //creating a class for tweet
    class Tweet{
        //id for tweet
        int tid;
        //tweet creation time
        int createdAt;
        //constructor
        public Tweet(int tid, int createdAt){
            this.tid = tid;
            this.createdAt = createdAt;
        }
    }
    //maps userId and the people(userIds) it is following
    HashMap<Integer, Set<Integer>> userToFollowee;
    //maps userId and all of its tweets
    HashMap<Integer, List<Tweet>> userToTweet;
    //time to keep track of tweet time
    int time;
    /** Initialize your data structure here. */
    public Twitter() {
        this.userToFollowee = new HashMap<>();
        this.userToTweet = new HashMap<>();
        this.time = 0;
    }

    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        //if it is the first tweet of userId
        if(!userToTweet.containsKey(userId)){
            //add userId to map
            userToTweet.put(userId, new ArrayList<>());
        }
        //get tweet list of userId
        List<Tweet> tweets = userToTweet.get(userId);
        //increement current time
        this.time = this.time+1;
        //create a new tweet with given tweetId and new time
        Tweet t = new Tweet(tweetId, this.time);
        //add tweet to the tweet list of userId
        tweets.add(t);
    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        //userId should always follow itself
        if(!userToFollowee.containsKey(userId) || !userToFollowee.get(userId).contains(userId)){
            follow(userId, userId);
        }

        //min heap to store 10 tweets in sorted order
        PriorityQueue<Tweet> q = new PriorityQueue<>((a,b)->a.createdAt-b.createdAt);
        //if userId exists
        if(userToFollowee.containsKey(userId)){
            //get set of all the followees that userId follow
            Set<Integer> set = userToFollowee.get(userId);
            //set is not empty
            if(set!=null && set.size()>0){
                //loop through all the followees
                for(int followeeId: set){//O(m) where m = avg # of followees
                    //get tweet list of current followees
                    List<Tweet> tweets = userToTweet.get(followeeId);
                    //if tweet list is not empty
                    if(tweets!=null && tweets.size()>0){
                        //loop through all the tweet list
                        for(Tweet t: tweets){//O(t), where t= Avg # of tweets for each followee
                            //add tweet to min head
                            q.add(t);
                            //if min heap size is greater than 10
                            if(q.size()>10){
                                //remove top elements from min heap
                                q.poll();
                            }
                        }
                    }
                }
            }
        }
        //new list to store result
        List<Integer> res = new ArrayList<>();
        //until list is empty
        while(!q.isEmpty()){
            //add tweet id to start of list
            res.add(0, q.poll().tid);
        }
        return res;
    }

    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        //if followerId not in map
        if(!userToFollowee.containsKey(followerId)){
            //add followerId to map
            userToFollowee.put(followerId, new HashSet<Integer>());
        }
        //get set of all the followees followed by followerId
        Set<Integer> set = userToFollowee.get(followerId);
        //add followeeId to set
        set.add(followeeId);
    }

    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        //if followerId exits in map and followerId is not same as followeeId
        if(userToFollowee.containsKey(followerId) && followerId!=followeeId){
            //get set of all followees of followerId
            Set<Integer> set = userToFollowee.get(followerId);
            //if set contains followeeId
            if(set.contains(followeeId)){
                //remove followeeId from set
                set.remove(followeeId);
            }
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