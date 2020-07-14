//Time Complexity: GetFeed - O(no of users*no of tweets) ; Post,follow,unfollow - O(1)
// Space Compleixty: O(1)
// Successfully ran on leetcode

class Twitter {
    
    class Tweet {
        int id;
        int createdAt;
        
        public Tweet(int id, int createdAt) {
            this.id = id;
            this.createdAt = createdAt;
        }
    }
    
    int timestamp;
    HashMap<Integer, HashSet<Integer>> followers;
    HashMap<Integer, ArrayList<Tweet>> usertweet;
    int feedSize;
    
    /** Initialize your data structure here. */
    public Twitter() {
        usertweet = new HashMap<>();
        followers = new HashMap<>();
        feedSize = 10;
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        // user followes himself so that his tweets are visible on his own feed
        if(!followers.containsKey(userId)) {
            followers.put(userId,new HashSet<>());
        }
        followers.get(userId).add(userId);
        if(usertweet.get(userId) == null) 
            usertweet.put(userId,new ArrayList<>());
        // add user tweets to tweet map
        usertweet.get(userId).add(new Tweet(tweetId,timestamp++));
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((t1,t2) -> {return t1.createdAt-t2.createdAt;});
        HashSet<Integer> followerSet = followers.get(userId);
        if(followerSet!=null) {
            for(Integer followerId:followerSet) {
                ArrayList<Tweet> tweets = usertweet.get(followerId);
                if(tweets!=null) {
                    for(Tweet tweet:tweets) {
                        pq.add(tweet);
                        if(pq.size() > feedSize) {
                            pq.poll();
                        }
                    }
                }
            }
        }
        Integer[] res = new Integer[pq.size()];
        for(int i=pq.size()-1;i>=0;i--) {
            res[i] = pq.poll().id;
        }
        return Arrays.asList(res);
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!followers.containsKey(followerId)) {
            followers.put(followerId,new HashSet<>());
        }
        followers.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(followers.containsKey(followerId) && followerId!=followeeId)
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