//Time complexity O(n)
//Space complexity O(n)
//Did this code successfully run on Leetcode : Yes
//Any problem you faced while coding this : No

class Twitter {
     class Tweet{
        int tid;
        int createdAt;

        public Tweet(int tid, int createdAt){
            this.tid = tid;
            this.createdAt = createdAt;
        }
    }
    
    HashMap<Integer, Set<Integer>> followed;
    HashMap<Integer, List<Tweet>> tweet;
    private int time;

    /** Initialize your data structure here. */
    public Twitter() {
        followed = new HashMap<>();
        tweet = new HashMap<>();
        time = 0;
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        follow(userId,userId);
        if(!tweet.containsKey(userId)){

            tweet.put(userId, new ArrayList<Tweet>());
        }
        time ++;
        Tweet curr = new Tweet(tweetId,time);
        tweet.get(userId).add(curr);
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        Set<Integer> set = followed.get(userId);

        PriorityQueue<Tweet> tweetPQ = new PriorityQueue<>((a,b)->(a.createdAt)-(b.createdAt));
        if(set != null){
            for(int currUserIdSet : set){
                List<Tweet> temp = tweet.get(currUserIdSet);
                if(temp != null){
                     for(int j = 0; j < temp.size(); j ++){
                        Tweet tweetObj = temp.get(j);
                        if(tweetPQ.size() < 10){
                            tweetPQ.add(tweetObj);
                        }
                        else{
                            tweetPQ.add(tweetObj);
                            tweetPQ.poll();
                        }
                    }
               }
            }
        }

        List<Integer> result = new ArrayList<>();

        while(!tweetPQ.isEmpty()){
            result.add(0,tweetPQ.poll().tid);
        }

        return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!followed.containsKey(followerId)){
            followed.put(followerId, new HashSet<Integer>());
        }

        followed.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(!followed.containsKey(followerId) && followerId == followeeId){
            return;
        }

        Set<Integer> set = new HashSet<>();
        set = followed.get(followerId);
        set.remove(followeeId);
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
