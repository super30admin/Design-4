
class Twitter {

    //system wide data structures

    //user vs his tweets
    HashMap<Integer, List<Tweet>> userTweets = new HashMap();
    //user vs users he follows
    HashMap<Integer, HashSet<Integer>> followed = new HashMap();

    int time;

    public Twitter() {
        userTweets = new HashMap();
        followed = new HashMap();
    }

    //now think of the smaller entities
    //user we can just maintain with an integer as a user id

    class Tweet{
        int id;
        int createdAt;

        public Tweet(int id, int t)
        {
            this.id = id;
            this.createdAt = t;
        }
    }

    public void postTweet(int userId, int tweetId) {

        //create the tweet
        Tweet t = new Tweet(tweetId, time++);

        //create list of tweets for this user if it's his first tweet
        if(!userTweets.containsKey(userId))
            userTweets.put(userId, new ArrayList());

        //add against the user in user tweet map
        userTweets.get(userId).add(t);

        //start following yourself
        follow(userId, userId);
    }

    public List<Integer> getNewsFeed(int userId) {

        //heap to prioritise tweets by timestamp
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);
        //get the list of users he followes
        HashSet<Integer> usersFollowed = followed.get(userId);
        //get tweets by each followee
        if(usersFollowed != null)
        {
            for(int followee : usersFollowed)
            {
                List<Tweet> tweets = userTweets.get(followee);
                //put the ten most recent tweets of each user in pq
                if(tweets != null)
                {
                    int n = tweets.size();
                    for(int i=n-1 ; i>=0 && i>n-11; i--)
                    {
                        pq.add(tweets.get(i));
                        //if size gets more than 10, remove the min time ones(earlier tweets)
                        if(pq.size() > 10)
                            pq.poll();
                    }
                }
            }
        }

        //put the tweets in a list and return
        List<Integer> result = new ArrayList();
        while(!pq.isEmpty())
            result.add(0, pq.poll().id); //keep adding to front (most recent first)

        return result;

    }

    public void follow(int followerId, int followeeId) {

        if(!followed.containsKey(followerId))
            followed.put(followerId, new HashSet());

        followed.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {

        //don't unfollow yourself
        if(followed.containsKey(followerId) && followeeId != followerId)
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
