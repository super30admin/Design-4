//Time Complexity : O(n)
// Space Complexity :O(n)
// Did this code successfully run on Leetcode :Yes
// Any problem you faced while coding this :No
class tweet{
public:
    int id; int time;
    tweet(int id, int time){
        this->id = id;
        this->time = time;
    }
    
};
class compare{
    bool func(tweet* t1, tweet* t2){
        return t1->time > t2->time;
    }
};

class Twitter {
public:
    map<int, set<int>> followees;
    map<int, vector<tweet*>> user_tweets;
    int timestamp; int feedsize;
    /** Initialize your data structure here. */
    Twitter() {
        feedsize = 10;
    }
    
    /** Compose a new tweet. */
    void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if(user_tweets.find(userId)==user_tweets.end()){
            vector<tweet*> t;
            user_tweets[userId] = t;
        }
        tweet *temp = new tweet(tweetId, timestamp++);
        user_tweets[userId].push_back(temp);
        
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    vector<int> getNewsFeed(int userId) {
        /*//brute force
        vector<int> res;
        vector<tweet*> t = user_tweets[userId];
        vector<vector<int>> collect_tweets;
        for(int i=0; i<t.size(); i++){
            vector<int> temp ;
            tweet* tw = t[i];
            temp.push_back(tw->time);
            temp.push_back(tw->id);
            collect_tweets.push_back(temp);
            
        }
        
        sort(collect_tweets.begin(), collect_tweets.end());
        int cnt =0;
        for(int i=collect_tweets.size()-1; i>=0; i--){
            if(cnt<feedsize){
                res.push_back(collect_tweets[i][1]);
                cnt++;
            }
                
        }
        
        return res;*/
        //map<int, set<int>> followees;
        //map<int, vector<tweet*>> user_tweets;
        priority_queue<int, vector<tweet*>, greater<tweet*>> pq;
        set<int> fids = followees[userId];
        if(fids.size()>0){
            for(auto it = fids.begin(); it!=fids.end(); it++){
                vector<tweet*> ftweets = user_tweets[*it];
                if(ftweets.size()>0){
                    for(tweet* tweets:ftweets){
                        pq.push(tweets);
                        if(pq.size()>feedsize)
                            pq.pop();
                    }
                }
            }
        }
            
        vector<int> res;
        while(pq.size()>0){
            tweet* twee = pq.top();
            res.push_back(twee->id);
            pq.pop();
        }
        
        reverse(res.begin(), res.end());
        return res;
        
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    void follow(int followerId, int followeeId) {
        if(followees.find(followerId)==followees.end()){
            set<int> temp;
            followees[followerId] = temp;
        }
        followees[followerId].insert(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    void unfollow(int followerId, int followeeId) {
        if(followees.find(followerId)!=followees.end() && followerId!=followeeId){
            followees[followerId].erase(followeeId);
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