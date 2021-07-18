from Queue import PriorityQueue
class Twitter(object):
    class Tweet(object):
        def __init__(self,tId,createdAt):
            self.tId = tId
            self.createdAt = createdAt
        
    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.tweets = {}
        self.following = {}
        self.time = 0

    def postTweet(self, userId, tweetId):
        """
        Compose a new tweet.
        :type userId: int
        :type tweetId: int
        :rtype: None
        """
        self.follow(userId,userId)
        if userId not in self.tweets:
            self.tweets[userId] = []
        self.time += 1
        self.tweets[userId].append(self.Tweet(tweetId,self.time))
        

    def getNewsFeed(self, userId):
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        :type userId: int
        :rtype: List[int]
        """
        q = PriorityQueue()
        followSet = self.following.get(userId,set())
        for user in followSet:
            fTweets = self.tweets.get(user,[])
            for tw in fTweets:
                q.put((tw.createdAt,tw))
                if q.qsize()>10:
                    q.get()
        res = []
        while not q.empty():
            res.append(q.get()[1].tId)
        return res[::-1]
        

    def follow(self, followerId, followeeId):
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        if followerId not in self.following:
            self.following[followerId] = set()
        self.following[followerId].add(followeeId)

    def unfollow(self, followerId, followeeId):
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        if followerId in self.following and followerId != followeeId and followeeId in self.following[followerId]:
            self.following[followerId].remove(followeeId)
        


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)
