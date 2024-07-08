/*
    LC - 355. Design Twitter 
    TC - 
        for getNewsFeed ---> 
            O(nk logk) ~ O(nk) as k is 10 which is constant 
        O(1) for other methods.
    SC - O(n) overall.
    Approach - 
        Using two hashmap to manage the user and tweet.
        Using min heap to get the 10 most recent tweets.
*/

class Twitter {

    class Tweet {
        int tweetId;
        int createdAt;
        public Tweet(int tweetId, int createdAt) {
            this.tweetId = tweetId;
            this.createdAt = createdAt;
        }
    }

    HashMap<Integer, HashSet<Integer>> userMap;
    HashMap<Integer, List<Tweet>> tweetMap;
    int time; // java by default assign zero

    public Twitter() {
        userMap = new HashMap<>();
        tweetMap = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        if(!tweetMap.containsKey(userId)) {
            tweetMap.put(userId, new ArrayList<>());
        }
        tweetMap.get(userId).add(new Tweet(tweetId, time++));
    }
    
    public List<Integer> getNewsFeed(int userId) {
        follow(userId, userId);
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt); // min heap
        HashSet<Integer> allUsers = userMap.get(userId);
        for(Integer user : allUsers) {
            List<Tweet> alltweets = tweetMap.get(user);
            if(alltweets != null) {
                for(Tweet tw: alltweets) {
                    pq.add(tw);
                    if(pq.size() > 10) {
                        pq.poll();
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()) {
            result.add(0, pq.poll().tweetId); // inserting an element at the 0th position to maintain the recent one.
        }
        return result;
    }
    
    public void follow(int followerId, int followeeId) {
        if(!userMap.containsKey(followerId)) {
            userMap.put(followerId, new HashSet<>());
        }
        userMap.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(!userMap.containsKey(followerId) || followerId == followeeId) {
            return;
        }
        if(userMap.get(followerId).contains(followeeId)) {
            userMap.get(followerId).remove(followeeId);
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