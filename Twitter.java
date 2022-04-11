import java.util.*;
import java.util.LinkedList;
//Time Complexity O(nk LogK)
//Space Complexity O(n*K) posts resp to users * users
//leetCode tested
public class Twitter {
    public int timeStamp;
    public HashMap<Integer,User> userMap;
    class Tweet{
        private int id,time;
        private Tweet next;
        public Tweet(int id, int time){
            this.id = id;
            this.time = time;
            next = null;
        }
        public int getId(){
            return this.id;
        }
    }
    class User{
        private Set<Integer> followed;
        private int id;
        private Tweet tweetHead;

        public User(int id){
            this.id = id;
            followed = new HashSet<>();
            tweetHead = null;
            followed.add(id);
        }
        public Tweet getTweetHead(){
            return tweetHead;
        }
        public Set<Integer> getFollowed(){
            return followed;
        }
        public void follow(int id){
            followed.add(id);
        }
        public void unfollow(int id){
            if(id!=this.id)
                followed.remove(id);
        }
        public void post(int postId){
            Tweet newTweet = new Tweet(postId,timeStamp);
            timeStamp++;
            newTweet.next = tweetHead;
            tweetHead=newTweet;
        }
    }

    public Twitter() {
        userMap = new HashMap<>();
        timeStamp = 0;
    }

    public void postTweet(int userId, int tweetId) {
        if(!userMap.containsKey(userId)){
            User user = new User(userId);
            userMap.put(userId,user);
        }
        userMap.get(userId).post(tweetId);
    }

    public List<Integer> getNewsFeed(int userId) {
        List<Integer> list = new LinkedList<>();
        if(!userMap.containsKey(userId)){
            return list;
        }
        Set<Integer> users =userMap.get(userId).getFollowed();
        PriorityQueue<Tweet> pq = new PriorityQueue<>(users.size(),(a,b) -> (b.time - a.time));
        for (int user:users) {
            Tweet t = userMap.get(user).getTweetHead();
            if(t == null) continue;
            pq.add(t);
        }
        while(!pq.isEmpty()){
            if(list.size() == 10){
                break;
            }
            Tweet t =pq.poll();
            list.add(t.getId());
            if(t.next!=null){
                pq.add(t.next);
            }
        }
        return list;
    }

    public void follow(int followerId, int followeeId) {
        if(!userMap.containsKey(followerId)){
            User user = new User(followerId);
            userMap.put(followerId,user);
        }
        if(!userMap.containsKey(followeeId)){
            User user = new User(followeeId);
            userMap.put(followeeId,user);
        }
        userMap.get(followerId).follow(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if(userMap.containsKey(followerId)){
            userMap.get(followerId).unfollow(followeeId);
        }
    }

}
