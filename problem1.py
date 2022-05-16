#Design twitter


# // Time Complexity :everything O(1) except getnewsfeed which is O(n*k) where n is users followed and k is their tweets
# // Space Complexity : O(1)
# // Did this code successfully run on Leetcode :yes
# // Any problem you faced while coding this :had difficult time trying to implement min heap in python by passing tweet element, but figured out after some time

from collections import defaultdict
import heapq

class Twitter:
    
    
    class Tweet():
        def __init__(self, tweet_id, time):
            self.id= tweet_id
            self.time =time
            
        
    def __init__(self):
        self.users = defaultdict(set)
        self.tweet = defaultdict(list)
        self.time = 0
        

    def postTweet(self, userId: int, tweetId: int) -> None:
        self.tweet[userId].append(self.Tweet(tweetId,self.time))        
        self.time+=1
        
        

    def getNewsFeed(self, userId: int) :
        self.follow(userId, userId)
        followers = self.users[userId]
        tweets=[]
        for i in followers:
            for j in self.tweet[i]:
                heapq.heappush(tweets, (j.time, j.id))
                if(len(tweets))>10:
                    heapq.heappop(tweets)
        res=[]
        size=len(tweets)
        while len(res)!=size:
            p=heapq.heappop(tweets)
            res.append(p[1])
        return res[::-1]
            
        
        

    def follow(self, followerId: int, followeeId: int) -> None:
        self.users[followerId].add(followeeId)
        

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if(followerId in self.users and followerId!=followeeId):
            self.users[followerId].remove(followeeId)
        


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)