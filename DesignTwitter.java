/*
Time Complexity:O(logN)
Space Complexity: unsure
*/

class Twitter {

   
    HashMap<Integer, Set<Integer>> user_follower = new HashMap<>();
    //list of tweets with user ID as key
    HashMap<Integer, List<Tweet>> user_tweets = new HashMap<>();
    int timestamp;
    int feedSize = 10;


  
    class Tweet{
        int id; // Tweet id
        int createdAt;
        public Tweet(int id, int createdAt){
            this.id = id;
            this.createdAt = createdAt;
        }
    }

    /** Initialize your data structure here. */
    public Twitter() {

    }

    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) { // 4
        //put the entry in followers map
        if(!user_follower.containsKey(userId))
            user_follower.put(userId, new HashSet<Integer>());//make the user follow themselves so that his tweet is also shown in the newsfeed in addition to tweets of the people he is following

        user_follower.get(userId).add(userId); 
        if(!user_tweets.containsKey(userId)){
            user_tweets.put(userId, new ArrayList<>());
        }
        user_tweets.get(userId).add(new Tweet(tweetId, timestamp++));

    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((t1, t2) -> t1.createdAt - t2.createdAt);//sorting by timestamp
        Set<Integer> fIds = user_follower.get(userId);
        if(fIds != null){
            for(int fId: fIds){
                List<Tweet> fTweets = user_tweets.get(fId);
                if(fTweets == null) continue;
                for(Tweet t: fTweets){
                    //Case: queue is not full
                    if(pq.size() < feedSize){
                        pq.add(t);
                    }else{
                        // replace tweet
                        if(t.createdAt > pq.peek().createdAt){
                            pq.poll();
                            pq.add(t);
						}							
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0, pq.poll().id); //add the polled item at the first index to get the correct order
        }
        return result;
    }

    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!user_follower.containsKey(followerId))
            user_follower.put(followerId, new HashSet<Integer>());

        user_follower.get(followerId).add(followeeId);
    }

    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(user_follower.containsKey(followerId) && followerId != followeeId)
            user_follower.get(followerId).remove(followeeId);
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