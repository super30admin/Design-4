/* Time Complexity : 
 *	postTweet, follow, unfollow - O(1)
 *	getNewsFeed - O(N log 10) ~ O(N)
 *	N - total number of users the curr. user follows + themselves */
/* Space Complexity : O(u+t) 
 *  u - size of the usersMap
 *  t - size of the tweetsMap  */
// Did this code successfully run on Leetcode : Yes 
// Any problem you faced while coding this :

class Twitter {

    class Tweet{
        int tweetId;
        int createdAt;
        public Tweet(int id, int time){
            this.tweetId = id;
            this.createdAt = time;
        }
    }

    HashMap<Integer, HashSet<Integer>> usersMap;
    HashMap<Integer, List<Tweet>> tweetsMap;
    int time;

    public Twitter() {
        this.usersMap = new HashMap<>();
        this.tweetsMap = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        if(!tweetsMap.containsKey(userId)){
            follow(userId, userId);
            tweetsMap.put(userId, new ArrayList<>());
        }
        tweetsMap.get(userId).add(new Tweet(tweetId, time++));
    }
    
    public List<Integer> getNewsFeed(int userId) {

        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        List<Integer> result = new ArrayList<>();
        if(usersMap.containsKey(userId)){
            HashSet<Integer> follows = usersMap.get(userId);
            for(int fId: follows){
                List<Tweet> fTweets = tweetsMap.get(fId);
                if(fTweets != null){
                    int k = fTweets.size();
                    for(int j = k-1; j >= 0 && j >= k - 12; j--){
                        pq.add(fTweets.get(j));
                        if(pq.size() > 10){
                            pq.poll();
                        }
                    }
                }
            }
        }
        while(!pq.isEmpty()){
            result.add(0, pq.poll().tweetId);
        }
        return result;
    }
    
    public void follow(int followerId, int followeeId) {
        if(!usersMap.containsKey(followerId)){
            usersMap.put(followerId, new HashSet<>());
        }
        usersMap.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(followerId != followeeId && usersMap.containsKey(followerId)){
            usersMap.get(followerId).remove(followeeId);
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