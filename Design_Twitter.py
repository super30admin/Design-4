# Time Complexity : All methods have O(1) except getNewsFeed with O(N log N) time complexity
# Space Complexity : O(M + N) , where M is the total number of users and N is the total number of tweets in the system.
import time
from collections import defaultdict, deque
from typing import List


class Tweet:
    def __init__(self, tweet_id, timestamp):
        self.tweet_id = tweet_id
        self.timestamp = timestamp


class User:
    def __init__(self, user_id):
        self.user_id = user_id
        self.following = set()
        self.tweets = deque()

    def post_tweet(self, tweet_id, timestamp):
        tweet = Tweet(tweet_id, timestamp)
        self.tweets.appendleft(tweet)

    def follow(self, user_id):
        self.following.add(user_id)

    def unfollow(self, user_id):
        if user_id in self.following:
            self.following.remove(user_id)


class Twitter:
    def __init__(self):
        self.timestamp = 0
        self.users = {}

    def _get_user(self, user_id):
        if user_id not in self.users:
            self.users[user_id] = User(user_id)
        return self.users[user_id]

    def postTweet(self, userId: int, tweetId: int) -> None:
        user = self._get_user(userId)
        user.post_tweet(tweetId, self.timestamp)
        self.timestamp += 1

    def getNewsFeed(self, userId: int) -> List[int]:
        user = self._get_user(userId)
        tweets = user.tweets.copy()
        for follower_id in user.following:
            follower = self._get_user(follower_id)
            tweets.extend(follower.tweets)
        tweets = sorted(tweets, key=lambda t: t.timestamp, reverse=True)
        return [tweet.tweet_id for tweet in tweets[:10]]

    def follow(self, followerId: int, followeeId: int) -> None:
        follower = self._get_user(followerId)
        follower.follow(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        follower = self._get_user(followerId)
        follower.unfollow(followeeId)

        


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)