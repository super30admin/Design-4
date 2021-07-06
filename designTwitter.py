# Time Complexity : O(1) for all but getNewFeed, O(n*m*k*log(k)), where k is 10 so O(f*t).
# Space Complexity : O(f+t), where f is the number of users and t is the number of tweets.
# Did this code successfully run on Leetcode : yes
# Any problem you faced while coding this : no

# Your code here along with comments explaining your approach
import heapq
from collections import defaultdict
# Class to store tweet information
class Tweet(object):
    def __init__(self, id, timestamp):
        self.id = id
        self.timestamp = timestamp

# Follow: Add to corresponding Hashset
# Unfollow: if not self remove and follower exists, remove from corresponding Hashset.
# postTweet: Add Tweet Object to list corresponding to the user.
# getNewsFeed: Add and maintain size and order by using a PQ/MinHeap, order maintained by recent Tweets.
class Twitter(object):
    def __init__(self):
        # hashmaps to store tweets users and their followers
        self.followers = defaultdict(set)
        self.tweets = defaultdict(list)
        # feed size for getNewsFeed func
        self.fSize = 10
        self.timestamp = 0

    def postTweet(self, userId, tweetId):
        # Follow self
        self.follow(userId, userId)
        # Add tweet to hashmap
        toAdd = Tweet(tweetId, self.timestamp)
        self.timestamp += 1
        self.tweets[userId].append(toAdd)

    def getNewsFeed(self, userId):
        # Priority Queue Container
        pQueue = []
        # Following for the given user
        fIds = self.followers[userId]
        for f in fIds:
            # Tweets of every follower
            fTweets = self.tweets[f]
            # adding to PQ while maintaining order and size 10
            for t in fTweets:
                heapq.heappush(pQueue, (t.timestamp, t.id))
                if len(pQueue) > self.fSize:
                    heapq.heappop(pQueue)
        # Adding to result
        retVal = []
        while len(pQueue) > 0:
            temp = heapq.heappop(pQueue)
            retVal.append(temp[1])
        return retVal[::-1]

    def follow(self, followerId, followeeId):
        # Add follower to corresponding hashset
        self.followers[followerId].add(followeeId)

    def unfollow(self, followerId, followeeId):
        # If following and not removal of self.
        if followeeId in self.followers[followerId] and followerId != followeeId:
            self.followers[followerId].remove(followeeId)
