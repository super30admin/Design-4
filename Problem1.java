// Time Complexity : O(1) - follow and unfollow, O(N log 10) where N is the number of tweets from all followers for getNewsFeed, postTweet - O(N) - num of tweets
// Space Complexity : O(f+t+u) -> f- number of followers, t -number of tweets, u- number of users
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach
//We use hashmaps to store the followers list and another to store the tweets of the user
//while posting tweet we check if user already has tweets existing if not create a list if yes add the new tweet to the map and increase the time and add himself as follower
//While getting the news feed store the tweets of user and his followers in Min heap and when queue size exceeds 10 pop the oldest out
// reverse the result list to get the most recent tweet at the top


class Twitter {
    int time;
    class Tweet {
        int tid;
        int timeStamp;

        public Tweet(int tid, int time) {
            this.tid = tid;
            this.timeStamp = time;
        }
    }
    HashMap<Integer, HashSet<Integer>> followeeMap;
    HashMap<Integer, List<Tweet>> tweetsMap;
    public Twitter() {
        this.followeeMap = new HashMap<>();
        this.tweetsMap = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        //check if user has tweets
        if(!tweetsMap.containsKey(userId)) {
            tweetsMap.put(userId, new ArrayList<>());
        }
        Tweet newTweet = new Tweet(tweetId, time);
        //add the tweet
        tweetsMap.get(userId).add(newTweet);
        time++;
        follow(userId, userId);
    }
    
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> result = new ArrayList<>();
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.timeStamp - b.timeStamp);
        //get the user followers
        HashSet<Integer> followees = followeeMap.get(userId);
        if(followees != null) {
            for(Integer followee: followees) {
                //get the followee tweets
                List<Tweet> tweets = tweetsMap.get(followee);
                if(tweets != null) {
                    for(Tweet tw: tweets) {
                        //add the tweets in the priority queue
                        pq.add(tw);
                        if(pq.size() > 10) {
                            pq.poll();
                        }
                    }

                }

            }
        }
        while(!pq.isEmpty()) {
            result.add(pq.poll().tid);
        }
        Collections.reverse(result);
        return result;
        
        
    }
    
    public void follow(int followerId, int followeeId) {
        if(!followeeMap.containsKey(followerId)) {
            //create a new hashset for this user to add his followers
            followeeMap.put(followerId, new HashSet());
        }
        followeeMap.get(followerId).add(followeeId); 
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(!followeeMap.containsKey(followerId)) return;
        followeeMap.get(followerId).remove(followeeId);
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
