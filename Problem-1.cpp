
class Twitter {
public:
    class Tweet{
        public:
        int tid;
        int createdAt;
        Tweet(int tid, int createdAt){
            this->tid = tid;
            this->createdAt = createdAt;
        }
    };
    struct comp{
      bool operator() (Tweet &t1, Tweet &t2){
          return t1.createdAt > t2.createdAt;
      } 
    };
    unordered_map<int, unordered_set<int>> followers; //follow, unfollow
    unordered_map<int, vector<Tweet>> tweet; //post,get tweets
    int time;
    /** Initialize your data structure here. */
    Twitter() {
        time = 0;
    }
    /** Compose a new tweet. */
    void postTweet(int userId, int tweetId) {                                       //TC = O(1)
        follow(userId, userId); //to show that user follow itself
        if(tweet.find(userId) == tweet.end()){

            tweet.insert({userId, vector<Tweet>()});
        }
        time++;
        Tweet newTweet(tweetId, time);
        tweet[userId].push_back(newTweet);
    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    vector<int> getNewsFeed(int userId) {                                           //TC = O(nk) where n is the total number of people the user follow and k is the total number of tweets tweeted by these n number of peoples.
        unordered_set<int> set = followers[userId];
        priority_queue<Tweet, vector<Tweet>, comp> pq;
        if(!set.empty()){
            for(int user : set){
                vector<Tweet> tweets = tweet[user];
                if(!tweets.empty()){
                    for(Tweet tw : tweets){
                        pq.push(tw);
                        if(pq.size() > 10)
                            pq.pop();   
                    }
                }
            }
        }
        vector<int> result;
        while(!pq.empty()){
            result.insert(result.begin(), pq.top().tid);
            pq.pop();
        }
        return result;
    }

    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    void follow(int followerId, int followeeId) {                                   //TC = O(1)
        if(followers.find(followerId) == followers.end()){
            followers.insert({followerId, unordered_set<int>()});
        }
        followers[followerId].insert(followeeId);
    }

    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    void unfollow(int followerId, int followeeId) {                                 //TC = O(1)
        if(followers.find(followerId) != followers.end()){
            if(followers[followerId].find(followeeId) != followers[followerId].end()){
                followers[followerId].erase(followeeId);
            }
        }   
    }
};

class Twitter {
public:
    class Tweet{
        public:
        int tid;
        int createdAt;
        Tweet(int tid, int createdAt){
            this->tid = tid;
            this->createdAt = createdAt;
        }
    };
    struct comp{
      bool operator() (Tweet &t1, Tweet &t2){
          return t1.createdAt > t2.createdAt;
      } 
    };
    unordered_map<int, unordered_set<int>> followers; //follow, unfollow
    unordered_map<int, vector<Tweet*>> tweet; //post,get tweets
    int time;
    /** Initialize your data structure here. */
    Twitter() {
        time = 0;
    }
    /** Compose a new tweet. */
    void postTweet(int userId, int tweetId) {                                           //TC = O(1)
        follow(userId, userId); //to show that user follow itself
        if(tweet.find(userId) == tweet.end()){

            tweet.insert({userId, vector<Tweet*>()});
        }
        time++;
        Tweet *t = new Tweet(tweetId, time);
        tweet[userId].push_back(t);
    }

    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    vector<int> getNewsFeed(int userId) {                                                //TC = O(nk) where n is the total number of people the user follow and k is the total number of tweets tweeted by these n number of peoples.
        unordered_set<int> set = followers[userId];
        priority_queue<Tweet, vector<Tweet>, comp> pq;
        if(!set.empty()){
            for(int user : set){
                vector<Tweet*> tweets = tweet[user];
                if(!tweets.empty()){
                    for(Tweet *tw : tweets){
                        pq.push(*tw);
                        if(pq.size() > 10)
                            pq.pop();   
                    }
                }
            }
        }
        vector<int> result;
        while(!pq.empty()){
            result.insert(result.begin(), pq.top().tid);
            pq.pop();
        }
        return result;
    }

    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    void follow(int followerId, int followeeId) {                                               //TC = O(1)
        if(followers.find(followerId) == followers.end()){
            followers.insert({followerId, unordered_set<int>()});
        }
        followers[followerId].insert(followeeId);
    }

    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    void unfollow(int followerId, int followeeId) {                                             //TC = O(1)
        if(followers.find(followerId) != followers.end()){
            if(followers[followerId].find(followeeId) != followers[followerId].end()){
                followers[followerId].erase(followeeId);
            }
        }   
    }
};
