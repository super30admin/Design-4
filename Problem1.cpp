// Problem: Design twitter
//  Time Complexity : O(n) where is n is the number of followees
//  Space Complexity : O(T + F + U) where t is total tweets, f is total
//  followers, U is total users Did this code successfully run on Leetcode : Yes
//  Any problem you faced while coding this : No

// Your code here along with comments explaining your approach in three
// sentences only
// 1. We use followerMap and tweetMap to store follower->followees and user_id
// -> tweets relationship
// 2. this gives follow, unfollow, postTweet O(1) operation
// 3. For postTweet, we take last 10 tweets from every follower of the user and
// add to priority queue Then we start taking the values out and return the
// reverse result

#include <bits/stdc++.h>
using namespace std;

class Tweet {
public:
  int tweetId;
  int createdAt;
  Tweet(int id, int time) {
    this->tweetId = id;
    this->createdAt = time;
  }
};

class Twitter {
  struct compare {
  public:
    bool operator()(Tweet *a, Tweet *b) { return a->createdAt > b->createdAt; }
  };

public:
  unordered_map<int, unordered_set<int>> followerMap;
  unordered_map<int, vector<Tweet *>> tweetMap;
  int time;
  Twitter() { this->time = 0; }

  void postTweet(int userId, int tweetId) {
    if (tweetMap[userId].size() == 0) {
      tweetMap[userId] = {};
    }
    Tweet *t = new Tweet(tweetId, ++time);
    tweetMap[userId].push_back(t);
    follow(userId, userId);
  }

  vector<int> getNewsFeed(int userId) {
    priority_queue<Tweet *, vector<Tweet *>, compare> pq;
    const auto &userIds = followerMap[userId];
    vector<int> result;
    for (auto followeeId : userIds) {
      const auto &tweets = tweetMap[followeeId];
      int size = (int)tweets.size();
      for (int i = size - 1; i >= size - 10 && i >= 0; i--) {
        pq.push(tweets[i]);
        if (pq.size() > 10) {
          pq.pop();
        }
      }
    }
    while (!pq.empty()) {
      auto top = pq.top();
      result.push_back(top->tweetId);
      pq.pop();
    }
    reverse(result.begin(), result.end());
    return result;
  }

  void follow(int followerId, int followeeId) {
    followerMap[followerId].insert(followeeId);
  }

  void unfollow(int followerId, int followeeId) {
    followerMap[followerId].erase(followeeId);
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
