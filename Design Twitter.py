class Twitter:
    def __init__(self):
        self.followed = {} # Hashmap to store who any user follows
        self.tweets = {} # Hashmap to store all tweets by a particular user
    global time # Global variable time to keep track of time at which tweet is posted
    time = 0
    def postTweet(self, userId: int, tweetId: int) -> None: # O(1) time
        global time
        self.follow(userId, userId)
        if userId not in self.tweets:
            self.tweets[userId] = []
        self.tweets[userId].append((time, tweetId)) # Appends tweet with timestamp as a tuple to be used by the minheap
        time += 1

    def getNewsFeed(self, userId: int) -> List[int]: # O(nm)
        # Get users that user is following
        result = deque()
        if userId not in self.followed:
            return []
        following = self.followed[userId]
        heap = []
        # heapq.heapify(heap)
        for fId in following:
            if fId not in self.tweets:
                continue
            ftweets = self.tweets[fId]
            if ftweets:
                for tw in ftweets:
                    heapq.heappush(heap, tw)
                    if len(heap) > 10:
                        heapq.heappop(heap)
        while heap:
            temp = heapq.heappop(heap)
            result.appendleft(temp[1])
        return result



    def follow(self, followerId: int, followeeId: int) -> None: # O(1)
        if followerId not in self.followed:
            self.followed[followerId] = set()
        self.followed[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None: # O(1)
        if followerId in self.followed and followerId != followeeId:
            if followeeId in self.followed[followerId]:
                self.followed[followerId].remove(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)