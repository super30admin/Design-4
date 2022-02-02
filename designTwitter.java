// Time Complexity :O(1) for postTweet, follow, unfollow and O(log k) for getNewsFeed where k is the no of tweets needed
// Space Complexity :O(n) where n in the no. of users
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach
// We use a hashMap to implement follow-unfollow functionality
// Map<Integer, HashSet> for user-Id, hashSet of Ids of followers

// Class Tweet for Id and time of tweet
// We use a hashMap to implement postTweet() functionality
// Map<Integer, List<Integer>> for user-Id, List of Tweets by the user

// For getNewsFeed() functionality, we use a min heap to store the latest k tweets
class Twitter {

    class Tweet {
        int tid;
        int createdAt;

        public Tweet(int tid, int createdAt) {
            this.tid = tid;
            this.createdAt = createdAt;
        }
    }

    Map<Integer, HashSet<Integer>> userMap;
    Map<Integer, List<Tweet>> tweetMap;
    int time;

    public Twitter() {
        userMap = new HashMap<>();
        tweetMap = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        Tweet obj = new Tweet(tweetId, time++);

        if(!tweetMap.containsKey(userId)) {
            tweetMap.put(userId, new ArrayList<>());
        }
        tweetMap.get(userId).add(obj);
    }

    public List<Integer> getNewsFeed(int userId) {
        follow(userId, userId);
        HashSet<Integer> users = userMap.get(userId);
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);

        for (Integer user: users) {
            List<Tweet> alltweets = tweetMap.get(user);
            if (alltweets != null) {
                for (Tweet tweet: alltweets) {
                    pq.add(tweet);
                    if (pq.size() > 10) {
                        pq.poll();
                    }
                }
            }
        }

        List<Integer> result = new ArrayList<>();
        while (pq.size()>0) {
            result.add(0, pq.poll().tid);
        }

        return result;
    }

    public void follow(int followerId, int followeeId) {
        if(!userMap.containsKey(followerId)) {
            userMap.put(followerId, new HashSet<>());
        }
        userMap.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (userMap.containsKey(followerId) && followerId != followeeId) {          //(followerId != followeeId) is present because a user cannot unfollow himself
            userMap.get(followerId).remove(followeeId);
        }
    }
}
