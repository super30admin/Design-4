"""
Problem: 355. Design Twitter
Leetcode: https://leetcode.com/problems/design-twitter/
Time Complexity: O(n log n) for getNewsFeed, other O(1)
Space Complexity: O(users * max no of tweets of a user + users * max no of followers for a user)
Did this code successfully run on Leetcode: Yes
"""

from heapq import heappush, heappop


# Create a class Tweet to store timestamp for each tweet along with its tweetId
class Tweet:
    def __init__(self, tweetId, timestamp):
        self.tweetId = tweetId
        self.timestamp = timestamp

    # Add a custom comparator function for heap/sorting
    def __lt__(self, other):
        return self.timestamp < other.timestamp


class Twitter:
    def __init__(self):
        """
        Initialize your data structure here.
        """
        # global timestamp to update the timestamp for each tweet and increment globally
        self.timestamp = 0
        # In user_tweet hashmap, key is userId and value is a list of tweetIds
        self.user_tweet = {}
        # In user_follower hashmap, followerId is key and followeeId is value in hashset
        self.user_follower = {}
        self.feed_limit = 10

    def _newUser(self, userId):
        # add userid itself into its own follower's hashset so that its post will be included in news feed
        if userId not in self.user_follower:
            self.user_follower[userId] = set()
            self.user_follower[userId].add(userId)

        # for new user, create an empty list of tweets
        if userId not in self.user_tweet:
            self.user_tweet[userId] = []

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        self._newUser(userId)
        tweet = Tweet(tweetId, self.timestamp)
        self.user_tweet[userId].append(tweet)
        self.timestamp += 1

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        self._newUser(userId)
        tweet_list = []
        followedUsers = self.user_follower[userId]
        if followedUsers:
            for followee in followedUsers:
                if followee in self.user_tweet and len(self.user_tweet[followee]) > 0:
                    # Using priority queue to maintain the sequence of tweets
                    for tweet in self.user_tweet[followee]:
                        heappush(tweet_list, tweet)
                        if len(tweet_list) > self.feed_limit:
                            heappop(tweet_list)
        output = []
        while len(tweet_list) > 0:
            output.append(heappop(tweet_list).tweetId)

        return output[::-1]

    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        self._newUser(followerId)
        self._newUser(followeeId)
        self.user_follower[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        self._newUser(followerId)
        self._newUser(followeeId)
        # check if key exists i.e follower is present and follower is not himself/herself
        if followerId in self.user_follower and followerId != followeeId:
            # check the values of particular followerId
            if followeeId in self.user_follower[followerId]:
                self.user_follower[followerId].remove(followeeId)

# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)