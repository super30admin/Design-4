import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class DesignTwitterOpt {

        //OPTIMIZED PQ

        // Tweet class
        public static class Tweet{

            // data member types of local tweet class
            int id;
            int createdAt;

            // Tweet constructor allocates memory to Tweet object
            public Tweet(int tweetID, int time) {

                // Tweet object's attributes
                this.id = tweetID;
                this.createdAt = time;
            }
        }

        // data member types of twitter class

        HashMap<Integer, HashSet<Integer>> followedByU;

        HashMap<Integer, List<Tweet>> tweetsbyU;

        int timeStamp;

        // Twitter method allocates memory to two hashmap data type members of Twitter object

        public void Twitter() {

            this.followedByU = new HashMap<>();

            this.tweetsbyU = new HashMap<>();

        }

        public void postTweet(int userId, int tweetId) {

            // a user can see own news feed, so follows self
            //hashmap ensures it is inserted only once
            follow(userId, userId);

            // if tweets map does not have key with given userid yet, create an entry
            if(!tweetsbyU.containsKey(userId)) {

                tweetsbyU.put(userId, new ArrayList<>());
            }

            // add new tweet object to value of key with given used id in tweets map
            tweetsbyU.get(userId).add(new Tweet(tweetId, timeStamp));

            // increment global time stamp variable after adding a tweet in tweets map
            timeStamp++;

        }

        public List<Integer> getNewsFeed(int userId) {

            List<Integer> result = new ArrayList<>();

            PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);

            // get news feeders of user i.e., users followed by user
            HashSet<Integer> feedersOfU = followedByU.get(userId);

            if(feedersOfU != null) {

                for(int id: feedersOfU) { //O(k) = O(1), if number of users k is constant

                    // get tweets of each feeder
                    List<Tweet> fT = tweetsbyU.get(id);

                    if(fT != null) {

                        // optimized priority queue with conditional addition of tweets of not more than 10 from each feeder's tweets list
                        int k = Math.min(11, fT.size());

                        for(int i = k-1; k >= 0 && i >= 0; i--) {

                            pq.add(fT.get(i));

                            //decrement k to get previous tweet
                            k--;

                            if(pq.size() > 10) pq.poll();
                        }
                    }
                }
            }

            // add tweet ids from priority queue of size 10 to result
            // min heap = [T8, T9, T10] = maintains minimum time at top of it
            while(!pq.isEmpty()) {

                //T10 is added to front of list as it is recent
                result.add(0, pq.poll().id);
            }

            return result;
        }

        // follow method on followedByU map

        public void follow(int followerId, int followeeId) { //O(1)

            // if users map does not contain key of given follower, add it to map
            if(!followedByU.containsKey(followerId)) {

                followedByU.put(followerId, new HashSet<>());
            }

            // add followee id to value of key
            followedByU.get(followerId).add(followeeId);

        }

        // unfollow method on followedByU map
        public void unfollow(int followerId, int followeeId) { //O(1)

            // get value of non-self user key (followerId) from users map and remove followeeId from its value
            if(followedByU.containsKey(followerId) && followerId != followeeId) {

                followedByU.get(followerId).remove(followeeId);
            }
        }




}

/*

SPACE COMPLEXITY = O(N*max(M,T)) where N is the number of users, M is the average number of followers and T is the average number of tweets.
TIME COMPLEXITY = O(N) = O(1) as number of users is constant

*/
/*
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */