import java.util.*;

// TC - getNewsFeed() --> O(N) , postTweet() --> O(1), follow() --> O(1), unfollow --> O(1)
// SC - O(n)

public class DesignTwitter {
    static class Twitter {

        static class Tweet{

            int tid;
            int createdAt;

            //constructor
            public Tweet(int id, int time){

                this.tid = id;
                this.createdAt = time;

            }
        }

        //two hashmaps
        //one for users followed , that contains one user and a set of user it follows
        HashMap<Integer, HashSet<Integer>> followed;

        //other one for list of tweet objects created by all different users
        HashMap<Integer, List<Tweet>> tweets;

        int time;
        public Twitter() {
            //initializing the maps
            this.followed = new HashMap<>();
            this.tweets = new HashMap<>();

        }

        public void postTweet(int userId, int tweetId) {

            //follow self to post a tweet
            follow(userId, userId);
            //if tweeting for the first time
            if(!tweets.containsKey(userId)){
                tweets.put(userId, new ArrayList<>());


            }
            //add new tweet object and incerease the time
            tweets.get(userId).add(new Tweet(tweetId, time++));
        }

        public List<Integer> getNewsFeed(int userId) {
            //heap of tweet objects
            PriorityQueue<Tweet> q = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);
            HashSet<Integer> followeds = followed.get(userId);

            if(followeds!=null){
                for(int followedId: followeds){
                    List<Tweet> ftweets = tweets.get(followedId);

                    if(ftweets!=null){
                        for(Tweet ftweet: ftweets){
                            q.add(ftweet);
                            if(q.size() >10){
                                q.poll();
                            }
                        }
                    }
                }
            }

            List<Integer> result = new ArrayList<>();
            while(!q.isEmpty()){
                result.add(0, q.poll().tid);
            }
            return result;

        }


        public void follow(int followerId, int followeeId) {

            //add the user with an empty set if not there
            if(!followed.containsKey(followerId)){
                followed.put(followerId, new HashSet<>());
            }

            //if the user is already there add the followee id
            followed.get(followerId).add(followeeId);

        }

        public void unfollow(int followerId, int followeeId) {

            //remove the follower without unfollowing the self user
            if(followed.containsKey(followerId) && followerId!=followeeId){
                followed.get(followerId).remove(followeeId);
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
}
