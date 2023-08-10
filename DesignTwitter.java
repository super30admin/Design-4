package org.example;

// Time Complexity : //O(N log k) => O(N)  since log k is very small -> k is the timelineLimit and N is the number of all tweets from all followees
// Did this code successfully run on Leetcode : Yes

import java.util.*;

public class DesignTwitter {
    int timeStamp;

    class Tweet{
        int tweetId;
        int time;

        public Tweet(int tweetId, int time){
            this.tweetId = tweetId;
            this.time = time;
        }
    }

    HashMap<Integer, HashSet<Integer>> followingByUserMap;
    HashMap<Integer, List<Tweet>> tweetsMap;

    public DesignTwitter() {
        this.followingByUserMap = new HashMap<>();
        this.tweetsMap = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {  //O(1)
        follow(userId,userId);
        Tweet newTweet = new Tweet(tweetId, timeStamp++);
        if(!tweetsMap.containsKey(userId))
        {
            tweetsMap.put(userId, new ArrayList<>());
        }
        tweetsMap.get(userId).add(newTweet);

    }

    public List<Integer> getNewsFeed(int userId) {

        int timeLineSize = 10;
        List<Integer> result = new ArrayList<>();
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b)->a.time-b.time);

        HashSet<Integer> followeeIds = followingByUserMap.get(userId);

        if(followeeIds!=null)
        {
            for(Integer followee: followeeIds)    //O(N log k) -> k is the timelineLimit and N is the number of all tweets from all followees
            {
                List<Tweet> tweets = tweetsMap.get(followee);
                if(tweets!=null)
                {
                    for(Tweet tweet: tweets)
                    {
                        pq.add(tweet);
                        if(pq.size()>timeLineSize)
                        {
                            pq.poll();
                        }
                    }
                }
            }
        }

        while(!pq.isEmpty())
        {
            result.add(0,pq.poll().tweetId);
        }
        return result;
    }

    public void follow(int followerId, int followeeId) {  //O(1)
        if(!followingByUserMap.containsKey(followerId))
        {
            followingByUserMap.put(followerId, new HashSet<>());
            follow(followerId,followerId);
        }
        followingByUserMap.get(followerId).add(followeeId);
        //follow(followerId,followerId);
    }

    public void unfollow(int followerId, int followeeId) {  //O(1)

        if(followingByUserMap.containsKey(followerId) && followerId != followeeId)
        {
            followingByUserMap.get(followerId).remove(followeeId);
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