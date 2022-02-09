"""
time: O(Followers)
space: O(1), max 10 tweets
"""
import time as time
from collections import defaultdict; import heapq
class Twitter:

    def __init__(self):
        self.tweets = defaultdict(list)
        self.followers = defaultdict(set)

    def postTweet(self, userId: int, tweetId: int) -> None:
        self.tweets[userId].insert(0,(tweetId, time.time()))

    def getNewsFeed(self, userId: int) -> List[int]:
        follwers = self.followers[userId]
        #print(follwers)
        minTime = None
        follwers.add(userId)
        l = []
        currIds = set()
        heapq.heapify(l)
        for i in follwers:
            for tweetId, time in self.tweets[i]:
                if len(l)<10:
                    heapq.heappush(l, (time, tweetId))
                    continue
                if len(l)==10:
                    if l[0][0]<time:
                        heapq.heappop(l)
                        heapq.heappush(l, (time, tweetId))
                
        tweetArray = []
        l.sort()
        #print(l)
        for i in range(len(l)-1,-1,-1):
            tweetArray.append(l[i][1])
        return tweetArray

    def follow(self, followerId: int, followeeId: int) -> None:
        self.followers[followerId].add(followeeId) 

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followeeId in self.followers[followerId]:
            self.followers[followerId].remove(followeeId) 

