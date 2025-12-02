## Problem 1: Design Twitter (https://leetcode.com/problems/design-twitter/)

# Method1: Using Brute force of going through all the tweets, sorting them and getting the top 10 most recent
# n - Number of users
# m - max/average number of tweets for each user
# N - Total number of tweets by all the users = n * m
# Time Complexity : O(1) -- follow, O(1) -- unfollow, O(1) -- postTweet, O(N * LogN) = O((n * m) Log(n * m)) -- sorting -- getNewsFeed
# Space Complexity : O(V + E) = (V -- No. of users, E -- edges = Total followees) = O(n + n^2) -- userMap + O(N) = O((n * m)) -- tweetMap + O(N) = O((n * m)) -- storing all the tweets in list
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No


# Your code here along with comments explaining your approach in three sentences only
# We need a user to users mapping where we store what all users the particular user follows. This would be helpful when getting the 
# news feed of the particular user. We also need a user to its tweets mapping. Here the tweet needs to be an object, where we need
# its tweetId and timestamp. This time would help us later to get the top 10 most recent tweets. Follow and unfollow is just 
# adding or removing the followeeId from the set of followerId in userFollowMap. When we post a tweet, a new tweet with tweetId
# and time gets created and gets added to the list of tweets of the userId. In getNewsFeed for a userId, we get the users this
# particular user follows, we get all the tweet objects for these users. Also we take the tweets made this particular user as well
# Then we sort the tweets based on the time of tweets in reverse. So we take the first 10 tweetIds as the most recent tweet would
# be at the front.

class Twitter(object):
    class Tweet(object):
        def __init__(self, tweetId, time):
            self.id = tweetId
            # timestamp for feed
            self.time = time

    def __init__(self):
        self.count = 0
        # user to users it follows
        self.userFollowMap = {}
        # user to its tweet
        self.userTweetsMap = {}
        

    def postTweet(self, userId, tweetId):# O(1)
        """
        :type userId: int
        :type tweetId: int
        :rtype: None
        """
        # if(userId not in self.userFollowMap):
        #     self.userFollowMap[userId] = set()
        if(userId not in self.userTweetsMap):
            self.userTweetsMap[userId] = []

        newTweet = self.Tweet(tweetId, self.count)
        self.count += 1

        self.userTweetsMap[userId].append(newTweet)
        

    def getNewsFeed(self, userId):# O(N LogN), N = no. of users * no. of tweets by that user, N = total number of tweets in the worst case
        """
        :type userId: int
        :rtype: List[int]
        """

        tweetIds = []
        following = set()
        if(userId in self.userFollowMap):
            following = self.userFollowMap[userId]

        # add this current user itself, so that we could get the tweets made by itself
        following.add(userId)

        for follow in following:
            if(follow in self.userTweetsMap):
                # get the tweets of this user
                for tweetObj in self.userTweetsMap[follow]:
                    tweetIds.append(tweetObj)


        if(not tweetIds):
            return tweetIds


        tweetIds.sort(key=lambda x:x.time, reverse = True)
        
        topTweets = []
        for i in range(10):
            if(i >= len(tweetIds)):
                break
            topTweets.append(tweetIds[i].id)

        return topTweets

        

    def follow(self, followerId, followeeId):# O(1)
        """
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        # user followerId follows followeeId
        if(followerId not in self.userFollowMap):
            self.userFollowMap[followerId] = set()

        self.userFollowMap[followerId].add(followeeId)

        

    def unfollow(self, followerId, followeeId):# O(1)
        """
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        # user followerId unfollows followeeId
        if(followerId not in self.userFollowMap):
            return
        
        # discard does not throw error even if key does not exist
        self.userFollowMap[followerId].discard(followeeId)
        


