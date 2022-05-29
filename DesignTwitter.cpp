// Time Complexity : O(1) All operation except getNewsFeed. TC of getNewsFeed = O(m*n*(log k))
//          where m :- number of users
//                n :- Average number of tweets per user.
//                k :- tweet limit.
// Space Complexity : O(m*n) -> for storing all the tweets and for getNewsFeed = O(k) to build heap.
// Did this code successfully run on Leetcode : yes

// Three line explanation of solution in plain english
/* Use two hashmaps, first to store follower and its followee. Second to user and its tweets.
 * When getNewsFeed is called for a user, then get the list of followees for that user. 
 * And build a min heap of size k of the tweets of all of its followee. Build a result using this heap and return.
 */

class Twitter {
public:
    class Tweet{
        
        public:
            int id;
            int time;
        
            Tweet(int id, int time)
            {
                this->id = id;
                this->time = time;
            }
    };
    
    map<int, set<int>> followers;
    map<int, vector<Tweet>> tweets;
    
    int time;
    int limit;
    
    Twitter() {
        time = 1;
        limit = 10;
    }
    
    void postTweet(int userId, int tweetId) {
        Tweet t(tweetId, time++);
        auto ele = followers.find(userId);
        if (ele == followers.end())
        {
            follow(userId, userId);
        }
        
        auto tweetsList = tweets.find(userId);
        if (tweetsList == tweets.end())
        {
            tweets.insert({userId, {}});
        }
        tweetsList = tweets.find(userId);
        tweetsList->second.push_back(t);
    }
    
    vector<int> getNewsFeed(int userId) {
        auto cmp = [](const Tweet& t1, const Tweet& t2){
            return t1.time > t2.time;
        };
        
        priority_queue<Tweet, vector<Tweet>, decltype(cmp)> pq(cmp);
        follow(userId, userId);
        auto follower = followers.find(userId);
        
        if (follower == followers.end())
            return {};
        
        for (auto ele : follower->second)
        {
            auto ts = tweets.find(ele);
            
            if (ts == tweets.end())
                continue;
            
            vector<Tweet> tweetsList = ts->second; 
            for (auto t : tweetsList)
            {
                pq.push(t);
                
                if (pq.size() > limit)
                    pq.pop();
            }
        }
        
        vector<int> results;
        while (!pq.empty())
        {
            auto t = pq.top();
            pq.pop();
            results.insert(results.begin(), t.id);
        }
        return results;
        // return {};
    }
    
    void follow(int followerId, int followeeId) {
        auto ele = followers.find(followerId);
        if (ele == followers.end())
        {
            followers.insert({followerId, {followerId}});
        }
        followers.find(followerId)->second.insert(followeeId);
    }
    
    void unfollow(int followerId, int followeeId) {
        auto ele = followers.find(followerId);
        if (ele != followers.end() && followeeId != followerId)
        {
            ele->second.erase(followeeId);
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