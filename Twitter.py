class twitter:
    def _init_(self, time, twitid):
        self.time = time
        self.twittid = twitid


class Twitter:

    def __init__(self):

        """
        Initialize your data structure here.
        """
        self.usermap = collections.defaultdict(set)
        self.twittmap = collections.defaultdict(list)
        self.time = 0

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """

        self.twittmap[userId].append([self.time, tweetId])
        self.time += 1
        if len(self.twittmap[userId]) > 10:
            self.twittmap[userId].pop(0)

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        heap = []
        userlist = self.usermap[userId]
        if len(userId) > 0:
            for follwer in userlist:
                tweets = self.twittmap(follwer)
                for tweet in tweets:
                    heapq.heappush(heap, (tweet[0], tweet[1]))
                    if heap._len_() > 10:
                        heapq.heappop(heap)

        result = []
        while heap._len_() > 0:
            result.append(heapq.heappushpop(0)[1])

    def follow(self, followerId: int, followeeId: int) -> None:

        if followeeId == followerId: return

        self.usermap[followerId].add(followeeId)

        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        if self.usermap[followerId] and followeeId in self.usermap[followerId]:
            self.usermap[followerId].remove(followeeId)

# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)