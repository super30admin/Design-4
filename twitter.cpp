/*
Approach:
1. Follow: Maintain a hasmap of user to the people they follow
2. Unfollow: We need to remove the followed from theu sers list, so maintain the users in 1 in a hashset instead of a list
3. Post: We maintain a list of tweets per user
4. GetFeed: Get all the people the user follows. Then get the latest 10 tweets per user. Using these 10 tweets per user, we get the top 10 from them by maintaing a minHeap(because time is increasing)"

Time:
Follow: O(1)
Unfollow: O(1)
Post: O(1)
GetFeed: O(usersFollowing)

Passes on leetcode: Yes

*/


#include<bits/stdc++.h>
using namespace std;

void printArr(vector<int> arr) {
    for(int a: arr) {
        cout<<a<<" ";
    }
    cout<<endl;
}

class Tweet {
public:
    int time;
    int tweetId;
    Tweet(int tweetId, int time) {
        this->time = time;
        this->tweetId = tweetId;
    }
};

class CustomComparator {
    public:
    bool operator()(Tweet* e1, Tweet* &e2) {
        return e1->time > e2->time;
    }
};

class Twitter {
public:
    unordered_map<int, unordered_set<int>> following;
    unordered_map<int, vector<Tweet*>> tweetRepo;
    int currTime;

    Twitter() {
        following.clear();
        tweetRepo.clear();
        currTime=0;
    }
    
    void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if(tweetRepo.find(userId) == tweetRepo.end()) {
            tweetRepo[userId] = vector<Tweet*>();
        }
        Tweet* newTweet = new Tweet(tweetId, currTime++);
        tweetRepo[userId].push_back(newTweet);
    }
    
    vector<int> getNewsFeed(int userId) {
        priority_queue<Tweet*, vector<Tweet*>, CustomComparator> minHeap;
        for(int followeeId: following[userId]) {
            vector<Tweet*> userTweets = tweetRepo[followeeId];
            // Traverse only last 10 tweets
            int end = max(0, (int)userTweets.size()-10);
            for(int i=userTweets.size()-1; i>=end; i--) {
                minHeap.push(userTweets[i]);
                if(minHeap.size() > 10) {
                    minHeap.pop();
                }
            }
        }

        vector<int> feed(minHeap.size());
        int pos=minHeap.size()-1;
        while(!minHeap.empty()) {
            feed[pos--]=minHeap.top()->tweetId;
            minHeap.pop();
        }

        return feed;
    }
    
    void follow(int followerId, int followeeId) {
        if(following.find(followerId) == following.end()) {
            following[followerId] = unordered_set<int>();
            following[followerId].insert(followerId);
        }
        following[followerId].insert(followeeId);
    }
    
    void unfollow(int followerId, int followeeId) {
        if(following.find(followerId) != following.end()) {
            following[followerId].erase(followeeId);
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

int main() {
    Twitter* twitter = new Twitter();
    twitter->postTweet(1, 5); // User 1 posts a new tweet (id = 5).
    printArr(twitter->getNewsFeed(1));  // User 1's news feed should return a list with 1 tweet id -> [5]. return [5]
    twitter->follow(1, 2);    // User 1 follows user 2.
    twitter->postTweet(2, 6); // User 2 posts a new tweet (id = 6).
    printArr(twitter->getNewsFeed(1));  // User 1's news feed should return a list with 2 tweet ids -> [6, 5]. Tweet id 6 should precede tweet id 5 because it is posted after tweet id 5.
    twitter->unfollow(1, 2);  // User 1 unfollows user 2.
    printArr(twitter->getNewsFeed(1));  // User 1's news feed should return a list with 1 tweet id -> [5], since user 1 is no longer following user 2.

}