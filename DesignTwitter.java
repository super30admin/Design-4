/*
* In design problems, always think about the bigger entity. 
- So to maintain the follower and followee id, we can maintain a map
    key: to store user id -> integer
    value: to store followerID's. We can maintain a set here because one follower cannot follow one followee multiple times
- We also need to maintain a map for the tweets that users are posting
    key: userID
    value: tweetID
- We can create a tweet object which will have the id, the timestamp, text, etc for each tweets
    - we dont need userID in the tweet object because we already have a map for userid to tweet id mapping, so storing user will create redundant data
- To get top 10 news feed data, we can use heap, apply the concept of merge K sorted lists to get the news feed in O(nlogk) time where n is the number of users and k = 10 here so its O(n)

 */

class Twitter {
    HashMap<Integer, HashSet<Integer>> followMap;
    HashMap<Integer, List<Tweet>> tweetMap;
    int time;

    class Tweet{
        int id;
        // in production ofc use timestamp
        int timeStamp;

        public Tweet(int id, int time){
            this.id = id;
            this.timeStamp = time;
        }
    }

    public Twitter() {
        this.followMap = new HashMap<>();
        this.tweetMap = new HashMap<>();
        this.time = 0;
    }
    
    // TC: O(1) -> map search
    public void postTweet(int userId, int tweetId) {
        Tweet t = new Tweet(tweetId, time++);
        if(!tweetMap.containsKey(userId)){
            tweetMap.put(userId, new ArrayList<>());
        }
        tweetMap.get(userId).add(t);

        // once the user has started posting the tweets, they should see their own tweets as well so they have to follow themselves as well
        follow(userId, userId);
        
    }
    
    // TC: O(nlogk) -> n is number of users & k is 10 (10 latest feeds) ->log 10 constant so O(n)
    public List<Integer> getNewsFeed(int userId) {
        // custom comparator based on timestamp comparison as we want latest tweets
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.timeStamp - b.timeStamp);
        HashSet<Integer> followee = followMap.get(userId);
        //handle case where there are no followers, so no news feed
        if(followee != null){
            for(Integer f : followee){
                List<Tweet> tweets = tweetMap.get(f);
                if(tweets != null){
                    // we only want to get last 10 tweets from the list of tweets
                    for(int i = tweets.size() - 1; i >= tweets.size() - 10 && i >= 0 ; i--){
                        pq.add(tweets.get(i));
                        if(pq.size() > 10){
                            pq.poll();
                        }
                    }
                }
            }
        }
        List<Integer> latestNewsFeed = new ArrayList<>();
        while(!pq.isEmpty()){
            latestNewsFeed.add(pq.poll().id);
        }

        Collections.reverse(latestNewsFeed);
        return latestNewsFeed;
    }
    // TC: O(1) -> map search
    public void follow(int followerId, int followeeId) {
        // if the userid doesnt belong in the map, add it to the map, create a new set, and then add followeeid to the map
        if(!followMap.containsKey(followerId)){
            followMap.put(followerId, new HashSet<>());
        }
        followMap.get(followerId).add(followeeId);
    }
    // TC: O(1) -> map search
    public void unfollow(int followerId, int followeeId) {
        if(followMap.containsKey(followerId)) {
            followMap.get(followerId).remove(followeeId);
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