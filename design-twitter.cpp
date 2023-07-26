// Time Complexity : O(m)
// Space Complexity : O(m + n)
// Did this code successfully run on Leetcode : Yes

#include <iostream>
#include <algorithm>
#include <unordered_map>
#include <unordered_set>
#include <vector>
#include <queue>

class Twitter {
private:
    struct Tweet {
        int tid;
        int createdAt;

        Tweet(int tid, int time) : tid(tid), createdAt(time) {}
    };

    std::unordered_map<int, std::unordered_set<int>> followed;
    std::unordered_map<int, std::vector<Tweet>> tweets;
    int time;

public:
    Twitter() {
        followed = std::unordered_map<int, std::unordered_set<int>>();
        tweets = std::unordered_map<int, std::vector<Tweet>>();
        time = 0;
    }

    void postTweet(int userId, int tweetId) {
        if (!tweets.count(userId)) {
            tweets[userId] = std::vector<Tweet>();
            follow(userId, userId);
        }
        tweets[userId].emplace_back(tweetId, time);
        time++;
    }

    std::vector<int> getNewsFeed(int userId) {
        auto cmp = [](const Tweet& a, const Tweet& b) { return a.createdAt > b.createdAt; };
        std::priority_queue<Tweet, std::vector<Tweet>, decltype(cmp)> pq(cmp);

        const auto& followeds = followed[userId];
        if (!followeds.empty()) {
            for (int fid : followeds) {
                const auto& fTweets = tweets[fid];
                if (!fTweets.empty()) {
                    int K = std::min(static_cast<int>(fTweets.size()), 10);
                    for (int i = K - 1; i >= 0 && K > 0; i--) {
                        pq.push(fTweets[i]);
                        K--;
                        if (pq.size() > 10) {
                            pq.pop();
                        }
                    }
                }
            }
        }

        std::vector<int> result;
        while (!pq.empty()) {
            result.emplace_back(pq.top().tid);
            pq.pop();
        }
        std::reverse(result.begin(), result.end());
        return result;
    }

    void follow(int followerId, int followeeId) {
        followed[followerId].insert(followeeId);
    }

    void unfollow(int followerId, int followeeId) {
        if (followed.count(followerId) && followerId != followeeId) {
            followed[followerId].erase(followeeId);
        }
    }
};