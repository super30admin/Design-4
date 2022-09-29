// https://leetcode.com/problems/design-twitter/submissions/

// Time Complexity : Mentioned for each operation
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : Nothing as such

class Twitter {
    HashMap<Integer, HashSet<Integer>> users;
    HashMap<Integer, List<Tweet>> tweets;
    
    public Twitter() {
        users = new HashMap<>();
        tweets = new HashMap<>();
    }
    
    class Tweet {
        int tid;
        int createdAt;
        public Tweet(int id, int ca) {
            this.tid = id;
            this.createdAt = ca;
        }
    }
    
    int time;
    public void postTweet(int userId, int tweetId) {// O(1)
        follow(userId, userId);
        if(!tweets.containsKey(userId)) {
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId, time++));
    }
    
    public List<Integer> getNewsFeed(int userId) { // O(n)
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);
        HashSet<Integer> following = users.get(userId);
        if (following != null) {
            for(int followId : following) {
                List<Tweet> allTweetsOfUser = tweets.get(followId);
                if (allTweetsOfUser != null) {
                    for(Tweet tweet : allTweetsOfUser) {
                        pq.add(tweet);
                        if(pq.size() > 10) {
                            pq.poll();
                        }
                    }
                }
            }
        }
        
        List<Integer> res = new ArrayList<>();
        while(!pq.isEmpty()) {
            res.add(0, pq.poll().tid);
        }
        return res;
    }
    
    public void follow(int followerId, int followeeId) {// O(1)
        if(!users.containsKey(followerId)){
            users.put(followerId, new HashSet<>());
        }
        users.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {// O(1)
        if(users.containsKey(followerId) && followerId != followeeId) {
            users.get(followerId).remove(followeeId);
        }
    }
}
