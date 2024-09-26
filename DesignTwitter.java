/*
The time complexity of follow and unfollow is O(1) since we are using hashset in user class. To get the new feed of top 10 we maintain
a priority queue which contains top 10 new tweets. To get the time stamp we maintain a field called time which is incremented with each
tweet. So, we traverse through each of the user tweets and the tweets of whom the user is following so O(n*log10) where n is sum of
tweets of user and the all the members in the following hashset.

Yes, the solution passed all the test cases in leetcode.
 */
class Twitter {

    int time;
    HashMap<Integer,User> map;
    /** Initialize your data structure here. */
    public Twitter() {
        this.time = 0;
        this.map = new HashMap<>();
    }

    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        User user = map.get(userId);
        if(user==null){
            user = new User(userId);
            map.put(userId,user);
        }
        user.tweets.add(new Tweet(tweetId,time++));
    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        LinkedList<Integer> ret = new LinkedList<>();
        PriorityQueue<Tweet> queue = new PriorityQueue<>((x,y)->x.timestamp-y.timestamp);
        User user = map.get(userId);
        if(user==null){return ret;}
        List<Tweet> tweets = user.tweets;
        for(Tweet t:tweets){
            if(queue.size()<10){
                queue.add(t);
            }
            else{
                if(queue.peek().timestamp<t.timestamp){
                    queue.poll();
                    queue.add(t);
                }
            }
        }

        Set<Integer> following = map.get(userId).following;
        for(int id:following){
            tweets = map.get(id).tweets;
            for(Tweet t:tweets){
                if(queue.size()<10){
                    queue.add(t);
                }
                else{
                    if(queue.peek().timestamp<t.timestamp){
                        queue.poll();
                        queue.add(t);
                    }
                }
            }
        }


        while(queue.size()>0){
            Tweet temp = queue.poll();
            ret.offerFirst(temp.tweetid);
        }

        return ret;


    }

    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(followerId==followeeId){return;}
        else{
            User user = map.get(followeeId);
            if(user==null){
                user = new User(followeeId);
                map.put(followeeId,user);
            }
            user.followers.add(followerId);
            user = map.get(followerId);
            if(user==null){
                user = new User(followerId);
                map.put(followerId,user);
            }
            user.following.add(followeeId);
        }
    }

    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(followerId==followeeId){return;}
        else{
            User user = map.get(followeeId);
            if(user==null){return;}
            user.followers.remove(followerId);
            user = map.get(followerId);
            if(user==null){return;}
            user.following.remove(followeeId);
        }
    }
}

class User {
    int userid;
    List<Tweet> tweets;
    Set<Integer> following;
    Set<Integer> followers;

    User(int id){
        this.userid = id;
        tweets = new ArrayList<>();
        following = new HashSet<>();
        followers = new HashSet<>();
    }
}

class Tweet {
    int tweetid;
    int timestamp;

    Tweet(int id,int timestamp){
        this.tweetid = id;
        this.timestamp = timestamp;
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