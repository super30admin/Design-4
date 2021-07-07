import heapq
from collections import deque,defaultdict
class Twitter:

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.time = 0
        self.tweets = defaultdict(deque)
        self.following = defaultdict(set)
    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        self.following[userId].add(userId)
        self.tweets[userId].appendleft((self.time,userId,tweetId))
        if len(self.tweets[userId]) > 10:
            self.tweets[userId].pop()
        self.time-=1
        

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        heap = []
        self.following[userId].add(userId)
        for user in self.following[userId]:
            if self.tweets[user]:
                heapq.heappush(heap,(self.tweets[user][0],0))
        result = []
        count= 0
        while heap and count<10:
            curr = heapq.heappop(heap)
            result.append(curr[0][2])
            if curr[1]!=len(self.tweets[curr[0][1]])-1:
                heapq.heappush(heap,(self.tweets[curr[0][1]][curr[1]+1],curr[1]+1))
            count+=1
        return result
            

    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        self.following[followerId].add(followeeId)
        
    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        if followeeId in self.following[followerId]:
            self.following[followerId].remove(followeeId)
        ```
