class Tweet{
    int twtid;
    int time;
    public Tweet(int TwtId, int Time){
        twtid=TwtId;
        time=Time;
    }
}
class Twitter {
    HashMap<Integer, HashSet<Integer>> usrmap = new HashMap<Integer, HashSet<Integer>>();
    HashMap<Integer, List<Tweet>> twtMap = new HashMap<Integer, List<Tweet>>();
    int t=0;
    public Twitter() {
        
    }
    //Time complexity is O(1)
    public void postTweet(int userId, int tweetId) {
        t=t+1;
        Tweet tweet = new Tweet(tweetId, t);
        if(!twtMap.containsKey(userId)){
            twtMap.put(userId, new ArrayList<Tweet>());
        }

        twtMap.get(userId).add(tweet);
    }
    //Time complexity is O(NlogK) N is number of tweets by users of userId,  K is depth of time heap
    public List<Integer> getNewsFeed(int userId) {
        follow(userId, userId);
        HashSet<Integer> users = usrmap.get(userId);
        PriorityQueue<Tweet> pq = new PriorityQueue<Tweet>((a,b)->b.time-a.time);
        for(int user: users){
            List<Tweet> twtli = twtMap.getOrDefault(user, new ArrayList());
            for(Tweet tt : twtli){
                pq.add(tt);
            }
        }
        List<Integer> result = new ArrayList();
        int i=0;
        while(i<10 && pq.size()>0){
            i=i+1;
            result.add(pq.poll().twtid);
        }
        return result;
    }
    //Time complexity is O(1)
    public void follow(int followerId, int followeeId) {
        if(!usrmap.containsKey(followerId)){
            usrmap.put(followerId, new HashSet<Integer>());
        }
        usrmap.get(followerId).add(followeeId);
    }
    //Time complexity is O(1)
    public void unfollow(int followerId, int followeeId) {
        if(usrmap.containsKey(followerId) && followerId!=followeeId && usrmap.get(followerId).contains(followeeId)){
            usrmap.get(followerId).remove(followeeId);
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