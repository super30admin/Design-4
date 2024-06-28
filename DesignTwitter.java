/*
TC: folow and unfollow and postTweet : O(1), getNewsFeed : O(n)
SC: O(h)- height of the tree , k in heap
*/

class Twitter {
    private HashMap<Integer, HashSet<Integer>> followedMap;
    private HashMap<Integer, List<Tweet>> tweetsMap;
    private int time;

    class Tweet{
        int id;
        int createdAt;
        public Tweet(int id, int time){
            this.id = id;
            this.createdAt = time;
        }
    }
    public Twitter() {
        this.followedMap = new HashMap<>();
        this.tweetsMap = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        follow(userId,userId);
        if(!tweetsMap.containsKey(userId)){
            tweetsMap.put(userId, new ArrayList<>());
        }
        tweetsMap.get(userId).add(new Tweet(tweetId, time));
        time++;    
    }

    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        HashSet<Integer> followedIds = followedMap.get(userId);
        if(followedIds != null){
            for ( int fId : followedIds){
                List<Tweet> fTweets = tweetsMap.get(fId);
                if(fTweets!=null){
                    for(Tweet fTweet : fTweets){
                        pq.add(fTweet);
                        if(pq.size()>10){
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
        if(!followedMap.containsKey(followerId)){
            followedMap.put(followerId, new HashSet<>());
        }
        followedMap.get(followerId).add(followeeId);    
    }

    public void unfollow(int followerId, int followeeId) {
        if(followedMap.containsKey(followerId) && followerId != followeeId){
            followedMap.get(followerId).remove(followeeId);
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
