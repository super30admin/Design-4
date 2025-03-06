
// Time Complexity : follow O(1) unfollow O(1) post O(1) newsfeed O(n)- no of followers
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach
/*
Design
 */

class Twitter {
    HashMap<Integer,HashSet<Integer>> userFollows;
    HashMap<Integer,HashSet<Tweet>> userTweets;
    int time;

    class Tweet{
        int id;
        int time;
        Tweet(int id,int time){
            this.id=id;
            this.time=time;
        }
    }

    public Twitter() {
        userFollows=new HashMap<>();
        userTweets=new HashMap<>();
        time=0;
    }

    public void postTweet(int userId, int tweetId) {
        follow(userId,userId);
        if(!userTweets.containsKey(userId)){
            userTweets.put(userId,new HashSet<>());
        }
        Tweet tweet=new Tweet(tweetId,time);
        time++;
        userTweets.get(userId).add(tweet);
    }

    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq=new PriorityQueue<>((a,b)-> a.time-b.time);
        HashSet<Integer> followers=userFollows.get(userId);
        if(followers==null) return new ArrayList<Integer>();
        for(int fId:followers){
            HashSet<Tweet> tweets=userTweets.get(fId);
            if(tweets==null)
                continue;
            for(Tweet t:tweets){
                pq.add(t);
                if(pq.size()>10){
                    pq.remove();
                }
            }
        }

        List<Integer> result=new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0,pq.poll().id);
        }

        return result;
    }

    public void follow(int followerId, int followeeId) {
        if(!userFollows.containsKey(followerId)){
            userFollows.put(followerId,new HashSet<>());
        }
        userFollows.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if(!userFollows.containsKey(followerId) ||
                followerId==followeeId) return;
        userFollows.get(followerId).remove(followeeId);
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


