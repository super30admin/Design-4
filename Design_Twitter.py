from queue import PriorityQueue
class Twitter:
    
    class Tweet:
        def __init__(self,ID,time):
            self.id = ID
            self.time = time
            
    class Distance:
            def __init__(self, tweet):
                self.tweet = tweet
                
            def __lt__(self, other):
                return self.tweet.time < other.tweet.time
    
    def __init__(self):
        self.tweets = {} # Values will be a list of tweets tweeted by eacg user
        self.followed = {} # Values will be a set of users followed by each user
        self.time = 0

    def postTweet(self, userId: int, tweetId: int) -> None:
        self.follow(userId,userId) # Make user follow themself in O(1) time
        self.time += 1
        if userId not in self.tweets.keys():
            self.tweets[userId] = []
        self.tweets[userId].append(self.Tweet(tweetId,self.time))
        

    def getNewsFeed(self, userId: int) -> List[int]:
        pq = PriorityQueue()
        followeds = []
        if userId in self.followed.keys():
            followeds = self.followed[userId]
        if followeds:
            for followerId in followeds:
                if followerId not in self.tweets.keys():
                    continue
                fTweets =  self.tweets[followerId]
                if fTweets:
                    size = len(fTweets)
                for k in range(size-1,size-11,-1):
                    if k<0:
                        break
                    fTweet = fTweets[k]
                    pq.put(self.Distance(fTweet))
                    if pq.qsize() > 10:
                        pq.get()
        
        result = collections.deque()
        while not pq.empty():
            result.appendleft(pq.get().tweet.id)
        return result
        

    def follow(self, followerId: int, followeeId: int) -> None:
        if followerId not in self.followed.keys():
            self.followed[followerId] = set()
        self.followed[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId in self.followed.keys() and followerId != followeeId and followeeId in self.followed[followerId]:
            self.followed[followerId].remove(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)