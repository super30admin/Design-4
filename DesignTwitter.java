// Time Complexity : O(nlog10) for getNewsfeed() ~ O(n) where n is number of followess, O(1) for the rest methods
// Space Complexity : O(t+f+u) , t = number of tweets, f = number of followers, u = number of users
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no

// Your code here along with comments explaining your approach
/*
Maintain 2 maps for list of tweets and set of followers paired with their corresponding userID.We also have
 a tweet class to maintain its fields.Now,to post a tweet, we can directly check if tweetMap has an entry for
 the input user.If not, create an entry and add the tweet to his list of tweets.We also need to keep track of
 time field for each tweet.For follow & unfollow methods, we check the followeesMap for the user entry first
 and then update/remove the followee accordingly.For getting the news feed, the ask is to view the feed of the
 followees or themself and constraint is user shouldnt be following themselves.So, we iterate through the tweets
 of the user himself and his followees' tweets using both the maps.Now, we maintain a min heap to keep track of
 10 tweets and add them to the heap.At last, due to min heap properties, all the smaller timestamps will get popped out
 and recent ones remain.We add those tweets to the result from the heap, sort them and return.
 */
class Twitter {
    Map<Integer, Set<Integer>> followeesMap;
    int time;
    Map<Integer, List<Tweet>> tweetMap;

    class Tweet {
        int tweetID;
        int timestamp;

        public Tweet(int tweetID, int timestamp) {
            this.tweetID = tweetID;
            this.timestamp = timestamp;
        }
    }

    public Twitter() {
        this.followeesMap = new HashMap<>();
        this.tweetMap = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        if(!tweetMap.containsKey(userId)) {
            tweetMap.put(userId, new ArrayList<>());
        }
        Tweet tweet = new Tweet(tweetId, time);
        time++;
        tweetMap.get(userId).add(tweet);
    }

    public List<Integer> getNewsFeed(int userId) {
        Queue<Tweet> pq = new PriorityQueue<>((a, b) -> (a.timestamp - b.timestamp));

        //get the followees
        Set<Integer> followeeSet = followeesMap.get(userId);
        if(followeeSet != null) {
            //fetch tweets made by each followee
            for(Integer followee : followeeSet) {
                List<Tweet> tweetList = tweetMap.get(followee);
                //check if they made any tweets
                if(tweetList != null) {
                    for(Tweet tweet : tweetList) {
                        pq.add(tweet);

                        //as required by the problem statement, need only 10 tweets
                        if(pq.size() > 10)
                            pq.poll();
                    }
                }
            }
        }

        //fetch the tweets made by the user themself
        List<Tweet> userTweetList = tweetMap.get(userId);

        //check if they made any tweets or not
        if(userTweetList != null) {
            for(Tweet tweet : userTweetList) {
                pq.add(tweet);

                if(pq.size() > 10)
                    pq.poll();
            }
        }

        List<Integer> result = new ArrayList<>();

        //iterate the heap
        while(!pq.isEmpty()) {
            result.add(pq.poll().tweetID);
        }

        //Obtained results would be in ascending order of timestamp
        //We need recent most => descending order
        Collections.reverse(result);
        return result;
    }

    public void follow(int followerId, int followeeId) {
        if(!followeesMap.containsKey(followerId)) {
            followeesMap.put(followerId, new HashSet<>());
        }
        followeesMap.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if(!followeesMap.containsKey(followerId)) {
            return;
        }
        followeesMap.get(followerId).remove(followeeId);
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