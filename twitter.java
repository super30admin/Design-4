// Problem 1: Design Twitter (https://leetcode.com/problems/design-twitter/)

// Time Complexity : O(1) for follow and unfollow and posttweet. o(nlogk)
// Space Complexity : O(k)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no

// Your code here along with comments explaining your approach in three sentences only
/*
 * Here, create a tweet class to keep track of both id and time at which tweet was made as object. Create 2 hashmaps, 1 for 
 * followees and one for tweets with a userId. For followers in hashmap key is the follower and value is the hashset of followees
 * and to unfollow just remove the followee id for particular id of user. For postTweet, 1st add userId who is creating to followees
 * hash map then if tweets hashmap does not contain userid of who is creating create an entry. For getNewsFeed create priotity queue
 * of tweet objects. First get the followees list of particular user and for every followee get tweets list and from that list add
 * each tweet object to pq until size 10. Add each tweet objects id to result at 0th index everytime to get order in reverse. Finally
 * return result.
 */
class Twitter {

    class Tweet{
        int id;
        int createdAt;
        public Tweet(int id, int time){
            this.id = id;
            this.createdAt = time;
        }

    }
    HashMap<Integer, HashSet<Integer>> followed;
    HashMap<Integer, List<Tweet>> tweets;
    private int time;
    public Twitter() {
        this.followed = new HashMap<>();
        this.tweets = new HashMap<>();
        this.time = 0;
    }
    
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if(!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<>());
        }
        Tweet tweet = new Tweet(tweetId, time);
        time++;
        tweets.get(userId).add(tweet);
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);
        HashSet<Integer> followedIds = followed.get(userId);
        if(followedIds == null) return new ArrayList<>();
        for(Integer followedId: followedIds){
            // get tweets of particular followed id
            List<Tweet> ftweets = tweets.get(followedId);
            if(ftweets == null) continue;
            // for(Tweet ftweet: ftweets){
            //     pq.add(ftweet);
            //     if(pq.size()>10){
            //         pq.poll();
            //     }
            // }
            int l = ftweets.size();
            for(int i = l-1; i>=0 && i>= l -11; i--){
                Tweet ftweet = ftweets.get(i);
                pq.add(ftweet);
                if(pq.size()>10){
                    pq.poll();
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0, pq.poll().id);
        }
        return result;
    }
    
    public void follow(int followerId, int followeeId) { 
        if(!followed.containsKey(followerId)){
            followed.put(followerId, new HashSet<>());
        }
        followed.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(!followed.containsKey(followerId) || followerId == followeeId) return;
        followed.get(followerId).remove(followeeId); 
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