/*
 * The following program provides a blueprint of a mock Twitter class with basic functions. The basic functions implemented are:
 * 1. Follow another user
 * 2. Unfollow another user
 * 3. Post a tweet
 * 4. Get the newsfeed for a particular user
 * 
 * Did this code run on leetcode: Yes
*/
class Twitter {
    // We create maps for keeping a track of all the users and who they are following, and also for mapping all the tweets posted by
    // a user to that user. We also keep track of the time on the server to help timestamp the tweets.
    HashMap<Integer, HashSet<Integer>> followList;
    HashMap<Integer, List<Tweet>> tweets;
    int serverTime;
    //Creating a nested class Tweet, to record the ID and timestamp of a tweet
    class Tweet{
        int id;
        int timestamp;
        
        public Tweet(int id, int timestamp)
        {
            this.id = id;
            this.timestamp = timestamp;
        }
    }
    public Twitter() {
        this.followList = new HashMap<>();
        this.tweets = new HashMap<>();
        serverTime = 0;
    }
    //Time Complexity: O(1)
    public void postTweet(int userId, int tweetId) {
        //If the user has never tweeted before, we make the user follow themselves so that they can see their own tweets on their feed.
        //This function also creates a new user if the user does not exist yet
        follow(userId, userId);
        if(!tweets.containsKey(userId))
            tweets.put(userId, new ArrayList<>());
        
        tweets.get(userId).add(new Tweet(tweetId, serverTime++));
        
    }
    /*This function returns only the top 10 most recent tweets aggregated from all the users our current user is following,
    including themself. The aggregated tweets are processed through a MinHeap, whose ordering is based on the timestamp of
    the tweets, and the tweets with the higher timestamp values are filtered out.

    Time Complexity: O(N(log(K))), N = total number of tweets aggregated, log(K) = time taken to add a tweet on the heap with K = 10
    Space Complexity: O(N), N = number of users
    */
    public List<Integer> getNewsFeed(int userId) {
        
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.timestamp - b.timestamp);
        
        HashSet<Integer> feed = followList.get(userId);
        //Get users only if the user is following other users
        if(feed != null)
        {
            for(int uid: feed)
            {
                List<Tweet> recent = tweets.get(uid);
                //Get tweets only if the followed user has posted atleast one tweet
                if(recent != null)
                {   //Get only the 10 most recent tweets posted by each user on the feed, instead of getting all of their tweets 
                    if(recent.size() >= 10)
                    {
                        for(int i = recent.size() - 1; i >= recent.size() - 10; i--)
                        {   Tweet tweet = recent.get(i);
                            pq.add(tweet);

                            if(pq.size() > 10)
                                pq.poll();
                        }
                    }
                    else
                    {
                        for(Tweet tweet : recent)
                        {
                            pq.add(tweet);
                            if (pq.size() > 10) 
                                pq.poll();
                        }
                    }
                }
            }
        }
        
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty())
            result.add(0, pq.poll().id);
        
        return result;
        
    }
    /*In this function we simply add a user the current user wants to follow, onto the following map entry of that user. We also
    create a new user if they do not currently exist.

    Time Complexity: O(1)
    */
    public void follow(int followerId, int followeeId) {
        if(!followList.containsKey(followerId))
            followList.put(followerId, new HashSet<>());
        
        followList.get(followerId).add(followeeId);
    }
    /*In this function we simply remove a user the current user wants to unfollow, from the following map entry of that user. 

    Time Complexity: O(1)
    */
    public void unfollow(int followerId, int followeeId) {
        if(followList.containsKey(followerId) && followerId != followeeId)
            followList.get(followerId).remove(followeeId);
    }
}

