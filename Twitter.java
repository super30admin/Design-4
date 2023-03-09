import java.util.*;
/*
Design Twitter
approach: start from bigger class i.e. twitter and then think of users, tweets.
 */
public class Twitter {

    public class Tweet {
        int tid, time;

        public Tweet(int tweetId, int time) {
            this.tid = tweetId;
            this.time = time;
        }
    }
    Map<Integer, Set<Integer>> followed;
    Map<Integer, List<Tweet>> tweets;
    int time;
    public Twitter() {
        this.followed = new HashMap<>();
        this.tweets = new HashMap<>();
        this.time = 0;
    }

    //O(1)
    public void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if(!tweets.containsKey(userId)) {
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId, time++));
    }

    //O(n) as 10 is a constant
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> res = new ArrayList<>();
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b)->(a.time-b.time));
        if(followed.containsKey(userId)) {
            Set<Integer> follows = followed.get(userId);
            for(int celeb: follows) {
                if(tweets.containsKey(celeb)) {
                    List<Tweet> celeb_tweets = tweets.get(celeb);
                    int size = celeb_tweets.size();

                    for(int i=size-1;i>=0 && i>=size-10;i--) {
                        pq.add(celeb_tweets.get(i));

                        if(pq.size()>10) {
                            pq.poll();
                        }
                    }
                }
            }
        }
        while(!pq.isEmpty()) {
            res.add(0, pq.poll().tid);
        }
        return res;
    }

    //O(1)
    public void follow(int followerId, int followeeId) {
        if(!followed.containsKey(followerId)) {
            followed.put(followerId, new HashSet<>(Arrays.asList(followerId, followeeId)));
        }
        else {
            followed.get(followerId).add(followeeId);
        }
    }

    //O(1)
    public void unfollow(int followerId, int followeeId) {
        if(followed.containsKey(followerId)) {
            followed.get(followerId).remove(followeeId);
        }
    }

    public static void main(String[] args) {
        Twitter twitter = new Twitter();
        twitter.postTweet(1,5);
        twitter.postTweet(1,3);
        twitter.getNewsFeed(1);
//        twitter.follow(2,1);
//        twitter.getNewsFeed(2);
//        twitter.unfollow(2,1);
//        twitter.getNewsFeed(2);
    }
}
