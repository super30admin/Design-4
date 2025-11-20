import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 
 */

/**
 * @author Aditya sinha
 * post stored with timestamp, they follow themselves
 * use min-heap to always keep 10 latest tweets
 * reverse the heap to get newest tweets first 
 * Time Complexity - postTweet: O(1) follow / unfollow: O(1) getNewsFeed: O(N log 10) -> O(N)
 * Space Complexity - O(t+f+u)
 */
public class DesignTwitter {

	HashMap<Integer, HashSet<Integer>> followeesMap;
    HashMap<Integer, List<Tweet>> tweetsMap;
    int time;

    class Tweet {
        int tweetId;
        int timeStamp;

        public Tweet(int tId, int time) {
            this.tweetId = tId;
            this.timeStamp = time;
        }
    }

    public DesignTwitter() {
        this.followeesMap = new HashMap<>();
        this.tweetsMap = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        if (!tweetsMap.containsKey(userId)) {
            tweetsMap.put(userId, new ArrayList<>());
        }
        Tweet tweet = new Tweet(tweetId, time);
        time++;
        tweetsMap.get(userId).add(tweet);
        follow(userId, userId);
    }
    
    public List<Integer> getNewsFeed(int userId) { // O(N log 10) -> O(N)
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.timeStamp - b.timeStamp);

        HashSet<Integer> followees = followeesMap.get(userId);
        if (followees != null) {
            for (Integer followee : followees) {
                List<Tweet> tweets = tweetsMap.get(followee);
                if (tweets != null) {
                    for (Tweet tweet : tweets) {
                        pq.add(tweet);
                        if (pq.size() > 10) {
                            pq.poll(); // get 10 latest tweets
                        }
                    }
                }
            }
        }

        List<Integer> result = new ArrayList<>();
        while (!pq.isEmpty()) {
            result.add(pq.poll().tweetId);
        }

        Collections.reverse(result); // reverse to get newest
        return result;
    }
    
    public void follow(int followerId, int followeeId) {
        if (!followeesMap.containsKey(followerId)) {
            followeesMap.put(followerId, new HashSet<>());
        }
        followeesMap.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if (!followeesMap.containsKey(followerId)) {
            return;
        }
        followeesMap.get(followerId).remove(followeeId);
    }
}
