import heapq

class Tweet():
    def __init__(self, tweetid, time):
        self.tweetid = tweetid
        self.time = time
    
    def __lt__(self, other):
        return self.time < other.time

class Twitter(object):

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.followers = dict()
        self.tweets = dict()
        self.time = 0

    def postTweet(self, userId, tweetId):
        """
        Compose a new tweet.
        :type userId: int
        :type tweetId: int
        :rtype: None
        """
        tweetobj = Tweet(tweetId, self.time)
        self.time += 1
        if userId not in self.tweets:
            self.tweets[userId] = []
        self.tweets[userId].append(tweetobj)
        #print(self.tweets)

    def getNewsFeed(self, userId):
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        :type userId: int
        :rtype: List[int]
        """
        alltweets = []
        if userId in self.tweets:
            alltweets.extend(self.tweets[userId])
        if userId in self.followers:
            for i in self.followers[userId]:
                if i in self.tweets:
                    alltweets.extend(self.tweets[i])
        #print(alltweets)
        if len(alltweets) == 0:
            return alltweets
        else:
            return self.getTopTweets(alltweets)
    
    def getTopTweets(self, alltweets):
        queue = []
        for i in alltweets:
            #queue.put(i)
            heapq.heappush(queue, i)
            if len(queue) > 10:
                #queue.get()
                heapq.heappop(queue)
        res = []
        while len(queue) > 0:
            #temp = queue.get()
            temp = heapq.heappop(queue)
            res.insert(0, temp.tweetid)
        return res

    def follow(self, followerId, followeeId):
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        if followerId != followeeId:
            if followerId not in self.followers:
                self.followers[followerId] = set()
            self.followers[followerId].add(followeeId)

    def unfollow(self, followerId, followeeId):
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        if followerId in self.followers:
            if followeeId in self.followers[followerId]:
                self.followers[followerId].remove(followeeId)