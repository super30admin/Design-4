'''
time complexity: O(nlogn)
space complexity: O(n)  # for the heap 
'''
import heapq
class Tweet:
    def __init__(self,id, createdAt):
        self.id = id
        self.createdAt = createdAt
    def __lt__(self,other):
        return self.createdAt < other.createdAt
class Twitter:
    def __init__(self):
        self.user = {}
        self.tweet = {}
        self.followed = {}
        self.time = 0

    def postTweet(self, userId: int, tweetId: int) -> None:
        self.follow(userId,userId)
        if userId not in self.tweet:
            self.tweet[userId] = []
        self.time+=1
        self.tweet[userId].append(Tweet(tweetId,self.time))

    def getNewsFeed(self, userId: int) -> List[int]:
        pq = []
        heapq.heapify(pq)
        fids = self.followed.get(userId,None)
        if(fids!=None):
            for fid in fids:
                ftweet = self.tweet.get(fid,None)
                if ftweet != None:
                    for ft in ftweet:
                        heapq.heappush(pq,ft)
                        if(len(pq)>10):
                            heapq.heappop(pq)
        arr = []
        while(len(pq)!=0):
            arr.append(heapq.heappop(pq).id)
        return arr[::-1]
        

    def follow(self, followerId: int, followeeId: int) -> None:
        if followerId not in self.followed:
            self.followed[followerId] = set()
        self.followed[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId in self.followed and followerId != followeeId:
            self.followed[followerId].discard(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)