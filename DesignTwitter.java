/* 
Time Complexity:
- postTweet: O(1)
- follow / unfollow: O(1) average (HashSet add/remove)
- getNewsFeed: getNewsFeed: O(N log 10) -> O(N), where N is total tweets from all followees.
               since we scan their tweets and maintain a min-heap of size K=10 (log K is constant)

Space Complexity:
O(t+f+u) where t is the number of tweets, f is the number of followers and u is the number of users.

Did this code successfully run on Leetcode: Yes
*/

// Approach:
// - Store follow relationships in followeesMap (follower -> set of followees) and tweets per user in tweetMap.
// - postTweet: ensure self-follow and append a (tweetId, timestamp) to the user's list.
// - getNewsFeed: iterate all followees (incl. self), push their tweets into a min-heap capped at size 10;
//   pop if size > 10. Extract, reverse to get most-recent-first.
// - follow/unfollow: HashSet add/remove on the follower's followee set.


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class DesignTwitter {
    HashMap<Integer, HashSet<Integer>> followeesMap;
    HashMap<Integer, List<Tweet>> tweetMap;
    int time;

    class Tweet{
        int tweetId;
        int timeStamp;

        public Tweet(int id, int time){
            this.tweetId = id;
            this.timeStamp = time;
        }
    }

    public DesignTwitter(){
        this.followeesMap = new HashMap<>();
        this.tweetMap = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId){
        follow(userId, userId);
        if(!tweetMap.containsKey(userId)){
            tweetMap.put(userId, new ArrayList<>());
        }
        tweetMap.get(userId).add(new Tweet(tweetId, time++));
    }

    public List<Integer> getNewsFeed(int userId){
        HashSet<Integer> followees = followeesMap.get(userId);
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b)-> a.timeStamp - b.timeStamp);

        if(followees != null){
            for(Integer followee : followees){
                List<Tweet> tweets = tweetMap.get(followee);
                if(tweets != null){
                    for(Tweet tw : tweets){
                        pq.add(tw);
                        if(pq.size() > 10){
                            pq.poll();
                        }
                    }
                }
            }
        }

        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(pq.poll().tweetId);
        }

        Collections.reverse(result);
        return result;
    }

    public void follow(int followerId, int followeeId){
        if(!followeesMap.containsKey(followerId)){
            followeesMap.put(followerId, new HashSet<>());
        }
        followeesMap.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId){
        if(!followeesMap.containsKey(followerId)) return;

        followeesMap.get(followerId).remove(followeeId);
    }
}
