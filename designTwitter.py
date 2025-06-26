"""
Problem: Design Twitter
Approach: efficiently store user -> tweets and user -> following in a hashmap for fast lookups and calculating the newsFeed.
"""
from heapq import heappop, heappush
from collections import defaultdict
class Twitter:

    def __init__(self):
        self.tweets = defaultdict(list)
        self.followUsers = defaultdict(set)
        self.time = 0

    def postTweet(self, userId: int, tweetId: int) -> None: #O(1)
        self.tweets[userId].append([tweetId, self.time])
        self.time += 1

    def getNewsFeed(self, userId: int) -> List[int]: #O(n*T) where n is number of followers including self and T is total tweets. heap takes nTlog10 = nT
        numTweets = 10
        minHeap = []
        
        for following in list(self.followUsers[userId]):
            followingTweets = self.tweets[following]
            for tweet in followingTweets:
                tweetId, time = tweet
                heappush(minHeap, (time, tweetId))
                if len(minHeap) > numTweets:
                    heappop(minHeap)
        
        for tweet in self.tweets[userId]:
            tweetId, time = tweet
            heappush(minHeap, (time, tweetId))
            if len(minHeap) > numTweets:
                heappop(minHeap)
        res = []
        while minHeap:
            _, tweetId = heappop(minHeap)
            res.append(tweetId)
        return res[::-1]
        

    def follow(self, followerId: int, followeeId: int) -> None: #O(1)
        self.followUsers[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None: #O(1)
        if followeeId not in self.followUsers[followerId] or followeeId == followerId:
            return
        self.followUsers[followerId].remove(followeeId)    


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)