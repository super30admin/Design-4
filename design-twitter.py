# Time Complexity: O(n)
# Space Complexity: O(1)
class Tweet:
    def __init__(self,iid:int,createdAt:int):
        self.iid=iid
        self.createdAt=createdAt
class Twitter:
    
    def __init__(self):
        self.followeed={}
        self.tweets={}
        self.time=0
    def postTweet(self, userId: int, tweetId: int) -> None:
        self.follow(userId,userId)
        if userId not in self.tweets:
            self.tweets[userId]=[]
        self.tweets[userId].append(Tweet(tweetId,self.time))
        self.time+=1

    def getNewsFeed(self, userId: int) -> List[int]:
        from heapq import heappush as push
        from heapq import heappop as pop
        setattr(Tweet, "__lt__", lambda self, other: self.createdAt <= other.createdAt)
        heap=[]
        # if userId in self.followeed:
        lis=self.followeed.get(userId) 
        if lis is not None:
            for i in lis:
                lis2=self.tweets.get(i)
                if lis2 is not None:
                    for t in lis2:
                        push(heap,t)
                        if len(heap)>10:
                            pop(heap)
        res=[]
        while len(heap)!=0:
            res.insert(0,pop(heap).iid)
        return res
        

    def follow(self, followerId: int, followeeId: int) -> None:
        if followerId not in self.followeed:
            self.followeed[followerId]=set()
        self.followeed[followerId].add(followeeId)
        

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId in self.followeed:
            if followeeId in self.followeed[followerId]:
                self.followeed[followerId].remove(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)