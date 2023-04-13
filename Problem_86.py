import collections

class Tweet:
    def __init__(self, tweetId, time):
        self.tweetId = tweetId
        self.time = time
    
class Twitter:

    def __init__(self):
        self.tweets = collections.defaultdict(list)
        self.followed = collections.defaultdict(set)
        self.time = 0
        
    # TC: O(1)
    # SC: O(1)
    def postTweet(self, userId: int, tweetId: int) -> None:
        self.follow(userId, userId)
        if userId not in self.tweets:
            self.tweets[userId] = []
        self.tweets[userId].append(Tweet(tweetId, self.time))
        self.time = self.time + 1
        
    # TC: O(Nlog10) ~ O(N)
    # SC: O(1)
    def getNewsFeed(self, userId: int) -> List[int]:
        self.follow(userId, userId)
        newsFeed = []
        minHeap = []
        Tweet.__lt__ = lambda x, y: x.time < y.time
        for u in self.followed[userId]:
            utweets = self.tweets[u]
            size = len(utweets)
            for i in range(size-1, -1, -1):
                if i<(size-10):
                    break
                else:
                    heapq.heappush(minHeap, utweets[i])
                    if len(minHeap)>10:
                        heapq.heappop(minHeap)

        while minHeap:
            newsFeed.insert(0, heapq.heappop(minHeap).tweetId)
        return newsFeed
    
    # TC: O(1)
    # SC: O(1)
    def follow(self, followerId: int, followeeId: int) -> None:
        if followerId not in self.followed:
          self.followed[followerId] = set()
        self.followed[followerId].add(followeeId)       
    
    # TC: O(1)
    # SC: O(1)
    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId in self.followed and followerId != followeeId:
          if followeeId in self.followed[followerId]:
            self.followed[followerId].remove(followeeId)
        


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)