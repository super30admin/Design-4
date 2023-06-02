/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter* obj = new Twitter();
 * obj->postTweet(userId,tweetId);
 * vector<int> param_2 = obj->getNewsFeed(userId);
 * obj->follow(followerId,followeeId);
 * obj->unfollow(followerId,followeeId);
 */

// Attempt 1 (Attempt 2 is much faster in terms of time complexity)

class Twitter {
    struct User{
        int userId;
        vector<int> followeeId;
        User(){}
        User(int id){
            userId = id;
        }
    };
    struct Tweet{
        int tId;
        int useId;
        Tweet(int id, int us1Id){
            tId = id;
            useId = us1Id;
        }
    };
        vector<User> user;
        vector<Tweet> tweet;
public:
    Twitter() {
        
    }
    User getUser(int userId){
        bool flag = false;
        User u1;
        for(auto u : user){
            if(u.userId == userId){
                flag = true;
                u1 = u;
                break;
            }
        }
        if(flag == false){
            u1 = User(userId);
            user.push_back(u1);
        }
        return u1;
    }
    
    void postTweet(int userId, int tweetId) {
        
        Tweet t = Tweet(tweetId, userId);
        User u1 = getUser(userId);
        
        tweet.push_back(t);
    }
    
    vector<int> getNewsFeed(int userId) {
        vector<int> feed;
        int counter = 0;
        User us;
        for(auto x : user){
            if(x.userId == userId){
                us = x;
                break;
            }
        }
        vector<int> followees = us.followeeId;
        vector<Tweet> orderedTw;
        int j = 0;
        for(int i = tweet.size() - 1; i >= 0; i--){
            
            orderedTw.push_back(tweet[i]);
            j++;
        }
        
        for(auto ot : orderedTw){
            if(counter > 9)
                break;
            if(ot.useId == userId){
                feed.push_back(ot.tId);
                counter++;
            }
            else{
                for(auto f : followees){
                    if(f == ot.useId){
                        feed.push_back(ot.tId);
                        counter++;
                    }
                }
            }
        }
        return feed;
    }
    
    void follow(int followerId, int followeeId) {
        getUser(followerId);
        getUser(followeeId);
        bool flag = false;
        for(auto& u : user){
            if(u.userId == followerId){
                for(auto& f : u.followeeId){
                    if(f == followeeId)
                        flag = true;
                }
                if(!flag)
                    u.followeeId.push_back(followeeId);
            }
        }
    }
    
    void unfollow(int followerId, int followeeId) {
         getUser(followerId);
         getUser(followeeId);
        for(auto& u : user){
            if(u.userId == followerId){
                int i;
                for(i = 0; i < u.followeeId.size(); i++){
                    if(u.followeeId[i] == followeeId){
                        u.followeeId.erase(u.followeeId.begin()+i);
                        break;
                    }
                }
            }
        }
    }
};



// Attempt - 2

class Twitter {
    struct Tweet{
        int id;
        int timestamp;
        
        public: Tweet(int tId, int time){
            id = tId;
            timestamp = time;
        }
    };
    
    unordered_map<int, set<int>> followingMap;
    unordered_map<int, vector<Tweet>> userTweetsMap;
    int t;
    
public:
    Twitter() {
        t = 0;
    }
    
    void postTweet(int userId, int tweetId) {
        if(userTweetsMap[userId].size() == 0)
            followingMap[userId].insert(userId);
        
        userTweetsMap[userId].push_back(Tweet(tweetId, t++));
    }
    
    struct comp{
        bool operator()(const Tweet& t1, const Tweet& t2){
            return t1.timestamp > t2.timestamp;
        }
    };
    vector<int> getNewsFeed(int userId) {
        priority_queue<Tweet, vector<Tweet>, comp> pq;
        
        set<int> followed = followingMap[userId];
        for(auto it = followed.begin(); it != followed.end(); ++it){
            vector<Tweet> userTweets = userTweetsMap[*it];
            for(Tweet t : userTweets){
                pq.push(t);
                if(pq.size() > 10)
                    pq.pop();
            }
        }
        
        vector<int> newsFeed;
        while(!pq.empty()){
            Tweet t = pq.top();
            newsFeed.push_back(t.id);
            pq.pop();
        }
        reverse(newsFeed.begin(), newsFeed.end());
        return newsFeed;
    }
    
    void follow(int followerId, int followeeId) {
        if(!followingMap[followerId].count(followeeId))
            followingMap[followerId].insert(followeeId);
    }
    
    void unfollow(int followerId, int followeeId) {
        if(followerId != followeeId)
            followingMap[followerId].erase(followeeId);
    }
};

