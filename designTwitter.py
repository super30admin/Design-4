class Tweet:
    def __init__(self, tweetId, createdAt):
        self.tweetId = tweetId
        self.createdAt = createdAt


class Twitter:

    def __init__(self):
        self.createdAt = 0
        self.userMap = defaultdict(set)
        self.tweetMap = defaultdict(list)
        self.maxFeed = 10

    def firstTime(self, userId: int) -> None:
        #   Time Complexity:    O(1)
        #   Space Complexity:   O(1)

        if userId not in self.userMap:
            self.userMap[userId] = set()
            self.userMap[userId].add(userId)

        if userId not in self.tweetMap:
            self.tweetMap[userId] = []

    def postTweet(self, userId: int, tweetId: int) -> None:
        #   Time Complexity:    O(1)
        #   Space Complexity:   O(1)

        # check if userId exists in tweets mapping
        self.firstTime(userId)
        # create Tweet object
        tweet = Tweet(tweetId, self.createdAt)
        # append Tweet object to corresponding udserId
        self.tweetMap[userId].append(tweet)
        # update the createdAt time
        self.createdAt += 1

    def getNewsFeed(self, userId: int) -> List[int]:
        #   Time Complexity:    O(NlogN) -> N is total tweets user and its followers have
        #   Space Complexity:   O(N)     -> N is total tweets user and its followers have

        # custom lessThan function for Tweet class
        lessThan = lambda x, y: x.createdAt < y.createdAt
        Tweet.__lt__ = lessThan

        # check if user exists
        self.firstTime(userId)

        # sort the entire list according to timestamp in descending order
        allTweets = []

        # add all tweets of the user and its followers
        for followerId in self.userMap[userId]:
            self.firstTime(followerId)
            allTweets.extend(self.tweetMap[followerId])

        allTweets.sort(reverse=True)

        tweetIds = []

        # put first 10 feeds
        for i in range(self.maxFeed):
            if i < len(allTweets):
                tweetIds.append(allTweets[i].tweetId)

        # return top 10
        return tweetIds

    def follow(self, followerId: int, followeeId: int) -> None:
        #   Time Complexity:    O(1)
        #   Space Complexity:   O(1)
        self.firstTime(followerId)
        self.firstTime(followeeId)
        self.userMap[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        #   Time Complexity:    O(1)
        #   Space Complexity:   O(1)
        self.firstTime(followerId)
        self.firstTime(followeeId)
        if followerId != followeeId:
            self.userMap[followerId].discard(followeeId)

# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)