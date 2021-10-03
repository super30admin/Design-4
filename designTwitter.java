// Time Complexity : O(n log k)
// Space Complexity : O(number of users + number of tweets)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this :

class Twitter {
    class Tweet{
        int id;
        int createdAt;
        public Tweet(int tweetId, int createdAt){
            this.id = tweetId;
            this.createdAt = time;
        }
    }
    int time;
    
    HashMap<Integer, HashSet<Integer>> followed;
    HashMap<Integer, List<Tweet>> tweets;
    
    public Twitter() {
        followed = new HashMap<>();
        tweets = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if(!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId, time++)); //add to list of tweets
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        // get list of people userId is following
        HashSet<Integer> fids = followed.get(userId);
        if(fids != null){
            for (Integer fid: fids){
                // take tweets belonging to each fid
                List<Tweet> fTweets = tweets.get(fid);
                if(fTweets != null){
                    for(Tweet fTweet: fTweets){
                        pq.add(fTweet);
                        if(pq.size() > 10){
                            pq.poll();
                        }
                    }
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
        // table of person I am following
        if(!followed.containsKey(followerId)){
            followed.put(followerId, new HashSet<>());
        }
        followed.get(followerId).add(followeeId);
    }
    
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