# Your Twitter object will be instantiated and called as such:
print("Method1: Using Brute force of going through all the tweets, sorting them and getting the top 10 most recent")
obj = Twitter()
obj.postTweet(1,5)
print(obj.getNewsFeed(1))
obj.follow(1,2)
obj.postTweet(2,6)
print(obj.getNewsFeed(1))
obj.unfollow(1,2)
print(obj.getNewsFeed(1))


# Method2: Using priority queue - min heap to get the top 10 latest tweets
# n - Number of users
# m - max/average number of tweets for each user
# N - Total number of tweets by all the users = n * m
# K = 10 = constant
# Time Complexity : O(1) -- follow, O(1) -- unfollow, O(1) -- postTweet, O(N * LogK) = O((n * m) Log(10)) = O(N) -- heap -- getNewsFeed
# Space Complexity : O(V + E) = (V -- No. of users, E -- edges = Total followees) = O(n + n^2) -- userMap + O(N) = O((n * m)) -- tweetMap
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No


# Your code here along with comments explaining your approach in three sentences only
# Logic for all the functions except getNewsFeed() is same as previous above. For getNewsFeed() method here I am using priority 
# queue based on min heap based on the time of the tweet. We have added a function to create custom heap based on the time of the 
# tweet. So we are going through all the tweets made by current user and made by users it is following. We keep adding to the heap
# time heap size < 10 and whenever it is greater than 10 we pop the tweet object. In this way, when we have gone through all the 
# relevant tweets, we would have the top 10 most recent tweets in the minheap. 

import heapq
class Twitter(object):
    class Tweet(object):
        def __init__(self, tweetId, time):
            self.id = tweetId
            # timestamp for feed
            self.time = time

        # for custom min heap where objects are compared based on time
        def __lt__(self, other):
            return self.time < other.time

    def __init__(self):
        self.count = 0
        # user to users it follows
        self.userFollowMap = {}
        # user to its tweet
        self.userTweetsMap = {}
        

    def postTweet(self, userId, tweetId):# O(1)
        """
        :type userId: int
        :type tweetId: int
        :rtype: None
        """
        # if(userId not in self.userFollowMap):
        #     self.userFollowMap[userId] = set()
        if(userId not in self.userTweetsMap):
            self.userTweetsMap[userId] = []

        newTweet = self.Tweet(tweetId, self.count)
        self.count += 1

        self.userTweetsMap[userId].append(newTweet)
        
    # using heap
    def getNewsFeed(self, userId):# maintain heap of size k = 10
        """
        :type userId: int
        :rtype: List[int]
        """
        # for 10 latest tweets starting from least recent to most recent
        heapTweets = []
        following = set()
        if(userId in self.userFollowMap):
            following = self.userFollowMap[userId]

        # add this current user itself, so that we could get the tweets made by itself
        following.add(userId)

        for follow in following:
            if(follow in self.userTweetsMap):
                # get the tweets of this user
                for tweetObj in self.userTweetsMap[follow]:
                    heapq.heappush(heapTweets, tweetObj)
                    if(len(heapTweets) > 10):
                        heapq.heappop(heapTweets)


        if(not heapTweets):
            return heapTweets
        
        topTweets = [None] * len(heapTweets)
        # the most recent would be popped at last
        k = len(heapTweets) - 1
        while(heapTweets):
            topTweets[k] = heapq.heappop(heapTweets).id
            k -= 1

        return topTweets

        

    def follow(self, followerId, followeeId):# O(1)
        """
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        # user followerId follows followeeId
        if(followerId not in self.userFollowMap):
            self.userFollowMap[followerId] = set()

        self.userFollowMap[followerId].add(followeeId)

        

    def unfollow(self, followerId, followeeId):# O(1)
        """
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        # user followerId unfollows followeeId
        if(followerId not in self.userFollowMap):
            return
        
        # discard does not throw error even if key does not exist
        self.userFollowMap[followerId].discard(followeeId)
        


