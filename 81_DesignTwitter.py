'''
Not acceppted on leetcode(355)
Problem Faced -
Passed few cases but having problem with cases in submission though they are executed when put in as testcase.
'''
import heapq
class Tweet():
    def __init__(self, tweetId, createdAt):
        self.tweetId = tweetId
        self.createdAt = createdAt


class Twitter(object):
    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.followed = {}
        self.tweets = {}
        self.timestamp = 0
        self.feedSize = 10

    def postTweet(self, userId, tweetId):
        """
        Compose a new tweet.
        :type userId: int
        :type tweetId: int
        :rtype: None
        """
        # For followers
        # self.follow(userId,userId)

        if userId not in self.followed:
            self.followed[userId] = set()
        self.followed[userId].add(userId)

        # for Tweets
        if userId not in self.tweets:
            self.tweets[userId] = []
        self.timestamp += 1
        self.tweets[userId].append(Tweet(tweetId, self.timestamp))

    def getNewsFeed(self, userId):
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        :type userId: int
        :rtype: List[int]
        """
        pq = []
        heapq.heapify(pq)
        fIds = None

        # for obj in self.tweets[userId]:
        #     print( obj.tweetId, obj.createdAt)
        if userId in self.followed:
            fIds = self.followed[userId]
        if fIds is not None:
            for i in fIds:
                ftweets = None
                if i in self.tweets:
                    ftweets = self.tweets[i]
                type(ftweets)
                if ftweets is None:
                    continue
                for tweet in ftweets:
                    # Case 1: Priority is not full
                    if len(pq) < self.feedSize:
                        heapq.heappush(pq, tweet)
                    # Case 2: Priority is full and tweet coming in is more recent than the tweet on the top, then replace the old tweet with new one.
                    else:
                        if tweet.CreatedAt > heapq.nsmallest(1, pq)[0].createdAt:
                            heapq.heappop(pq)
                            heapq.heappush(pq, tweet)

        result = []
        while pq:
            result.insert(0, heapq.heappop(pq).tweetId)
        return result

    def follow(self, followerId, followeeId):
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        # For followers
        if followerId not in self.followed:
            self.followed[followerId] = set()
        self.followed[followerId].add(followeeId)

    def unfollow(self, followerId, followeeId):
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        # For followers
        if followerId in self.followed and followerId != followeeId and followeeId in self.followed[followerId]:
            self.followed[followerId].remove(followeeId)

# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)