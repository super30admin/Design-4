class Twitter {

    //Time Complexity : 0(m*n log 10)where m is the users and n is the tweets
    //Space complexity: 0(m*n)where m is the users and n is the tweets
    //Did it successfully run on leetcode: Yes
    //Did you face any problem while coding: No

    //In SHort, explain your approach:

    class Tweet{//created a class tweet to store the tweet of each user and the timestamp(as the latest tweets are required)
        int id, timeId;
        public Tweet(int id, int time){
            this.id = id;
            this.timeId = time;
        }
    }
    int timestamp;  //timestamp to store the time a tweet was made
    HashMap <Integer, HashSet<Integer>> user = new HashMap<>();//hashmap of users with user id as key and the accounts it follows as values
    HashMap <Integer, List<Tweet>> tweets = new HashMap<>();//hashmap of tweets where user id is stores as key and list of tweets the user has tweeted as value

    public Twitter() {
        user = new HashMap<>();
        tweets = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        if(!tweets.containsKey(userId)){    //checking if there this is the first tweet by the user of if he has tweeted before
            tweets.put(userId, new ArrayList<>());//if it is first, creating a nw hashmap for the user to store its tweets
        }
        tweets.get(userId).add(new Tweet(tweetId, timestamp++));//adding the tweet to the map along with its time stamp

    }

    public List<Integer> getNewsFeed(int userId) {
        follow(userId, userId);//making a user follow himself as problem demands to return 10 latest tweets including the tweets from user himself
        PriorityQueue <Tweet> q = new PriorityQueue<>((a,b) -> a.timeId - b.timeId);//creating a prioprity queue to store the latest tweets and customizing it as the per the latest time
        HashSet<Integer> users = user.get(userId);//getting all the followers of the user
        for(int user : users){//going through all the follower individually to get all the tweets by them
            List<Tweet> t = tweets.get(user);//creating a tweet list to store all the tweets of individual user
            if(t != null){
                for(Tweet a : t){
                    q.add(a);
                    if(q.size() > 10){
                        q.poll();
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();//storing all the tweets in a list to give the result
        while(!q.isEmpty()){
            result.add(0, q.poll().id);
        }
        return result;
    }

    public void follow(int followerId, int followeeId) {
        if(!user.containsKey(followerId)){//creating a new user if the user doesn't exist
            user.put(followerId, new HashSet<>());
        }
        user.get(followerId).add(followeeId);//adding the followe to the user
    }

    public void unfollow(int followerId, int followeeId) {
        if(user.containsKey(followerId) && followerId != followeeId){//getting the user to remove the follower
            user.get(followerId).remove(followeeId);
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