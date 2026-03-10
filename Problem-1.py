# Time Complexity : 
#   postTweet: O(1)
#   follow/unfollow: O(1)
#   getNewsFeed: O(N log k), N = total tweets by user + followees, k = 10 (max feed size)
# Space Complexity : O(T + U), T = total tweets, U = total users for followers mapping
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No

# Explanation:
# We implement a simplified Twitter:
# - Each user has a set of followees (including themselves).
# - Tweets are stored globally with timestamps to maintain order.
# - postTweet adds a tweet with current timestamp.
# - getNewsFeed collects all tweets from the user and their followees, sorts by timestamp, 
#   and returns the 10 most recent tweet IDs.
# - follow/unfollow updates the followers mapping.

from typing import List
import heapq
from collections import defaultdict

class Twitter:

    def __init__(self):
        # Map user -> set of followees (user follows themselves by default)
        self.followees = defaultdict(set)
        # List of tuples: (timestamp, userId, tweetId)
        self.tweets = []
        self.time = 0  # Global timestamp to order tweets

    def postTweet(self, userId: int, tweetId: int) -> None:
        # Ensure user follows themselves
        self.followees[userId].add(userId)
        # Add tweet with current timestamp
        self.tweets.append((self.time, userId, tweetId))
        self.time += 1

    def getNewsFeed(self, userId: int) -> List[int]:
        res = []
        if userId not in self.followees:
            return res
        # Get the set of users to consider
        users = self.followees[userId]
        # Use a max-heap to get 10 most recent tweets
        heap = []
        for time, uId, tId in reversed(self.tweets):
            if uId in users:
                heapq.heappush(heap, (-time, tId))
        feed = []
        while heap and len(feed) < 10:
            _, tId = heapq.heappop(heap)
            feed.append(tId)
        return feed

    def follow(self, followerId: int, followeeId: int) -> None:
        self.followees[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        # Can't unfollow oneself
        if followeeId != followerId and followeeId in self.followees[followerId]:
            self.followees[followerId].remove(followeeId)

# Example usage:
# obj = Twitter()
# obj.postTweet(1, 5)
# print(obj.getNewsFeed(1))  # Output: [5]
# obj.follow(1, 2)
# obj.unfollow(1, 2)