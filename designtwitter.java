/*
for getNewsFeed() 
Time Complexity: O(KlogK) 
Space Complexity: O(N)
for posttweet(), follow(), unfollow()
Time Complexity: O(1) 
Space Complexity: O(N)
f
*.
class Twitter {
    HashMap<Integer, HashSet<Tweet>> posts; 
    HashMap<Integer, HashSet<Integer>> follows;
    int time;
    
    public Twitter() {
        posts = new HashMap<>();
        follows = new HashMap<>();
        time = 0;
    }
    
    public void postTweet(int userId, int tweetId) {
        if(!posts.containsKey(userId)) posts.put(userId, new HashSet<>());
        posts.get(userId).add(new Tweet(tweetId, time++));
    }
    
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> newsFeed = new LinkedList<>();
        PriorityQueue<Tweet> pq = new PriorityQueue<>();
        
        if(posts.containsKey(userId)) {
            for(Tweet tweet : posts.get(userId)) {
                if(pq.size() < 10) {
                    pq.add(tweet);
                } else if(tweet.compareTo(pq.peek()) > 0) {
                    pq.poll();
                    pq.add(tweet);
                }
            }
        }
        
        if(follows.containsKey(userId)) {
            for(int foloweeId : follows.get(userId)) {
                if(posts.containsKey(foloweeId)) {
                    for(Tweet tweet : posts.get(foloweeId)) {
                            if(pq.size() < 10) {
                            pq.add(tweet);
                        } else if(tweet.compareTo(pq.peek()) > 0) {
                            pq.poll();
                            pq.add(tweet);
                        }
                    }
                }
            }
        }
        
        while(!pq.isEmpty()) {
            newsFeed.add(0, pq.poll().tweetId);
        }
        
        return newsFeed;
    }
    
    public void follow(int followerId, int followeeId) {
        if(!follows.containsKey(followerId)) follows.put(followerId, new HashSet<>());
        follows.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(follows.containsKey(followerId)) follows.get(followerId).remove(followeeId);
    }
    
    private class Tweet implements Comparable<Tweet> {
        int tweetId, time;
        public Tweet(int tweetId, int time) {
            this.tweetId = tweetId;
            this.time = time;
        }
        @Override
        public int compareTo(Tweet tweet) {
            return this.time - tweet.time;
        }
    }
}
