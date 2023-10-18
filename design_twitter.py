class Tweet:
    def __init__(self, tweetId, time):
        self.tweetId = tweetId
        self.time = time

    def __lt__(self, other):
        return self.time < other.time

class Twitter:

    def __init__(self):
        self.time = 0
        self.followers = dict() #user_id -> set(followers)
        self.tweetMap = dict() # user_id -> List<Tweet>)

    def postTweet(self, userId: int, tweetId: int) -> None:
        t = Tweet(tweetId, self.time)
        self.time += 1
        if userId not in self.tweetMap:
            self.tweetMap[userId] = []
        self.tweetMap[userId].append(t) 

    def getNewsFeed(self, userId: int) -> List[int]:
        self.follow(userId, userId)
        allFollowers = self.followers[userId]
        if allFollowers == None or len(allFollowers) == 0:
            return []
        heap = []
        heapq.heapify(heap)
        for follower in allFollowers:
            if follower in self.tweetMap:
                tweets = self.tweetMap[follower]
                for tweet in tweets:
                    heapq.heappush(heap, tweet)
                    if len(heap) > 10:
                        heapq.heappop(heap)
        
        q= deque()
        while(len(heap)>0):
            tweet = heapq.heappop(heap)
            q.appendleft(tweet.tweetId)
        return list(q)

    def follow(self, followerId: int, followeeId: int) -> None:
        if followerId not in self.followers:
            self.followers[followerId] = set()
        self.followers[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId in self.followers and followerId != followeeId:
            self.followers[followerId].remove(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)
