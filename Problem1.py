#Time Complexity :O(n)
#Space Complexity :O(n)  
#Did this code successfully run on Leetcode : Yes
#Any problem you faced while coding this : No

import heapq
class Twitter:
    def __init__(self):
        self.followedMap={}
        self.tweetMap={}
        self.timeTweet=0

    def postTweet(self, userId: int, tweetId: int) -> None:
        self.follow(userId,userId)
        if userId not in self.tweetMap.keys():
            self.tweetMap[userId]=set()
        self.tweetMap[userId].add((self.timeTweet,tweetId))
        self.timeTweet+=1

    def getNewsFeed(self, userId: int) -> List[int]:
        heap=[]
        following=self.followedMap.get(userId)
        if following:
            for followee in following:
                tweets=self.tweetMap.get(followee)
                if tweets:
                    for t in tweets:
                        heapq.heappush(heap,(t))
                        if len(heap)>10:
                            heapq.heappop(heap)                
        result = []
        while heap:
            tweet = heapq.heappop(heap)
            result.append(tweet[1])
        return result[::-1]

    def follow(self, followerId: int, followeeId: int) -> None:
        if followerId not in self.followedMap.keys():
            self.followedMap[followerId]=set()
        self.followedMap[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId in self.followedMap.keys() and followeeId in self.followedMap[followerId] and followerId!=followeeId:
            print(self.followedMap.get(followerId))
            self.followedMap.get(followerId).remove(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)