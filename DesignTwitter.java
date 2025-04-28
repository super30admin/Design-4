import java.util.*;
// TC: O(1) for follow/unfollow, O(N) for getNewsFeed
// SC: O(N × (M + T))

/*
This code designs a basic version of Twitter where users can post tweets,
follow or unfollow others, and fetch a personalized news feed. Tweets are
stored with timestamps, and follow relationships are maintained using hash maps.
When fetching the news feed, a min-heap (priority queue) is used to efficiently keep
 track of the 10 most recent tweets among all followees. Posting a tweet automatically
  makes a user follow themselves to simplify retrieval. Optimizations
like scanning only the last 10 tweets of each followee improve performance significantly.
* */
class DesignTwitter {


    class Tweet{
        int tweetId;
        int timeStamp;

        public Tweet(int tweetId,int timeStamp){
            this.tweetId=tweetId;
            this.timeStamp=timeStamp;
        }
    }

    HashMap<Integer, HashSet<Integer>> followeesMap;
    HashMap<Integer, List<Tweet>> tweetMap;
    int time;
    public Twitter() {
        followeesMap = new HashMap<>();
        tweetMap = new HashMap<>();
        //time=0;
    }

    public void postTweet(int userId, int tweetId) {
        if(!tweetMap.containsKey(userId))
        {
            tweetMap.put(userId,new ArrayList<>());
        }
        Tweet tweet=new Tweet(tweetId,time);
        time++;
        tweetMap.get(userId).add(tweet);

        follow(userId,userId);
    }

    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq=new PriorityQueue<>((a,b)->a.timeStamp - b.timeStamp);
        HashSet<Integer> followees=followeesMap.get(userId);

        if(followees!=null)
        {
            for(Integer followee:followees)
            {
                List<Tweet> tweets=tweetMap.get(followee);
                if(tweets!=null)
                {
                    /*for(Tweet tweet:tweets)
                    {
                        pq.add(tweet);
                        if(pq.size() > 10)
                        pq.poll();
                    }*/

                    //optimized loop
                    for(int i=tweets.size() - 1;i>=tweets.size() - 10 && i >=0;i--)
                    {
                        Tweet tweet=tweets.get(i);
                        pq.add(tweet);
                        if(pq.size() > 10)
                        {
                            pq.poll();
                        }
                    }


                }
            }
        }

        List<Integer> result=new ArrayList<>();


        while(!pq.isEmpty())
        {
            result.add(pq.poll().tweetId);
        }

        Collections.reverse(result);

        return result;
    }

    public void follow(int followerId, int followeeId) {
        if(!followeesMap.containsKey(followerId)){
            followeesMap.put(followerId,new HashSet<>());
        }
        followeesMap.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if(!followeesMap.containsKey(followerId))
        {
            return;
        }
        followeesMap.get(followerId).remove(followeeId);
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