class Twitter:
    class Tweet:
        def __init__(self,tId,tTime):
            self.tweetId=tId
            self.timestamp=tTime

    def __init__(self):
        self.time=0
        self.TweetMap={}
        self.followerMap={}

    def postTweet(self, userId: int, tweetId: int) -> None:
        if userId not in self.TweetMap:
            self.TweetMap[userId]=[]
        
        tweet=Twitter.Tweet(tweetId,self.time)
        self.time+=1
        self.TweetMap[userId].append(tweet)
        self.follow(userId,userId)
        

    def getNewsFeed(self, userId: int) -> List[int]:
        import heapq
        followees=self.followerMap.get(userId)
        lst=[]
        pq=[]

        if followees is not None:
            for followee in followees:
                tweets=self.TweetMap.get(followee)
                if tweets is not None:
                    # for tweet in tweets:
                    for i in range(len(tweets)-1,max(len(tweets)-10-1,-1),-1):
                        tweet=tweets[i]
                        heapq.heappush(pq,(tweet.timestamp,tweet))
                        if len(pq)>10:
                            heapq.heappop(pq)
            
        result=[]
        while pq:
            result.append(heapq.heappop(pq)[1].tweetId)
        result.reverse()
        return result

    def follow(self, followerId: int, followeeId: int) -> None:
        if followerId not in self.followerMap:
            self.followerMap[followerId]=set()
        self.followerMap[followerId].add(followeeId)

        

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId not in self.followerMap:
            return
        self.followerMap[followerId].discard(followeeId)
        
