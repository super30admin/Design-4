package S30.Design_4;

import java.util.*;

/*
Time Complexity : Follow - O(1), UnFollow - O(1), GetNewsFeed - O(NlogK) N is total posts to display
Space Complexity : O(total tweets) + O(total users)
Did this code successfully run on Leetcode : Yes
Any problem you faced while coding this : None
*/

class Twitter {

    class Tweet{
        int id;
        int createdAt;
        public Tweet(int id, int createdAt){
            this.id = id;
            this.createdAt = createdAt;
        }
    }

    HashMap<Integer,Set<Integer>> followsTable;
    HashMap<Integer,List<Tweet>> tweetsTable;
    int currentTimeStamp;
    int feedSize;

    /** Initialize your data structure here. */
    public Twitter() {
        this.currentTimeStamp = 0;
        this.followsTable = new HashMap<>();
        this.tweetsTable = new HashMap<>();
        this.feedSize = 10; // requirement

    }

    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {

        //if user doesn't exist in followsTable, add it and make it follow itself
        follow(userId,userId);

        Tweet userTweet = new Tweet(tweetId,currentTimeStamp++);

        if(!tweetsTable.containsKey(userId)){
            tweetsTable.put(userId,new ArrayList<Tweet>());
        }
        tweetsTable.get(userId).add(userTweet);
    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {

        PriorityQueue<Tweet> minHeap = new PriorityQueue<>(new Comparator<Tweet>(){

            @Override
            public int compare(Tweet t1, Tweet t2){
                return t1.createdAt - t2.createdAt;
            }

        });

        Set<Integer> followees = followsTable.get(userId);
        if(followees != null){
            for(int fId : followees){
                List<Tweet> fTweets = tweetsTable.get(fId);
                if(fTweets != null){
                    for(Tweet fTweet : fTweets){
                        minHeap.offer(fTweet);
                        if(minHeap.size() > feedSize){
                            minHeap.poll();
                        }
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while(!minHeap.isEmpty()){
            result.add(0,minHeap.poll().id);
        }
        return result;
    }

    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {

        if(!followsTable.containsKey(followerId)){
            followsTable.put(followerId,new HashSet<Integer>());
        }

        followsTable.get(followerId).add(followeeId);
    }

    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {

        if(followerId == followeeId) return; //user cannot unfollow itself

        if(followsTable.containsKey(followerId)){
            followsTable.get(followerId).remove(followeeId);
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
