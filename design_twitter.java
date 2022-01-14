class Twitter {
    class Tweet { // we are creating a class for tweet. we name the class as tweet
        int tid; // a tweet will have a tweetid which is called as tid and we are initializing it here.
        int createdAt;//we are creating an integer variable createdat which stores the timestamp of when the tweet is created.
        public Tweet(int tid, int createdAt) { // we create a constructor which has the input values as tid and created at
            this.tid = tid;//we are bringing the tid value to the constructor
            this.createdAt = createdAt; // we are bringing the createdAt value to the constructor.
        }
    }
    int time; // we are taking a class variable called as time which we increment whenever we post a tweet.
    HashMap<Integer, HashSet<Integer>> followers; // we are creating a hashmap with name followeres for storing the followers and followees. so the input for this will be the integer(userid) as the key and the list of integers(which stores the followees) as the value.
    HashMap<Integer, List<Tweet>> tweets; // we are creating another hashmap with name tweets which takes the integer as the key input which stores the user.if and the value will be List of tweets as the inputs which are to be displayed to the user.
    public Twitter() {
        followers = new HashMap<>();// we are creating the hashmap for followers here.
        tweets = new HashMap<>();  // we are creating the hashmap for tweets here.
    }
    public void postTweet(int userId, int tweetId) {
        Tweet tw = new Tweet(tweetId, time++); // we are creating an object for tweet which is called as tw and to that we pass the tweetId and time of post increment.
        if(!tweets.containsKey(userId)) { // in the tweets hashmap we are checking if that hashmap is having the entry of the userid. if it dosent contain the user id
            tweets.put(userId, new ArrayList<>()); // we put that in the hashmap and we create a new arraylist.
        }
        tweets.get(userId).add(tw); // when we are sure that we have a user id in the tweets hashmap we get that user id add to that list, add tw.
    }
    public List<Integer> getNewsFeed(int userId) {
        follow(userId, userId); // we have to say that the user follows himself.
        HashSet<Integer> users = followers.get(userId); // this will query the first hashmap and give the Hashset present in that followers hashmap
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);/ we take a priority queue with name pq which is having an input as Tweet object. //(a,b) -> a.createdAt - b.createdAt is using a min heap which is taking the smallest values first and then the bigger values.
        for(Integer user : users) { // we go through all the users
            List<Tweet> alltweets = tweets.get(user);// we are getting the tweets posted by those users.
            if(alltweets != null) { // we check if the tweets are not equal to null
                for(Tweet tweet : alltweets) { // we go thorugh all the tweets and of each individual tweet
                    pq.add(tweet); // we add it to the priority queue.
                    if(pq.size() > 10) {// if the size of the priority queue is greater tha 10
                        pq.poll();  // we remove the first tweet.
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>(); // we create a new arraylist which stores the result of the list of integers which we get for pq.poll.
        while(!pq.isEmpty()) { // we are checking if pq is not empty
            result.add(0, pq.poll().tid); // to the result we add at index 0 the id of element of pq.poll()
        }
        return result; // in the end, we return the result
    }
    public void follow (int followerId, int followeeId) {
        if(!followers.containsKey(followerId)) { // here we are checking if the map dosent contain the follower id.
            followers.put(followerId, new HashSet<>()); // we put the follower id along with initializing a new hashset
        }
        followers.get(followerId).add(followeeId); //if we are certain that we are having a follower id in the hashmap, we add the followeeid to the respective followerid.
    }
    public void unfollow(int followerId, int followeeId) {
        if(followers.containsKey(followerId) && followerId != followeeId) { // if the map contains the follower id
            followers.get(followerId).remove(followeeId); // we get the respective follower id and remove the followee id.
        }
    }
}