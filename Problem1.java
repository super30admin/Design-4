// Time Complexity : O(1) for postTweet, follow and unfollow, O(m * n) where m is the number of
// user a person follows and n is the number of tweets for each follower
// Space Complexity : O(n) for postTweet and follow, O(1) for unfollow, O(m + n) where m is the number
// user a person follows and n is the number of tweets for each followers
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

public class Problem1 {
    class Tweet{
        int id;
        int createTime;

        public Tweet(int id, int createTime){
            this.id = id;
            this.createTime = createTime;
        }
    }

    int time;
    HashMap<Integer, HashSet<Integer>> followers;
    HashMap<Integer, List<Tweet>> tweets;
    public Twitter() {
        followers = new HashMap<>();
        tweets = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        if(!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId, time++));
    }

    public List<Integer> getNewsFeed(int userId) {
        follow(userId, userId);
        HashSet<Integer> allFollowers = followers.get(userId);
        if(allFollowers == null){
            return new ArrayList<>();
        }

        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createTime - b.createTime);
        for(Integer follower : allFollowers){
            List<Tweet> allTweets = tweets.get(follower);
            if(allTweets == null){
                continue;
            }
            for(Tweet tweet : allTweets){
                pq.add(tweet);
                if(pq.size() > 10){
                    pq.poll();
                }
            }
        }

        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0, pq.poll().id);
        }

        return result;
    }

    public void follow(int followerId, int followeeId) {
        if(!followers.containsKey(followerId)){
            followers.put(followerId, new HashSet<>());
        }
        followers.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if(followers.containsKey(followerId) && followerId != followeeId){
            followers.get(followerId).remove(followeeId);
        }
    }
}
