/*
* Approach:
*  1. Start with root class Twitter, 
    which holds user following and tweets of application.

    user following is stored because tweets of the given user should be
    fetched based on current user tweets and following tweets.

    Therefore, mapping from userid to following users mapping will give O(1).
    We have to consider hashset because if one user selects follow multiple times,
    we should consider it as only one follow.

    following -> user : Hashset<user>

    tweets -> user: List<tweet>
* 
*  2. Tweet class should have id along with createdAt, because feed is based on timestamp.
* 
*  3. posttweet: create new tweet object and add to tweets with userid as key.
    getnewsfeed: get the curr user tweets along with following users tweets,    
            take latest 10 tweets.(can be done using min heap with size:10)
    follow,unfollow: can be updated by checking the following map and 
            respective hashset values.
* 
* Did this code successfully run on Leetcode : YES
* 
* Any problem you faced while coding this : NO
* 
* Time Complexity: 
        postTweet: O(1)
        follow, unfollow: O(1)
        getNewsFeed: O(nlogk)
* 
* Space Complexity: O(1)
* 
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

public class Twitter {
    class Tweet {
        int tweetId;
        int createdAt;

        public Tweet(int tweetId, int createdAt) {
            this.tweetId = tweetId;
            this.createdAt = createdAt;
        }
    };

    HashMap<Integer, List<Tweet>> tweets;

    HashMap<Integer, HashSet<Integer>> following;

    PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);

    int time;

    public Twitter() {
        this.tweets = new HashMap<>();
        this.following = new HashMap<>();
        this.time = 0;
    }

    public void postTweet(int userId, int tweetId) {
        Tweet newTweet = new Tweet(tweetId, ++time);

        if (!tweets.containsKey(userId)) {
            tweets.put(userId, new ArrayList<>());
        }

        tweets.get(userId).add(newTweet);
    }

    public List<Integer> getNewsFeed(int userId) {
        follow(userId, userId);

        Iterator<Integer> followingOfUserID = following.get(userId).iterator();

        while (followingOfUserID.hasNext()) {
            List<Tweet> userTweets = tweets.get(followingOfUserID.next());

            if (userTweets != null) {
                for (Tweet tweet : userTweets) {
                    pq.add(tweet);

                    if(pq.size()>10)
                        pq.poll();
                }
            }
        }

        List<Integer> result = new ArrayList<>();

        while(!pq.isEmpty()){
            result.add(0, pq.poll().tweetId);
        }

        return result;
    }

    public void follow(int followerId, int followeeId) {
        if (!following.containsKey(followerId)) {
            following.put(followerId, new HashSet<>());
        }

        following.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (following.containsKey(followerId)) {
            following.get(followerId)
                    .remove(followeeId);
        }
    }
}
