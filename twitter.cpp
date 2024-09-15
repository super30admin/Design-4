class Twitter {
private:    
   unordered_map<int,set<int>>users;
   unordered_map<int,vector<pair<int, int>>> t;
   long time; 

public:
/** Initialize your data structure here. */
Twitter() {
    time = 0;
}

/** Compose a new tweet. */
void postTweet(int userId, int tweetId) {
    t[userId].push_back({time++, tweetId});
}

/** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
vector<int> getNewsFeed(int userId) {
    priority_queue<pair<int,int>>pq;
    for (auto it=t[userId].begin();it!=t[userId].end();it++)
        pq.push(*it);
    for (auto it1=users[userId].begin();it1!=users[userId].end();it1++)
    {
        int user=*it1;
        for (auto it2=t[user].begin();it2!=t[user].end();it2++)
            pq.push(*it2);
    }   
    vector<int> result;
    while(pq.size()>0) 
    {
        result.push_back(pq.top().second);
        if (result.size()==10)
            break;
        pq.pop();
    }
    return result;
}

/** Follower follows a followee. If the operation is invalid, it should be a no-op. */
void follow(int followerId, int followeeId) {
    if (followerId != followeeId)
        users[followerId].insert(followeeId);
}

/** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
void unfollow(int followerId, int followeeId) {
    users[followerId].erase(followeeId);
}
};