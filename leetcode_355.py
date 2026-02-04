class Twitter:

    def __init__(self):
        self.time = 1
        self.tweets = {}      
        self.following = {}
       
        

    def postTweet(self, userId: int, tweetId: int) -> None:
        if userId not in self.tweets:
            self.tweets[userId] = []
        self.tweets[userId].append((self.time, tweetId))
        self.time += 2
       
        

    def getNewsFeed(self, userId: int) -> List[int]:
        heap = []

        # include user's own tweets
        if userId in self.tweets:
            for t in self.tweets[userId][-10:]:
                heapq.heappush(heap, t)

        # include followees' tweets
        if userId in self.following:
            for f in self.following[userId]:
                if f in self.tweets:
                    for t in self.tweets[f][-10:]:
                        heapq.heappush(heap, t)

        
        return [tweet for _, tweet in heapq.nlargest(10, heap)]
        

    def follow(self, followerId: int, followeeId: int) -> None:
                if followerId not in self.following:
                    self.following[followerId] = set()
                if followerId != followeeId:
                    self.following[followerId].add(followeeId)

              
        

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId in self.following:
            self.following[followerId].discard(followeeId)
        