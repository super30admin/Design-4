import heapq as hq
class Twitter:
    
    class Tweet:
        
        def __init__(self,id,createdAt):
            self.id = id
            self.createdAt = createdAt
            
        def __lt__(self,other):
            return self.createdAt < other.createdAt
        
            
        
    def __init__(self):
        self.usersMap = {}
        self.tweetsMap = {}
        self.time = 0
    
    #Time Complexity: O(1)
    #Space Complexity: O(n)
    def postTweet(self, userId: int, tweetId: int) -> None:
        self.follow(userId,userId)
        if userId not in self.tweetsMap:
            self.tweetsMap[userId] = []
        self.time += 1
        self.tweetsMap[userId].append(self.Tweet(tweetId,self.time))
        
        
    #Time Complexity: O(nlogk)
    #Space Complexity: O(n)
    def getNewsFeed(self, userId: int) -> List[int]:
        heap = []
        if len(self.usersMap)>0:
            followerIds = set(self.usersMap[userId])
            if len(followerIds) > 0:
                for fid in followerIds:
                    if fid in self.tweetsMap:
                        followersTweets = self.tweetsMap[fid]
                        if len(followersTweets) > 0:
                            for tweet in followersTweets:
                                hq.heappush(heap,tweet)
                                if len(heap) > 10:
                                    hq.heappop(heap)
                                    
        result = []
        while len(heap)!= 0:
            result.insert(0,hq.heappop(heap).id)
            
        return result
                
            
        
    #Time Complexity: O(1)
    #Space Complexity: O(n)
    def follow(self, followerId: int, followeeId: int) -> None:
        if followerId in self.usersMap:
            self.usersMap[followerId].add(followeeId)
        else:
            st = set()
            st.add(followeeId)
            self.usersMap[followerId] = st
            
    #Time Complexity: O(1)
    #Space Complexity: O(n)
    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId != followeeId:
            if followerId in self.usersMap:
                if followeeId in self.usersMap[followerId]:
                    self.usersMap[followerId].remove(followeeId)
        
        
