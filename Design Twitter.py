""""// Time Complexity : O(1)
// Space Complexity :O(n)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this :
"""

import heapq
class Tweet:
    def __init__(self, tweetId, timestamp):
        self.tweetId = tweetId
        self.timestamp = timestamp

    def __lt__(self, tweet):
        return self.timestamp < tweet.timestamp


class Twitter:
    def __init__(self):
        self.tweets = {}
        self.users = {}
        self.timestamp = 0

    def postTweet(self, userId: int, tweetId: int) -> None:
        self.follow(userId, userId)
        if userId not in self.tweets:
            self.tweets[userId] = []
        self.timestamp += 1
        tweet = Tweet(tweetId, self.timestamp)
        self.tweets[userId].append(tweet)

    def getNewsFeed(self, userId: int) -> List[int]:
        heap = []
        count = 0
        if userId not in self.users:
            return []
        if userId in self.users:
            for x in self.users[userId]:
                if x in self.tweets:
                    if self.tweets[x]:
                        for y in self.tweets[x]:
                            heapq.heappush(heap, y)
                            count += 1
                            if count > 10:
                                heapq.heappop(heap)
                                count -= 1
        returnList = []

        for i in range(0, len(heap)):
            returnList.append(heapq.heappop(heap).tweetId)

        returnList = returnList[-1::-1]

        return returnList

    def follow(self, followerId: int, followeeId: int) -> None:
        if followerId not in self.users:
            self.users[followerId] = set()
            # self.users[followerId].add(followerId)
        self.users[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if (followerId in self.users) and (followeeId in self.users[followerId]):
            self.users[followerId].remove(followeeId)

# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)