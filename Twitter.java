import java.util.*;
public class Twitter {

    public class Tweet{
        int id;
        int createdAt;
        public Tweet(int tweetId, int time){
            this.id = tweetId;
            this.createdAt = time;
        }
    }
    int time;
    Map<Integer, HashSet<Integer>> followed;
    Map<Integer, List<Tweet>> tweets;
    public Twitter(){
        this.followed = new HashMap<>();
        this.tweets = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId){
        follow(userId, userId);
        if(!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId, time++));
    }   

    public List<Integer> getNewsFeed(int userId){
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a, b) -> a.createdAt - b.createdAt);
        HashSet<Integer> fids = followed.get(userId);
        if(fids != null && fids.size() > 0){
            for(Integer fid: fids){
                List<Tweet> fTweets = tweets.get(fid);
                if(fTweets != null && fTweets.size() > 0){
                    for(Tweet fTweet: fTweets){
                        pq.add(fTweet);
                        if(pq.size() > 10){
                            pq.poll();
                        }
                    }
                    
                }
            }
            
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0, pq.poll().id);
        }
        return result;

    }

    public void follow(int followerId, int followeeId){
        if(!followed.containsKey(followerId)){
            followed.put(followerId, new HashSet<>());
        }
        followed.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId){
        if(followed.containsKey(followerId) && followerId != followeeId){
            followed.get(followerId).remove(followeeId);
        }
    }


    
}
