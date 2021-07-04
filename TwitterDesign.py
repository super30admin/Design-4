'''
Problems faced: How to peek without increment the iterator
Did it run on leet code: Yes

Solution:
- Created a extra variable 'current' which stores next value
- and incremented iterator based on the conditions
'''


import collections
import heapq

class Twitter(object):

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.timestamp = 0
        self.tweets = collections.defaultdict(list)
        self.followers = collections.defaultdict(set)
        

    def postTweet(self, userId, tweetId):
        """
        Compose a new tweet.
        :type userId: int
        :type tweetId: int
        :rtype: None
        """
        self.tweets[userId].append([-self.timestamp,userId,tweetId])
        self.timestamp+=1
        
    def getNewsFeed(self, userId):
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        :type userId: int
        :rtype: List[int]
        """
        h = []
        result = []
        if self.tweets[userId]:
            idx = len(self.tweets[userId])-1
            h = [self.tweets[userId][-1] +[idx]]
        
        for user in self.followers[userId]:
            if self.tweets[user]:
                idx = len(self.tweets[user])-1
                heapq.heappush(h, self.tweets[user][-1]+[idx])
        
        for i in range(min(len(h),10)):
            timestamp, user, tweeter, idx = heapq.heappop(h)
            result.append(tweeter)
            if idx > 0:
                heapq.heappush(h, self.tweets[user][idx-1] + [idx-1])
            
        return result
        
        
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

        if followerId == followeeId:
            return
        elif followeeId in self.followers[followerId]:
            self.followers[followerId].remove(followeeId)
        


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)