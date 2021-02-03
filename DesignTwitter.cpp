class Twitter {
public:
    /** Initialize your data structure here. */
    unordered_map<int, unordered_map<int,int>> follows;
    vector<pair<int,int>> tweets;
    Twitter() {
        
    }
    
    /** Compose a new tweet. */
    void postTweet(int userId, int tweetId) {
        tweets.push_back({userId, tweetId});
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    vector<int> getNewsFeed(int userId) {
        int count=0;
        vector<int> answer;
        for (int i=tweets.size()-1; i>=0 && count<10 ;i--) {
            if (tweets[i].first==userId || follows[userId][tweets[i].first]==1) {
                answer.push_back(tweets[i].second);
                count++;
            }
        }
        return answer;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    void follow(int followerId, int followeeId) {
        follows[followerId][followeeId]=1;
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    void unfollow(int followerId, int followeeId) {
        follows[followerId][followeeId]=0;
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