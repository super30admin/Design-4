class Tweet:
    def __init__(self, tid: int, createdAt: int):
        self.tid = tid
        self.createdAt = createdAt

class Twitter:
    def __init__(self):
        self.followed = defaultdict(set)
        self.tweets = defaultdict(list)
        self.timeStamp = 0

    def postTweet(self, userId: int, tweetId: int) -> None:
        self.follow(userId, userId)
        newTweet = Tweet(tweetId, self.timeStamp)
        self.timeStamp += 1
        self.tweets[userId].append(newTweet)
        

    def getNewsFeed(self, userId: int) -> List[int]:
        pq = []
        followedIds = self.followed[userId]

        for fId in followedIds:
            ftweets = self.tweets[fId]
            for ftweet in ftweets:
                    heapq.heappush(pq,(-ftweet.createdAt,ftweet.tid))

        result = []
        while pq and len(result) < 10:
            result.append(heapq.heappop(pq)[1])
        return result 
        

    def follow(self, followerId: int, followeeId: int) -> None:
        self.followed[followerId].add(followeeId)
        

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId in self.followed and followerId != followeeId:
            self.followed[followerId].discard(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)