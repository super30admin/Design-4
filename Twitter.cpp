/*
Intuition: Think about all the high level design elements first. Dont think about variables.
First see how the users can be related to each other using following list( adjaancy list)
Then think about how to store tweets.

#####################################################################
postTweet Method: Adding the tweet in set
Time Complexity : O(1)
#####################################################################
getNewsFeed Method: Getting the K tweets where K = 10
Time Complexity : O(N*T), Getting T tweets from N users, where T is the total number of tweets from N total users.
Space Complexity : O(NlogK) For the heap to sort it according to min heap.
#####################################################################
follow Method: Adding a user to the set.
Time Complexity : O(1)
#####################################################################
unfollow Method: Remove a user from the set.
Time Complexity : O(1)
#####################################################################

*/
class Tweet{
    public:
        int tweetID;
        int time;
        Tweet(int tweetID, int time){
            this->tweetID = tweetID;
            this->time = time;
        }
};
struct compares{
    bool operator()(Tweet p1, Tweet p2){
        return p1.time >p2.time;
    }
};

class Twitter {
public:
    int time =0;
    map<int, set<int>> followingList;
    map<int, vector<Tweet>> tweets;
    Twitter() {
    }
    
    /** Compose a new tweet. */
    void postTweet(int userId, int tweetId) {
        follow(userId, userId);
        if ( tweets.find(userId) == tweets.end()){
            vector<Tweet> tweetsOfUser;
            tweets[userId] = tweetsOfUser;
        }
        
        tweets[userId].push_back(Tweet(tweetId, time++));
        
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    vector<int> getNewsFeed(int userId) {
        vector<int> feed;
        priority_queue <Tweet, vector<Tweet>, compares> pq;
        set<int> following = followingList[userId];
        
        for ( auto followingUser: following){
            vector<Tweet>tweetsOfFollowingUser = tweets[followingUser];
            for ( auto tweet: tweetsOfFollowingUser){
                pq.push(tweet);
                if ( pq.size()>10){
                    pq.pop();
                }
            }
        }
        while ( pq.size()!=0){
            auto element = pq.top();
            pq.pop();
            feed.push_back(element.tweetID);
        }
        reverse(feed.begin(), feed.end());
        return feed;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    void follow(int followerId, int followeeId) {
        if ( followingList.find(followerId) == followingList.end()){
            set<int> newSet;
            followingList[followerId] = newSet;
        }
        followingList[followerId].insert(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    void unfollow(int followerId, int followeeId) {
        if ( followingList.find(followerId) != followingList.end()){
            followingList[followerId].erase(followeeId);
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