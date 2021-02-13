// Time Complexity :
// Space Complexity :
// Did this code successfully run on Leetcode :yes
// Any problem you faced while coding this :


// Your code here along with comments explaining your approach
class Twitter {
    HashMap<Integer,HashSet<Integer>> follower;
    HashMap<Integer,List<Tweet>> tweets;
        
    class Tweet{
        int tweetId;
        int tweetTime;
        Tweet(int twee,int ti){
            this.tweetId=twee;
            this.tweetTime=ti;
        }
        
    }
    
    int time;
    /** Initialize your data structure here. */
    public Twitter() {
        follower=new HashMap<Integer,HashSet<Integer>>();
        tweets=new HashMap<Integer,List<Tweet>>();
        time=0;
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        follow(userId,userId);
        if(tweets.containsKey(userId)){
            tweets.get(userId).add(new Tweet(tweetId,time++));
        }else{
            List<Tweet> list=new ArrayList<>();
            list.add(new Tweet(tweetId,time++));
            tweets.put(userId,list);
        }
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> result=new ArrayList<>();
        List<Tweet> list=new ArrayList<>();
        // System.out.println(userId);
        // System.out.println(follower);
        // System.out.println(tweets);
        if(follower.containsKey(userId)){
            for(int id:follower.get(userId)){
                if(tweets.containsKey(id)){
                    for(Tweet tw:tweets.get(id)){
                        list.add(tw);
                    }
                }
            }
        }
        // System.out.println(list);
        Collections.sort(list,(a,b)->(b.tweetTime-a.tweetTime));
        if(list.size()<10){
            for(int i=0;i<list.size();i++){
                result.add(list.get(i).tweetId);
            }
        }else{
           for(int i=0;i<10;i++){
                result.add(list.get(i).tweetId);
            } 
        }
        return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(follower.containsKey(followerId)){
            follower.get(followerId).add(followeeId);
        }else{
            HashSet<Integer> set=new HashSet<Integer>();
            set.add(followeeId);
            set.add(followerId);
            follower.put(followerId,set);
        }
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(followerId==followeeId){
            return;
        }
        if(follower.containsKey(followerId)){
            follower.get(followerId).remove(followeeId);
        }
    }
}