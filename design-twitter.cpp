// TC = O(N)
// SC = O(N*max(M,T)) N = users, M = average number of followers & T = average number of tweets
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

class Twitter {
private:
    struct Tweet {
        int id, createdAt;
        Tweet(int id, int createdAt) : id(id), createdAt(createdAt) {}
    };
    
    map<int, vector<Tweet>> tweets;
    map<int, set<int>> followee;
    int time;
    
public:
    Twitter() {
        this->tweets.clear();
        this->followee.clear();
        time = 0;
    }
    
    void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if (tweets.find(userId) == tweets.end())
            tweets[userId] = vector<Tweet>();
        tweets[userId].emplace_back(Tweet(tweetId, ++time));
    }
    
    vector<int> getNewsFeed(int userId) {
        auto compare = [](const Tweet& a, const Tweet& b) {
            return a.createdAt > b.createdAt;
        };
        priority_queue<Tweet, vector<Tweet>, decltype(compare)> pq(compare);
        set<int> followeeSet = followee[userId];
        
        // Add the user's own tweets to the followee set temporarily
        followeeSet.insert(userId);
        
        for (int uid : followeeSet) {
            vector<Tweet> fTweets = tweets[uid];
            for (const Tweet& fTweet : fTweets) {
                pq.push(fTweet);
                if (pq.size() > 10) pq.pop();
            }
        }
        
        // Remove the user's own tweets from the followee set
        followeeSet.erase(userId);
        
        vector<int> result;
        while (!pq.empty()) {
            result.insert(result.begin(), pq.top().id);
            pq.pop();
        }
        return result;
    }
    void follow(int followerId, int followeeId) {
        if (followee.find(followerId) == followee.end())
            followee[followerId] = set<int>();
        followee[followerId].insert(followeeId);
    }
    
    void unfollow(int followerId, int followeeId) {
        if (followee.find(followerId) != followee.end() && followerId != followeeId) {
            followee[followerId].erase(followeeId);
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