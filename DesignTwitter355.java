/** 
Time Complexity : O(1) for follow(), unfollow(), postTweet() and O(nk) for getNewsFeed() 
Space Complexity : O(n) 
Did this code successfully run on Leetcode : Yes
Any problem you faced while coding this : No
**/

class Twitter {

    class Tweet{
        int tId;
        int createdAt;
         public Tweet( int tId , int createdAt){
            this.tId = tId;
            this.createdAt = createdAt;
        }
    }
    int time;
    HashMap<Integer,HashSet<Integer>> followers = new HashMap<>();
    HashMap<Integer,List<Tweet>> tweets = new HashMap<>();
    
    public void postTweet(int userId, int tweetId) {
        Tweet tw = new Tweet(tweetId, time++);
        if(!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(tw); 
    }
    
    public List<Integer> getNewsFeed(int userId) {
        follow(userId,userId);
        List<Integer> result = new ArrayList<Integer>();
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b)->a.createdAt - b.createdAt);
        HashSet<Integer> users = followers.get(userId);
        if(users != null){
        for(Integer user : users){
            if(tweets.get(user) != null ){
                List<Tweet> alltweets = tweets.get(user);
                if(alltweets != null){
            for( Tweet tweet : alltweets){
                    if(pq.size() == 10){
                        pq.poll();
                    }
                    pq.add(tweet);
                    
                }
                }

            }
        }
    }
        while(!pq.isEmpty()){
            Tweet twit = pq.poll();
            result.add(0,twit.tId);
        }
        
        return result;
        
    }
    
    public void follow(int followerId, int followeeId) {
        if(!followers.containsKey(followerId)){
            followers.put(followerId,new HashSet<>());
        }
        followers.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(followers.containsKey(followerId)){
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

