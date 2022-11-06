//approach -1
//user Hashmap for followers map, and another for user and their tweets. 
//define a class for Tweets and 2 variables will be tweetid and timestamp.
//will use priority queue - min heap - of size 10 and that will be our answer. 

class Twitter {
    
    class Tweet{
        int id;
        int createdAt;
        public Tweet(int tweetid, int time){
            this.id = tweetid;
            this.createdAt = time;
        }
    }
    
    int time;
    Map<Integer, List<Tweet>> tweets;
    Map<Integer, HashSet<Integer>> followers;
   
    PriorityQueue<Tweet> pq;

    public Twitter() {
        time =0;
        tweets = new HashMap<>();
        followers = new HashMap<>();
        
    }
    
    public void postTweet(int userId, int tweetId) {
        if(!tweets.containsKey(userId))
        {
            tweets.put(userId, new ArrayList<>());
        }
        Tweet tweet = new Tweet(tweetId, time++);
        tweets.get(userId).add(tweet);
    }
    
    public List<Integer> getNewsFeed(int userId) {
        follow(userId,userId);  // user follows himself to get the feeds
        pq = new PriorityQueue<>((a,b)-> a.createdAt - b.createdAt); //resets it every time! a-b is min heap based on timestamp
        
        List<Integer> result = new ArrayList<>();
        //get the follower list from followers
        HashSet<Integer> followees = followers.get(userId);
        //traverse through the set, and for every user, traverse through their tweeets
        if(followees == null) return result;
        
        for(int followeeId : followees)
        {
            List<Tweet> usersTweets = tweets.get(followeeId);
            
            if(usersTweets != null)
            {
                for(Tweet tweet : usersTweets)
                {
                    pq.add(tweet);
                    
                    if(pq.size() > 10)
                    {
                        pq.poll();
                    }
                }
            }
            
        }
        
        // I come here when we are done traversing through al the tweets for ll users and then we extract from queue and add to the list. 
        
        while(!pq.isEmpty())
        {
            Tweet temp = pq.poll();
            result.add(0,temp.id); // all recent one will be added to zero and it will shift the previous one to the right. 
        }
        
        return result;
    }
    
    public void follow(int followerId, int followeeId) {
        if(!followers.containsKey(followerId))
        {
            followers.put(followerId, new HashSet<>());
        }
        followers.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(followers.containsKey(followerId) && followerId != followeeId)
        {
            followers.get(followerId).remove(followeeId);
        }
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

//tc - follow, unfollow, post - O(1) ; getFeed -O(N log k) = O(N) ; N= mn; m = users, n= avg tweet by 1 user, kis 10 so negligible
//sc -follow, unfollow one hashmap O(M) -> m users, m hashset;  M = m*m, post - O(N) = N =m*n ; m= users, n = tweets 




//approach-2
//take a user class and define all the user - tweet relation there
//for one user there is an arraylist of <Tweet>; tweet is class!, so no tweets map
//and rest of the things as it is. 
//here we are just fetching objects and dealing with it. 
//also in user class , have an hashset for each user to add the followees and get rid of that map as well!
//this is just a diff approach not saving space!! 