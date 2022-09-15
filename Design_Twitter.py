from queue import PriorityQueue


class Twitter:
    class Tweet:
        def __init__(self, tid, time):
            self.tid = tid
            self.time = time

    class Distance:
        def __init__(self, tweet):
            self.tweet = tweet

        def __lt__(self, other):
            self.tweet.time < other.tweet.time

    def __init__(self):
        self.tweets = {}
        self.followed = {}
        self.time = 0

    def postTweet(self, userId: int, tweetId: int) -> None:  # TC - O(1)
        self.follow(userId, userId)
        if userId not in self.tweets.keys():
            self.tweets[userId] = []
        self.time += 1
        self.tweets[userId].append(self.Tweet(userId, self.time))

    def getNewsFeed(self, userId: int) -> List[int]:  # TC - O(n)
        pq = PriorityQueue()
        followeds = []
        if userId in self.followed.keys():
            followeds = self.followed[userId]
        if followeds:
            for fo in followeds:
                if fo not in followeds:
                    continue
                all_tweet = self.tweets[fo]
                if all_tweet:
                    n = len(all_tweet)
                for i in range(n-1, n-11, -1):
                    if i < 0:
                        break
                    top_tweet = all_tweet[i]
                    pq.put(self.Distance(top_tweet))
                    if pq.qsize() > 10:
                        pq.get()
        res = collections.deque()
        while not pq.empty():
            res.appendleft(pq.get().tweet.tid)
        return res

    def follow(self, followerId: int, followeeId: int) -> None:  # TC - O(1)
        if followerId not in self.followed.keys():
            self.followed[followerId] = set()
        self.followed[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:  # TC - O(1)
        if followerId in self.followed.keys() and followerId != followeeId and followeeId in self.followed[followerId]:
            self.followed[followerId].remove(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)
