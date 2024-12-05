// Time Complexity :getNewsFeed(): O(n)
//                  : postTweet():O(1)
//                  : follow(): O(1)
//                  : unfollow():O(1)
// Space Complexity :O(N*m+N*M+n)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : nothing

import java.util.*;

public class Twitter {
    HashMap<Integer, HashSet<Integer>> followeeMap;
    HashMap<Integer, List<Tweet>> tweetsMap;
    int timestamp;

    public Twitter() {
        followeeMap = new HashMap<>();
        tweetsMap = new HashMap<>();
    }

    class Tweet{
        int tweetId;
        int createdAt;

        public Tweet(int tweetId, int timestamp){
            this.tweetId = tweetId;
            this.createdAt= timestamp;
        }
    }

    public void postTweet(int userId, int tweetId) {
        follow(userId,userId);
        if(!tweetsMap.containsKey(userId)){
            tweetsMap.put(userId, new ArrayList<>());
        }
        tweetsMap.get(userId).add( new Tweet(tweetId, timestamp++));
    }

    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b)->a.createdAt-b.createdAt);

        HashSet<Integer> followees = followeeMap.get(userId);
        if(followees!=null){
            for(Integer followee: followees){
                List<Tweet> tweets = tweetsMap.get(followee);
                if(tweets != null){
                    for(int i= tweets.size()-1; i>=0 && i>=tweets.size()-10; i--){
                        pq.add(tweets.get(i));
                        if(pq.size()>10){
                            pq.poll();
                        }
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0,pq.poll().tweetId);
        }
        return result;
    }

    public void follow(int followerId, int followeeId) {
        if(!followeeMap.containsKey(followerId)){
            followeeMap.put(followerId, new HashSet<>());
        }
        followeeMap.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if(followeeMap.containsKey(followerId)){
            followeeMap.get(followerId).remove(followeeId);
        }
    }
}
