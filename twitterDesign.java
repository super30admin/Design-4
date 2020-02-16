class TimeStamp{
    int time;
    int tweetId;
    TimeStamp(int time, int tweetId){
        this.time = time;
        this.tweetId = tweetId;
    }
}
class Twitter {
    int timeCount;
    /** Initialize your data structure here. */
    Map<Integer,Set<Integer>> followers;
    Map<Integer,List<TimeStamp>> map;
    public Twitter() 
    {
        followers=new HashMap<>();
        map=new HashMap<>();
        timeCount=0;
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) 
    {
        if(!map.containsKey(userId))
            map.put(userId,new ArrayList());
        map.get(userId).add(new TimeStamp(timeCount++,tweetId));
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) 
    {
            if(!map.containsKey(userId))
            map.put(userId,new ArrayList());
            PriorityQueue<TimeStamp> pq=new PriorityQueue(new Comparator<TimeStamp>(){
                public int compare(TimeStamp a,TimeStamp b)
                {
                    return Integer.compare(b.time,a.time);
                }
            });
            for(TimeStamp t:map.get(userId))
            {
                pq.add(t);
            }
            if(!followers.containsKey(userId))
            {}
            else
            {
                for(Integer i:followers.get(userId))
                {
                    if(!map.containsKey(i))
                    map.put(i,new ArrayList());
                    for(TimeStamp t:map.get(i))
                    {
                        pq.add(t);
                    }
                }
            }
            List<Integer> res=new ArrayList<>();
            while(!pq.isEmpty())
            {
                res.add(pq.poll().tweetId);
                if(res.size()==10)
                    break;
            }
        return res;
           
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) 
    {
        if(!followers.containsKey(followerId))
            followers.put(followerId,new HashSet<>());
        if(followerId==followeeId)
            return;
        followers.get(followerId).add(followeeId);
        //System.out.println(followers.get(followerId));
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) 
    {
        if(!followers.containsKey(followerId))
            return;
        if(!followers.get(followerId).contains(followeeId))
        {}
        else
            followers.get(followerId).remove(followeeId);
        
    }
}

// Time Complexity: O(n)
// Space Complexity : O(n)