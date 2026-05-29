class Twitter:

    class Tweet:
        def __init__(self, tweetId, time):
            self.tweetId = tweetId
            self.timeStamp = time

    def __init__(self):
        self.userMap = {}
        self.tweetMap = {}
        self.time = 0

    def postTweet(self, userId, tweetId):
        tweet = Twitter.Tweet(tweetId, self.time)
        self.time += 1
        if userId not in self.tweetMap:
            self.tweetMap[userId] = []
        self.tweetMap[userId].append(tweet)
        self.follow(userId, userId)

    def getNewsFeed(self, userId):
        lst = []
        followees = self.userMap.get(userId)
        pq = []
        if followees is not None:
            for followee in followees:
                tweets = self.tweetMap.get(followee)
                if tweets is not None:
                    for tweet in tweets:
                        lst.append(tweet)
        lst.sort(key=lambda a: a.timeStamp)
        result = []
        for i in range(len(lst) - 1, max(-1, len(lst) - 10 - 1), -1):
            result.append(lst[i].tweetId)
        return result

    def follow(self, followerId, followeeId):
        if followerId not in self.userMap:
            self.userMap[followerId] = set()
        self.userMap[followerId].add(followeeId)

    def unfollow(self, followerId, followeeId):
        if followerId not in self.userMap:
            return
        self.userMap[followerId].discard(followeeId)
