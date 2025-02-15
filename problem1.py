"""
1. We are using two hashmaps to keep track of a user's tweets along with times and their followees
2. For getting a user's feed, we maintain a minHeap (python) so we start the time from 0
3. For each followee of the user (including himself as he needs to see what he posted as well),
we first populate the mingheap, with the latest tweet by each followees, take the latest tweet which lies at the top
of minheap as heap is sorted based on first value - time in the list inserted.
4. Once we get the minimum one (latest), we add the next tweet by the same followee into the minheap and it heapifies maintaining
the latest of these set at the top.
5. Perform the same until we get the 10 tweets or heap is empty.

TC: O(k) k-> Number of followees
SC: O(n) hashmap
"""


class Twitter:
    def __init__(self):
        self.tweets = defaultdict(list)
        self.followMap = defaultdict(set)
        self.time = 0

    def postTweet(self, userId: int, tweetId: int) -> None:
        self.tweets[userId].append([self.time, tweetId])
        self.time -= 1

    def getNewsFeed(self, userId: int) -> List[int]:
        res = []
        minHeap = []

        self.followMap[userId].add(userId)
        for followeeId in self.followMap[userId]:
            if followeeId in self.tweets:
                index = len(self.tweets[followeeId]) - 1
                time, tweetId = self.tweets[followeeId][index]
                minHeap.append([time, tweetId, followeeId, index - 1])
        heapq.heapify(minHeap)
        while minHeap and len(res) < 10:
            time, tweetId, followeeId, index = heappop(minHeap)
            res.append(tweetId)
            if index >= 0:
                time, tweetId = self.tweets[followeeId][index]
                heappush(minHeap, [time, tweetId, followeeId, index - 1])
        return res

    def follow(self, followerId: int, followeeId: int) -> None:
        self.followMap[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followeeId in self.followMap[followerId]:
            self.followMap[followerId].remove(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)
