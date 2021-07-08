package Design4;

import java.util.*;

public class question85_DesignTwitter {
    /* Created by palak on 7/8/2021 */

    class Tweet {
        int tweetId;
        int createdAt;
        public Tweet(int tweetId, int createdAt) {
            this.tweetId = tweetId;
            this.createdAt = createdAt;
        }
    }


    /** Initialize your data structure here. */
    HashMap<Integer, HashSet<Integer>> followed; //users I follow is maintained in this map.
    HashMap<Integer, List<Tweet>> tweets;
    int time;
    public question85_DesignTwitter() {
        followed = new HashMap<>();
        tweets = new HashMap<>();
    }

    /** Compose a new tweet. */
    // O(1)
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if(!tweets.containsKey(userId)) {
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId, time++));
    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        //Get all the users
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);
        //Now get the list of all the users/ people I follow in a list
        HashSet<Integer> fIds = followed.get(userId);
        if(fIds != null) {
            for(Integer fId: fIds) {
                List<Tweet> fTweets = tweets.get(fId);
                if(fTweets != null) {
                    for(Tweet ftweet: fTweets) {
                        pq.add(ftweet);
                        if(pq.size() > 10) {
                            pq.poll();
                        }
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()) {
            result.add(0, pq.poll().tweetId);
        }
        return result;
    }

    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    // O(1)
    public void follow(int followerId, int followeeId) {
        if(!followed.containsKey(followerId)) {
            //then I will create one
            followed.put(followerId, new HashSet<>());
        }
        // If the follower is already available. We need to get the from the map and then add the followee (user I am following) in the hashset in the Map.
        followed.get(followerId).add(followeeId);
    }

    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    // O(1)
    public void unfollow(int followerId, int followeeId) {
        // I am always going to follow myself, thus I need to check for the other followee Ids.
        if(followed.containsKey(followerId) && followerId != followeeId) {
            Set<Integer> set = followed.get(followerId);
            set.remove(followeeId);
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