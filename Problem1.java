// Problem 1 : Design Twitter
// Time Complexity : 
/*
 * postTweet() - O(1)
 * getNewsFeed() - O(N) where N is the number of user
 * follow() - O(1)
 * unfollow - O(1)
 */
// Space Complexity : 
/*O(N*max(F,T)) 
where N is the number of the user, F is the average number of the followers and T is the average number of tweets
*/
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this :
/*
None
*/

// Your code here along with comments explaining your approach
class Twitter {
    // creating class Tweet which will store tweet id and created time 
    class Tweet {
        int tid;
        int createdAt;
        public Tweet(int tid, int createdAt) {
            this.tid = tid;
            this.createdAt = createdAt;
        }
    }

    // creating two hashmap 
    // followed hashmap contains mapping of userid and hashset of users id to which the follow
    private HashMap<Integer, HashSet<Integer>> followed;
    // tweets hashmap will contain mapping of user id and list of tweet which the user has posted
    private HashMap<Integer, List<Tweet>> tweets;
    // time variable which will be used when creating the tweets
    private int time;

    public Twitter() {
        // initialize the followed and tweets hashmap
        this.followed = new HashMap<>();
        this.tweets = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        // check if the user id is present in tweets hashmap or not
        if (!tweets.containsKey(userId)) {
            // if user is not present then create an entry for the user and call follow
            tweets.put(userId, new ArrayList<>());
            follow(userId, userId);
        }
        // add the object of Tweet containing value tweetid and time in the list of Tweet for userid
        tweets.get(userId).add(new Tweet(tweetId, time));
        // increment the timer
        time++;
    }
    
    public List<Integer> getNewsFeed(int userId) {
        // creating a min heap of Tweet which will store 10 most recent tweet
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b)-> a.createdAt - b.createdAt);
        // get the list of the user ids to which userId is following
        HashSet<Integer> followeds = followed.get(userId);
        if(followeds != null) {
            // loop through every user in followed
            for (int fuserId : followeds) {
                // get the tweets of that user
                List<Tweet> ftweets = tweets.get(fuserId);
                if(ftweets != null) {
                    // get the minimum number between ftweets size and 11 
                    int K = Math.min(ftweets.size(), 11);
                    // loop from K-1 since we need 10 most recent tweet and 
                    // make sure i and k both are greater than 0
                    for (int i = K-1; i >= 0 && K >=0; i--) {
                        // add tweets to priority queue 
                        pq.add(ftweets.get(i));
                        // decrement K
                        K--;
                        // if the size of the priority queue is greater than 10 then pop the ifrst element
                        if(pq.size() > 10){
                            pq.poll();
                        }
                    }
                }
            }
        }

        // initialisze the result array
        ArrayList<Integer> result = new ArrayList<>();
        // add elements from priority queue
        while(!pq.isEmpty()) {
            // add the tweet id to the first position of the result array
            result.add(0, pq.poll().tid);
        }
        // return result
        return result;
    }
    
    public void follow(int followerId, int followeeId) {
        // checking if we have entry for followerId in the followed hashmap
        if (!followed.containsKey(followerId)) {
            // if there is no entry then creat an entry in hashmap and initialize the hashSet for followee user
            followed.put(followerId, new HashSet<>());
        }
        // add the followeeId to the HashSet of the followerId 
        followed.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        // check if the user is in hashmap and value of followerId and followeeId is not same
        if(followed.containsKey(followerId) && followerId != followeeId) {
            // it the condition is true then remove the followerId from the HashSet
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