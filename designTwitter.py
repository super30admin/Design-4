# Time Complexity :
# postTweet: O(1)
# getNewsFeed: O(nlogk)
# follow: O(1)
# unfollow: O(1)
# Space Complexity : 
# followMap: O(n)
# tweetMap: O(n)
# heap: O(k)

# Approach:
# We are designing a Twitter-like system where users can post tweets, follow other users, and get their news feed.
# We will use a min heap to keep track of the latest tweets from the users that the user is following.
# We will also use a dictionary to keep track of the users that each user is following and the tweets that each user has posted.
# The postTweet method will add a new tweet to the user's tweet list and also add the user to their own follow list.
# The getNewsFeed method will return the latest 10 tweets from the users that the user is following.
# The follow method will add a user to the follower's follow list and the unfollow method will remove a user from the follower's follow list.

import heapq
# Tweet class to store the tweetId and timeStamp
# The timeStamp is used to keep track of the order of the tweets
class Tweet:
    def __init__(self, tweetId, timeStamp):
        self.time = 0
        self.tweetId = tweetId
        self.timeStamp = timeStamp
    # Override the less than operator to compare the timeStamp of two tweets
    # This is used to create a min heap based on the timeStamp
    def __lt__(self, other):
        return self.timeStamp < other.timeStamp

class Twitter:
    # Initialize the Twitter class with two dictionaries and a timeStamp variable
    def __init__(self):
        self.followMap = {}
        self.tweetMap = {}
        self.timeStamp = 0
    # Post a new tweet with the userId and tweetId
    def postTweet(self, userId: int, tweetId: int) -> None:
        # If the userId is not in the tweetMap, create a new list for the userId
        if userId not in self.tweetMap:
            self.tweetMap[userId] = []
        # Create a new tweet object with the tweetId and timeStamp
        tweet = Tweet(tweetId, self.timeStamp)
        # Increment the timeStamp variable to keep track of the order of the tweets
        self.timeStamp += 1
        # Add the tweet to the user's tweet list
        self.tweetMap[userId].append(tweet)
        # When a user posts a tweet, we make them follow themselves, so we can get their own tweets in the news feed
        self.follow(userId, userId)
    # Get the latest 10 tweets from the users that the user is following
    def getNewsFeed(self, userId: int):
        # Create a min heap to keep track of the latest tweets
        heap = []
        # Create a result list to store the latest tweets
        res = []
        # If the user has not followed anyone, return an empty list
        followees = None
        if userId in self.followMap:
            # Get the list of users that the user is following
            followees = self.followMap[userId]
        if followees:
            # Iterate through the list of users that the user is following
            tweets = None
            for followee in followees:
                # If the user's followee has posted any tweets, get the list of tweets
                if followee in self.tweetMap:
                    tweets = self.tweetMap[followee]
                    # Iterate through the list of tweets in reverse order
                    # This is done to get the latest tweets first
                    for i in range(len(tweets)-1, -1, -1):
                        # If there are no more tweets, break the loop
                        if i < 0:
                            break
                        # If the heap size is less than 10, add the tweet to the heap
                        heapq.heappush(heap, tweets[i])
                        # If the heap size exceeds 10, pop the minimum element from the heap
                        # This is done to keep only the latest 10 tweets in the heap
                        if len(heap) > 10:
                            heapq.heappop(heap)
        # Once we have latest 10 tweets in the heap, we pop the elements from the heap and add them to the result list
        # The result list is in reverse order, so we reverse it before returning
        for i in range(len(heap)):
            tweet = heapq.heappop(heap)
            t = tweet.tweetId
            res.append(t)
        return res[::-1]
    # Follow a user with the followerId and followeeId
    def follow(self, followerId: int, followeeId: int) -> None:
        # If the followerId is not in the followMap, create a new set for the followerId
        if followerId not in self.followMap:
            self.followMap[followerId] = set()
        # Add the followeeId to the follower's follow list
        self.followMap[followerId].add(followeeId)
    # Unfollow a user with the followerId and followeeId
    def unfollow(self, followerId: int, followeeId: int) -> None:
        # If the followerId is not in the followMap, it means that the user is not following anyone
        # So we return without doing anything
        if followerId not in self.followMap:
            return
        # If the followeeId is in the follower's follow list, remove it
        if followeeId in self.followMap[followerId]:
            self.followMap[followerId].remove(followeeId)