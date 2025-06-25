//intermediate
// class Twitter {
//     class Tweet{
//         int tid;
//         int createdAt;
//         public Tweet(int tid, int time){
//             this.tid = tid;
//             this.createdAt = time;
//         }
//     }
//     private HashMap<Integer, HashSet<Integer>> followedMap; //people user has followed
//     private HashMap<Integer, List<Tweet>> tweetsMap;
//     int time;
//     public Twitter() {
//         this.followedMap = new HashMap<>();
//         this.tweetsMap = new HashMap<>();
//     }
    
//     public void postTweet(int userId, int tweetId) {
//         follow(userId, userId);
//         if(!tweetsMap.containsKey(userId)){
//             tweetsMap.put(userId, new ArrayList<>());
//         }
//         Tweet  tweet = new Tweet(tweetId, time);
//         time++;
//         tweetsMap.get(userId).add(tweet);
//     }
    
//     public List<Integer> getNewsFeed(int userId) { //O(N)
//         PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> (a.createdAt-b.createdAt));
//         HashSet<Integer> followeds = followedMap.get(userId);
//         if(followeds != null){
//             for(int fid: followeds){
//                 List<Tweet> fTweets = tweetsMap.get(fid);
//                 if(fTweets != null){
//                     for (Tweet fTweet: fTweets){
//                         pq.add(fTweet);
//                         if(pq.size()>10){
//                             pq.poll();
//                         }
//                     }
//                 }
//             }
//         }
//         List<Integer> result = new ArrayList<>();
//         while(!pq.isEmpty()){
//             result.add(0,pq.poll().tid);
//         }
//         return result;
//     }
    
//     public void follow(int followerId, int followeeId) { //O(1)
//         if(!followedMap.containsKey(followerId)){
//             followedMap.put(followerId, new HashSet<>());
//         }
//         followedMap.get(followerId).add(followeeId);
//     }
    
//     public void unfollow(int followerId, int followeeId) { //O(1)
//         if(followedMap.containsKey(followerId) && followerId!= followeeId){
//             followedMap.get(followerId).remove(followeeId);
//         }
//     }
// }

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */


 //Better as we are just getting recent k(10) tweets from all followed :in getNewsFeed
class Twitter {
    class Tweet{
        int tid;
        int createdAt;
        public Tweet(int tid, int time){
            this.tid = tid;
            this.createdAt = time;
        }
    }
    private HashMap<Integer, HashSet<Integer>> followedMap; //people user has followed
    private HashMap<Integer, List<Tweet>> tweetsMap;
    int time;
    public Twitter() {
        this.followedMap = new HashMap<>();
        this.tweetsMap = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) { //O(1)
        follow(userId, userId);
        if(!tweetsMap.containsKey(userId)){
            tweetsMap.put(userId, new ArrayList<>());
        }
        Tweet  tweet = new Tweet(tweetId, time);
        time++;
        tweetsMap.get(userId).add(tweet);
    }
    
    public List<Integer> getNewsFeed(int userId) { //O(k), k is 10 here
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt-b.createdAt);
        HashSet<Integer> followeds = followedMap.get(userId);
        if(followeds != null){
            for(int fid: followeds){
                List<Tweet> fTweets = tweetsMap.get(fid);
                if(fTweets != null){
                    int size = fTweets.size();
                    for(int k=size-1; k>=0 && k>=size-10;k--){
                        Tweet fTweet = fTweets.get(k);
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
            result.add(0,pq.poll().tid);
        }
        return result;
    }
    
    public void follow(int followerId, int followeeId) { //O(1)
        if(!followedMap.containsKey(followerId)){
            followedMap.put(followerId, new HashSet<>());
        }
        followedMap.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) { //O(1)
        if(followedMap.containsKey(followerId) && followerId!= followeeId){
            followedMap.get(followerId).remove(followeeId);
        }
    }
}