// Time Complexity : O(nlogn) n -> no of tweets (sorting)
// Space Complexity : O(m*n) m-> no of unique users, n -> no. of unique tweets
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

// Approach :
// 1. A mapping is required for followers and followees which is taken care by userFollows map in this case where key(user) follows value(users)
// 2. When a tweet is posted, the timestamp needs to increase as well as that tweet entry must be made in a map corresponding to the user who tweeted. userTweets map takes care of this.
// 3. For getting the latest 10 tweets, sortng is required. Tweets from all the users who are followed by a particular user are sorted and top 10 tweet Ids are returned (using iterator).

class Twitter {
    
    class Tweets {
        int tweetId;
        int createdAt;
        
        public Tweets(int tweetId, int createdAt) {
            this.tweetId = tweetId;
            this.createdAt = createdAt;
        }
    }
    
    Map<Integer, List<Tweets>> userTweets;
    Map<Integer, Set<Integer>> userFollows; // {u1 : [u2, u3]} u1 follows u2, u3 
    
    int timestamp;
    int feedcount;

    /** Initialize your data structure here. */
    public Twitter() {
        userTweets = new HashMap<>();
        userFollows = new HashMap<>();
        
        timestamp = 0;
        feedcount = 10;
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        // check and add user entry in maps
        newUserCheck(userId);
        
        Tweets tweet = new Tweets(tweetId, ++timestamp);
        userTweets.get(userId).add(tweet); // get user's tweet list and add the new tweet
        // System.out.println(userTweets);
        // System.out.println(userFollows);
        
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        // check and add user entry in maps
        newUserCheck(userId);
        
        // get the followee list of current user
        Set<Integer> followees = userFollows.get(userId);
        // Iterate over the followee list and collect all tweets from these followees
        List<Tweets> tweetList = new ArrayList<>(); // tweet list
        for(int followee : followees) {
            tweetList.addAll(userTweets.get(followee));   
        }
        Collections.sort(tweetList, (a,b) -> b.createdAt-a.createdAt);
        
        List<Integer> result = new ArrayList<>(); // result list containing tweet Ids
        
        Iterator<Tweets> iterator = tweetList.iterator();
        int count = feedcount;
        while(count-- > 0 && iterator.hasNext()) {
            result.add(iterator.next().tweetId);
        }
        
        return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        // check and add follower and followee entries in both maps if the users are new
        newUserCheck(followerId);
        newUserCheck(followeeId);
        
        // add follower
        Set<Integer> followees = userFollows.get(followerId);
        followees.add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        // check and add follower and followee entries in both maps if the users are new
        newUserCheck(followerId);
        newUserCheck(followeeId);
        
        // remove entries of followee
        Set<Integer> followees = userFollows.get(followerId);
        if(followerId != followeeId) // follower cannot remove oneself from the userFollows map
            followees.remove(followeeId);
    }
    
    private void newUserCheck(int userId) {
        // check in user Follows map and add new user as its follower
        if(!userFollows.containsKey(userId)) {
            Set<Integer> followee = new HashSet<>();
            followee.add(userId);
            userFollows.put(userId, followee);
        }
        
        // check in user Tweets map and add empty tweet list for the new user
        if(!userTweets.containsKey(userId)) {
            userTweets.put(userId, new LinkedList<>());
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

