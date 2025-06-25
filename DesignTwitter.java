// Time Complexity : postTweet, follow and unfollow = O(1), getNewsFeed-O(nlogk) where n= 10*number of followees and k=10
// Space Complexity : O(n+t) n for followedMap and t for tweetMap
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no
// Approach : maintain a followedMap mapping each user to the users he is following. Maintain tweetmap where we map userid to all his tweets
// for follow and unfollow, update the followedMap. For each posTweet, add tweetid to user's entry in tweetMap. For newsFeed, go through all users's that current
// user if following, add recent ten tweets to a priority queue and return most recent 10 among all followees
class Twitter {
    class Tweet{
        int tid;
        int createdAt;
        Tweet(int tid, int createdAt){
            this.tid = tid;
            this.createdAt = createdAt;
        }
    }
    HashMap<Integer, HashSet<Integer>> followedMap;
    HashMap<Integer, ArrayList<Tweet>> tweetMap;
    int time;

    public Twitter() {
        followedMap = new HashMap<>();
        tweetMap = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        Tweet tweet = new Tweet(tweetId, time);
        time++;
        if(!tweetMap.containsKey(userId))
        {
            tweetMap.put(userId, new ArrayList<Tweet>());
        }
        tweetMap.get(userId).add(tweet);
        follow(userId, userId);
        
    }
    
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> result = new ArrayList<Integer>();
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b)-> a.createdAt-b.createdAt);
        //System.out.println("tweetMap = "+tweetMap);
        //System.out.println(followedMap);
        if(followedMap.containsKey(userId)){
            HashSet<Integer> followees = followedMap.get(userId);
            
            for(Integer followee: followees){
                if(tweetMap.containsKey(followee)){
                    List<Tweet> ftweets = tweetMap.get(followee);
                    for(int k=ftweets.size()-1; k>=0 && k>ftweets.size()-11;k--){
                        //System.out.println("Followee id "+followee+" has tweet "+ftweets.get(k).tid);
                        pq.add(ftweets.get(k));
                        //System.out.println(pq.size());
                        if(pq.size()>10){
                            //System.out.println("Am i polling?");
                            pq.poll();
                        }
                    }
                }
            }  
        }
        //System.out.println("After loop, pq size = "+ pq.size());
        int size = pq.size();
        for(int i=0;i<size;i++){
            Tweet t = pq.poll();
            //System.out.println("Adding tid to result "+t.tid);
            result.add(0, t.tid);
        }
        return result;
    }
    
    public void follow(int followerId, int followeeId) {
        if(!followedMap.containsKey(followerId)){
            followedMap.put(followerId, new HashSet<Integer>());
        }
        followedMap.get(followerId).add(followeeId);
        
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(followedMap.containsKey(followerId)){
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