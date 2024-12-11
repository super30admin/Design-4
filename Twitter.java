/*
TC:
postTweet(int userId, int tweetId):
    Following the user (via follow):O(1).
    Adding a tweet to the user's tweet list:O(1).
    Total TC:O(1).

getNewsFeed(int userId):
    Retrieving followees:O(1) since it's a HashMap lookup.

    Iterating through followees:
        For each followee, retrieve tweets:O(k), where k is the number of followees.

        Adding up to 10 tweets per followee to the priority queue:O(m⋅log(10))=O(m), where m is the number of tweets
        examined (at most 10 per followee).

        Extracting results from the priority queue:O(10⋅log(10))=O(1).

    Total TC: O(k+m), where k is the number of followees and m is the number of tweets examined.

follow(int followerId, int followeeId):
    HashMap lookup or insertion:O(1).
    Adding followeeId to the HashSet:O(1).
    Total TC:O(1).

unfollow(int followerId, int followeeId):
    HashMap lookup:O(1).
    Removing followeeId from the HashSet:O(1).
    Total TC:O(1).

SC:

followeesMap:
    Stores a HashSet for each user. If n is the number of users and each user follows k users, the space used is O(n⋅k).

tweetsMap:
    Stores a List of tweets for each user. If n is the number of users and each user tweets t times, the space used is
    O(n⋅t).
    Priority Queue in getNewsFeed:

    Holds at most 10 tweets. Space usage is O(10)=O(1).

Auxiliary Data Structures:
    Result list in getNewsFeed: O(10)=O(1).
    Total SC: Dominated by followeesMap+tweetsMap=O(n⋅k+n⋅t).
*/
import java.util.*;

class Twitter
{
    HashMap<Integer, HashSet<Integer>> followeesMap; //UserId/followerId, hashset of followees
    HashMap<Integer, List<Tweet>> tweetsMap; //UserId, list of tweets{tweetId, createdAt/timeStamp}
    int timeStamp;

    class Tweet {
        int tweetId;
        int createdAt;

        public Tweet(int tweetId, int timeStamp) {
            this.tweetId = tweetId;
            this.createdAt = timeStamp;
        }
    }

    public Twitter() {
        this.followeesMap = new HashMap<>();
        this.tweetsMap = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        /*
        In the question, it is mentioned, a user cannot follow himself, but here we are adding in
        so user can see his own tweets. Other option is to add the code in getNewsFeed to get the 
        user tweets
        */
        follow(userId, userId);
        if(!tweetsMap.containsKey(userId)) {
            tweetsMap.put(userId, new ArrayList<>());
        }
        tweetsMap.get(userId).add(new Tweet(tweetId, timeStamp++));
    }

    public List<Integer> getNewsFeed(int userId)
    {
        /*
            Creating a heap of type Tweets but the sorting will be based on createdAt/timeStamp 
            in ascending order, which means it is a min heap. As the PriorityQueue is of type 
            Object Tweets, we need to specify the comparator or else the heap cannot handle the 
            sorting for min heap, the min heap is only possible if it is Integers etc.
        */
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);

        /*
            So first thing we need to do is get all the followees, basically the people user is 
            following from the followeesMap using the user id, once we have the hashset of the 
            followees, we need to get the tweets from the tweetsMap based on the followeeId.

            Once we have the tweets of all the followees, we can put them in the heap to get the
            latest tweets
        */
        // List<Tweet> ownTweets = tweetsMap.get(userId);
        // if (ownTweets != null) {
        //     for (Tweet tw : ownTweets) {
        //         pq.add(tw);
        //         if (pq.size() > 10) {
        //             pq.poll();
        //         }
        //     }
        // }
        HashSet<Integer> followees = followeesMap.get(userId);
        if(followees != null)
        {
            for(Integer followee : followees)
            {
                //if (followee == userId) continue; // Skip user's own tweets (already added)
                List<Tweet> tweets = tweetsMap.get(followee);
                if(tweets != null)
                {
                    /*
                       We can only put latest 10 tweets of the user, rather than putting entire tweets in pq
                     */
                    for(int i = tweets.size()-1; i>=0 && i>=tweets.size()-10; i--) //O(n)
                    {
                        Tweet tw = tweets.get(i);
                        pq.add(tw);
                        if(pq.size() > 10) { //Once the heap size is greater than 10, remove the smallest element
                            pq.poll();
                        }
                    }
                }
            }
        }

        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()) {
            result.add(0, pq.poll().tweetId);//we need the result in descending order as we want the latest news first
        }

        return result;
    }

    public void follow(int followerId, int followeeId)
    {
        /*
        If a user wants to follow a user, check in the followeesMap if the user is present or not.
        If it is present, add the followeeId in the map, if the user is not present in the followeesMap,
        create the entry and add the followeeId
        */
        if(!followeesMap.containsKey(followerId)) {
            followeesMap.put(followerId, new HashSet<>());
        }
        followeesMap.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId)
    {
        /*
        Check if the user exists in followeesMap, if it does, get the hashset and remove the followeeId
        */
        if(followeesMap.containsKey(followerId)) {
            followeesMap.get(followerId).remove(followeeId);
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