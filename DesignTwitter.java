/* 355. Design Twitter
Time Complexity: getNewsFeed - O(log n) operation to insert and delete element- Priority Queue - Heap (MaxHeap and MinHeap)
*/

class Twitter {

    //Data memebers
    //user - id, set of users they follow, user and their followers
    HashMap<Integer, Set<Integer>> user_follower = new HashMap<>(); //{}
    // User with list of tweets, list of tweets mapped to a user ID 
    HashMap<Integer, List<Tweet>> user_tweets = new HashMap<>(); //{1: [t1,t2,t4]};
    int timestamp;
    int feedSize = 10;


    //Tweet class for the tweet
    class Tweet{
        int id; // T1
        int createdAt; // timestamp of when the tweet was created, simpler code, so int here
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
            user_follower.put(userId, new HashSet<Integer>());

        //make it follow itself
        user_follower.get(userId).add(userId);  //HashSet, so

        //follow(userId, userId);  -- just call this function instead, alternative

        //Add tweet to HashMap of Tweets
        if(!user_tweets.containsKey(userId)){
            user_tweets.put(userId, new ArrayList<>());
        }
        user_tweets.get(userId).add(new Tweet(tweetId, timestamp++));

    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        //Pririty Queue, heap of tweets, comparator of two tweets and sort them by their timestamps, custom comparator
        PriorityQueue<Tweet> pq = new PriorityQueue<>((t1, t2) -> t1.createdAt - t2.createdAt);
        //people i am following
        Set<Integer> fIds = user_follower.get(userId);// [2,3,1]
        //edge case check
        if(fIds != null){
            for(int fId: fIds){
                List<Tweet> fTweets = user_tweets.get(fId);
                if(fTweets == null) continue;
                for(Tweet t: fTweets){
                    //case1 priority queue is not full
                    if(pq.size() < feedSize){
                        pq.add(t);
                    }else{
                        // add to pq if tweet coming in is recent than the top tweet
                        if(t.createdAt > pq.peek().createdAt){
                            pq.poll();
                            pq.add(t);
                        } // ascending order [1,2,3] 3 is more recent    
                    }
                }
            }
        }
        //give out a list of integers
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0, pq.poll().id); //why 0? whatever is polled out, always add at first index
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