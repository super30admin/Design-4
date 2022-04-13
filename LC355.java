//Time Complexity : O(n)
//Space Complexity : O(n)

class Twitter {
    
    class Tweet
    {
        int id;
        int createdAt;
        
        public Tweet(int id,int time)
        {
            this.id = id;
            this.createdAt = time;
        }
    }
    
    HashMap<Integer,HashSet<Integer>> followed;
    HashMap<Integer,List<Tweet>> tweets;
    int time;
    
    public Twitter() {
        
        followed = new HashMap<>();
        tweets = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        
        if(!tweets.containsKey(userId))
        {
            tweets.put(userId,new ArrayList<>());
        }
        
        follow(userId,userId);
        
        tweets.get(userId).add(new Tweet(tweetId,time++));
        
        return;
    }
    
    public List<Integer> getNewsFeed(int userId) {
        
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        HashSet<Integer> set = followed.get(userId);
        
        if(set == null)
        {
            return new ArrayList<>();
        }
        
        for(int ele : set)
        {
            List<Tweet> ftweet = tweets.get(ele);
            
            if(ftweet!=null)
            {
                for(int i=0;i<ftweet.size();i++)
                {
                    pq.add(ftweet.get(i));
                    
                    if(pq.size() > 10)
                    {
                        pq.poll();
                    }
                }
            }
        }
        
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty())
        {
            result.add(0,pq.poll().id);
        }
        
        return result;
    }
    
    public void follow(int followerId, int followeeId) {
        
        if(!followed.containsKey(followerId))
        {
            followed.put(followerId,new HashSet<>());
        }
        
        followed.get(followerId).add(followeeId);
        
        return;
    }
    
    public void unfollow(int followerId, int followeeId) {
        
        if(!followed.containsKey(followerId))
        {
            return;
        }
        
        followed.get(followerId).remove(followeeId);
        
        return;
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