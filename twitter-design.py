from collections import defaultdict
import heapq


class Twitter(object):

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.timer = 0
        self.followers = defaultdict(set)
        self.tweets = defaultdict(list)

    def postTweet(self, userId, tweetId):
        """
        Compose a new tweet.
        :type userId: int
        :type tweetId: int
        :rtype: None
        """

        self.tweets[userId].append([tweetId, self.timer])
        self.timer += 1

    def getNewsFeed(self, userId):
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        :type userId: int
        :rtype: List[int]
        """
        collection = []
        self.followers[userId].add(userId)
        for i in self.followers[userId]:
            collection.extend(self.tweets[i])
        heapq.heapify(collection)
        collection = heapq.nlargest(10, collection)
        # print(collection)
        res = []
        for i in sorted(collection, reverse=True):
            print(i)
            res.append(i[0])
        return res

    def follow(self, followerId, followeeId):
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """

        self.followers[followerId].add(followeeId)

    def unfollow(self, followerId, followeeId):
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        if followerId != followeeId:
            self.followers[followerId].remove(followeeId)
# time-O(nlogk)
# space-O(n)

# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)