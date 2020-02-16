"""
Time Complexity : postTweet() - O(n)
                  getNewsFeed() - O(n logn)
                  follow - O(1)
                  unfollow - O(1)
Space Complexity : O(m*n) - where m is the number of users and n is the average number of tweets per user
Did this code successfully run on Leetcode : Yes
Any problem you faced while coding this: none
"""

from collections import defaultdict
class Twitter():
    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.tweets = defaultdict(list)
        self.follows = defaultdict(set)
        self.time = 0

    def postTweet(self, userId, tweetId):
        """
        Compose a new tweet.
        :type userId: int
        :type tweetId: int
        :rtype: None
        """
        self.tweets[userId].insert(0,(self.time,tweetId)) #most recent posts at the front
        self.time+=1

    def getNewsFeed(self, userId):
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user             followed or by the user herself. Tweets must be ordered from most recent to least recent.
        :type userId: int
        :rtype: List[int]
        """
        d = {}
        if userId not in self.follows[userId]:  #user should also see his own posts
            self.follows[userId].add(userId)  #so he should follow himself
            
        for followee in self.follows[userId]:
            for tweet in self.tweets[followee][0:10]:
                d[tweet[0]] = tweet[1]
        feeds = sorted(d.items(), key=lambda t: t[0], reverse=True) #sort by time
        feed = [i[1] for i in feeds]     # get value from (time,value) pair
        return feed[0:10]                # return only top 10 tweets

    def follow(self, followerId, followeeId):
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        self.follows[followerId].add(followeeId)

    def unfollow(self, followerId, followeeId):
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        if followeeId in self.follows[followerId]:
            self.follows[followerId].remove(followeeId)