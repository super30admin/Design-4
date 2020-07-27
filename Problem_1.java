// Time complexity - 1. Post Tweet - O(1), 2. Follow - O(1), 3. Unfollow - O(1), GetFeed- MNlog10, M number of followees, N number of tweets of each followee, log10 for heapify
// Space complexity - O(N*max(M,T)), where N is the number of users, M is the average number of followers and T is the average number of tweets

class Twitter {
    HashMap <Integer, Set<Integer>> followed;
    HashMap <Integer, List<Tweet>> tweets;
    int timeStamp;
    int feedSize;
    private static class Tweet {
        int id;
        int createdAt;
        public Tweet(int id, int createdAt){
            this.id = id;
            this.createdAt = createdAt;
        }
    }
    
    /** Initialize your data structure here. */
    public Twitter() {
        followed = new HashMap<>();
        tweets = new HashMap<>();
        feedSize = 10;        
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        // if user is not there in followed table add him
        if(!followed.containsKey(userId)) followed.put(userId, new HashSet<>());
        followed.get(userId).add(userId);
        if(!tweets.containsKey(userId)) tweets.put(userId, new ArrayList <>());
        tweets.get(userId).add(new Tweet(tweetId, timeStamp++));
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue <Tweet> pTweets = new PriorityQueue<>((t1,t2) -> t1.createdAt - t2.createdAt);
        Set<Integer> followeds = followed.get(userId);
        if(followeds != null){
            for(int f : followeds){
                List<Tweet> fTweets = tweets.get(f);
                if(fTweets == null) continue;
                for(Tweet t: fTweets){
                    if(pTweets.size() < feedSize){
                        pTweets.add(t);
                    } else {
                        // where my pq is full
                        if(t.createdAt > pTweets.peek().createdAt){
                            pTweets.poll();
                            pTweets.add(t);
                        }
                    }
                }
            }
        }
        List<Integer> res = new ArrayList<>();
        while(!pTweets.isEmpty()) res.add(0, pTweets.poll().id);
        return res;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!followed.containsKey(followerId)) followed.put(followerId, new HashSet<>());
        followed.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(followed.containsKey(followerId) && followerId != followeeId){
            followed.get(followerId).remove(followeeId);
        }
    }
}
