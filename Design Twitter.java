/*
class Tweet:
    def __init__(self, tid, time):
        self.tweetid = tid
        self.creationTime = time
        
    def __lt__(self, other):
        return self.creationTime < other.creationTime
    
class Twitter:

    def __init__(self):
        self.followed = dict()
        self.tweets = dict()
        self.time = 0
    
    def postTweet(self, userId: int, tweetId: int) -> None:
        if userId not in self.tweets:
            self.tweets[userId] = []
        
        self.tweets[userId].append(Tweet(tweetId, self.time))
        self.time += 1

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        self.follow(userId, userId)
        pq = []
        
        followers = self.followed[userId]
        if followers is not None:
            for fid in followers:
                if fid in self.tweets:
                    tweets = self.tweets[fid]
                    if tweets is not None:
                        for t in tweets:
                            heapq.heappush(pq, t)
                            if len(pq) > 10:
                                heapq.heappop(pq)
        result = []
        while len(pq) > 0:
            cur = heapq.heappop(pq)
            result.insert(0, cur.tweetid)
        return result
        

    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId not in self.followed:
            self.followed[followerId] = set()
        
        self.followed[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId in self.followed:
            if followeeId in self.followed[followerId]:
                self.followed[followerId].remove(followeeId)

*/

// Time Complexity : O(nklog10) where n is no of users and k is average no of tweets per user so gettweets function will have this complexity
// Space Complexity : O(n) where n is no of users as hashmap will store no of followers and also tweets by user

// Your code here along with comments explaining your approach: Maintained two hashmap for followers and tweets and used heap for getting
// most recent 10 tweets

class Tweet{
    int tid;
    int creationTime;
    public Tweet(int tweetId, int time){
        tid = tweetId;
        creationTime = time;
    }
}
class Twitter {
    HashMap<Integer, List<Tweet>> tweets;
    HashMap<Integer, HashSet<Integer>> followers;
    int time;

    /** Initialize your data structure here. */
    public Twitter() {
        tweets = new HashMap<>();
        followers = new HashMap<>();
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        if (!tweets.containsKey(userId)){
            tweets.put(userId, new ArrayList<>());
        }
        tweets.get(userId).add(new Tweet(tweetId, time++));
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {      // O(nklog10)
        follow(userId, userId);
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b) -> a.creationTime - b.creationTime);
        Set<Integer> f = followers.get(userId);
        for (int fid: f){
            List<Tweet> twts = tweets.get(fid);
            if (twts != null){
                for (Tweet t: twts){
                    pq.add(t);
                    if (pq.size() > 10)
                        pq.remove();
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while (pq.size() > 0){
            
            result.add(0, pq.remove().tid);
        }
        return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if (!followers.containsKey(followerId)){
            followers.put(followerId, new HashSet<>());
        }
        followers.get(followerId).add(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if (followers.containsKey(followerId)){
            followers.get(followerId).remove(followeeId);
        }
    }
}