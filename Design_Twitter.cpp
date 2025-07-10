#include <bits/stdc++.h>
using namespace std;

class Twitter {
public:
    struct Tweet {
        int tid;
        int createdAt;
        Tweet(int id, int time) : tid(id), createdAt(time) {}
    };

    unordered_map<int, set<int>> followedMap;
    unordered_map<int, vector<Tweet>> tweetsMap;
    int time;

    Twitter() {
        time = 0;
    }

    void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        tweetsMap[userId].push_back(Tweet(tweetId, time++));
    }

    vector<int> getNewsFeed(int userId) {
        struct compare {
            bool operator()(const Tweet& a, const Tweet& b) {
                return a.createdAt > b.createdAt;
            }
        };

        priority_queue<Tweet, vector<Tweet>, compare> pq;

        if (followedMap.count(userId)) {
            for (int fid : followedMap[userId]) {
                if (tweetsMap.count(fid)) {
                    for (const Tweet& t : tweetsMap[fid]) {
                        pq.push(t);
                        if (pq.size() > 10) pq.pop();
                    }
                }
            }
        }

        vector<int> res;
        while (!pq.empty()) {
            res.push_back(pq.top().tid);
            pq.pop();
        }
        reverse(res.begin(), res.end());
        return res;
    }

    void follow(int followerId, int followeeId) {
        followedMap[followerId].insert(followeeId);
    }

    void unfollow(int followerId, int followeeId) {
        if (followerId != followeeId) {
            followedMap[followerId].erase(followeeId);
        }
    }
};
