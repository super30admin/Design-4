"""
Implemented referring to the solution
"""
import heapq as heap
class Tweet(object):

    def __init__(self, tweetId, createdAt):

        self.tweetId=tweetId
        self.createdAt=createdAt

class Twitter(object):

    def __init__(self):

        self.tweets=dict()
        self.following=dict()
        self.timestamp=0

    def postTweet(self, userId, tweetId):

        self.follow(userId,userId)
        if userId not in self.tweets:
            self.tweets[userId]=[]
        self.timestamp+=1
        self.tweets[userId].append(Tweet(tweetId, self.timestamp))

    def getNewsFeed(self, userId):

        follows=self.following.get(userId)
        pq=[]
        moreThan = lambda x, y: x.createdAt<y.createdAt
        Tweet.__lt__ = moreThan
        if follows is not None:
            for user in follows:
                usertweets=self.tweets.get(user)
                if usertweets is not None:
                    for tweet in usertweets:
                        heap.heappush(pq,tweet)
                        if len(pq)>10: 
                            heap.heappop(pq)      
        result=[]

        while len(pq)!=0:
            result.insert(0,heap.heappop(pq).tweetId)
        return result

    def follow(self, followerId, followeeId):

        if followerId not in self.following:
            self.following[followerId]=set()
        self.following[followerId].add(followeeId)

    def unfollow(self, followerId, followeeId):

        if followerId in self.following and followerId!= followeeId:
            if followeeId in self.following[followerId]:
                self.following[followerId].remove(followeeId)  

# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)