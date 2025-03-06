// Time Complexity :O(nlogK) 
// Space Complexity :O(n) -> k is the number of list 
// Did this code successfully run on Leetcode :yes
// Any problem you faced while coding this :no
 

// Your code here along with comments explaining your approach: here implementing tweet class to maintian tweetObject and createdat.
// as well to maintian followedby list  for particular follower maintain hashmap and to matain tweets of user maintain user and corresponding tweetid and createdAt becuase we need to give most recent tweets.
class Twitter {
    class Tweet {
        int tweetId;
        int createdAt;

        public Tweet(int tweetId, int createdAt) {//constructor for tweet
            this.tweetId = tweetId; //initalizing tweetid and createdAt
            this.createdAt = createdAt;
        }
    }

    // Tweet tweet = new Tweet();
    HashMap<Integer, HashSet<Integer>> followedMap; // follower and followedby list
    HashMap<Integer, List<Tweet>> tweetMap; //user and corresponding tweets -> {tweetid,createdAt}
    int timeStamp;

    public Twitter() {
        this.followedMap = new HashMap<>();
        this.tweetMap = new HashMap<>();
        this.timeStamp = 0;
    }

    public void postTweet(int userId, int tweetId) {
        follow(userId,userId);
        Tweet newTweet = new Tweet(tweetId, timeStamp);
        if (!tweetMap.containsKey(userId)) {
            tweetMap.put(userId, new ArrayList<Tweet>());
        }
        tweetMap.get(userId).add(newTweet);
        timeStamp++;
    }

    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> b.createdAt - a.createdAt);
        List<Integer> finalTweetsList = new ArrayList<>();
        HashSet<Integer> followedSet = followedMap.get(userId);

        if (followedSet == null)
            return finalTweetsList;
        ;

        for (Integer user : followedSet) {
            List<Tweet> tweets = tweetMap.get(user);
            if(tweets == null)break;
            for (Tweet newTweet : tweets) {
                pq.add(newTweet);
            }
        }
        while (!pq.isEmpty() && finalTweetsList.size() < 10) {
            // if (pq.size() > 10) {
            //     pq.poll();
            // } 
            finalTweetsList.add(pq.poll().tweetId);
        }
        return finalTweetsList;

    }

    public void follow(int followerId, int followeeId) {
        if (!followedMap.containsKey(followerId)) {
            followedMap.put(followerId, new HashSet<>());
        }
        followedMap.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (followerId != followeeId) {
            followedMap.get(followerId).remove(followeeId);
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