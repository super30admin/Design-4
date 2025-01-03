// why set was taken instead of list, because if you keep on following a single account multiple accounts, only 1 time it will be added.
// and the search is O(1), which is faster.
// Priority queue is taken so as to put elements in a sorted manner. 
class Twitter {
    int time;
    class Tweet {
        int tId;
        int createdAt;

        Tweet(int tId, int createdAt) {
            this.tId = tId;
            this.createdAt = createdAt;            
        }
}
    Map<Integer, Set<Integer>> followedMap;
    Map<Integer, List<Tweet>> tweetsMap;

    public Twitter() {
        followedMap = new HashMap<>();
        tweetsMap = new HashMap<>();        
    }
    
    public void postTweet(int userId, int tweetId) { // O(1)
        // when you post a tweet, it needs to go to you as well.
        follow(userId, userId);
        // just normal adding to the map.
        if(!tweetsMap.containsKey(userId)) {
            tweetsMap.put(userId, new ArrayList<>());
        }
        time++;
        tweetsMap.get(userId).add(new Tweet(tweetId, time));
        
    }
    
    public List<Integer> getNewsFeed(int userId) { // O(no.of followers * 10 tweets * log(no.of followers* 10)).
    // each followers 10 tweets are taken.
    // and it is added to the priority queue.
    // insertion in priority queue for each tweet is log(n*10)
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b)-> a.createdAt- b.createdAt);// min heap, so that the max time stamped tweet is at the top.
        Set<Integer> followers = followedMap.get(userId);
        if(followers != null) {  
            for(int id: followers) {
            List<Tweet> li = tweetsMap.get(id);
            if(li != null) {
                            for(int k=li.size()-1;k>=0 && k>= li.size()-10; k--) {
                pq.add(li.get(k));
                if(pq.size()>10) {
                    pq.poll();
                }

            }
            }

        }

        }

        List<Integer> list = new ArrayList<>();
        while(!pq.isEmpty()) {
            list.add(0, pq.poll().tId);
        }
        return list;

        
    }
    
    public void follow(int followerId, int followeeId) { // O(1)
        if(!followedMap.containsKey(followerId)) {
            followedMap.put(followerId, new HashSet<>());
        }
        followedMap.get(followerId).add(followeeId);

        
    }
    
    public void unfollow(int followerId, int followeeId) { // O(1)
        if(!followedMap.containsKey(followerId) || !followedMap.get(followerId).contains(followeeId)) return;
        followedMap.get(followerId).remove(followeeId);
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