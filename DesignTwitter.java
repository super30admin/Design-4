// Time Complexity : O(1) for postTweet, follow, unfollow and O(t log10) for getNewsFeed where t = no. of tweets
// Space Complexity : O(u+t), u = no. of users, t = no. of tweets
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

// Notes : Create a HashMap of userIds and HashSet of people they follow. Create a HashMap of userIds and HashSet of tweet objects posted by the user. For getNewsFeed(), use a Min Heap(PriorityQueue) based on the time of creation of the tweet and select the 10 tweets with max time values(i.e. which are the most recent). 

public class DesignTwitter {
    class Tweet{
        int id;
        int createdAt;
        Tweet(int id, int createdAt){
            this.id = id;
            this.createdAt = createdAt;
        }
    }
    
    HashMap<Integer, HashSet<Integer>> users;
    HashMap<Integer, HashSet<Tweet>> tweets;
    int time = 0;
    
    /** Initialize your data structure here. */
    public Twitter() {
        users = new HashMap<>();
        tweets = new HashMap<>();
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if(tweets.get(userId) == null){
            tweets.put(userId, new HashSet<>());
        }
        tweets.get(userId).add(new Tweet(tweetId, time++));
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> newsFeed = new ArrayList<>();
        
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);
        HashSet<Integer> following = users.get(userId);
   
        if(following != null){
            for(int followee : following){
                     
                HashSet<Tweet> tweetSet = tweets.get(followee);
                
                if(tweetSet != null){
                    for(Tweet t : tweetSet){
                        
                        pq.add(t);
                        if(pq.size() > 10){
                            pq.poll();
                        }
                    }
                }
            }
        }
        
        for(int i = 0; i < 10; i++){
            if(pq.isEmpty())
                break;    
            newsFeed.add(0, pq.poll().id);
        }
        
        return newsFeed;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(users.get(followerId) == null){
            users.put(followerId, new HashSet<>());
        }
        users.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(users.get(followerId) != null && followerId != followeeId){
            users.get(followerId).remove(followeeId);
        }
    }
}
