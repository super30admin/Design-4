## Problem 1: Design Twitter (https://leetcode.com/problems/design-twitter/)
import heapq
from collections import defaultdict
from typing import List

class Tweet:
    def __init__(self, tweetId: int, createdAt: int):
        # Time: O(1) | Space: O(1)
        self.tweetId = tweetId
        self.createdAt = createdAt

class Twitter:
    def __init__(self):
        # Time: O(1) initialization
        # Space: O(U * F + T) overall, where U is users, F is max followees per user, and T is total tweets.
        self.followed = defaultdict(set)
        self.tweetMap = defaultdict(list)
        self.time = 1

    def postTweet(self, userId: int, tweetId: int) -> None:
        # Time: O(1)
        # Space: O(1) additional space per tweet
        # Logic: Auto-follow self so own tweets appear in feed. Append tweet with an incrementing global timestamp.
        self.follow(userId, userId)
        self.tweetMap[userId].append(Tweet(tweetId, self.time))
        self.time += 1

    def getNewsFeed(self, userId: int) -> List[int]:
        # Time: O(N * log k), where N is total tweets from all followed users, and k=10 (max heap size). Simplifies to O(N).
        # Space: O(k) = O(1) for maintaining the heap of max size 10.
        # Logic: Iterate through all tweets of all followed users. Maintain a min-heap of size 10 
        # to filter out the 10 most recent tweets. Pop and reverse to get descending order.
        minheap = []
        following = self.followed[userId]
        
        if len(following) > 0:
            for uid in following:
                tweets = self.tweetMap.get(uid)
                if tweets:
                    for tweet in tweets:
                        # Push current tweet onto min-heap
                        heapq.heappush(minheap, (tweet.createdAt, tweet.tweetId))
                        # Pop the oldest tweet if we exceed the 10-tweet limit
                        if len(minheap) > 10:
                            heapq.heappop(minheap)
        
        feed = []
        while minheap:
            createdAt, tweetId = heapq.heappop(minheap)
            # Insert at the beginning to reverse the min-heap order (most recent first)
            feed.insert(0, tweetId)
            
        return feed

    def follow(self, followerId: int, followeeId: int) -> None:
        # Time: O(1) average case for hash set insertion
        # Space: O(1) additional space per follow action
        # Logic: Add the followee to the follower's hash set.
        self.followed[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        # Time: O(1) average case for hash set removal
        # Space: O(1)
        # Logic: Prevent users from unfollowing themselves (breaking the postTweet logic). 
        # Use discard to safely remove the followee without throwing a KeyError if they aren't followed.
        if followerId != followeeId:
            self.followed[followerId].discard(followeeId)