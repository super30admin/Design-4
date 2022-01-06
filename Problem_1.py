import heapq
class Tweet:
    def __init__(self, id, createdAt):
        self.id = id
        self.createdAt = createdAt
    
    def __lt__(self, other):
        return self.createdAt < other.createdAt

class Twitter:

    def __init__(self):
        self.tweets = {}
        self.followed = {}
        self.time = -1
        
    def postTweet(self, userId: int, tweetId: int) -> None:
        self.follow(userId, userId)
        if userId not in self.tweets:
            self.tweets[userId] = list()
        self.time += 1
        self.tweets[userId].append(Tweet(tweetId, self.time))
        

    def getNewsFeed(self, userId: int) -> List[int]:
        heap = []
        if userId in self.followed:
            userSet = self.followed[userId]
            for user in userSet:
                if user in self.tweets:
                    ftweets = self.tweets[user]
                    for tweet in ftweets:
                        heapq.heappush(heap, tweet)
                        if len(heap) > 10:
                            heapq.heappop(heap)
        result = []
        while len(heap):
            result.append(heapq.heappop(heap).id)
        return result[::-1]
                

    def follow(self, followerId: int, followeeId: int) -> None:
        if followerId not in self.followed:
            self.followed[followerId] = set()
        self.followed[followerId].add(followeeId)
        
    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId in self.followed and followerId != followeeId:
            userSet = self.followed[followerId]
            if followeeId in userSet:
                userSet.remove(followeeId)
            

# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)