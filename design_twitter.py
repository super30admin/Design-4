# ---------------------------------------------
# Approach:
# - Maintain User objects with their tweets and followee set.
# - Each tweet has a timestamp (simulated with a decreasing counter).
# - To get the news feed, collect up to 10 tweets from the user and each followed user.
# - Use a min-heap to get top 10 most recent tweets.
#
# Time Complexity:
# - postTweet: O(1)
# - follow / unfollow: O(1)
# - getNewsFeed: O(n), where n = number of followees (each has ≤10 tweets)
#
# Space Complexity:
# - O(1) per user for tweets/followees (bounded by 10)
# - O(n) for collecting tweets and heap
# ---------------------------------------------
import time
import heapq

class User:
    def __init__(self, UserId):
        self.UserId = UserId
        self.following_set = set()
        self.tweets = []
    
    def follow_user(self, uid):
        if uid in self.following_set:
            return False
        self.following_set.add(uid)
        return True
    
    def unfollow_user(self, uid):
        if uid not in self.following_set:
            return False
        self.following_set.remove(uid)
        return True
    
    def add_tweets(self, tweet):
        self.tweets.append(tweet)
        return
    
    def get_tweets(self):
        if len(self.tweets) > 10:
            return self.tweets[-10:]
        return self.tweets if self.tweets != None else []

class tweet:
    def __init__(self, tweetId, time):
        self.tweetId = tweetId
        self.createdAt = time

class Twitter:

    def __init__(self):
        self.userslist = {}
        self.time = 10**9

    def postTweet(self, userId, tweetId):
        if userId not in self.userslist:
            self.userslist[userId] = User(userId)
        t1 = tweet(tweetId, self.time)
        self.time -= 1
        self.userslist[userId].add_tweets(t1)

    def getNewsFeed(self, userId):
        if userId not in self.userslist:
            return []
        res = list(self.userslist[userId].get_tweets())
        for user in self.userslist[userId].following_set:
            res.extend(self.userslist[user].get_tweets())
        minHeap = []
        for tweet in res:
            heapq.heappush(minHeap, (-tweet.createdAt, tweet.tweetId))
            if len(minHeap) > 10:
                heapq.heappop(minHeap)
        result = []
        while minHeap:
            result.append(heapq.heappop(minHeap)[1])
        return result[::-1]
        

    def follow(self, followerId, followeeId):
        if followerId not in self.userslist:
            u1 = User(followerId)
            self.userslist[followerId] = u1
        if followeeId not in self.userslist:
            u2 = User(followeeId)
            self.userslist[followeeId] = u2
        if followerId != followeeId:
            self.userslist[followerId].follow_user(followeeId)
        return
        

    def unfollow(self, followerId, followeeId):
        if followerId not in self.userslist:
            u1 = User(followerId)
            self.userslist[followerId] = u1
        if followeeId not in self.userslist:
            u2 = User(followeeId)
            self.userslist[followeeId] = u2
        if followerId != followeeId:
            self.userslist[followerId].unfollow_user(followeeId)
        return


# ---------------- MAIN FUNCTION ----------------
if __name__ == "__main__":
    twitter = Twitter()
    twitter.postTweet(1, 5)                      # User 1 posts tweet id 5
    print(twitter.getNewsFeed(1))                # [5]
    twitter.follow(1, 2)                         # User 1 follows user 2
    twitter.postTweet(2, 6)                      # User 2 posts tweet id 6
    print(twitter.getNewsFeed(1))                # [6, 5]
    twitter.unfollow(1, 2)                       # User 1 unfollows user 2
    print(twitter.getNewsFeed(1))                # [5]