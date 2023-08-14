import heapq
class Twitter:

    def __init__(self):
        self.followed = {}
        self.tweets = {}
        self.time = 0
        

    def postTweet(self, userId: int, tweetId: int) -> None:
        if userId not in self.tweets:
            self.tweets[userId] = set()
            self.follow(userId, userId)
        
        self.tweets[userId].add((self.time, tweetId))
        self.time+=1

    def getNewsFeed(self, userId: int) -> List[int]:
        heap = []

        if userId in self.followed:
            followed_users = self.followed[userId]

            if followed_users:
                for fid in followed_users:
                    if fid in self.tweets:
                        f1 = self.tweets[fid]
                        for t in f1:
                            heapq.heappush(heap, t)
                            if len(heap) > 10:
                                heapq.heappop(heap)
            res = []

            for _ in range(10):
                if not heap:
                    break
                else:
                    res.append(heapq.heappop(heap)[1])
            return res[::-1]
        
        

    def follow(self, followerId: int, followeeId: int) -> None:
        if followerId not in self.followed:
            self.followed[followerId] = set()
        self.followed[followerId].add(followeeId)

        

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId in self.followed and followeeId in self.followed[followerId] and followerId != followeeId:
            self.followed[followerId].remove(followeeId)
        


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)