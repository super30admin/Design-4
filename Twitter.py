#//Time Complexity : O(N)
#//Space Complexity : O(N)
#//Did this code successfully run on Leetcode : Yes
#//Any problem you faced while coding this : No

import  collections
class Twitter(object):
    
    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.tweets_by_user = collections.defaultdict(list)
        self.follows = collections.defaultdict(set)
        self.timestamp = 0

    def postTweet(self, userId, tweetId):
        """
        Compose a new tweet.
        :type userId: int
        :type tweetId: int
        :rtype: None
        """
        self.timestamp += 1
        self.tweets_by_user[userId].append((self.timestamp, tweetId))
        
    def getNewsFeed(self, userId):
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        :type userId: int
        :rtype: List[int]
        """
        tweets = []
        
        tweets += self.tweets_by_user[userId]
        for other in self.follows[userId]:
            tweets += self.tweets_by_user[other]
        
        last_10_tweets = sorted(tweets)[-10:]
        return [tweetId for _, tweetId in last_10_tweets][::-1]

    def follow(self, followerId, followeeId):
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        if followerId != followeeId:
            self.follows[followerId].add(followeeId)

    def unfollow(self, followerId, followeeId):
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        self.follows[followerId].discard(followeeId)