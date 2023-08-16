#Time Complexity for getNewsFeed method: O(Nlogk) k=>10(elements in heap) N=> no. of users*no of tweets. logk is for sorting
#Space Complexity: O(N)
#Firstly we start with larger(main) class twitter. It has 2 members-followersmap and Tweets(both hashmap)
class Twitter:
    global count
    class Tweet:
        def __init__(self,tid, time):
            self.tweetID=tid
            self.time=time

    def __init__(self):
        self.followersMap={} #user-> whom they follow
        self.Tweets={} #user-> their tweets
        self.count=0

    def postTweet(self, userId: int, tweetId: int) -> None:
        if userId not in self.followersMap:
            self.follow(userId,userId)
        if userId not in self.Tweets:
            self.Tweets[userId]=set()
        self.count=self.count+1 #For time
        tweet=self.Tweet(tweetId,self.count)
        self.Tweets[userId].add(tweet)

    def getNewsFeed(self, userId: int) -> List[int]:
        result=[]
        minheap=[]
        if userId in self.followersMap:
            for user in self.followersMap[userId]:
                if user in self.Tweets:
                    for tw in self.Tweets[user]:
                        heapq.heappush(minheap,(tw.time,tw.tweetID))
                        if len(minheap)>10:
                            heapq.heappop(minheap)
        while minheap:
            time,tweetID = heapq.heappop(minheap)
            result.insert(0,tweetID) #heap as 1-2-3-4.....10 and time 1 will be popped first but it has to be added last so moving right using index 0
        return result

    def follow(self, followerId: int, followeeId: int) -> None:
        if followerId not in self.followersMap:
            self.followersMap[followerId]=set()
            self.followersMap[followerId].add(followerId) #user has to follow themselves
            
        if followeeId not in self.followersMap[followerId]:
            self.followersMap[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId in self.followersMap and followerId!=followeeId and followeeId in self.followersMap[followerId]: 
            self.followersMap[followerId].remove(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)