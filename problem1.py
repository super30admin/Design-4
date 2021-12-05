class Twitter:

    def __init__(self):
        
        self.tweets=dict() # UserIds and list of tweets
        self.followers=dict() #Followerids and followeeids
        self.all_tweets=[] #UserIds, tweetTds
        

    def postTweet(self, userId: int, tweetId: int) -> None:
        
    #Time O(1) adding tweets to tweets and all_tweets
        if userId not in self.tweets:
            self.tweets[userId]=[]
            
        self.tweets[userId].append(tweetId)
        
        self.all_tweets.append((userId,tweetId))
        

    def getNewsFeed(self, userId: int) -> List[int]:
        res=[]
     #Time O(n)
     #Recent tweets   
        if userId in self.followers:
            followers=self.followers[userId]
            followers.add(userId)
            
            for i in self.all_tweets[::-1]:
                if i[0] in followers:
                    res.append(i[1])
                if len(res)==10:
                    break
                    
        elif userId in self.tweets:
            res=self.tweets[userId][::-1][:10]
            
        return res
        

    def follow(self, followerId: int, followeeId: int) -> None:
        
        #Time O(1)
        #Adding followeeId to followers
        if followerId not in self.followers:
            self.followers[followerId]=set()
            
        self.followers[followerId].add(followeeId)
        

    def unfollow(self, followerId: int, followeeId: int) -> None:
      
    #Time O(1)
    #Removing value and key from followers
        if followerId in self.followers:
            if followeeId in self.followers[followerId]:
                self.followers[followerId].remove(followeeId)
                
            if len( self.followers[followerId])==0:
                self.followers.pop(followerId)
                
