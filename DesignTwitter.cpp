#include<iostream>
#include<bits/stdc++.h>
#include<vector>
using namespace std;
/*
Each user is represented by a User object that stores their own tweets and the set of people they follow.
When posting a tweet, we create a Tweet with a global timestamp and attach it to the user's tweet list.
To build the news feed, we gather tweets from all followees, push the latest into a min-heap, and return the top 10 in reverse order.
*/
class Twitter{
public:
    struct Tweet{
        int tweetId;
        int time;
        Tweet(int id, int time){
            this->tweetId = id;
            this->time = time;
        }
    };
    struct User{
        int userId;
        unordered_set<int> followees;
        vector<Tweet> tweets;
        User(int id) : userId(id){}
    };

    int timeStamp = 0;
    unordered_map<int, User*> userMap;

    Twitter() {}

    void postTweet(int userId, int tweetId){
        Tweet newTweet(tweetId, timeStamp++);
        if(userMap.count(userId)){
            userMap[userId]->tweets.push_back(newTweet);
        }else{
            User* newUser = new User(userId);
            newUser->followees.insert(userId);
            newUser->tweets.push_back(newTweet);
            userMap[userId] = newUser;
        }
    }

    vector<int> getNewsFeed(int userId){
        vector<int> result;
        auto cmp = [](Tweet& a, Tweet& b) {return a.time > b.time;};
        priority_queue<Tweet, vector<Tweet>, decltype(cmp) > pq(cmp);

        if(!userMap.count(userId)) return result;
        
        unordered_set<int>& followees = userMap[userId]->followees;
        
        for(int followee : followees){
            vector<Tweet>& tweets = userMap[followee]->tweets;
            for(int i = tweets.size()-1; i >= max(0, (int)tweets.size() - 10); i--){
                pq.push(tweets[i]);
                if(pq.size() > 10) pq.pop();
            }
        }
        vector<int> stack;
        while(!pq.empty()){
            stack.push_back(pq.top().tweetId);
            pq.pop();
        }
        reverse(stack.begin(), stack.end());
        return stack;
    }

    void follow(int followerId, int followeeId){
        if(!userMap.count(followerId)){
            User* newUser = new User(followerId);
            newUser->followees.insert(followeeId);
            userMap[followerId] = newUser;
        }else{
            userMap[followerId]->followees.insert(followerId);
            userMap[followerId]->followees.insert(followeeId);
        }
        if(!userMap.count(followeeId)){
            User* newUser = new User(followeeId);
            userMap[followeeId] = newUser;
        }
    }

    void unfollow(int followerId, int followeeId){
        if(userMap.count(followerId)){
            userMap[followerId]->followees.erase(followeeId);
        }
    }
};
