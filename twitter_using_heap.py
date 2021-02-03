class Twitter:

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.users = {}
        self.time = 0
        # userId- key
        # value: set()
        
        # tweet [(tweetid,userid)]-> order it as well, will use stack
        

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        O(1)
        """
        if(userId in self.users.keys()):
            self.users[userId]['tweet'].append((self.time,tweetId))
            self.time += 1
        else:
            self.users[userId] = {'followers':set([userId]), 'tweet':[(self.time,tweetId)]}
            self.time += 1
            

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        
        O(nlogk) worst case where n is total number of tweets
        """
        if(not userId in self.users.keys()):
            return []
        
        hp = []
        op = deque([])
        '''
        for i in self.users[userId]['tweet']:
            heapq.heappush(hp, i)
            if(len(hp)>10):
                heapq.heappop(hp)
        '''
        for j in self.users[userId]['followers']:
            for i in self.users[j]['tweet']:
                heapq.heappush(hp, i)
                if(len(hp)>10):
                    heapq.heappop(hp)
        
        while(len(hp)>0):
            op.appendleft(heapq.heappop(hp)[1])
        
        return op
                
    def follow(self, followeeId: int, followerId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        O(1)
        """
        if(followeeId in self.users.keys()):
            self.users[followeeId]['followers'].add(followerId)
        else:
            self.users[followeeId] = {'followers':set([followeeId, followerId]), 'tweet':[]}
        
        if(not followerId in self.users.keys()):
            self.users[followerId] = {'followers':set([followerId]), 'tweet':[]}

    def unfollow(self, followeeId: int, followerId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        O(1)
        """
        if(followeeId == followerId):
            return
        if(followeeId in self.users.keys()):
            try:
                self.users[followeeId]['followers'].remove(followerId)
            except:
                pass
        else:
            self.users[followeeId] = {'followers':set([followeeId]), 'tweet':[]}
        
        if(not followerId in self.users.keys()):
            self.users[followerId] = {'followers':set([followerId]), 'tweet':[]}



# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)
