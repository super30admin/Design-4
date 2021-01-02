class Twitter {
public:
    int time;
    unordered_map<int,set<int>> follows;
    priority_queue<pair<int,int>,vector<pair<int,int>>, greater<pair<int,int>> > pq;
    
    unordered_map<int,vector<pair<int,int>>> tweets;
    /** Initialize your data structure here. */
    Twitter() {
        time = INT_MIN;
    }
    
    /** Compose a new tweet. */
    void postTweet(int userId, int tweetId) {
        time++;
        tweets[userId].push_back(make_pair(time,tweetId));
    }
    
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    vector<int> getNewsFeed(int userId) {
        
        vector<int> ret;
        int i = tweets[userId].size()-1;
        int count = 1;
        while(count<=10 && i>=0){
            pq.push(tweets[userId][i--]);
            if(pq.size()>10) pq.pop();
            count++;
        }
        
        for(auto i = follows[userId].begin();i!=follows[userId].end();i++){
            if(*i!=userId){
                int j = tweets[*i].size()-1;
                int count = 1;
                while(count<=10 && j>=0){
                    pq.push(tweets[*i][j--]); 
                    if(pq.size()>10) pq.pop();
                    count++;
                }
            }
        }
        
        while(!pq.empty()){
            ret.push_back(pq.top().second);
            pq.pop();
        }
        
        reverse(ret.begin(),ret.end());
        
        return ret;
        
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    void follow(int followerId, int followeeId) {
        follows[followerId].insert(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    void unfollow(int followerId, int followeeId) {
        follows[followerId].erase(followeeId);
    }
};