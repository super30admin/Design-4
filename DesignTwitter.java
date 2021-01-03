/*
Approach:

Here we would use 3 data structures:
1) class: a class Tweet to hold properties tweet id and creation time of a tweet
2) Hashmap1: a hashmap to hold the data of user and his/her followees. Here values would be a hashset so that we can retrieve any follower in O(1) time complexity
3) Hashmap2: a hashmap to hold the tweets data of an user
4) Priority queue: a min heap to get the top 10 feeds for an user

Why min heap?
First of all here, what value of time should be considered younger and what value should be considered elder. If thought logically, bigger the value of time younger it is and vice versa. 

So if we maintain a mean heap of size 10, then at any point in time it will hold the latest/youngest tweets that it has encountered uptill now. So if we feed him all the tweets from the followers of the given user then after it has added them (and removed them when capacity is reached) it will have the latest 10 tweets inside it. 

Now that the data structures and logic behind using them is clear, everything else is simple.

1) Post a tweet: Just the add the value tweet with its properties to the key user 
2) Get news feed: Set up priority queue with capacity of 10. Get followers of users. Get tweets of followers and keep adding them to priority queue. In the end return all the feed in priority queue.
3) Follow: Add followee into the list of followers of user
4) Unfollow: Remove followee from the list of followers of user

Time complexity: O(n*mlog10); where n = no of followers, m = avg no tweets done by an user. Adding into the queue takes mlog10
Space complexity: O(max(tweets, pqSize));   
*/

class Twitter { 
    class Tweet{
        int tweetId;
        int creationTime;
        Tweet(int tweetId, int creationTime){
            this.tweetId = tweetId;
            this.creationTime = creationTime;
        }
    }
    
    HashMap<Integer, HashSet<Integer>> followed;
    HashMap<Integer, List<Tweet>> tweets;  
    int time;
    
    /** Initialize your data structure here. */
    public Twitter() {
        followed = new HashMap<>();
        tweets = new HashMap<>();
    }
    
    /** Compose a new tweet. */
    // Time complexity: O(1)
    public void postTweet(int userId, int tweetId) {
        if (!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<>());   
        }
        tweets.get(userId).add(new Tweet(tweetId, time++));
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    // Time complexity: O(n*mlog10); where n = no of followers, m = avg no tweets done by an user. Adding into the queue takes mlog10
    public List<Integer> getNewsFeed(int userId) {
        follow(userId, userId);
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.creationTime - b.creationTime);
        Set<Integer> fids = followed.get(userId);
        if (fids != null){
            for (Integer fid: fids){
                List<Tweet> tws = tweets.get(fid);
                if(tws != null){
                    for (Tweet tw: tws){
                        pq.add(tw);
                        if (pq.size() > 10){
                            pq.remove();
                        }
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while (!pq.isEmpty()){
            result.add(0, pq.poll().tweetId);
        }
        return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    // Time complexity: O(1)
    public void follow(int followerId, int followeeId) {
        if(!followed.containsKey(followerId)){
            followed.put(followerId, new HashSet<>());
        }
        followed.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    // Time complexity: O(1)
    public void unfollow(int followerId, int followeeId) {
        if(followed.containsKey(followerId)){
            followed.get(followerId).remove(followeeId);
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