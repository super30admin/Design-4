class Twitter {
public:
    /** Initialize your data structure here. */
    vector<int>tweets;
    typedef vector<int>indices;
    map<int,indices>usertweets;
    map<int,set<int>>followees;
    Twitter() {
        
    }
    
    /** Compose a new tweet. */
    void postTweet(int userId, int tweetId) {
        tweets.push_back(tweetId);
        usertweets[userId].push_back(tweets.size()-1);
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    vector<int> getNewsFeed(int userId) {
        priority_queue<int, vector<int>, greater<int>>q;
        for(auto i: usertweets[userId])
        {
            q.push(i);
            if(q.size()>10)
                q.pop();
        }
        for(auto f:followees[userId])
        for(auto i:usertweets[f]) 
        {
            q.push(i);
            if(q.size()>10)
                q.pop();
        }
        int pos=q.size();
        vector<int>res(pos,0);
        while(pos-->0)
        {
            res[pos]=tweets[q.top()];
            q.pop();
        }
        return res;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    void follow(int followerId, int followeeId) {
        if(followerId != followeeId)
            followees[followerId].insert(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    void unfollow(int followerId, int followeeId) {
        followees[followerId].erase(followeeId);
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