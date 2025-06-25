# Time Complexity : O(n) for postTweet, O(n) for getNewsFeed, O(1) for follow and unfollow
# Space Complexity : O(n) for tweets and following
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No

class Twitter:

    def __init__(self):
        self.timestamp = 0
        self.tweets = {}  # userId -> list of (timestamp, tweetId)
        self.following = {}  # userId -> set of followeeIds

    def postTweet(self, userId: int, tweetId: int) -> None:
        if userId not in self.tweets:
            self.tweets[userId] = []
        self.tweets[userId].append((self.timestamp, tweetId))
        self.timestamp += 1

    def getNewsFeed(self, userId: int) -> List[int]:
        heap = []
        users = set([userId])
        if userId in self.following:
            users |= self.following[userId]
        for uid in users:
            if uid in self.tweets:
                for t in self.tweets[uid][-10:]:
                    heapq.heappush(heap, t)
                    if len(heap) > 10:
                        heapq.heappop(heap)
        return [tweetId for _, tweetId in sorted(heap, reverse=True)]

    def follow(self, followerId: int, followeeId: int) -> None:
        if followerId == followeeId:
            return
        if followerId not in self.following:
            self.following[followerId] = set()
        self.following[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId in self.following:
            self.following[followerId].discard(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)