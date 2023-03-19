import java.util.*;
public class Twitter {
    HashMap<Integer, HashSet<Integer>> following;
    HashMap<Integer, List<Tweet>> tweets;
    int time;
    class Tweet{
        int tid;
        int createdAt;
        public Tweet(int tid,int created){
            this.tid = tid;
            this.createdAt = created;
        }
    }

    public Twitter() {
        this.following = new HashMap<>();
        this.tweets = new HashMap<>();
        this.time = 0;
    }

    public void postTweet(int userId, int tweetId) {
        follow(userId,userId);
        Tweet tobj = new Tweet(tweetId,time++);
        if(!tweets.containsKey(userId)){
            tweets.put(userId,new ArrayList<>());
        }
        tweets.get(userId).add(tobj);
    }

    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> minheap = new PriorityQueue<>((a,b) -> a.createdAt-b.createdAt);
        HashSet<Integer> follows = following.get(userId);
        if(follows!=null){
            for(Integer uid:follows){
                List<Tweet> tw = tweets.get(uid);
                if(tw!=null){
                    for(Tweet each:tw){
                        minheap.add(each);
                        if(minheap.size()>10)
                            minheap.poll();
                    }
                }

            }
        }
        List<Integer> result = new ArrayList<>();
        while(!minheap.isEmpty()){
            result.add(0,minheap.poll().tid);
        }
        return result;
    }

    public void follow(int followerId, int followeeId) {
        if(!following.containsKey(followerId)){
            following.put(followerId,new HashSet<>());
        }
        following.get(followerId).add(followeeId);

    }

    public void unfollow(int followerId, int followeeId) {
        if(following.containsKey(followerId)&&followerId!=followeeId){
            following.get(followerId).remove(followeeId);
        }
    }
}
