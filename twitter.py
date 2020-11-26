#Difficulties: How do I implement a custom comparator for the heapq used instead of priority queue module used in java as taught in class?
from collections import defaultdict 
import heapq
class Tweet:
    def __init__(self,id,createdAt):
        self.id = id
        self.createdAt = createdAt
class Twitter:

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.followed = defaultdict(set)
        self.tweets = defaultdict(list)
        self.time = 0
        
        

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        if userId not in self.followed:
            self.followed[userId] = set()
        t = Tweet(userId,self.time+1)
        
        #make the user follow itself
        self.followed[userId].append(userId)
        if userId not in self.tweets:
            self.tweets[userId] = list()
        self.tweets[userId].append(t)

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        li = []
        fids = set(self.followed[userId])
        if fids is not None:
            for fid in fids:
                ftweets = self.tweets[fid]
                if ftweets is not None:
                    for ftweet in ftweets:
                        heapq.push(li,ftweet)


    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId not in self.followed:
            self.followed[followerId] = set()
        self.followed[followerId].append(followeeId)
        

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId == followeeId:
            return
        else:
            self.followed[followerId].remove(followeeId)
        


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)