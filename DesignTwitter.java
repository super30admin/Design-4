// Time Complexity : For each tweet, postTweet -> O(1), follow -> O(1), unfollow -> O(1), getNewsFeed -> O(nm +  mlog k) = O(m) where m is the number of tweets and n is the number of users
// Space Complexity : O(n + n) for Users Hashmap and O(n + m) where n is the number of users and m is number of tweet objects for tweet HashMap.
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : Coming up with traversal of getNewsfeed and designing of the different scenarios
/* Algorithm: Whenever, a tweet is posted, ensure that the user is in users database which we are maintaining in user hashmap and tweets database
that contains users along with their tweet objects. Check if the user Id is present in both the hashmaps, if not add the relevant Id to the hashmaps.
If user 1 follows user 2, add the user 2 to the hashset (to avoid duplicate followers and O(1) add/remove) if not present, similarly, remove at
O(1) if user 1 unfollows user 2. To get the news feed, we will have top down approach where we can fetch the user Id along with its followee. we
can traverse through the followee tweets and put upto feedsize number of tweets in the priority queue. Then we fetch the top 10 number of tweets
for the user Id mentioned. 
*/
class Twitter {
    class Tweet{
        int time;                                                       // Tweet object with timestamp and tweet id
        int id;
        Tweet(int t, int Id){
            this.time = t;
            this.id = Id;
        }
    }
    /** Initialize your data structure here. */
    HashMap<Integer, HashSet<Integer>> users;                                           // Users database
    HashMap<Integer, List<Tweet>> tweets;                                               // Tweets database
    public static int timestamp =0;                                                         // Timestamp increments with every tweet
    int feedsize = 10;
    public Twitter() {
         users = new HashMap<>();
         tweets = new HashMap<>();
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
            if(!users.containsKey(userId)){
                HashSet<Integer> uId = new HashSet<>();                              // if the user Id is not there, how will it post the tweet?
                users.put(userId, uId);                                                // Add the user to the database first
            }
        users.get(userId).add(userId);
        if(!tweets.containsKey(userId)){
            List<Tweet> t_id = new ArrayList<>();
            t_id.add(new Tweet(timestamp++, tweetId));                                  // Similarly add the same user with the tweet object
            tweets.put(userId, t_id);
        } else {
            tweets.get(userId).add(new Tweet(timestamp++, tweetId));                // User is tweeting more nowadays
        }
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> temp = new ArrayList<Integer>();
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b)->a.time - b.time);                                  // Ascending order of timestamp of tweets
        HashSet<Integer> following = users.get(userId);
        if(following != null){
            for(int fId : following){                                               // For each of the user followers
                List<Tweet> t = tweets.get(fId);
                if(t != null){
                for(Tweet post: t){                                                 // For each of the tweets of their followers
                    if(pq.size() < feedsize){                                       // If PQ is not filled
                    pq.add(post);
                    } else {
                        if(post.time >= pq.peek().time){                                // If the incoming tweet is latest than the root of PQ tweet
                            pq.poll();
                            pq.add(post);                                           // Add to the PQ
                        }
                    }
                }
                }
            }
        }
        while(!pq.isEmpty()){
            temp.add(0, pq.poll().id);                                              // Add to tmp in descending order of timestamp of tweets
        }
        return temp;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!users.containsKey(followerId) && followerId != followeeId){                                 // If user is not in database
            HashSet<Integer> u_id = new HashSet<>();
            users.put(followerId, u_id);
        }
         users.get(followerId).add(followeeId);                                             // Add the user and the followee to the hashmap
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
           if(users.containsKey(followerId) && followerId != followeeId){
            users.get(followerId).remove(followeeId);                                                               // Remove as required
        } 
    }
}