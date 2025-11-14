// Time Complexity : postTweet : O(1), getNewsFeed: O(f) number of followers, Follow: O(1), Unfollow: O(1)
// Space Complexity : getNewsFeed: O(t): number of tweets by followers
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach

class Twitter {
 
    HashMap<Integer,HashSet<Integer>> followedIds; // userId and mapping of his followers
    HashMap<Integer,List<Tweet>> tweetsMap; //userId and his Tweets
    int time; // variable to mimic time

    class Tweet{
        int tid;
        int createdAt;

        public Tweet(int id, int time){
            this.tid = id;
            this.createdAt = time;
        }
    }

public Twitter() {
    this.followedIds = new HashMap();
    this.tweetsMap = new HashMap();
}

public void postTweet(int userId, int tweetId) {

    follow(userId,userId);
    if(!tweetsMap.containsKey(userId))
        tweetsMap.put(userId, new ArrayList());
    
    tweetsMap.get(userId).add(new Tweet(tweetId, ++time));
}

public List<Integer> getNewsFeed(int userId) {

    PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt); // Pq to store recent tweets in min heap

    HashSet<Integer> followerIds = followedIds.get(userId);
    if(followerIds!=null && !followerIds.isEmpty()){
        for(Integer follower: followerIds){
            List<Tweet> tweets = tweetsMap.get(follower); //get each followers tweets
            if(tweets != null){
                int size = tweets.size();
                for(int k = size -1; k>=0 && k>= size -11;k--){
                    pq.add(tweets.get(k));  //add last 10 tweets to pq
                    if(pq.size()>10)
                        pq.poll();
                }
            }
        }
    }

    List<Integer> result = new ArrayList();
    while (!pq.isEmpty()) {
        result.add(0, pq.poll().tid); //reverse the heap and store it in result // adding at the start index
    }

    return result;
    
}

public void follow(int followerId, int followeeId) {

    if(!followedIds.containsKey(followerId))
        followedIds.put(followerId,new HashSet());
    
    followedIds.get(followerId).add(followeeId);
    
}

public void unfollow(int followerId, int followeeId) {

    if(!followedIds.containsKey(followerId) || followerId == followeeId )  return;
    
    followedIds.get(followerId).remove(followeeId);
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