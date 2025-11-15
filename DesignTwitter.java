// Time Complexity : For postTweet: O(1)
// for follow and unfollow: O(1)
// for getNewsFeed: O(n × 10 × log 10) = O(n log 10) = effectively O(n) where n is the number of followees
// Space Complexity : O(N) where N is the sum of  number of tweets, number of followers and number of users.
// Did this code successfully run on Leetcode : Yes
// Approach : We maintain a usersFollowMap for userIds mapped to their follower userIds, tweetMap for userId mapped to the tweets object.
// For follow or follow we check if usersFollowMap contains key and then add or remove. For postTweet, we self follow first and add to
// tweetMap object. For getNewsFeed, maintain a min heap and extract the tweets for each follower. Consider only top 10 for each follower
// so that we dont add extra time complexity.


class Twitter {

    HashMap<Integer, HashSet<Integer>> usersFollowMap; //hashmap to store each user as key and followers as HashSet values
    HashMap<Integer, List<Tweets>> tweetMap;//hashmap to store each userId as key and mapped tweetIds as values.
    int timeStamp;

    public Twitter() {
        this.usersFollowMap = new HashMap<>();
        this.tweetMap = new HashMap<>();
    }

    class Tweets{
        int tweetId;
        int timeStamp;

        public Tweets(int tweetId, int timeStamp) {
            this.tweetId = tweetId;
            this.timeStamp = timeStamp;
        }
    }

    public void postTweet(int userId, int tweetId) {
        follow(userId, userId); //follow self
        if(!tweetMap.containsKey(userId)){
            tweetMap.put(userId, new ArrayList<>()); //add the userId to tweetMap
        }
        tweetMap.get(userId).add(new Tweets(tweetId, timeStamp));//add the tweet object to the tweetMap
        timeStamp++;
    }

    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweets> queue = new PriorityQueue<>((a,b) -> a.timeStamp - b.timeStamp); //min heap
        HashSet<Integer> followers = usersFollowMap.get(userId); //HashSet of followers for a userId
        if(followers != null){
            for(int follower : followers){
                List<Tweets> tweets = tweetMap.get(follower); //tweets of each follower
                if(tweets != null){
                    int size = tweets.size();
                    for(int i = size-1;i >= 0 && i > size - 11;i--){ //consider only top 10 recent tweets
                        Tweets tweet = tweets.get(i);
                        queue.add(tweet);
                        if(queue.size() > 10){ //maintain 10 tweets in the priority queue
                            queue.poll();
                        }
                    }
                }

            }
        }
        List<Integer> result = new ArrayList<>();
        while(queue.size() > 0){
            result.add(0,queue.poll().tweetId); //add at 0th index to get in the decreasing order
        }
        return result;
    }

    public void follow(int followerId, int followeeId) {
        if(!usersFollowMap.containsKey(followerId)){
            usersFollowMap.put(followerId, new HashSet<>()); //new user
        }
        usersFollowMap.get(followerId).add(followeeId); //add follower
    }

    public void unfollow(int followerId, int followeeId) {
        if(usersFollowMap.containsKey(followerId)){
            usersFollowMap.get(followerId).remove(followeeId); //remove follower
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