// Time Complexity : O(1) for follow, unfollow and postTweet, O(n * k * log(n * k) + log(n * k)) where n is the number of users followed and k is the max feed size which is 10
// Space Complexity : O(users+tweets) for the maps
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

// Approach : I used hashmaps to store follow list and tweet list, I also defined an object Tweet to store tweet along with its timestamp for getting latest ones. For follow, unfollow and post it is very simple, directly as per requirement I add/remove to the maps. For the feed, I get the user's following list, and from each of those users, I add a maximum of 10 tweets in reverse order to only get latest tweets - I add all of this to maxheap and then take out 10 latest tweetIds.

class Twitter {
    HashMap<Integer, HashSet<Integer>> usersFollowingList;
    HashMap<Integer, List<Tweet>> tweetsPostedByUser;
    int timestamp;
    public Twitter() {
        usersFollowingList = new HashMap<>();
        tweetsPostedByUser = new HashMap<>();
        timestamp = 0;
    }
    
    public void postTweet(int userId, int tweetId) {
        Tweet tweet = new Tweet(tweetId, timestamp++);
        tweetsPostedByUser.putIfAbsent(userId, new ArrayList<>());
        tweetsPostedByUser.get(userId).add(tweet);
    }
    
    public List<Integer> getNewsFeed(int userId) {
        ArrayList<Integer> feed = new ArrayList<>();
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b)->b.timestamp-a.timestamp);

        usersFollowingList.putIfAbsent(userId, new HashSet<>());
        usersFollowingList.get(userId).add(userId);

        for(int followedUser : usersFollowingList.get(userId)){
            tweetsPostedByUser.putIfAbsent(followedUser, new ArrayList<>());
            List<Tweet> tweetPostedByFollowedUser = tweetsPostedByUser.get(followedUser);
            for(int i = tweetPostedByFollowedUser.size()-1; i>=0 && i>=tweetPostedByFollowedUser.size()-10; i--){
                pq.offer(tweetPostedByFollowedUser.get(i));
            }
        }
        for(int i = 1; i<=10; i++){
            if(pq.isEmpty()) break;
            feed.add(pq.poll().tweetId);
        }
        return feed;
    }
    
    public void follow(int followerId, int followeeId) {
        usersFollowingList.putIfAbsent(followerId, new HashSet<>());
        usersFollowingList.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(!usersFollowingList.containsKey(followerId) || !usersFollowingList.get(followerId).contains(followeeId) || followerId==followeeId) return;
        usersFollowingList.get(followerId).remove(followeeId);
    }

    class Tweet{
        int tweetId;
        int timestamp;
        public Tweet(int tweetId, int timestamp){
            this.tweetId = tweetId;
            this.timestamp = timestamp;
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