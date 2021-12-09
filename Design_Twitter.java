// Time Complexity : O(N)
// Space Complexity : O(N)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

class Twitter {
    class Tweet{
        int createdAt;
        int tweetId;
        Tweet(int tweetId, int createdAt){
            this.tweetId=tweetId;
            this.createdAt=createdAt;
        }
    }
    HashMap<Integer,HashSet<Integer>> users;
    HashMap<Integer,List<Tweet>> tweets;
    int time=0;
    
    public Twitter() {
       users=new HashMap<>();
       tweets=new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
       follow(userId,userId);
        if(!tweets.containsKey(userId)){
             tweets.put(userId, new ArrayList<>());
        }
        
        Tweet tw=new Tweet(tweetId, time++);
            
        tweets.get(userId).add(tw);
        
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq=new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        if(users.containsKey(userId)){
            HashSet<Integer> following=users.get(userId);

            for(int i : following){
                List<Tweet> twts=tweets.get(i);
                if(twts!=null){
                for(Tweet t:twts){
                    if(pq.size()>10){
                        pq.poll(); 
                    }
                    pq.add(t);
                }
                }
                
            }
        }
        List<Integer> result=new ArrayList<>();
        if(pq.size()>10) pq.poll();
        while(pq.size()>0){
            result.add(0,pq.poll().tweetId);
        }
        
        return result;

    }
    
    public void follow(int followerId, int followeeId) {
        if(!users.containsKey(followerId)){
            users.put(followerId,new HashSet<>());
        }
        users.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        users.get(followerId).remove(new Integer(followeeId));
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