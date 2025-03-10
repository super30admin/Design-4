# Time Complexity : 
    # postTweet() -> O(1)
    # getNewsFeed() -> O(N) where N is the number of users the user follows.
    # unfollow() -> O(1)

# Space Complexity : O(N)

class Tweet:
    def __init__(self, tweetId, createdAt):
        self.tweetId = tweetId
        self.createdAt = createdAt

class Twitter:

    def __init__(self):
        self.userFollowees = collections.defaultdict(set)
        self.userTweets = collections.defaultdict(list)
        self.clock = 0
        self.K = 10

    def postTweet(self, userId: int, tweetId: int) -> None:
        self.follow(userId, userId)
        tweet = Tweet(tweetId, self.clock)
        self.clock += 1
        self.userTweets[userId].append(tweet)

    def getNewsFeed(self, userId: int) -> List[int]:
        min_heap = [] # size K
        for fId in self.userFollowees[userId]:
            tweets = self.userTweets[fId]
            for i in range(len(tweets)-1, len(tweets)-1 - self.K, -1):
                if i < 0:
                    break
                heapq.heappush(min_heap, (tweets[i].createdAt, tweets[i].tweetId))
                if len(min_heap) > self.K:
                    heapq.heappop(min_heap)
        
        res = []
        while min_heap:
            res.append(heapq.heappop(min_heap)[1])

        return res[::-1]

    def follow(self, followerId: int, followeeId: int) -> None:
        self.userFollowees[followerId].add(followeeId)
        

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId == followeeId or followeeId not in self.userFollowees[followerId]:
            return

        self.userFollowees[followerId].remove(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)