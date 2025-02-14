class Twitter:
    # TC : O(n)
    # SC : O(1)
    class Tweet:
        def __init__(self,tweetId,createdAt):
            self.tweetId = tweetId
            self.createdAt = createdAt

    def __init__(self):
        self.userMap = {}
        self.tweetMap = {}
        self.time = 0

    def postTweet(self, userId: int, tweetId: int) -> None:
        if userId not in self.tweetMap.keys():
            self.tweetMap[userId] = []
        if tweetId not in self.tweetMap[userId]:
            self.time += 1
            self.tweetMap[userId].append(self.Tweet(tweetId,self.time))
        

    def getNewsFeed(self, userId: int) -> List[int]:
        heap = []
        if userId in self.tweetMap.keys():
            for tweet in self.tweetMap[userId]:
                tid,timestamp = tweet.tweetId,tweet.createdAt
                if len(heap) < 10:
                    heapq.heappush(heap,(timestamp,tid))
                else:
                    heapq.heappushpop(heap,(timestamp,tid))
        if userId in self.userMap.keys():
            for friend in self.userMap[userId]:
                if friend in self.tweetMap.keys():
                    for tweet in self.tweetMap[friend]:
                        tid,timestamp = tweet.tweetId,tweet.createdAt
                        if len(heap) < 10:
                            heapq.heappush(heap,(timestamp,tid))
                        else:
                            heapq.heappushpop(heap,(timestamp,tid))
        res = []
        while len(heap) > 0:
            timestamp,tid = heapq.heappop(heap)
            res.insert(0,tid)
        return res

       
        

    def follow(self, followerId: int, followeeId: int) -> None:
        if followerId not in self.userMap.keys():
            self.userMap[followerId] = set()
        self.userMap[followerId].add(followeeId)
        

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId not in self.userMap.keys():
            return
        if followeeId in self.userMap[followerId]:
            self.userMap[followerId].remove(followeeId)
        


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)