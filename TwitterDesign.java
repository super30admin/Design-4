// Time Complexity : O(n) where n is number of users.
// Space Complexity : O(1)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

public class TwitterDesign
{
    HashMap<Integer, HashSet<Integer>> userMap;
    HashMap<Integer, List<Tweet>> tweetMap;
    int time;
    class Tweet
    {
        int tweetId;
        int createTime;
        public Tweet(int tweetId, int createTime )
        {
            this.tweetId = tweetId;
            this.createTime = createTime;
        }
    }
    public Twitter() {
        userMap = new HashMap();
        tweetMap = new HashMap();
    }

    public void postTweet(int userId, int tweetId) {
        if(!tweetMap.containsKey(userId))
        {
            follow(userId, userId);
            tweetMap.put(userId, new ArrayList());
        }
        time++;
        tweetMap.get(userId).add(new Tweet(tweetId, time));
    }

    public List<Integer> getNewsFeed(int userId) {
        List<Integer> result = new ArrayList();
        if(!userMap.containsKey(userId))
            return result;

        PriorityQueue<Tweet> pqTweet = new PriorityQueue<>((a,b)-> a.createTime - b.createTime);
        for(int followeeId : userMap.get(userId))
        {
            List<Tweet> tweets = tweetMap.get(followeeId);
            if(tweets == null)
                continue;
            int k = tweets.size();
            for(Tweet tweetId: tweets)
            //for(int j = k-1; j >= 0 && j >= k-12; j--)
            {
                //pqTweet.add(tweets.get(j));
                pqTweet.add(tweetId);
                if(pqTweet.size() > 10)
                    pqTweet.poll();
            }
        }

        while(!pqTweet.isEmpty())
            result.add(0, pqTweet.poll().tweetId);

        return result;
    }

    public void follow(int followerId, int followeeId) {
        if(!userMap.containsKey(followerId))
            userMap.put(followerId, new HashSet());

        userMap.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId)
    {
        if(userMap.containsKey(followerId))
            userMap.get(followerId).remove(followeeId);
    }
}
