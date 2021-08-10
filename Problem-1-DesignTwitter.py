# Time Complexity: O(N) N- no of following
import heapq
class Twitter:

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.followeeMap = defaultdict(set)
        self.tweetMap = defaultdict(deque)
        self.timeStamp = 0

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """

        self.timeStamp += 1
        tweetlist = self.tweetMap[userId]
        tweetlist.append(((self.timeStamp), tweetId))
        if len(tweetlist) > 10:
            tweetlist.popleft()

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """

        heap = []
        t = self.tweetMap[userId]
        heap.extend(t)
        heapify(heap)

        for f in self.followeeMap[userId]:
            t = self.tweetMap[f]

            for i in range(len(t) - 1, -1, -1):
                if len(heap) < 10:
                    heappush(heap, t[i])
                else:
                    if heap[0][0] < t[i][0]:
                        heappushpop(heap, t[i])
                    else:
                        break
        result = []
        for i in range(len(heap)):
            result.append(heappop(heap)[1])
        return result[::-1]

    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId != followeeId:
            self.followeeMap[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId != followeeId:
            self.followeeMap[followerId].discard(followeeId)


