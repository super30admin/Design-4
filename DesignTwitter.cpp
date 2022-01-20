//Time Complexity O(n)
// Space Complexity O(n)
//Problem successfully executed on leetcode
//No problems faced while coading this.


#include<iostream>
#include<vector>
#include<queue>
#include<map>
#include<stack>
using namespace std;

class tweet
{
    public:
    int tId;
    int timeStamp;
    tweet(int tId1,int timeStamp1)
    {
        tId=tId1;
        timeStamp=timeStamp1;
    }
};

struct compare
{
    bool operator() (tweet* a, tweet* b)
    {
        return a->timeStamp > b->timeStamp;
    }
};


class Twitter {
public:
    
    map<int,vector<tweet*>> uIdToTweet;
    
    map<int,unordered_set<int>>  uIdTouIds;
    
    priority_queue<tweet* , vector<tweet*>, compare> pq;
    
    int ts;
    Twitter() {
        ts=0;
    }
    
    void postTweet(int userId, int tweetId) {
        tweet* t=new tweet(tweetId,ts);
        ts++;
        
        if(uIdToTweet.find(userId)==uIdToTweet.end())
        {
            vector<tweet*> tweetVec;
            uIdToTweet[userId]=tweetVec;
        }
        uIdToTweet[userId].push_back(t);
    }
    
    vector<int> getNewsFeed(int userId) {
        vector<int> result;
        uIdTouIds[userId].insert(userId);
        for(auto &uid : uIdTouIds[userId])
        {
            vector<tweet*> twee=uIdToTweet[uid];
            for(auto &t : twee)
            {
                pq.push(t);
                if(pq.size()>10)
                {
                    pq.pop();
                }
            }
        }
        
        while(!pq.empty())
        {
            tweet* t=pq.top();
            pq.pop();
            result.push_back(t->tId);
        }
        reverse(result.begin(), result.end());
        return result;
    }
    
    void follow(int followerId, int followeeId) {
        // If followerId does not exist in Hashmap
        if(uIdTouIds.find(followerId)==uIdTouIds.end())
        {
            unordered_set<int> uIds;
            uIdTouIds[followerId]=uIds;
        }
        uIdTouIds[followerId].insert(followeeId);
    }
    
    void unfollow(int followerId, int followeeId) {
        //If followerId is present in hashmap remove it
        if(uIdTouIds.find(followerId)!=uIdTouIds.end())
        {
            uIdTouIds[followerId].erase(followeeId);
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