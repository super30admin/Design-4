# Time complexity:
#   postTweet: O(1)
#   getNewsFeed: O(n log k) 
#   follow and unfollow: O(1)
# Space complexity: O(n + m)

import heapq
from collections import defaultdict

class Twitter:

    def __init__(self):
        self.tweets = defaultdict(list)
        self.followers = defaultdict(set)
        self.time = 0

    def postTweet(self, userId: int, tweetId: int) -> None:
        self.tweets[userId].append((-self.time, tweetId))
        self.time += 1

    def getNewsFeed(self, userId: int) -> list[int]:
        news_feed = []
        users = self.followers[userId].union({userId})
        for user in users:
            news_feed.extend(self.tweets[user][-10:])
        return [tweet[1] for tweet in heapq.nsmallest(10, news_feed)]

    def follow(self, followerId: int, followeeId: int) -> None:
        self.followers[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        self.followers[followerId].discard(followeeId)
