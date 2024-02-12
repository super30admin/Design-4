#Time Complexity : O(n log k), where n is the total number of tweets and k is the number of followees
#Space Complexity :O(n + k)
#Did this code successfully run on Leetcode : yes

from ast import List
from collections import defaultdict
import heapq

class Twitter:

    def __init__(self):
        self.count = 0
        self.tweetmap = defaultdict(list)
        self.followmap = defaultdict(set)
        

    def postTweet(self, userId: int, tweetId: int) -> None:
        self.tweetmap[userId].append([self.count, tweetId])
        self.count -= 1

    def getNewsFeed(self, userId: int) -> List[int]:
        res = []
        minHeap = []

        self.followmap[userId].add(userId)
        for followeeId in self.followmap[userId]:
            if followeeId in self.tweetmap:
                idx = len(self.tweetmap[followeeId]) - 1
                count, tweetId = self.tweetmap[followeeId][idx]
                minHeap.append((count, tweetId, followeeId, idx-1))
        
        heapq.heapify(minHeap)
        while minHeap and len(res) < 10:
            count, tweetId, followeeId, idx = heapq.heappop(minHeap)
            res.append(tweetId)
            
            if idx >= 0:
                count, tweetId = self.tweetmap[followeeId][idx]
                heapq.heappush(minHeap, (count, tweetId, followeeId, idx-1))
        
        return res
        
    def follow(self, followerId: int, followeeId: int) -> None:
        self.followmap[followerId].add(followeeId)
        

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followeeId in self.followmap[followerId]:
            self.followmap[followerId].remove(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)