# Your Twitter object will be instantiated and called as such:
print("Method2: Using priority queue - min heap to get the top 10 latest tweets")
obj = Twitter()
obj.postTweet(1,5)
print(obj.getNewsFeed(1))
obj.follow(1,2)
obj.postTweet(2,6)
print(obj.getNewsFeed(1))
obj.unfollow(1,2)
print(obj.getNewsFeed(1))


# Method3: Using priority queue - min heap to get the top 10 latest tweets. But going through only top 10 tweets of each related user
# n - Number of users
# m - max/average number of tweets for each user
# N - Total number of tweets by all the users = n * m
# K = 10 = constant
# Time Complexity : O(1) -- follow, O(1) -- unfollow, O(1) -- postTweet, O(n * K * LogK) = O(n * 10 * Log(10)) = O(n) -- users
# Space Complexity : O(V + E + T) = (V -- No. of users, E -- edges = Total followees, T -- tweets)
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No


# Your code here along with comments explaining your approach in three sentences only
# Logic for all the functions except getNewsFeed() is same as previous above. For getNewsFeed() method the logic is almost same 
# as above. Instead of going through all the tweets of the relevant users, we just go through the top 10 latest tweets of each and every 
# relevant user. 


import heapq
class Twitter(object):
    class Tweet(object):
        def __init__(self, tweetId, time):
            self.id = tweetId
            # timestamp for feed
            self.time = time

        # for custom min heap where objects are compared based on time
        def __lt__(self, other):
            return self.time < other.time

    def __init__(self):
        self.count = 0
        # user to users it follows
        self.userFollowMap = {}
        # user to its tweet
        self.userTweetsMap = {}
        

    def postTweet(self, userId, tweetId):# O(1)
        """
        :type userId: int
        :type tweetId: int
        :rtype: None
        """
        # if(userId not in self.userFollowMap):
        #     self.userFollowMap[userId] = set()
        if(userId not in self.userTweetsMap):
            self.userTweetsMap[userId] = []

        newTweet = self.Tweet(tweetId, self.count)
        self.count += 1

        self.userTweetsMap[userId].append(newTweet)
        
    # using heap
    def getNewsFeed(self, userId):# maintain heap of size k = 10
        """
        :type userId: int
        :rtype: List[int]
        """
        # for 10 latest tweets starting from least recent to most recent
        heapTweets = []
        following = set()
        if(userId in self.userFollowMap):
            following = self.userFollowMap[userId]

        # add this current user itself, so that we could get the tweets made by itself
        following.add(userId)

        for follow in following:
            if(follow in self.userTweetsMap):
                # get just the latest 10 tweets of this user
                userTweetsList = self.userTweetsMap[follow]
                for k in range(len(userTweetsList) - 1, max(len(userTweetsList) - 1 - 10, -1), -1):
                    heapq.heappush(heapTweets, userTweetsList[k])
                    if(len(heapTweets) > 10):
                        heapq.heappop(heapTweets)

                    # tweetIds.append(tweetObj)


        if(not heapTweets):
            return heapTweets
        
        topTweets = [None] * len(heapTweets)
        # the most recent would be popped at last
        k = len(heapTweets) - 1
        while(heapTweets):
            topTweets[k] = heapq.heappop(heapTweets).id
            k -= 1

        return topTweets

        

    def follow(self, followerId, followeeId):# O(1)
        """
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        # user followerId follows followeeId
        if(followerId not in self.userFollowMap):
            self.userFollowMap[followerId] = set()

        self.userFollowMap[followerId].add(followeeId)

        

    def unfollow(self, followerId, followeeId):# O(1)
        """
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        # user followerId unfollows followeeId
        if(followerId not in self.userFollowMap):
            return
        
        # discard does not throw error even if key does not exist
        self.userFollowMap[followerId].discard(followeeId)
        


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)

