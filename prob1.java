// Time Complexity : O(1) 
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : Yes

class Twitter {
    Stack<Pair<Integer,Integer>> tweets;
    HashMap<Integer, Set<Integer>> network;

    public Twitter() {
		tweets = new Stack<>();
		network = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        tweets.push(new Pair(userId, tweetId));
    }
    
    public List<Integer> getNewsFeed(int userId) {
        Stack<Pair<Integer, Integer>> store = new Stack<>();
        List<Integer> ans = new ArrayList<>();
        Set<Integer> following = network.getOrDefault(userId, new HashSet<>());
        
        while(tweets.size()!=0 && ans.size()!=10){
            Pair<Integer, Integer> curr = tweets.pop();
            if((following.contains(curr.getKey()) || curr.getKey()==userId)
               && ans.size()!=10) ans.add(curr.getValue());
            store.push(curr);
        }
        while(store.size()!=0) tweets.push(store.pop());
        return ans;
    }
    
    public void follow(int followerId, int followeeId) {
        Set<Integer> following = network.getOrDefault(followerId, new HashSet<>());
        following.add(followeeId);
        network.put(followerId, following);
    }
    
    public void unfollow(int followerId, int followeeId) {
        Set<Integer> following = network.get(followerId);
        if(following == null) return;
        following.remove(followeeId);
        network.put(followerId, following);
    }
}

