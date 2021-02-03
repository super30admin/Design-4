
// Time Complexity : postTweet,follow, follow: O(1), getNewsFeed: O(n)
// Space Complexity : O(n) 
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

// Your code here along with comments explaining your approach
class Twitter {
    
    int time;
    private Map<Integer,Set<Integer>> folowers;
    private Map<Integer,List<Integer>> tweets;
    private Map<Integer,Integer> alltweet;

    /** Initialize your data structure here. */
    public Twitter() {
        folowers=new HashMap<>();
        tweets=new HashMap<>();
        alltweet=new HashMap<>();
        time=0; 
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        time--;
        if(!tweets.containsKey(userId)){
            tweets.put(userId,new ArrayList<>());
        }
        tweets.get(userId).add(time);
        alltweet.put(time,tweetId);
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        
        Map<Integer,Integer> lastfresh=new TreeMap<>();
        List<Integer>news=new ArrayList<>();

        if(tweets.containsKey(userId))
            for (int  time :tweets.get(userId)){
                lastfresh.put(time,alltweet.get(time));
            }

        if(folowers.get(userId)!=null)
            for(int i:folowers.get(userId)){
                if(tweets.containsKey(i))
                    for (int time:tweets.get(i)){
                        lastfresh.put(time,alltweet.get(time));
                    }
            }

        int c=0;
        for(int i: lastfresh.keySet()) {
            news.add(lastfresh.get(i));
            c++;
            if (c >= 10) break;
        }
        return news;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!folowers.containsKey(followerId)){
            folowers.put(followerId,new HashSet<>());
        }
        if(followerId!=followeeId&&!folowers.get(followerId).contains(followeeId))
            folowers.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(folowers.containsKey(followerId)){
            Set<Integer> tmp=folowers.get(followerId);
            tmp.remove(followeeId);
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