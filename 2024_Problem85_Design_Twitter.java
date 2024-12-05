//355. Design Twitter - https://leetcode.com/problems/design-twitter/description/
//Time Complexity: O(n*k)

class Twitter {
    HashMap<Integer, HashSet<Integer>>  followeesMap; //userId: list of followeres
    HashMap<Integer, List<Tweet>>  tweetsMap; //tweetId: tweets object
    int timestamp;

    class Tweet{
        int tweetId;
        int createdAt;

        public Tweet(int tweetId, int timestamp){
            this.tweetId = tweetId;
            this.createdAt = timestamp;
        }
    }

    public Twitter(){
        this.followeesMap = new HashMap<>();
        this.tweetsMap = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        follow(userId, userId); //to see own tweet
        if(!tweetsMap.containsKey(userId)){
            tweetsMap.put(userId, new ArrayList<>());
        }
        tweetsMap.get(userId).add(new Tweet(tweetId, timestamp++));
    }

    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);

        HashSet<Integer> followees = followeesMap.get(userId);
        if(followees != null){
            for(Integer followee : followees){
                List<Tweet> tweets = tweetsMap.get(followee);
                if(tweets != null){
                    for(Tweet tw: tweets){ //O(N * log k) - ading all tweets
                        pq.add(tw);
                        if(pq.size() > 10){
                            pq.poll();
                        }
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0, pq.poll().tweetId);
        }
        return result;
    }

    public void follow(int followerId, int followeeId) {
        if(!followeesMap.containsKey(followerId)){
            followeesMap.put(followerId, new HashSet<>());
        }
        followeesMap.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if(followeesMap.containsKey(followerId)){
            followeesMap.get(followerId).remove(followeeId);
        }
    }
}

//Updated getNewsFeed(int userId)
class Twitter {
    HashMap<Integer, HashSet<Integer>>  followeesMap; //userId: list of followeres
    HashMap<Integer, List<Tweet>>  tweetsMap; //tweetId: tweets object
    int timestamp;

    class Tweet{
        int tweetId;
        int createdAt;

        public Tweet(int tweetId, int timestamp){
            this.tweetId = tweetId;
            this.createdAt = timestamp;
        }
    }

    public Twitter(){
        this.followeesMap = new HashMap<>();
        this.tweetsMap = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        follow(userId, userId); //to see own tweet
        if(!tweetsMap.containsKey(userId)){
            tweetsMap.put(userId, new ArrayList<>());
        }
        tweetsMap.get(userId).add(new Tweet(tweetId, timestamp++));
    }

    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.createdAt - b.createdAt);

        HashSet<Integer> followees = followeesMap.get(userId);
        if(followees != null){
            for(Integer followee : followees){
                List<Tweet> tweets = tweetsMap.get(followee);
                if(tweets != null){
                    //don't add all tweets. only iterate over last 10 tweets
                    //start from end
                    for(int i=tweets.size()-1; i>=0 && i>=tweets.size()-10; i--){ //O(n)
                        Tweet tw = tweets.get(i);
                        pq.add(tw);
                        if(pq.size() > 10){
                            pq.poll();
                        }
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while(!pq.isEmpty()){
            result.add(0, pq.poll().tweetId);
        }
        return result;
    }

    public void follow(int followerId, int followeeId) {
        if(!followeesMap.containsKey(followerId)){
            followeesMap.put(followerId, new HashSet<>());
        }
        followeesMap.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if(followeesMap.containsKey(followerId)){
            followeesMap.get(followerId).remove(followeeId);
        }
    }
}