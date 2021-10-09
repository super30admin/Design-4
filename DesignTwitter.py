"""
Time Complexity : O(1) for follow(), unfollow(), postTweet() and O(nk) for getNewsFeed() 
Space Complexity : O(n) 
Did this code successfully run on Leetcode : Yes
Any problem you faced while coding this : No
"""

class Twitter:
    class Tweet:
        def __init__(self, tId, createdAt):
            self.tId = tId
            self.createdAt = createdAt
        
    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.followers = {}
        self.tweet = {}
        self.time = 0
    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        TC : O(1)
        """
        self.follow(userId, userId)
        if userId not in self.tweet:
            self.tweet[userId] = []
        self.time += 1
        self.tweet[userId].append(self.Tweet(tweetId, self.time))

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        TC : O(NK) where N is the no. of users
        """
        self.Tweet.__lt__ = lambda self, other: self.createdAt < other.createdAt
        heap = []
        if len(self.followers) < 1 or len(self.tweet) < 1:
            return []
        hashSet = self.followers[userId]
        if hashSet:
            for user in hashSet:
                tweets = self.tweet[user]
                if tweets:
                    for tw in tweets:
                        heapq.heappush(heap, tw)
                        if len(heap) > 10:
                            heapq.heappop(heap)
        result = []
        while len(heap) > 0:
            result.insert(0, heapq.heappop(heap).tId)
        return result
        
    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-         op.
        TC : O(1)
        """
        if followerId not in self.followers:
            self.followers[followerId] = set()
        self.followers[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        TC : O(1)
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