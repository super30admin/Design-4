#Time Complexity: O(n)
#Space Complexity: O(m)

from heapq import heapify,heappop,heappush

class Twitter:
    def __init__(self):
        self.data = {}
        self.followUser = {}
        self.time = 0
    def createUser(self,idd):
        if idd not in self.data:
            self.data[idd] = []
        if idd not in self.followUser:
            self.followUser[idd] = set()
    def postTweet(self, userId: int, tweetId: int) -> None:
        self.createUser(userId)
        self.data[userId].append([self.time,tweetId])
        self.time += 1
    def getNewsFeed(self, userId: int) -> List[int]: 
        self.createUser(userId)
        res = []
        res += self.data[userId]
        for u in self.followUser[userId]:
            res += self.data[u]
        heapify(res)
        return [v for t,v in heapq.nlargest(10,res)]
    def follow(self, followerId: int, followeeId: int) -> None:
        self.createUser(followerId)
        self.createUser(followeeId)
        self.followUser[followerId].add(followeeId)
        
    def unfollow(self, followerId: int, followeeId: int) -> None:
        self.createUser(followerId)
        self.createUser(followeeId)
        if followeeId in self.followUser[followerId]:
            self.followUser[followerId].remove(followeeId)
        


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)