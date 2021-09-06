class Twitter:
    class Tweet:
        def __init__(self, tid, createdAt):
            self.tid = tid
            self.createdAt = createdAt

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.followers = dict()
        self.tweet = dict()
        self.time = 0

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        self.follow(userId, userId)
        if userId not in self.tweet:
            self.tweet[userId] = []
        self.time += 1
        self.tweet[userId].append(self.Tweet(tweetId, self.time))

        # O(nK)

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        self.Tweet.__lt__ = lambda self, other: self.createdAt < other.createdAt
        if len(self.followers) < 1 or len(self.tweet) < 1:
            return []
        s = self.followers[userId]
        pq = []
        if s:
            for user in s:
                tweets = self.tweet[user]
                if tweets:
                    for tw in tweets:
                        heapq.heappush(pq, tw)
                        if len(pq) > 10:
                            heapq.heappop(pq)
        result = []
        while len(pq) > 0:
            result.insert(0, heapq.heappop(pq).tid)
        return result

    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId not in self.followers:
            self.followers[followerId] = set()
        self.followers[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId in self.followers:
            if followeeId in self.followers[followerId]:
                self.followers[followerId].remove(followeeId)

# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)