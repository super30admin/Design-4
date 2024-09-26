class Twitter {
    Map<Integer,User> map;
    newsFeed head;
    newsFeed tail;
    /** Initialize your data structure here. */
    public Twitter() {
        map = new HashMap<>();
        head = new newsFeed(-1,-1);
        tail = new newsFeed(-1,-1);
        head.next = tail;
        tail.prev = head;
    }


    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        User user = map.get(userId);
        //if user does not exist create one

        if(user == null){
            User u = new User(userId);
            map.put(userId,u);
        }
        newsFeed newsfeed = new newsFeed(userId,tweetId);
        addHead(newsfeed);

    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        User user = map.get(userId);
        List<Integer> tweets = new ArrayList<>();

        //if user does not exist
        if(user == null){
            return tweets;
        }
        int count = 10;
        newsFeed current = head.next;
        while(count !=0 && current!=null){
            if(current.userId == userId || user.follows.contains(current.userId)){
                tweets.add(current.tweetId);
                count--;
            }
            current= current.next;

        }
        return tweets;
    }

    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if (followerId==followeeId) return;
        User u = map.get(followerId);
        //user does not exit,create new
        if(u==null){
            User newuser = new User(followerId);
            newuser.follows.add(followeeId);
            map.put(followerId,newuser);
            return;
        }
        if(!u.follows.contains(followeeId)){
            u.follows.add(followeeId);
        }
    }

    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if (followerId==followeeId) return;
        User u = map.get(followerId);
        //user does not exit
        if(u==null){
            return;
        }
        if(u.follows.contains(followeeId)){

            u.follows.remove(followeeId);
        }
    }
    public void addHead(newsFeed newsfeed){
        newsFeed c = head.next;
        head.next = newsfeed;
        newsfeed.next = c;
        c.prev = newsfeed;
    }
    public void remove(newsFeed newsfeed){
        newsFeed prevfeed = newsfeed.prev;
        newsFeed nextfeed = newsfeed.next;
        prevfeed.next = nextfeed;
        nextfeed.prev = prevfeed;
    }

}
class User{
    int userId;
    HashSet<Integer> follows;
    User(int userId){
        this.userId = userId;
        follows = new HashSet<>();
    }
}
class newsFeed{
    newsFeed next;
    newsFeed prev;
    int tweetId;
    int userId;
    newsFeed(int userId,int tweetId){
        this.userId = userId;
        this.tweetId = tweetId;
        next=null;
        prev=null;
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