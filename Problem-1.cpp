// https://leetcode.com/problems/design-twitter/description/
// Time Complexity : 
// postTweet() : O(1)
// getNewsFeed() : O(N log 10) n is the number of tweets from the users and their followees
// follow() : O(1)
// unfollow() : O(1)
// Space Complexity : O(n + m) where n is the number of tweets and m is the followers/followees
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach

class Tweet{
    public:
        int tid;
        int time;
    
        Tweet(int tid, int time) : tid(tid), time(time){
    
        };
    };
    class Twitter {
    private:
        unordered_map<int, unordered_set<int>> followers;
        unordered_map<int, vector<Tweet>> tweets;
        int timestamp;
    public:
    
        Twitter() : timestamp(0) {
            
        }
        
        void postTweet(int userId, int tweetId) {
            tweets[userId].emplace_back(tweetId, timestamp++);
        }
        
        vector<int> getNewsFeed(int userId) {
            priority_queue<pair<int,int>> pq;
            unordered_set<int> followeeList = followers[userId];
            followeeList.insert(userId);
            for(auto i:followeeList){
                auto j = tweets[i];
                for(int i= max(0, (int)j.size()-10); i < j.size(); i++){
                    pq.emplace(j[i].time, j[i].tid);
                }
            }
            vector<int> res;
            while(!pq.empty() && res.size()<10){
                res.push_back(pq.top().second);
                pq.pop();
            }
            return res;
        }
        
        void follow(int followerId, int followeeId) {
            if(followerId != followeeId){
                followers[followerId].insert(followeeId);
            }
        }
        
        void unfollow(int followerId, int followeeId) {
            followers[followerId].erase(followeeId);
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