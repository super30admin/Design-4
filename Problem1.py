# 355. Design Twitter
from typing import List
import heapq

class Twitter:
    class Tweet:
        def __init__(self, id, time):
            self.tweetId = id
            self.createdAt = time
        
        def __lt__(self, other):
            # For heap comparison - newer tweets (higher timestamp) come first
            return self.createdAt > other.createdAt

    def __init__(self):
        self.followeesMap = {}  # userId -> set of followeeIds
        self.tweetsMap = {}     # userId -> list of tweets
        self.timestamp = 0
        
    def postTweet(self, userId: int, tweetId: int) -> None:
        if userId not in self.tweetsMap:
            self.tweetsMap[userId] = []
        self.tweetsMap[userId].append(self.Tweet(tweetId, self.timestamp))
        self.timestamp += 1  # Fixed: self.timestamp instead of timestamp
            
    def getNewsFeed(self, userId: int) -> List[int]:
        # Initialize heap
        hp = []
        
        # Get tweets from user themselves
        if userId in self.tweetsMap:
            for tweet in self.tweetsMap[userId]:
                heapq.heappush(hp, tweet)
        
        # Get tweets from followees
        if userId in self.followeesMap:
            for followeeId in self.followeesMap[userId]:
                if followeeId in self.tweetsMap:
                    for tweet in self.tweetsMap[followeeId]:
                        heapq.heappush(hp, tweet)
        
        # Get top 10 most recent tweets
        result = []
        while hp and len(result) < 10:
            result.append(heapq.heappop(hp).tweetId)
            
        return result
        
    def follow(self, followerId: int, followeeId: int) -> None:
        if followerId not in self.followeesMap:
            self.followeesMap[followerId] = set()
        self.followeesMap[followerId].add(followeeId)
        
    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId in self.followeesMap:
            self.followeesMap[followerId].discard(followeeId)  # Using discard instead of remove