print("Method3: Using priority queue - min heap to get the top 10 latest tweets. But going through only top 10 tweets of each related user")
obj = Twitter()
obj.postTweet(1,5)
print(obj.getNewsFeed(1))
obj.follow(1,2)
obj.postTweet(2,6)
print(obj.getNewsFeed(1))
obj.unfollow(1,2)
print(obj.getNewsFeed(1))


# Method4: Using User Class instead of two separate maps
# n - Number of users
# m - max/average number of tweets for each user
# N - Total number of tweets by all the users = n * m
# K = 10 = constant
# Time Complexity : O(1) -- follow, O(1) -- unfollow, O(1) -- postTweet, O(n * K * LogK) = O(n * 10 * Log(10)) = O(n) -- users
# Space Complexity : O(V + E + T) = (V -- No. of users, E -- edges = Total followees, T -- tweets)
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No


# Your code here along with comments explaining your approach in three sentences only
# Logic for all the functions is same, except here we are storing the mapping of user-followees and user-tweets in the single
# User Class object

# Using User class instead of user-tweets mapping
# Our user class would have the tweets
import heapq
class Twitter(object):
    class User(object):
        def __init__(self, userId):
            self.id = userId
            # tweets made by the user
            self.tweets = []
            # userId of neighbors
            self.neighbors = set()

    class Tweet(object):
        def __init__(self, tweetId, time):
            self.id = tweetId
            # timestamp for feed
            self.time = time

        # for custom min heap where objects are compared based on time
        def __lt__(self, other):
            return self.time < other.time

    def __init__(self):
        self.count = 0
        # userId to user map
        self.userMap = {}
        

    def postTweet(self, userId, tweetId):# O(1)
        """
        :type userId: int
        :type tweetId: int
        :rtype: None
        """
        if(userId not in self.userMap):
            # create a new user
            self.userMap[userId] = self.User(userId)

        newTweet = self.Tweet(tweetId, self.count)
        self.count += 1

        # user
        (self.userMap[userId]).tweets.append(newTweet)
        
    # using heap
    def getNewsFeed(self, userId):# maintain heap of size k = 10
        """
        :type userId: int
        :rtype: List[int]
        """
        # for 10 latest tweets starting from least recent to most recent
        heapTweets = []
        following = set() # users this user is following
        if(userId in self.userMap):
            following = self.userMap[userId].neighbors

        # add this current user itself, so that we could get the tweets made by itself
        following.add(userId)

        for follow in following:
            if(follow in self.userMap):
                # get just the latest 10 tweets of this user
                userTweetsList = self.userMap[follow].tweets
                if(userTweetsList):
                    for k in range(len(userTweetsList) - 1, max(len(userTweetsList) - 1 - 10, -1), -1):
                        heapq.heappush(heapTweets, userTweetsList[k])
                        if(len(heapTweets) > 10):
                            heapq.heappop(heapTweets)


        if(not heapTweets):
            return heapTweets
        
        topTweets = [None] * len(heapTweets)
        # the most recent would be popped at last
        k = len(heapTweets) - 1
        while(heapTweets):
            topTweets[k] = heapq.heappop(heapTweets).id
            k -= 1

        return topTweets

        

    def follow(self, followerId, followeeId):# O(1)
        """
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        # user followerId follows followeeId
        if(followerId not in self.userMap):
            self.userMap[followerId] = self.User(followerId)

        self.userMap[followerId].neighbors.add(followeeId)

        

    def unfollow(self, followerId, followeeId):# O(1)
        """
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        # user followerId unfollows followeeId
        if(followerId not in self.userMap):
            return
        
        # discard does not throw error even if key does not exist
        self.userMap[followerId].neighbors.discard(followeeId)
        


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)

print("Method4: Using User Class instead of two separate maps")
obj = Twitter()
obj.postTweet(1,5)
print(obj.getNewsFeed(1))
obj.follow(1,2)
obj.postTweet(2,6)
print(obj.getNewsFeed(1))
obj.unfollow(1,2)
print(obj.getNewsFeed(1))