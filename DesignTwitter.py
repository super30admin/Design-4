# Time Complexity : O(1) follow, unfollow: O(1), getNewsFeed: O(n × 10 × log 10) = O(n log 10) = effectively O(n) where n is the number of followees
# Space Complexity : O(t+f+u) where t is the number of tweets, f is the number of followers and u is the number of users.
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No
# Approach : Tweets are stored with timestamps, and users follow themselves on first post.
# While collecting feed, we loop through at most 10 latest tweets per followee.
# A min-heap of size 10 ensures we always return the most recent tweets in order.

class Twitter:

    class Tweet:
        def __init__(self, tId, time):
            self.tweetId = tId
            self.timeStamp = time

    def __init__(self):
        self.followeesMap = {}
        self.tweetsMap = {}
        self.time = 0

    def postTweet(self, userId, tweetId):
        if userId not in self.tweetsMap:
            self.tweetsMap[userId] = []
        tweet = Twitter.Tweet(tweetId, self.time)
        self.time += 1
        self.tweetsMap[userId].append(tweet)
        self.follow(userId, userId)

    def getNewsFeed(self, userId):
        pq = []
        followees = self.followeesMap.get(userId)
        if followees is not None:
            for followee in followees:
                tweets = self.tweetsMap.get(followee)
                if tweets is not None:
                    for i in range(len(tweets) - 1, max(len(tweets) - 10 - 1, -1), -1):
                        tweet = tweets[i]
                        heapq.heappush(pq, (tweet.timeStamp, tweet))
                        if len(pq) > 10:
                            heapq.heappop(pq)
        result = []
        while pq:
            result.append(heapq.heappop(pq)[1].tweetId)
        result.reverse()
        return result

    def follow(self, followerId, followeeId):
        if followerId not in self.followeesMap:
            self.followeesMap[followerId] = set()
        self.followeesMap[followerId].add(followeeId)

    def unfollow(self, followerId, followeeId):
        if followerId not in self.followeesMap:
            return
        self.followeesMap[followerId].discard(followeeId)
