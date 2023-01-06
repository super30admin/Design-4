// Time Complexity : 
/*
postTweet: O(1)
getNewsFeed:O(n)
follow:O(1)
unfollow:O(1)
*/
// Space Complexity :  
/*
postTweet: O(1)
getNewsFeed:O(n)
follow:O(1)
unfollow:O(1)
*/
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this :
/* Got more clarification during class for implementing it for O(1) complexity */

// Your code here along with comments explaining your approach
/* 
 * Maintain 2 maps to stores followed list list and tweets per userId
 */
class Twitter {
    private Map<Integer, HashSet<Integer>> followed;
    private Map<Integer, List<Tweet>> tweets;
    private int time;
    public Twitter() {
        this.followed = new HashMap<>();
        this.tweets = new HashMap<>();
    }

    class Tweet{
        int id;
        int createdAt;
        public Tweet(int tweetId, int createdAt){
            this.id = tweetId;
            this.createdAt = createdAt;
        }
    }

    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if(!tweets.containsKey(userId))
        {
            tweets.put(userId, new ArrayList<>());
        }
        Tweet newTweet = new Tweet(tweetId, time);
        time++;
        tweets.get(userId).add(newTweet);
    }

    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        //get set of users following
        HashSet<Integer> followeds = followed.get(userId);
        List<Integer> result = new ArrayList<>();
        if(followeds == null)
            return result;
        for(int fid: followeds)
        {
            //get list of tweets;
            List<Tweet> ftweets = tweets.get(fid);
            if(ftweets != null)
            {
                int size = ftweets.size();
                int min = Math.min(size, 10);
                for(int i = size - 1; i >= size - min; i--)
                {
                    pq.add(ftweets.get(i));
                    if(pq.size() > 10)
                    {
                        pq.poll();
                    }
                }
            }
        }
        
        while(!pq.isEmpty())
        {
            result.add(0,pq.poll().id);
        }

        return result;
    }
    public void follow(int followerId, int followeeId) {
        if(!followed.containsKey(followerId))
        {
            followed.put(followerId, new HashSet<>());
        }
        followed.get(followerId).add(followeeId);
    }
    public void unfollow(int followerId, int followeeId) {
        if(!followed.containsKey(followerId))
        {
            return;
        }
        
        followed.get(followerId).remove(followeeId);
        
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