/* Problem Statement:
Verified on leetcode

355. Design Twitter
Medium

Design a simplified version of Twitter where users can post tweets, follow/unfollow another user and is able to see the 10 most recent tweets in the user's news feed. Your design should support the following methods:

    postTweet(userId, tweetId): Compose a new tweet.
    getNewsFeed(userId): Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
    follow(followerId, followeeId): Follower follows a followee.
    unfollow(followerId, followeeId): Follower unfollows a followee.

Example:

Twitter twitter = new Twitter();

// User 1 posts a new tweet (id = 5).
twitter.postTweet(1, 5);

// User 1's news feed should return a list with 1 tweet id -> [5].
twitter.getNewsFeed(1);

// User 1 follows user 2.
twitter.follow(1, 2);

// User 2 posts a new tweet (id = 6).
twitter.postTweet(2, 6);

// User 1's news feed should return a list with 2 tweet ids -> [6, 5].
// Tweet id 6 should precede tweet id 5 because it is posted after tweet id 5.
twitter.getNewsFeed(1);

// User 1 unfollows user 2.
twitter.unfollow(1, 2);

// User 1's news feed should return a list with 1 tweet id -> [5],
// since user 1 is no longer following user 2.
twitter.getNewsFeed(1);


 *
 * Time Complexity : postTweet, follow,unfollow O(1) operation, newsFeed : O(n * t) : n is no of users being followed , t is 
         the total number of tweets  
 * Space complexity : O(n) n is number of tweets  
 *
 */


/* Optimizations :  To take away this timestamp hash table. Currently I am not able to optimize it more. Will think
 through it and then update it again  */
class Twitter {
public:
    int timestamp; /* unique timestamp for each tweet */
    /* Key: user id value : set of user ids who are being followed by key user id */
    unordered_map<int, unordered_set<int>> hashmap_followers;
    /* key: user id: value : timestamp of each tweet */
    unordered_map<int, set<int,greater<int>>> hashmap_tweets;
    /* Key: timestamp value : tweet */
    unordered_map<int, int> hashmap_tstamp;
    
    /** Initialize your data structure here. */
    Twitter() {
        timestamp = 0;    
    }
    
    /** Compose a new tweet. */
    void postTweet(int userId, int tweetId) {
        /* increment time stamp, add values in 2 hash maps and also start following the user */
        timestamp++;
        hashmap_tweets[userId].insert(timestamp);
        hashmap_tstamp[timestamp] = tweetId;
        follow(userId, userId);
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    vector<int> getNewsFeed(int userId) {
        vector<int> result;
        /* min heap to get combined top kth recent elements from all tweets of users */
        priority_queue <int, vector<int>, greater<int>> pqueue;
        /* start iterating set of all user id followers for that user Id */
        unordered_set<int>::iterator it2 = hashmap_followers[userId].begin();

        for (; it2 != hashmap_followers[userId].end(); it2++) {
            /* get all tweets for that user, go over one by one, add it to min heap */
            set<int,greater<int>>::iterator it_tweets = hashmap_tweets[*it2].begin();
            
            for (; it_tweets != hashmap_tweets[*it2].end(); it_tweets++) {
                /* 10 is the max count. max recent tweets being asked for */
                if (pqueue.size() == 10) {
                    /* only add it to heap now, if the following condition is satisfied */
                    if (*it_tweets > pqueue.top()) {
                        pqueue.pop();
                        pqueue.push(*it_tweets);
                    }
                } else {
                    pqueue.push(*it_tweets);                    
                }
            }
        }
        /* shrink to total size of elements got in heap */
        result.resize(pqueue.size());
        /* store in reverse order to get our desired output . */
        for (int res = pqueue.size() - 1;res >= 0; res--) {
            /* get mapped tweet from the other hash table */
            result.at(res) = hashmap_tstamp[pqueue.top()];
            pqueue.pop();
        }
        return result;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    void follow(int followerId, int followeeId) {
        /* find the followeeId hash table entry, if already present ignore */
        unordered_set<int>::iterator it = hashmap_followers[followerId].find(followeeId);
        if(it == hashmap_followers[followerId].end()) {
            hashmap_followers[followerId].insert(followeeId);
        }
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    void unfollow(int followerId, int followeeId) {
        /* dont allow deleting own mapping userid->userid, */
        if (followerId != followeeId) {
            if(hashmap_followers[followerId].find(followeeId) != hashmap_followers[followerId].end()) {
                    hashmap_followers[followerId].erase(followeeId);
            }
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
/* Execute on leetcode platform */


