// This Solution Worked on LeetCode

class Twitter {

    /** Initialize your data structure here. */
    class Tweet{
        int id;
        int createdAt;
        public Tweet(int id,int createdAt){
            this.id = id;
            this.createdAt = createdAt;
        }
    }
    HashMap<Integer,Set<Integer>> followed;
    HashMap<Integer,List<Tweet>> tweets;
    int timestamp =0;
    public Twitter() {
        followed = new HashMap<>();
        tweets = new HashMap<>();
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        if(!followed.containsKey(userId)){
            followed.put(userId,new HashSet<>());    
        }
        followed.get(userId).add(userId);
        if(!tweets.containsKey(userId)) {
            tweets.put(userId,new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId,timestamp++));
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> result = new ArrayList<>();
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        Set<Integer> fids = followed.get(userId);
        if(fids != null){
            for(int Fid : fids ){
                List<Tweet> ftweets = tweets.get(Fid);
                if (ftweets == null)    continue;
                for(Tweet twid : ftweets){
                    if(pq.size() < 10){
                        pq.add(twid);
                    }
                    else{
                        if(twid.createdAt > pq.peek().createdAt){
                            pq.poll();
                            pq.add(twid);    
                        } 
                    }
                }
            }
            while(!pq.isEmpty()){
                result.add(0,pq.poll().id);
            }    
        }
        
        return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!followed.containsKey(followerId)){
            followed.put(followerId, new HashSet<>());
        }
        followed.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(followed.containsKey(followerId) && followerId != followeeId){
            followed.get(followerId).remove(followeeId);
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
