## Problem 1: Design Twitter (https://leetcode.com/problems/design-twitter/)

class Twitter:

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.tweets = collections.defaultdict(list)
        
        self.followeing = collections.defaultdict(set)
        self.time = 0
        

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        self.tweets[userId].append([-self.time, tweetId])
        self.time += 1
        if len(self.tweets[userId]) > 10:
            self.tweets[userId].pop(0)
        

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        heap = []
        for tweet in self.tweets[userId]:
            heap.append(tweet)
        for f in self.followeing[userId]:
            for tweet in self.tweets[f]:
                heap.append(tweet)
        ret = []
        heapq.heapify(heap)
        for _ in range(10):
            if heap:
                ret.append(heapq.heappop(heap)[1])
        return ret
        

    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId == followeeId:
            return
        self.followeing[followerId].add(followeeId)
            
        

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        if self.followeing[followerId] and followeeId in self.followeing[followerId]:
            self.followeing[followerId].remove(followeeId)
        


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)