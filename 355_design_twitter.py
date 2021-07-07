from collections import defaultdict, deque
import heapq
from typing import List


class Twitter:
    """
        https://leetcode.com/problems/design-twitter/
        Time Complexity -
                    O(nlogk) but since k is constant, so O(n).
                    'k' is the number of recent recent posts.
                    logk to pop from heap.
        Space Complexity -
                    O(n) is the number of users
    """

    class Tweet:
        def __init__(self, id, createdAt):
            self.id = id
            self.createdAt = createdAt

        def __lt__(self, other):
            return self.createdAt < other.createdAt

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.following = defaultdict(set)
        self.tweets = defaultdict(list)
        self.time_stamp = 0
        self.feed_size = 10

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        # creating a user if not present
        if userId not in self.following:
            self.following[userId] = set()
        # adding tweet for the user
        self.time_stamp += 1
        self.tweets[userId].append(self.Tweet(tweetId, self.time_stamp))

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        user_followers = set(self.following[userId])
        # adding the user because his/her tweets must be visible
        # in news feed as well
        user_followers.add(userId)
        result_tweets = []
        for follower in user_followers:
            follower_tweets = self.tweets[follower]
            for tweet in follower_tweets:
                # if no of tweets are less than required, simply add it
                if len(result_tweets) < self.feed_size:
                    heapq.heappush(result_tweets, tweet)
                # if the current tweet is recent from the top element
                # remove top tweet and insert the recent one
                elif tweet.createdAt > result_tweets[0].createdAt:
                    heapq.heappop(result_tweets)
                    heapq.heappush(result_tweets, tweet)
        # getting elements from heap
        tweet_ids = deque()
        for _ in range(len(result_tweets)):
            tweet = heapq.heappop(result_tweets)
            tweet_ids.appendleft(tweet.id)
        return list(tweet_ids)

    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        if followeeId not in self.following[followerId]:
            self.following[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        if followeeId in self.following[followerId]:
            self.following[followerId].remove(followeeId)

# # Your Twitter object will be instantiated and called as such:
# twitter = Twitter()
#
# # User 1 posts a new tweet (id = 5)
# print(twitter.postTweet(1, 5))
#
# # User 1's news feed should return a list with 1 tweet id -> [5].
# print(twitter.getNewsFeed(1))
#
# # User 1 follows user 2.
# print(twitter.follow(1, 2))
#
# # User 2 posts a new tweet (id = 6).
# print(twitter.postTweet(2, 6))
#
# # User 1's news feed should return a list with 2 tweet ids -> [6, 5].
# # Tweet id 6 should precede tweet id 5 because it is posted after tweet id 5.
# print(twitter.getNewsFeed(1))
#
# # User 1 unfollows user 2.
# print(twitter.unfollow(1, 2))
#
# # User 1's news feed should return a list with 1 tweet id -> [5],
# # since user 1 is no longer following user 2.
# print(twitter.getNewsFeed(1))

# twitter = Twitter()
#
# twitter.postTweet(1, 5)
# twitter.postTweet(1, 3)
# twitter.postTweet(1, 101)
# twitter.postTweet(1, 13)
# twitter.postTweet(1, 10)
# twitter.postTweet(1, 2)
# twitter.postTweet(1, 94)
# twitter.postTweet(1, 505)
# twitter.postTweet(1, 333)
# twitter.postTweet(1, 22)
# twitter.postTweet(1, 11)
# twitter.getNewsFeed(1)
