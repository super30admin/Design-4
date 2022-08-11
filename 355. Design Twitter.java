// Time Complexity: O(m * n)
// Space Complexity: O()
// Did this code successfully run on Leetcode: YES
// Any problem you faced while coding this: NO

class Twitter {
    
    class Tweet{
        int id;
        int createdAt;
        public Tweet(int id, int time){
            this.id = id;
            this.createdAt = time;
        }
    }
    
    HashMap<Integer, HashSet<Integer>> users;
    HashMap<Integer, List<Tweet>> tweets;
    int time;
    
    public Twitter() {
        users = new HashMap<>();
        tweets = new HashMap<>();
    }
    
    //TC:O(1)
    public void postTweet(int userId, int tweetId) {
        if(!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId, time++));
    }
    
    //TC:O(m * n)
    public List<Integer> getNewsFeed(int userId) {
        follow(userId, userId);
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);
        HashSet<Integer> followedPpl = users.get(userId);
        for(int user : followedPpl){
            List<Tweet> alltweets = tweets.get(user);
            if(alltweets != null){
                for(Tweet tw : alltweets){
                    pq.add(tw);
                    if(pq.size() > 10){
                        pq.poll();
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
    
    //TC: O(1)
    public void follow(int followerId, int followeeId) {
        if(!users.containsKey(followerId)){
            users.put(followerId, new HashSet<>());
        }
        users.get(followerId).add(followeeId);
    }
    
    //TC: O(1)
    public void unfollow(int followerId, int followeeId) {
        if(users.containsKey(followerId) && followerId != followeeId){
            users.get(followerId).remove(followeeId);
        }
    }
}