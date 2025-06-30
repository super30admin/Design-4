class Tweet:
    def __init__(self, tweetId, createdAt):
        self.tweetId = tweetId
        self.createdAt = createdAt
    def __lt__(self, other):
        return self.createdAt < other.createdAt  # Min-heap by createdAt
class Twitter:

    def __init__(self):
        # User: Tweets mapping
        # User: user mapping(list of following)
        self.tweets = defaultdict(list)
        self.followed = defaultdict(set)
        self.timestamp = 0

    def postTweet(self, userId: int, tweetId: int) -> None:
        # When posting the tweet, the user follow itself
        if userId not in self.followed:
            self.follow(userId, userId)
        self.tweets[userId].append(Tweet(tweetId, self.timestamp))
        self.timestamp += 1

    def getNewsFeed(self, userId: int) -> List[int]:
        # Get the followings Set object for the userid
        pq = []
        if userId in self.followed:
            for fid in self.followed[userId]:
                ftweets = self.tweets.get(fid, [])
                K = min(len(ftweets), 10)
                for i in range(len(ftweets)-1, len(ftweets)-K-1, -1):
                    heapq.heappush(pq, ftweets[i])
                    if len(pq) > 10:
                        heapq.heappop(pq)
        # Get tweet Ids from the heap in reverse order
        res = []
        while pq:
            res.insert(0, heapq.heappop(pq).tweetId)
        return res

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