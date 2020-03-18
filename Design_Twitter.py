// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : None

from collections import OrderedDict
#created a class tweet which contains tweetid and created time.
class Tweet:
    def __init__(self,tweetid,createdAt):
        self.tweetid=tweetid
        self.createdAt=createdAt
    
class Twitter(object):

    def __init__(self):
        """
        Initialize your data structure here.
        """
        # feedsize to get the number of recent tweets when getNewsFeed() is called.
        self.feedsize=10
        self.timestamp=0
        self.userTweetsMap=dict()
        self.userFollowsMap=dict()
        
        #O(1) operation
     #checks if the user is new .If yes create a key for that user in both the dictionaries.
    def isFirstTime(self,userId):
        if userId not in self.userTweetsMap.keys():
            self.userTweetsMap[userId]=[]
            t=set()
            t.add(userId)
            self.userFollowsMap[userId]=t
     #check if user is present or not.If yes add the tweet to his key_value else create a new one.
    def postTweet(self, userId, tweetId):
        """
        Compose a new tweet.
        :type userId: int
        :type tweetId: int
        :rtype: None
        """
        # print(self.userTweetsMap)
        self.isFirstTime(userId)
        self.timestamp=self.timestamp+1
        tweet=Tweet(tweetId,self.timestamp)
        self.userTweetsMap[userId].append(tweet)

    # we can get the top tweets posted by a user and his followers.
    def getNewsFeed(self, userId):
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        :type userId: int
        :rtype: List[int]
        """
        self.isFirstTime(userId)
        followees=self.userFollowsMap[userId]
        totusertweets=dict()
        for j in followees:
            usertweet=self.userTweetsMap[j]
            for k in usertweet:
                totusertweets[k.tweetid]=k.createdAt
        #used for ordering the tweets as per the timeframe created.
        tweets=OrderedDict(sorted(totusertweets.items(), key=lambda t: t[1],reverse=True))
        count=self.feedsize
        result=[]
        for k in tweets:
            if count!=0:
                result.append(k)
            else:
                break
            count=count-1
        return result
        #user to follow another user
    def follow(self, followerId, followeeId):
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        self.isFirstTime(followerId)
        self.isFirstTime(followeeId)
        self.userFollowsMap[followerId].add(followeeId)
        
#user to unfollow another user.
    def unfollow(self, followerId, followeeId):
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        self.isFirstTime(followerId)
        self.isFirstTime(followeeId)
        if followerId!=followeeId:
            if followeeId in self.userFollowsMap[followerId]:
                self.userFollowsMap[followerId].remove(followeeId)
        


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)