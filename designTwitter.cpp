// Time Complexity :O(m*n) where m is users and n is the average number of tweets per user.
// Space Complexity : O(S) - S is the space used for maps & heap.  
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No
class Twitter {
public:
    /** Initialize your data structure here. */
    class Tweet{
        
    public:
        int tid;
        int createdAt;
        Tweet(int tid, int createdAt){
            this->tid = tid;
            this->createdAt = createdAt;
        }
    };
    unordered_map<int, set<int>> followerMap;
    unordered_map<int,set<Tweet*>> tweetMap;
    int time = 0;
    Twitter() {
        
    }
    
    /** Compose a new tweet. */
    void postTweet(int userId, int tweetId) {
        follow(userId,userId);
        Tweet *newTweet = new Tweet(tweetId,++time);
        if(tweetMap.find(userId) == tweetMap.end()){
            set<Tweet*> tweets;
            tweetMap[userId]  = tweets;
        }
        tweetMap[userId].insert(newTweet);
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    class mycompare{
    public:
        bool operator() (Tweet* a,Tweet* b){
            return a->createdAt > b->createdAt;
        }
    };
    vector<int> getNewsFeed(int userId) {
        priority_queue<Tweet* , vector<Tweet*>,mycompare> pq;
        set<int> followers = followerMap[userId];
        for(auto follower: followers){
            set<Tweet*> tweets = tweetMap[follower];
            if(tweets.size()){
                for(auto tweet : tweets){
                    pq.push(tweet);
                    if(pq.size() > 10){
                        pq.pop();
                    }
                }
            }
        }
        vector<int> result;
        while(!pq.empty()){
            result.push_back(pq.top()->tid);
            pq.pop();
        }
        reverse(result.begin(),result.end());
        return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    void follow(int followerId, int followeeId) {
        if(followerMap.find(followerId) == followerMap.end()){
            set<int> followers;
            followerMap[followerId] = followers;
        }
        followerMap[followerId].insert(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    void unfollow(int followerId, int followeeId) {
        if(followerMap.find(followerId) != followerMap.end() && followerId != followeeId){
            set<int> followers = followerMap[followerId];
            cout<<followers.size();
            if(followers.find(followeeId) != followers.end())
                followerMap[followerId].erase(followeeId);
            cout<<followers.size();
            
        }
    }
};

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter* obj = new Twitter();
 * obj->postTweet(userId,tweetId);
 * vector<int> param_2 = obj->getNewsFeed(userId);
 * obj->follow(followerId,followeeId);
 * obj->unfollow(followerId,followeeId);
 */