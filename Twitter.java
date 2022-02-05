// Time Complexity : O(M* N)
// Space Complexity : O(M* N)
class Twitter {
    class Tweets{
        int id;
        int createdAt;
        public Tweets(int id,int time){
            this.id=id;
            this.createdAt=time;
        }
    }
    int time;
    HashMap<Integer,HashSet<Integer>> followed;
    HashMap<Integer,List<Tweets>> tweets;
    public Twitter() {
        followed=new HashMap<>();
        tweets=new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        follow(userId,userId);
        if(!tweets.containsKey(userId)){
            tweets.put(userId,new ArrayList<>());
        }
        tweets.get(userId).add(new Tweets(tweetId,time++));
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweets> q=new PriorityQueue<>((a,b)->a.createdAt-b.createdAt);
        HashSet<Integer> followers=followed.get(userId);
        if(followers!=null){
            for(int follower:followers){
                List<Tweets> tweetsList=tweets.get(follower);
                if(tweetsList!=null){
                    for(Tweets tweet:tweetsList){
                        q.add(tweet);
                        if(q.size()>10)
                            q.poll();
                    }
                }
            }
        }
        List<Integer> result=new ArrayList<>();
        while(!q.isEmpty()){
            result.add(0,q.poll().id);
        }
        return result;
    }
    
    public void follow(int followerId, int followeeId) {
        if(!followed.containsKey(followerId)){
            followed.put(followerId,new HashSet<>());
        }
        followed.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(followeeId!=followerId && followed.containsKey(followerId))
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
