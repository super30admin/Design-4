class Twitter {

    class Tweets
    {
        int id;
        int createdAt;

        public Tweets(int id, int time)
        {
            this.id = id;
            this.createdAt = time;
        }
    }

    // This will keep track of times when the tweets are created
    int time;

    HashMap<Integer, HashSet<Integer>> followers;
    HashMap<Integer, List<Tweets>> user_and_tweets;

    public Twitter() {
        
        followers = new HashMap();
        user_and_tweets = new HashMap();
    }
    
    public void postTweet(int userId, int tweetId) {
        
        if(!user_and_tweets.containsKey(userId)) user_and_tweets.put(userId, new ArrayList());

        // time here is a global variable and post increment is happening, so we are putting the tweet id at time 0
        // and this goes on for other tweet objects
        user_and_tweets.get(userId).add(new Tweets(tweetId, time++));
    }
    
    public List<Integer> getNewsFeed(int userId) {

        // Add the same user into the set so that he himself follows. This would be helpful for get news feed
        follow(userId, userId);
        
        // We will store tweet objects into min heap but depending upon it's created time
        PriorityQueue<Tweets> heap = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);

        // Get the list of users from followers list
        HashSet<Integer> users = followers.get(userId);
        
        for(int user : users)
        {
            List<Tweets> all_tweets = user_and_tweets.get(user);
            // Add all the tweets posted by this user in to heap

            if(all_tweets != null)
            {
                for(Tweets tweet_object : all_tweets)
                {
                    heap.add(tweet_object);

                    // If heap exceeds storing more than 10 tweets remove the minimum one from it
                    if(heap.size() > 10) heap.poll();
                }
            }
        }

        List<Integer> result = new ArrayList();

        // Pop the remaining tweet objects from the heap and store the tweet id's from it
        // We are adding at 0th index bcoz in output they are expecting us to return in the order from most recent to 
        // least recent
        while(!heap.isEmpty()) result.add(0, heap.poll().id);

        return result;
    }
    
    public void follow(int followerId, int followeeId) {
        
        if(!followers.containsKey(followerId))
        {
            followers.put(followerId, new HashSet());
        }

        followers.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        
        // We should be sure that we are not unfollowing ourselves
        if(followers.containsKey(followerId) && followerId != followeeId)
            followers.get(followerId).remove(followeeId);
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