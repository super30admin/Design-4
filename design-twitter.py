# Time O(N log k)
# Space O(total followees connections or edges in graph) + O(total tweets)
from heapq import *
class Twitter:
    class Tweet:
        def __init__(self, time, id):
            self.tTime = time
            self.id = id

    def __init__(self):
        self.tweets = {} # userId to List[Tweet]
        self.followees = {} # userId to List[userId]
        self.time = 0
        
    # O(1)
    def postTweet(self, userId: int, tweetId: int) -> None:
        self.follow(userId, userId)
        if userId in self.tweets:
            self.tweets[userId].append(self.Tweet(self.time,tweetId))
        else:
            self.tweets[userId] = [self.Tweet(self.time,tweetId)]
        self.time += 1

    # O(N log k) N: total tweets in twitter
    def getNewsFeed(self, userId: int) -> List[int]:
        hq = []
        if userId in self.followees:
            for user in self.followees[userId]:
                if user in self.tweets:
                    for tweet in self.tweets[user]:
                        heappush(hq, (tweet.tTime, tweet))
                        if len(hq) > 10: heappop(hq)
        res = []
        while len(hq) > 0:
            time, tweet = heappop(hq)
            res.append(tweet.id)
        return res[::-1]

    #O(1)
    def follow(self, followerId: int, followeeId: int) -> None:
        if followerId in self.followees:
            self.followees[followerId].add(followeeId)
        else:
            self.followees[followerId] = {followeeId}


    # O(1)
    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId in self.followees:
            if followeeId in self.followees[followerId]:
                self.followees[followerId].remove(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)