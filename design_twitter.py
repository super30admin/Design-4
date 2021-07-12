# Time Complexity: O(N) N- no of following
# Space Complexity: O(N)
import heapq
from collections import defaultdict
from collections import deque
class Twitter:

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.followee = defaultdict(set)
        self.tweets = defaultdict(deque)
        self.time = 0

    def postTweet(self, userId, tweetId):
        """
        Compose a new tweet.
        """
        #         arrange in the sorted order of posting time, we will update in the tweet dictionary
        self.time += 1
        tweetlist = self.tweets[userId]
        tweetlist.append(((self.time), tweetId))
        if len(tweetlist) > 10:
            tweetlist.popleft()

    def getNewsFeed(self, userId):
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        #         to get th enews feed in sorted order we will use heap and we will heapify the given tweets of the userid
        # we will get only to 10 feeds.
        heapt = []
        t = self.tweets[userId]
        heapt.extend(t)
        heapq.heapify(heapt)

        for f in self.followee[userId]:
            t = self.tweets[f]

            for i in range(len(t) - 1, -1, -1):
                if len(heapt) < 10:
                    heapq.heappush(heapt, t[i])
                else:
                    if heapt[0][0] < t[i][0]:
                        heapq.heappushpop(heapt, t[i])
                    else:
                        break
        result = []
        for i in range(len(heapt)):
            result.append(heapq.heappop(heapt)[1])
        return result[::-1]

    # We will add given followee id in the followersid
    def follow(self, followerId, followeeId):
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId != followeeId:
            self.followee[followerId].add(followeeId)

    # if there is already an entry under followee of given followeeid we will remove it
    def unfollow(self, followerId, followeeId):
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId != followeeId:
            self.followee[followerId].discard(followeeId)

# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)