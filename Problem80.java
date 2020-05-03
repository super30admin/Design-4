/**
 * Space Complexity - O(n+k) = number of users and k tweets
 */
class Twitter {

    class Tweet {
        int id, createdAt;
        public Tweet(int _id, int _createdAt){
            id = _id;
            createdAt = _createdAt;
        }
    }

    Map<Integer, List<Tweet>> tweets;
    Map<Integer, Set<Integer>> followers;
    int timestamp;
    int maxFeed=10;
    /** Initialize your data structure here. */
    public Twitter() {
        tweets = new HashMap<>();
        followers = new HashMap<>();
        timestamp=0;
    }

    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) { // O(1)

        if(!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId,timestamp++));
        if(!followers.containsKey(userId)){
            followers.put(userId, new HashSet<>());
        }
        followers.get(userId).add(userId);
    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) { // O(n*k) number of followees and k number of tweets

        PriorityQueue<Tweet> minHeap = new PriorityQueue<>((tweet1, tweet2) -> tweet1.createdAt - tweet2.createdAt);

        if(followers.containsKey(userId)){
            for(int followee : followers.get(userId)){
                if(tweets.containsKey(followee)){
                    for(Tweet tweet : tweets.get(followee)){

                        if(minHeap.size() >= maxFeed){

                            if(minHeap.peek().createdAt < tweet.createdAt){
                                minHeap.poll();
                                minHeap.add(tweet);
                            }
                        }else{
                            minHeap.add(tweet);
                        }
                    }
                }
            }
        }

        List<Integer> result = new ArrayList<>();
        while(!minHeap.isEmpty()){
            result.add(0,minHeap.poll().id);
        }
        return result;
    }

    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {    // O(1)
        if(!followers.containsKey(followerId)){
            followers.put(followerId, new HashSet<>());
        }
        followers.get(followerId).add(followeeId);
    }

    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {  // O(1)

        if(!followers.containsKey(followerId) || followerId == followeeId)
            return;
        followers.get(followerId).remove(followeeId);
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