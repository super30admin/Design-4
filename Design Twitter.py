# time complexity is o(following * tweets(log(k))), k is the number of posts user can see
# sapce o(u*t) + o(u*following)
# space is o(u * t), u, t is number of total users and total tweets
# space is o(u*following), one user can follow all other users, u, is number of total users
class Twitter:
    
    class Tweet:
        def __init__(self, tid, createdAt):
            self.id = tid
            self.createdAt = createdAt

    def __init__(self):
        self.user_to_tweets = dict() #space is o(u * t), u, t is number of total users                                                          and total tweets
        self.user_following = dict() # space is o(u*u), one user can follow all other                                                        users, u, is number of total users
        self.time = 0

    def postTweet(self, userId: int, tweetId: int) -> None: #time o(1)
        self.time += 1
        self.follow(userId, userId)
        if(userId not in self.user_to_tweets):
            self.user_to_tweets[userId] = list()
        self.user_to_tweets[userId].append(self.Tweet(tweetId, self.time))
        

    def getNewsFeed(self, userId: int) -> List[int]: #o(u * tlog10) + o(10)
        from heapq import heappush
        from heapq import heappop
        heap = []
        result = []
        lessthan = lambda x, y : x.createdAt < y.createdAt
        self.Tweet.__lt__ = lessthan
        if(userId in self.user_following):
            userset = self.user_following[userId]
            for user in userset: #o(users(following))
                if(user in self.user_to_tweets):
                    for t in self.user_to_tweets[user]:#o(t)
                        heappush(heap,t) #push Tweet object, o(log10)
                        if(len(heap) > 10):
                            heappop(heap)
        while(len(heap) > 0): #o(10)
            result.insert(0, heappop(heap).id)
        return result
            
                
        

    def follow(self, followerId: int, followeeId: int) -> None:#o(1)
        if(followerId not in self.user_following):
            self.user_following[followerId] = set()
        self.user_following[followerId].add(followeeId)
        

    def unfollow(self, followerId: int, followeeId: int) -> None:#o(1)
        if(followerId in self.user_following and followerId != followeeId):
            if(followeeId in self.user_following[followerId]):
                self.user_following[followerId].remove(followeeId)
        


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)