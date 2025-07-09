from collections import defaultdict, deque
import heapq

class Twitter:

    def __init__(self):
        self.timestamp = 0
        self.tweets = defaultdict(deque)  # userId -> deque of (timestamp, tweetId)
        self.follows = defaultdict(set)   # userId -> set of followeeIds

    def postTweet(self, userId: int, tweetId: int) -> None:
        self.timestamp += 1
        self.tweets[userId].appendleft((self.timestamp, tweetId))
        if len(self.tweets[userId]) > 10:
            self.tweets[userId].pop()

    def getNewsFeed(self, userId: int) -> list[int]:
        max_heap = []
        self.follows[userId].add(userId)  # ensure user follows themself

        for followee in self.follows[userId]:
            for tweet in self.tweets[followee]:
                heapq.heappush(max_heap, tweet)
                if len(max_heap) > 10:
                    heapq.heappop(max_heap)

        result = []
        while max_heap:
            result.append(heapq.heappop(max_heap)[1])

        return result[::-1]  # most recent first

    def follow(self, followerId: int, followeeId: int) -> None:
        if followerId != followeeId:
            self.follows[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followeeId in self.follows[followerId]:
            self.follows[followerId].remove(followeeId)
