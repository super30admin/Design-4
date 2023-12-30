import java.util.*;

class Twitter {
    private HashMap<Integer, List<Tweet>> userToPostedTweetsMap;
    private HashMap<Integer, HashSet<Integer>> userToFollowingUsersMap;
    private int currentTimestamp;
    private int newsFeedSize = 10;

    private class Tweet{
        private int tweetId;
        private int createdAt;

        public Tweet(int id, int timestamp){
            this.tweetId = id;
            this.createdAt = timestamp;
        }
    }



    public Twitter() {
        this.userToPostedTweetsMap = new HashMap<>();
        this.userToFollowingUsersMap = new HashMap<>();
        this.currentTimestamp = 0;
    }

    public void postTweet(int userId, int tweetId) {
        if(!userToPostedTweetsMap.containsKey(userId)){
            //first tweet
            userToPostedTweetsMap.put(userId, new ArrayList<>());
            follow(userId, userId);
        }
        userToPostedTweetsMap.get(userId).add(new Tweet(tweetId, currentTimestamp++));
    }

    public List<Integer> getNewsFeed(int userId) {
        List<Integer> newsFeed = new ArrayList<>();
        PriorityQueue<Tweet> pq = new PriorityQueue<>((tweetA, tweetB) -> tweetA.createdAt - tweetB.createdAt);
        HashSet<Integer> listOfFollowedUsers = userToFollowingUsersMap.get(userId);
        if(listOfFollowedUsers == null || listOfFollowedUsers.isEmpty()){
            return newsFeed;
        }
        for(Integer followedUserId : listOfFollowedUsers){
            List<Tweet> tweetsPostedByFollowedUser = userToPostedTweetsMap.get(followedUserId);
            if(tweetsPostedByFollowedUser == null || tweetsPostedByFollowedUser.isEmpty()){
                continue;
            }
            int traverseListCount = Math.min(tweetsPostedByFollowedUser.size(), newsFeedSize);
            for(int i = tweetsPostedByFollowedUser.size()-1; i >= 0 && traverseListCount > 0; i--, traverseListCount--){
                Tweet tweet = tweetsPostedByFollowedUser.get(i);
                pq.add(tweet);
                if(pq.size() > newsFeedSize){
                    pq.poll();
                }
            }
        }

        while(!pq.isEmpty()){
            newsFeed.add(0, pq.poll().tweetId);
        }
        return newsFeed;
    }

    public void follow(int followerId, int followeeId) {
        if(!userToFollowingUsersMap.containsKey(followerId)){
            userToFollowingUsersMap.put(followerId, new HashSet<>());
        }
        userToFollowingUsersMap.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if(userToFollowingUsersMap.containsKey(followerId) && followeeId != followerId){
            userToFollowingUsersMap.get(followerId).remove(followeeId);
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