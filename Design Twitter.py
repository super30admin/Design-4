from heapq import heappush as insert
from heapq import heappop as remove
class Tweet:
    def __init__(self, tweetId, timestamp):
        self.tweetId = tweetId
        self.createdAt = timestamp

class User:
    def __init__(self, userId):
        self.userId = userID


class Twitter:
    time = 0
    newsFeedSize = 10 
    def __init__(self):
        self.tweets = {}
        self.follows = {}
        

    def postTweet(self, userId: int, tweetId: int) -> None: # TC O(1)
        """
        :type userId: int
        :type tweetId: int
        :rtype: None
        """
        t = Tweet(tweetId, self.time)
        if userId in self.tweets:
            self.tweets[userId].append(t)
        else:
            self.tweets[userId] = [t]
        self.time += 1 # increment the time
        self.follow(userId, userId)

    def getNewsFeed(self, userId: int) -> List[int]: # TC O(n*logk) 
        """
        :type userId: int
        :rtype: List[int]
        """
        heap = []
        Tweet.__lt__ = lambda t1,t2: t1.createdAt < t2.createdAt
        if userId in self.follows:
            for user in self.follows[userId]:
                if user in self.tweets:
                    for tweet in self.tweets[user][-self.newsFeedSize:]:
                        insert(heap, tweet)
                        if len(heap) > self.newsFeedSize:
                            remove(heap)
        result = []
        while heap:
            result.append(remove(heap).tweetId)
        return reversed(result)
            
    def follow(self, followerId: int, followeeId: int) -> None: # TC O(1)
        """
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        if not followerId in self.follows:
            self.follows[followerId] = set()
            self.follows[followerId].add(followerId)
        self.follows[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None: # TC O(1)
        """
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        if followerId in self.follows and followeeId in self.follows[followerId]:
            self.follows[followerId].remove(followeeId)
        
# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)