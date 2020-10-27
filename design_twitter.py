"""
Problem: 355. Design Twitter
Leetcode: https://leetcode.com/problems/design-twitter/

"""


class Twitter:
    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.users = {}
        self.followers = {}
        self.assign_priority = 0

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        self.assign_priority += 1
        if userId in self.users:
            self.users[userId].append((tweetId, self.assign_priority))
        else:
            self.users[userId] = [(tweetId, self.assign_priority)]

    def get_tweet(self, userId):
        tweets = []
        if userId in self.users:
            tweets += self.users[userId]
        if userId in self.followers:
            followees = self.followers[userId]
            for u_id in followees:
                if u_id in self.users:
                    tweets += self.users[u_id]
        tweets = sorted(tweets, key=lambda posts: posts[1], reverse=True)
        return tweets[0:10]

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        recent_Tweets = self.get_tweet(userId)
        return [post[0] for post in recent_Tweets]

    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId != followeeId:
            if followerId in self.followers:
                self.followers[followerId].add(followeeId)
            else:
                self.followers[followerId] = {followeeId}

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId in self.followers:
            if followeeId in self.followers[followerId]:
                self.followers[followerId].remove(followeeId)

# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)