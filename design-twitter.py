class Twitter:
    def __init__(self):
        self.tweets = {} # { userId: [(tweetId,timer)] }
        self.followers={} # { followerId: [followeeId1,followeeId2] }
        self.timer = 0

    def postTweet(self, userId: int, tweetId: int) -> None:
        if userId not in self.tweets:
            self.tweets[userId]=[]
        self.timer+=1
        self.tweets[userId].append((tweetId,self.timer))

    def getNewsFeed(self, userId: int) -> List[int]:
        # .get(key, default)
        # who does the user follow
        sources = list(self.followers.get(userId,set()))+[userId]
        heap=[]
        res=[]
        for follower in sources:
            if follower in self.tweets:
                # start with the newest tweet from each user
                tweetId, timestamp = self.tweets[follower][-1]
                # heapq is minheap by default
                # multiply by -1 to make max heap (want largest timestamp first)

                # len(self.tweets[follower])-1) tracks the exact index of this tweet inside the user's tweet list (last index at first). Decrement index when we pop
                heapq.heappush(heap,(-timestamp,tweetId,follower,len(self.tweets[follower])-1))
        
        while heap and len(res) < 10: # 10 most recent tweets
            time,tweetId,userId,index=heapq.heappop(heap)
            res.append(tweetId)
            index-=1
            if index >= 0:
                newTweetId, newTimestamp = self.tweets[userId][index]
                heapq.heappush(heap,(-newTimestamp,newTweetId,userId,index))
        
        return res

    def follow(self, followerId: int, followeeId: int) -> None:
        if followerId not in self.followers:
            self.followers[followerId]=set()
        self.followers[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId not in self.followers:
            return
        self.followers[followerId].discard(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)


