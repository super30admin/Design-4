# ----------------------------------------------------
# Intuition:
#
# We need to design a basic version of Twitter with the ability to:
# 1. Post tweets
# 2. Follow/unfollow users
# 3. Retrieve the 10 most recent tweets from self and followees
#
# ----------------------------------------------------
# Naive Approach (Commented Below):
# - Keep a global list of all tweets as (userId, tweetId, timestamp)
# - On getNewsFeed, iterate in reverse and collect latest tweets from self + followees
# - Time: O(n) per getNewsFeed (n = total number of tweets)
#
# ----------------------------------------------------
# Optimal Approach:
# - Store tweets per user with timestamps
# - Maintain followee list using a hash map
# - Use a min-heap of size 10 to track the most recent tweets efficiently
# - Time: O(m log 10) per getNewsFeed where m = total tweets from self + followees
# ----------------------------------------------------

# ----------------------------------------------------
# Time and Space Complexity (Optimal Approach):
#
# postTweet:    O(1)
# follow/unfollow: O(1)
# getNewsFeed:
#   - Time: O(m log 10) ≈ O(m), where m is total tweets from user and followees
#           (each tweet pushed to a heap of size 10)
#   - Space: O(m + 10) for heap and final output list
#
# Overall Space:
# - O(u + t)
#   - u = number of users (for followees map)
#   - t = number of tweets stored
# ----------------------------------------------------

from typing import List
from collections import defaultdict
import heapq

class Twitter:

    def __init__(self):
        self.tweets = defaultdict(list)  # userId -> list of (timestamp, tweetId)
        self.followees = defaultdict(set)  # userId -> set of followeeIds
        self.timestamp = 0

    def postTweet(self, userId: int, tweetId: int) -> None:
        self.tweets[userId].append((self.timestamp, tweetId))
        self.timestamp += 1

    def getNewsFeed(self, userId: int) -> List[int]:
        heap = []
        users = self.followees[userId] | {userId}

        for user in users:
            for tweet in self.tweets[user]:
                heapq.heappush(heap, tweet)
                if len(heap) > 10:
                    heapq.heappop(heap)

        return [tweet_id for _, tweet_id in sorted(heap, reverse=True)]

    def follow(self, followerId: int, followeeId: int) -> None:
        if followerId != followeeId:
            self.followees[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        self.followees[followerId].discard(followeeId)


# ----------------------------------------------------
# Time and Space Complexity (Naive Approach):
#
# postTweet:      O(1)
# follow/unfollow: O(1)
# getNewsFeed:
#   - Time: O(n) where n = total tweets ever posted (scans all tweets)
#   - Space: O(10) for result list
#
# Overall Space:
# - O(n + u)
#   - n = number of tweets
#   - u = number of users
# ----------------------------------------------------

# ----------------------------------------------------
# 🐌 Naive Approach (Commented)
# ----------------------------------------------------

# from typing import List
# from collections import defaultdict
#
# class Twitter:
#
#     def __init__(self):
#         self.tweets = []  # List of (userId, tweetId, timestamp)
#         self.followees = defaultdict(set)
#         self.timestamp = 0
#
#     def postTweet(self, userId: int, tweetId: int) -> None:
#         self.tweets.append((userId, tweetId, self.timestamp))
#         self.timestamp += 1
#
#     def getNewsFeed(self, userId: int) -> List[int]:
#         res = []
#         users = self.followees[userId] | {userId}
#         for uid, tid, ts in reversed(self.tweets):
#             if uid in users:
#                 res.append(tid)
#                 if len(res) == 10:
#                     break
#         return res
#
#     def follow(self, followerId: int, followeeId: int) -> None:
#         if followerId != followeeId:
#             self.followees[followerId].add(followeeId)
#
#     def unfollow(self, followerId: int, followeeId: int) -> None:
#         self.followees[followerId].discard(followeeId)
#
# Time: O(n) per getNewsFeed where n = total number of tweets
# Space: O(n + u) where u = users, n = tweets
# ----------------------------------------------------

# ----------------------------------------------------
# Example Usage
if __name__ == "__main__":
    twitter = Twitter()
    twitter.postTweet(1, 5)
    print(twitter.getNewsFeed(1))  # [5]

    twitter.follow(1, 2)
    twitter.postTweet(2, 6)
    print(twitter.getNewsFeed(1))  # [6, 5]

    twitter.unfollow(1, 2)
    print(twitter.getNewsFeed(1))  # [5]

    twitter.postTweet(1, 7)
    twitter.postTweet(1, 8)
    twitter.postTweet(1, 9)
    twitter.postTweet(1, 10)
    twitter.postTweet(1, 11)
    twitter.postTweet(1, 12)
    twitter.postTweet(1, 13)
    twitter.postTweet(1, 14)
    twitter.postTweet(1, 15)
    twitter.postTweet(1, 16)
    twitter.postTweet(1, 17)
    print(twitter.getNewsFeed(1))  # Most recent 10 tweet IDs
