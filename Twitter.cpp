//time: O(nlogk)
//space: O(k)

class Tweet {
public:
    int id, timeStamp;

    Tweet(int id, int timeStamp) {
        this->id = id;
        this->timeStamp = timeStamp;
    }
};

class Twitter {
private:
    unordered_map<int, unordered_set<int>> followedMap;
    unordered_map<int, vector<Tweet>> tweetsMap;
    int time;
    int MAX_TWEETS = 10;

public:
    Twitter()  {
        this->time = 1;
    }

    void postTweet(int userId, int tweetId) {
        if (!followedMap.count(userId)) {
            follow(userId, userId);
        }

        if (!tweetsMap.count(userId)) {
            tweetsMap[userId] = std::vector<Tweet>();
        }
        tweetsMap[userId].emplace_back(Tweet(tweetId, time++));
    }

    vector<int> getNewsFeed(int userId) {
        auto compare = [](const Tweet& a, const Tweet& b) {
            return a.timeStamp > b.timeStamp;
        };
        priority_queue<Tweet, vector<Tweet>, decltype(compare)> minHeap(compare);
        vector<int> result;

        if (!followedMap.count(userId))
            return result;

        const unordered_set<int>& users = followedMap[userId];
        vector<Tweet> fTweets;
        for (int user : users) {
            if (!tweetsMap.count(user))
                continue;
            const vector<Tweet>& tweets = tweetsMap[user];
            for (const Tweet& tweet : tweets) {
                fTweets.push_back(tweet);
            }
        }

        for (const Tweet& tweet : fTweets) {
            minHeap.push(tweet);
            if (minHeap.size() > MAX_TWEETS) {
                minHeap.pop();
            }
        }

        while (!minHeap.empty()) {
            result.insert(result.begin(), minHeap.top().id);
            minHeap.pop();
        }

        return result;
    }

    void follow(int followerId, int followeeId) {
        if (!followedMap.count(followerId)) {
            followedMap[followerId] = unordered_set<int>();
            followedMap[followerId].insert(followerId);
        }
        followedMap[followerId].insert(followeeId);
    }

    void unfollow(int followerId, int followeeId) {
        if (followerId == followeeId || !followedMap.count(followerId))
            return;

        followedMap[followerId].erase(followeeId);
    }
};