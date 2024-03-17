// Time Complexity : O(N * log k), N = total number of tweets from user's followers. k = 10, which is constant so TC = N
//  O(n * log k ) - approach 2 -  we can achieve this by only processing the last k nodes of list. n = number of user's followers
// Space Complexity :O(h),auxiallary space -  height of the tree which will be k elements in heap.
// Did this code successfully run on Leetcode : yes
// Three line explanation of solution in plain english : Maintain a min heap of size 10, put all tweets from 

class Twitter {
    private HashMap<Integer,HashSet<Integer>> followingMap;
    private HashMap<Integer,List<Tweet>> tweetMap;
    int time;

    class Tweet{
        int id;
        int createdAt;

        public Tweet(int id, int time){
            this.id = id;
            this.createdAt = time;
        }
    }

    public Twitter() {
        this.followingMap = new HashMap<>();
        this.tweetMap = new HashMap<>();    
    }
    //TC: O(1)
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId); // follow yourself, when a user starts posting tweets.
        Tweet tweet = new Tweet(tweetId, time++);

        if(!tweetMap.containsKey(userId)){
            tweetMap.put(userId, new ArrayList<Tweet>());
        }
        tweetMap.get(userId).add(tweet);
    }
    //TC: O(N * log k) - Approach 1 , O(n * log k ) - approach 2
    public List<Integer> getNewsFeed(int userId) {
       PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt); // min heap
       HashSet<Integer> followingIds = followingMap.get(userId);
       if(followingIds != null){ // check : user can try fetching getNewsFeed without posting or following anyone
            for(int fid : followingIds){
                List<Tweet> fTweets = tweetMap.get(fid); // get all the tweet of the followers
                if(fTweets != null){ // check if a user has not tweeted

                    // Approach 1

                    
                    // for(Tweet t: fTweets){
                    //     pq.add(t);
                    //     if(pq.size() > 10){
                    //         pq.poll();
                    //     }
                    // }
                
                    // Approach 2
                    for(int i = fTweets.size() - 1; i >= 0 && i >= fTweets.size() - 10 ; i--){ // only process last 10 tweets of users
                        Tweet t  = fTweets.get(i);
                        pq.add(t);
                        if(pq.size() > 10){
                            pq.poll();
                        }
                    }
                }
                
            }
       }
       List<Integer> feed = new ArrayList<>();
       while(!pq.isEmpty()){
        feed.add(0,pq.poll().id);
       }

       return feed;
    }
    //TC: O(1)
    public void follow(int followerId, int followeeId) {
        if(!followingMap.containsKey(followerId)){
            followingMap.put(followerId, new HashSet<Integer>());
        }
        followingMap.get(followerId).add(followeeId);
    }
    //TC: O(1)
    public void unfollow(int followerId, int followeeId) {
        if(followingMap.containsKey(followerId) && followerId != followeeId){
            followingMap.get(followerId).remove(followeeId);
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