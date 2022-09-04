import heapq


class Tweet:
    def __init__(self, tweetId, time):
        self.tweetId = tweetId
        self.createdAt = time


class Twitter:
    time = 0
    followed = 0
    tweets = 0

    def __init__(self):
        self.time = 0
        self.followed = dict()
        self.tweets = dict()

    def postTweet(self, userId: int, tweetId: int) -> None:
        self.follow(userId, userId)
        if userId not in self.tweets:
            self.tweets[userId] = []
        self.tweets[userId].append(Tweet(tweetId, self.time))
        self.time += 1

    def getNewsFeed(self, userId: int) -> List[int]:
        # pq = PriorityQueue()
        h = []
        fIds = self.followed.get(userId)
        # print(self.tweets)
        if fIds:
            for fId in fIds:
                ftweets = self.tweets.get(fId)
                if ftweets:
                    for tweet in ftweets:
                        h.append([tweet.createdAt, tweet.tweetId])
                        heapify(h)
                        if len(h) > 10:
                            heapq.heappop(h)

        result = []
        while len(h) > 0:
            result.insert(0, heapq.heappop(h)[1])
        return result

    def follow(self, followerId: int, followeeId: int) -> None:
        if followerId not in self.followed:
            self.followed[followerId] = set()

        self.followed[followerId].add(followeeId)
        # print(self.followed,"this")

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followeeId in self.followed[followerId] and followerId != followeeId:
            # print(self.followed)
            self.followed.get(followerId).remove(followeeId)

# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)

# Time Complexity = follow and unfollow is O(1).heap O(nlogk) where k is the number of tweets and n is the
# number of tweets that there are.
# Did this code successfully run on Leetcode : yes
