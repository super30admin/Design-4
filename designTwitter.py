# postTweet: TC O(1), SC O(1) per call
# getNewsFeed: TC O(f), SC O(1) — f = number of followees + self
# follow: TC O(1), SC O(1)
# unfollow: TC O(1), SC O(1)
# Overall SC: O(u + t) — u = users, t = total tweets 
class Twitter:

    def __init__(self):
        self.tweetMap={}
        self.followMap={}
        self.time=1

    def postTweet(self, userId: int, tweetId: int) -> None:
        if userId not in self.tweetMap:
            self.tweetMap[userId]=[]
        heapq.heappush(self.tweetMap[userId],(self.time,tweetId))
        if len(self.tweetMap[userId])>10:
            heapq.heappop(self.tweetMap[userId])
        self.time+=1

    def getNewsFeed(self, userId: int) -> List[int]:
        feed=[]
        
        # Followed users' tweets
        if userId in self.followMap:
            for fid in self.followMap[userId]:
                if fid in self.tweetMap:
                    for tweet in self.tweetMap[fid]:
                        heapq.heappush(feed,tweet)
                        if len(feed)>10:
                            heapq.heappop(feed)
        
        # Own tweets
        if userId in self.tweetMap:
            for tweet in self.tweetMap[userId]:
                heapq.heappush(feed,tweet)
                if len(feed)>10:
                    heapq.heappop(feed)
        
        # Retaining only tweetIds in feed (removing  timestamps)
        feed.sort(key=lambda x: x[0], reverse=True)
        return [tweetId for _, tweetId in feed]

    def follow(self, followerId: int, followeeId: int) -> None:
        if followerId not in self.followMap:
            self.followMap[followerId]=set()
        self.followMap[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId in self.followMap:
            self.followMap[followerId].remove(followeeId)
