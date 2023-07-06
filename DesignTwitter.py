class Twitter:
    def __init__(self):
        self.followed=defaultdict(list)
        self.tweets=defaultdict(list)
        self.tweetOrder =0
        

    def postTweet(self, userId: int, tweetId: int) -> None:
        self.tweetOrder -= 1
        self.tweets[userId].append((self.tweetOrder,tweetId))
        #if(userId not in self.tweets.keys()):
            #self.tweets[userId]=[]
        #self.tweets[userId].append(Tweet(tweetId, self.time))
        #self.time+=1
    
    def getNewsFeed(self, userId: int) -> List[int]:
        tweets = []
        tweetHeap = []
        latest = 10
        
        users = [userId, *self.followed[userId]]
        for user in users:
            if self.tweets[user]:
                tweetHeap = [*tweetHeap, *self.tweets[user]]
        heapq.heapify(tweetHeap)
        while tweetHeap and latest > 0:
            tweets.append(heapq.heappop(tweetHeap)[1])
            latest -= 1
        return tweets

    def follow(self, followerId: int, followeeId: int) -> None:
        if followeeId not in self.followed[followerId]:
            self.followed[followerId].append(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followeeId in self.followed[followerId]:
            self.followed[followerId].remove(followeeId)