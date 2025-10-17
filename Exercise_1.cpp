// Time Complexity: O(n)
// Space Complexity: O(n)
// Were you able to solve the problem on Leetcode ? No
// Did you face any challenges while solving the problem? No

class Tweet {
public:
    int tweetId;
    int createdTs;
    Tweet(int tweetId, int createdTs):tweetId(tweetId),createdTs(createdTs) {

    }
};

class Compare{
    
public:
    bool operator()(Tweet* a, Tweet* b){
        return a->createdTs > b->createdTs;
    }
};

class Twitter {
public:
    static int s_time;
    
    unordered_map<int, vector<Tweet*>> tweetMap;
    unordered_map<int, unordered_set<int>> followerMap;

    
    Twitter() {
        s_time = 0;
    }
    
    void postTweet(int userId, int tweetId) {

        // create new tweet
        if (tweetMap.find(userId) == tweetMap.end()) {
            tweetMap[userId] = vector<Tweet*>();
        }
        tweetMap[userId].push_back(new Tweet(tweetId, s_time++));
        
        // check if user exists in followerMap
        // if (followerMap.count(userId) == 0){
        //     followerMap.insert(userId);
        // }
        follow(userId, userId);
        
    }
    
    vector<int> getNewsFeed(int userId) {
        std::priority_queue<Tweet*, vector<Tweet*>, Compare> recentHeap;
        // // get tweets for self
        // for (auto d: tweetMap[userId]){
        //     recentHeap.push(tweet);
        //     if(recentHeap.size()>10){
        //         recentHeap.pop();
        //     }
        // }
        // get tweets for followees
        if (followerMap.find(userId) != followerMap.end()) {

            // cout<<"List of tweets"<<endl;
            for (int d: followerMap[userId] ){
                
                if (tweetMap.find(d) != tweetMap.end() ){
                    
                    for (Tweet* tweet: tweetMap[d]){
                        
                        // cout<<tweet->tweetId<<endl;
                        recentHeap.push(tweet);

                        if(recentHeap.size()>10){
                            recentHeap.pop();
                        }
                    }
                }
                
            }

        }

        vector<int> mostRecentTweets;
        while (!recentHeap.empty()) {
            mostRecentTweets.push_back(recentHeap.top()->tweetId);
            recentHeap.pop();
        }
        reverse(mostRecentTweets.begin(), mostRecentTweets.end());
        return mostRecentTweets;
        
    }
    
    void follow(int followerId, int followeeId) {
        // user cannot follow themself
        
        // if(followerId == followeeId) return;
        // followerMap[followeeId].insert(followerId);

        // have user followthemself
        // followerMap[followeeId].insert(followeeId);

        if( followerMap.find(followerId) == followerMap.end() ){
            followerMap[followerId] = unordered_set<int>();
        }

        followerMap[followerId].insert(followeeId);;
        // cout<<"List of Followers"<<endl;
        // for(auto i: followerMap[followerId]){
        //     cout<<i<<endl;
        // }
        
    }
    
    void unfollow(int followerId, int followeeId) {

        // user cannot follow themself
        if(followerId == followeeId) return;

        auto it = followerMap.find(followerId);

        if(it != followerMap.end() ){
            // followeeId not found in map
            unordered_set<int>& targetSet = it->second;

            targetSet.erase(followeeId);
            
        }

        // erase followeeId key from followerMap if set is empty
        // if(targetSet.empty()) {
        //     followerMap.erase(followeeId);
        // }
        
    }
};

int Twitter::s_time = 0;
/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter* obj = new Twitter();
 * obj->postTweet(userId,tweetId);
 * vector<int> param_2 = obj->getNewsFeed(userId);
 * obj->follow(followerId,followeeId);
 * obj->unfollow(followerId,followeeId);
 */