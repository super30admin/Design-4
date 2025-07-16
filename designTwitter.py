import heapq
from collections import defaultdict,deque

class Twitter:

    def __init__(self):
        self.timestamp=0
        self.user_tweets=defaultdict(deque)
        self.followees=defaultdict(set)
    
    def postTweet(self,userId,tweetId):
        self.timestamp +=1
        self.user_tweets[userId].appendleft((self.timestamp,tweetId))
        if len(self.user_tweets[userId])>10:
            self.user_tweets[userId].pop()

    def getNewsFeed(self,userId):
        heap=[]
        users=self.followees[userId]|{userId}

        for uid in users:
            for tweet in self.user_tweets[uid]:
                heapq.heappush(heap,tweet)
                if len(heap)>10:
                    heapq.heappop(heap)
        
        return[tweetId for _,tweetId in sorted(heap,reverse=True)]
    
    def follow(self,followerId,followeeId):
        if followerId !=followeeId:
            self.followees[followerId].add(followeeId)
    
    def unfollow(self,followerId,followeeId):
        self.followees[followerId].discard(followeeId)

twitter=Twitter()
twitter.postTweet(1,5)
print(twitter.getNewsFeed(1))
twitter.follow(1,2)
twitter.postTweet(2,6)
print(twitter.getNewsFeed(1))
twitter.unfollow(1,2)
print(twitter.getNewsFeed(1))
twitter.unfollow(1,2)
print(twitter.getNewsFeed(1))
