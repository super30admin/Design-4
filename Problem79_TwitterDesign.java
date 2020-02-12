//Time Complexity: O(n)
//Space Complexity: O(n)

public class Twitter {

    private static class Tweet{
        int tweetId;
        int timePosted;
        
        //constructor of the class
        public Tweet(int twId, int time){
            tweetId = twId;
            timePosted = time;
        }
    }
    
    //global declarations
    static int time;
    int maxFeed;
    Map<Integer, Set<Integer>> followees;   //followeeHashMap
    Map<Integer, List<Tweet>> tweets;       //tweetHashMap

    //Initializing data structure
    public Twitter() {
        time = 0;
        maxFeed = 10;
        followees = new HashMap<>();
        tweets = new HashMap<>();
    }

    //post new tweet
    public void postTweet(int userId, int tweetId) {
        if(!tweets.containsKey(userId)) 
        {
            //if no post tweeted with the id, add
            tweets.put(userId, new LinkedList<Tweet>());
            follow(userId, userId);  //follow self
        }
        //else new tweet add with time
        tweets.get(userId).add(0, new Tweet(tweetId, time++)); 
    }


    public List<Integer> getNewsFeed(int userId) {
        //min heap to find latest tweet
        PriorityQueue<Tweet> minHeap = new PriorityQueue<>(new Comparator<Tweet>(){
            public int compare(Tweet t1, Tweet t2){
                return t1.timePosted - t2.timePosted;
            }
        });

        //add tweets of the followees
        Set<Integer> currFollowees = followees.get(userId);
        if(currFollowees != null)
        {
            for(int followeeId : currFollowees)
            {
                List<Tweet> followeeTweets = tweets.get(followeeId);
                if(followeeTweets == null) 
                    continue;
                for(Tweet t : followeeTweets){
                    if(minHeap.size() < maxFeed) 
                        minHeap.add(t);
                    else{
                        if(t.timePosted <= minHeap.peek().timePosted) 
                            break;
                        else
                        {
                            minHeap.add(t);
                            minHeap.poll(); //remove the oldest tweet
                        }
                    }
                }
            }
        }
        List<Integer> myFeed = new LinkedList<>();
        while(!minHeap.isEmpty())
        {
            myFeed.add(0, minHeap.poll().tweetId);
        }
        return myFeed;
    }

    public void follow(int followerId, int followeeId) {
        //add follower if doesnot exist
        if(!followees.containsKey(followerId)){ 
            followees.put(followerId, new HashSet<Integer>());
        }
        //else add
        followees.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        //if follower doesnot exist or follower = followee
        if(!followees.containsKey(followerId) || followerId == followeeId) 
            return; 
        //else get id and remove
        followees.get(followerId).remove(followeeId);
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