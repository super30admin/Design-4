import java.util.*;

public class DesignTwitter {

    static class Tweet { //Inner class Tweet stores the tweetId and its timestamp
        int tweetId;
        int time;

        public Tweet(int tweetId, int time) { //constructor
            this.tweetId = tweetId;
            this.time = time;
        }
    }

    HashMap<Integer, HashSet<Integer>> userMap; //stores userId as key and whom he is following as value
    HashMap<Integer, HashSet<Tweet>> tweetMap; //stores userId as key and his list of tweets as value
    int timeStamp; //timeStamp associated with each tweet

    public DesignTwitter() { //Overall S.C, O(U+F+T) where U is users, F is followee relations, T is tweets
        this.userMap = new HashMap<>();
        this.tweetMap = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) { //O(1) T.C
        if(!tweetMap.containsKey(userId)) { //If this is user's first tweet
            tweetMap.put(userId, new HashSet<>()); //create his entry in the tweet map
        }
        Tweet newTweet = new Tweet(tweetId, timeStamp); //create a tweet object with the input tweetId and current time
        tweetMap.get(userId).add(newTweet); //add this tweet object against the user in the tweet map
        timeStamp++; //increment the time for the next incoming tweet
    }

    public List<Integer> getNewsFeed(int userId) {
        //Overall T.C, O(nk) where n is number of follow relations of input user and k is number of tweets of followees
        follow(userId, userId); //make the user follow himself, as news feed should also contain his own news

        List<Integer> result = new ArrayList<>();
        //If the user doesn't follow anyone and he himself hasn't tweeted anything yet, return null
        if(!userMap.containsKey(userId) && tweetMap.containsKey(userId)) return result;

        PriorityQueue<Tweet> pq =
                new PriorityQueue<>((Tweet a, Tweet b) -> a.time - b.time); //min-heap based on timeStamp of tweets

        for(int followee : userMap.get(userId)) { //for the followees of current user, O(n) T.C
            if(tweetMap.containsKey(followee)) { //if their tweets are not empty
                for(Tweet tweet : tweetMap.get(followee)) { //for the tweets of the followees, O(k) T.C
                    pq.add(tweet); //add to the min-heap, O(log10) T.C
                    if(pq.size() > 10) pq.poll(); //if the heap size crosses 10, start polling the earliest tweet
                }
            }
        }

        while(!pq.isEmpty()) { //once the final 10 tweets are remaining
            result.addFirst(pq.poll().tweetId); //append one by one at the start of result list, O(10) T.C
        }
        return result;
    }

    public void follow(int followerId, int followeeId) { //O(1) T.C
        if(!userMap.containsKey(followerId)) { //If there is no entry for the follower already
            userMap.put(followerId, new HashSet<>()); //create his entry in the user map
        }
        userMap.get(followerId).add(followeeId); //and add his followee as the value
    }

    public void unfollow(int followerId, int followeeId) { //O(1) T.C
        if(userMap.containsKey(followerId)) { //if there is an entry for the follower
            userMap.get(followerId).remove(followeeId); //remove the followee from his value
        }
    }

    public static void main(String[] args) {
        DesignTwitter twitter = new DesignTwitter();
        twitter.postTweet(1,1);
        twitter.postTweet(2,2);
        twitter.follow(1,2);
        twitter.postTweet(1,3);
        twitter.postTweet(2,4);
        System.out.println("News feed of user 1 when following 2 is: " +
                twitter.getNewsFeed(1));
        System.out.println("News feed of user 2 is: " +
                twitter.getNewsFeed(2));
        twitter.unfollow(1,2);
        System.out.println("News feed of user 1 when not following 2 is: " +
                twitter.getNewsFeed(1));
    }
}