class Twitter {

    class Tweet{
        int id;
        int time;

        public Tweet(int userId,int time)
        {
            this.id = userId;
            this.time = time;
        }
    }
    HashMap<Integer,HashSet<Integer>> followerMap;
    HashMap<Integer,List<Tweet>> tweetMap;
    int time;
    public Twitter() {
        followerMap = new HashMap<>();
        tweetMap = new HashMap<>();
    }
    //TC : O(1)
    //SC : O(1)
    public void postTweet(int userId, int tweetId) {
        if(!tweetMap.containsKey(userId))
        {
            tweetMap.put(userId,new ArrayList<>());
        }
        Tweet t1 = new Tweet(tweetId,time++);
        tweetMap.get(userId).add(t1);
    }
    //TC : O(ft log 10) where f = no of person user follows t = avg no of tweet by a user
    //SC : O(1)
    public List<Integer> getNewsFeed(int userId) {
        follow(userId,userId);
        List<Integer> result = new ArrayList<>();
        PriorityQueue <Tweet> pq = new PriorityQueue<>((a, b) -> a.time-b.time);
        HashSet<Integer> hs = followerMap.get(userId);
        for(Integer uId : hs)
        {
            List<Tweet> l =  tweetMap.get(uId);
            if(l!=null)
            {
                for(Tweet t : l)
                {
                    pq.add(t);
                    if(pq.size()>10)
                        pq.poll();
                }
            }
        }
        //System.out.println(pq);
        while(!pq.isEmpty())
            result.add(0,pq.poll().id);

        return result;
    }
    //TC : O(1)
    //SC : O(1)
    public void follow(int followerId, int followeeId) {
        if(!followerMap.containsKey(followerId))
        {
            followerMap.put(followerId,new HashSet<>());
        }
        followerMap.get(followerId).add(followeeId);
    }
    //TC : O(1)
    //SC : O(1)
    public void unfollow(int followerId, int followeeId) {
        if(followerMap.containsKey(followerId)&&followerId!=followeeId)
        {
            followerMap.get(followerId).remove(followeeId);
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