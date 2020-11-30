// Time Complexity : O(nm(log(k)),where n is the number of users and m is the number of tweets per user and k is the capacity
// Space Complexity :O(n^2 + nm), where n is the number of users and m is the number of tweets per user (space for the hashMaps)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : Correct me on the space and time complexity


// Your code here along with comments explaining your approach

class Twitter {

    //tweet object
    class Tweet{
        int tweetId;
        int createdAt;
        
        public Tweet(int tweetId, int createdAt){
            this.tweetId = tweetId;
            this.createdAt = createdAt;
        }
    }
    
    HashMap<Integer, Set<Integer>> followers;
    HashMap<Integer, List<Tweet>> userTweets;
    //counter to maintain time
    int time;
    /** Initialize your data structure here. */
    public Twitter() {
        followers = new HashMap<>();
        userTweets = new HashMap<>();
        time = 0;
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        //add the userId with a new list in the userTweets hashMap, if userId not exists
        //check if userId exists in the followes hashMap, else add one and make it follow itself
        follow(userId, userId);
        if(!userTweets.containsKey(userId)){
            userTweets.put(userId, new ArrayList<>());
        }
        //add the tweet for the userId
        Tweet tweet = new Tweet(tweetId, time);
        userTweets.get(userId).add(tweet);
        time++;
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        int count = 10;
        //to return the feeds
        List<Integer> result = new ArrayList<>();
        //priority queue to get the 10 most recent feeds
        PriorityQueue<Tweet> pq= new PriorityQueue<>((a,b)->(a.createdAt-b.createdAt)); 
        //Get all the tweets of all the followers of the userId inculding herself(userId)
        if(followers.containsKey(userId)){
            Set<Integer> users = followers.get(userId);
            if(users != null){
                for(int user: users){
                    if(userTweets.containsKey(user)){
                        List<Tweet> tweets = userTweets.get(user);
                        if(tweets != null){
                            for(Tweet tweet : tweets){
                                //put the tweets in the priority queue to get the recents tweets in the queue
                                pq.add(tweet);
                                if(pq.size() > count){
                                    pq.poll();
                                }
                            }
                        }
                    }
                }
                //add the tweets from the piority queue to the result
                while(!pq.isEmpty()){
                    result.add(0, pq.poll().tweetId);
                }
            }
        }
        return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!followers.containsKey(followerId)){
            followers.put(followerId, new HashSet<>());
             //make the userId follow itself
            followers.get(followerId).add(followerId);
        }
        //add the followee to the follower
        followers.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        //if the followerId exixts in the followers hashMap, delete the followee from the the Set
        //cannot unfollow himself
        if(followerId == followeeId) return;
        
        if(followers.containsKey(followerId) && followers.get(followerId).contains(followeeId)){
            followers.get(followerId).remove(followeeId);
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