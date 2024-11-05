class Tweet:
        def __init__(self,tweetId,createdAt):
            self.tweetId = tweetId
            self.created_at = createdAt

        def __lt__(self, other):
            return self.created_at < other.created_at
        
class Twitter(object):

    def __init__(self):
        self.followeesMap = {}
        self.tweetsMap = {}
        self.timeStamp = 0

    def postTweet(self, userId, tweetId):
        """
        :type userId: int
        :type tweetId: int
        :rtype: None
        """
        self.follow(userId,userId)
        if userId not in self.tweetsMap:
            self.tweetsMap[userId] = []
        self.tweetsMap[userId].append(Tweet(tweetId,self.timeStamp))
        self.timeStamp+=1
        

    def getNewsFeed(self, userId):
        """
        :type userId: int
        :rtype: List[int]
        """
        fIds = self.followeesMap.get(userId,set())
        pq = []

        for id_ in fIds:
            tweets = self.tweetsMap.get(id_,[])
            for tw in tweets:
                heapq.heappush(pq,tw)
                if(len(pq)>10):
                    heapq.heappop(pq)

        result = []
        while pq:
            result.insert(0, heapq.heappop(pq).tweetId)
        
        return result
        

    def follow(self, followerId, followeeId):
        """
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        if followerId not in self.followeesMap:
            self.followeesMap[followerId] = set()
        self.followeesMap[followerId].add(followeeId)
        

    def unfollow(self, followerId, followeeId):
        """
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        if followerId in self.followeesMap:
            if followeeId != followerId:
                self.followeesMap[followerId].discard(followeeId)
            if not self.followeesMap[followerId]:
                del self.followeesMap[followerId]
        
        


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)