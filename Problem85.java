//Time Complexity: postTweet: O(1) follow, unfollow: O(1) getNewsFeed: O(n × 10 × log 10) = O(n log 10) = effectively O(n) where n is the number of followees
//Space Complexityt: O(t+f+u) where t is the number of tweets, f is the number of followers and u is the number of users.

class Twitter {
    HashMap<Integer, HashSet<Integer>> followeesMap;
    HashMap<Integer, List<Tweets>> tweetsMap;
    int time;

    public class Tweets {
        int tweetId;
        int timeStamp;

        public Tweets(int tweetId, int timeStamp){
            this.tweetId = tweetId;
            this.timeStamp = time;
        }
    }

    public Twitter() {
        this.followeesMap = new HashMap<>();
        this.tweetsMap = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        if(!tweetsMap.containsKey(userId)) {
            tweetsMap.put(userId, new ArrayList<>());
        }
        tweetsMap.get(userId).add(new Tweets(tweetId, time));
        time++;
        follow(userId, userId);
    }
    
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweets> pq = new PriorityQueue<>((a,b) -> a.timeStamp - b.timeStamp);

        HashSet<Integer> followees = followeesMap.get(userId);
        if(followees != null) {
            for(Integer followee : followees) {
                List<Tweets> tweets = tweetsMap.get(followee);
                if (tweets != null) {
                    for(Tweets tweet : tweets) {
                        pq.add(tweet);
                        if (pq.size() > 10) {
                            pq.poll();
                        }
                    }
                }
            }
        }

        ArrayList<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(pq.poll().tweetId);
        }

        Collections.reverse(result);
        return result;
    }
    
    public void follow(int followerId, int followeeId) {
        if(!followeesMap.containsKey(followerId)) {
            followeesMap.put(followerId, new HashSet<>());
        }
        followeesMap.get(followerId).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if(!followeesMap.containsKey(followerId)) {
            return;
        }
        followeesMap.get(followerId).remove(followeeId);
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