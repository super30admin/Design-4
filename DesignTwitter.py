import collections
from collections import defaultdict
from typing import DefaultDict, List
import heapq
# class Tweet:
#     def __init__(self, tweetId, time):
#         self.tweetId = tweetId
#         self.createdAt = time

# class Twitter:

#     def __init__(self):
#         self.time = 0
#         self.followedMap = defaultdict(set)
#         self.tweetMap = defaultdict(list)
        

#     def postTweet(self, userId: int, tweetId: int) -> None:
#         self.follow(userId,userId)
#         tweet = Tweet(tweetId, self.time)
#         self.time+=1
#         self.tweetMap[userId].append(tweet)
        
    
#     def getNewsFeed(self, userId: int) -> List[int]:
#         res = []
#         minHeap = []
#         for followedId in self.followedMap[userId]:
#             if followedId in self.tweetMap:
#                 for ftweets in self.tweetMap[followedId]:
#                     heapq.heappush(minHeap, (ftweets.createdAt, ftweets.tweetId))
#                     if len(minHeap) > 10:
#                         heapq.heappop(minHeap)
#         res = [tweetId for _, tweetId in sorted(minHeap, reverse=True)]
#         return res

        

#     def follow(self, followerId: int, followeeId: int) -> None:
#         self.followedMap[followerId].add(followeeId)

#     def unfollow(self, followerId: int, followeeId: int) -> None:
#         if followeeId in self.followedMap[followerId] and followerId!=followeeId :
#             self.followedMap[followerId].remove(followeeId)

# class Tweet:
#     def __init__(self, tweetId, time):
#         self.tweetId = tweetId
#         self.createdAt = time

# class Twitter:

#     def __init__(self):
#         self.time = 0
#         self.followedMap = defaultdict(set)
#         self.tweetMap = defaultdict(list)
        

#     def postTweet(self, userId: int, tweetId: int) -> None:
#         self.follow(userId,userId)
#         tweet = Tweet(tweetId, self.time)
#         self.time+=1
#         self.tweetMap[userId].append(tweet)
        
    
#     def getNewsFeed(self, userId: int) -> List[int]:
#         res = []
#         minHeap = []
#         for followedId in self.followedMap[userId]:
#             if followedId in self.tweetMap:
#                 for ftweets in self.tweetMap[followedId]:
#                     heapq.heappush(minHeap, (-ftweets.createdAt, ftweets.tweetId))
#         while minHeap and len(res) < 10:
#             res.append(heapq.heappop(minHeap)[1])
#         return res

        

#     def follow(self, followerId: int, followeeId: int) -> None:
#         self.followedMap[followerId].add(followeeId)

#     def unfollow(self, followerId: int, followeeId: int) -> None:
#         if followeeId in self.followedMap[followerId] and followerId!=followeeId :
#             self.followedMap[followerId].remove(followeeId)

class Tweet:
    def __init__(self, tweetId, time):
        self.tweetId = tweetId
        self.createdAt = time

class Twitter:

    def __init__(self):
        self.time = 0
        self.followedMap = defaultdict(set)
        self.tweetMap = defaultdict(list)
        

    def postTweet(self, userId: int, tweetId: int) -> None:
        self.follow(userId,userId)
        tweet = Tweet(tweetId, self.time)
        self.time+=1
        self.tweetMap[userId].append(tweet)
        
    
    def getNewsFeed(self, userId: int) -> List[int]:
        res = []
        minHeap = []
        for followedId in self.followedMap[userId]:
            if followedId in self.tweetMap:
                tweets= self.tweetMap[followedId]
                for ftweets in tweets[-10:]:
                    heapq.heappush(minHeap, (-ftweets.createdAt, ftweets.tweetId))
        while minHeap and len(res) < 10:
            res.append(heapq.heappop(minHeap)[1])
        return res

        

    def follow(self, followerId: int, followeeId: int) -> None:
        self.followedMap[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followeeId in self.followedMap[followerId] and followerId!=followeeId :
            self.followedMap[followerId].remove(followeeId)

        


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)