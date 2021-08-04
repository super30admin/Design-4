import heapq
class Tweet:
    def __init__(self,id,createdate):
        self.tweetid=id
        self.createdate=createdate
        
class Twitter:

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.tweetList=dict()
        self.followList=dict()
        
        self.time=0
        

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        tweet=Tweet(tweetId,self.time) 
        self.time+=1
        if userId not in self.tweetList:
            self.tweetList[userId]=[]
            if userId not in self.followList:
                self.followList[userId]=set()
            self.followList[userId].add(userId)
        self.tweetList[userId].append(tweet)
        

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        heapstore=[]
       
        if  userId in self.followList:
            for followers in self.followList[userId]:
                
                if followers in self.tweetList:
                    
                    for f in self.tweetList[followers]:
                        
                        heappush(heapstore,(f.createdate,f.tweetid))
                        if len(heapstore)>10:
                           
                            v=heappop(heapstore)
                            
        final=[]
        
        while(len(heapstore)>0):
            final.append(heappop(heapstore)[1])
        return final[::-1]
        

    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId not in self.followList:
            self.followList[followerId]=set()
        
        self.followList[followerId].add(followeeId)
        
        

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId in self.followList and followerId!= followeeId:
            if followeeId in self.followList[followerId]:
                self.followList[followerId].remove(followeeId)
        
    #Time complexity of GETNEWSFEED is O(Nlog k),N is total number of tweets by Self+followees. Other methods are O(1). K is 10 here so. O(N)
    #space O(k)=k=10
            


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)
