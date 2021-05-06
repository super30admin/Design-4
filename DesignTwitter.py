import heapq

class Tweet:
    #time = 0
    def __init__(self,time,tweetId):
        self.time = time
        # self.time = Tweet.time
        # Tweet.time += 1        
        self.tweetId = tweetId
        

class Twitter:

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.followers = {}
        self.tweets = {}
        self.time = 1
        

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        self.follow(userId,userId)
        t = Tweet(self.time,tweetId)
        self.time +=1
        if userId not in self.tweets:
            self.tweets[userId] = [t]
        else:
            self.tweets[userId].append(t)

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        users = set()
        if userId in self.followers:
            users = self.followers[userId]
        heap = []
        
        if users:
            for user in users:
                if user in self.tweets:
                    tws = self.tweets[user]
                    if tws:
                        for tw in tws:
                            heappush(heap,(tw.time ,tw.tweetId))
                            if len(heap)>10:
                                print(heappop(heap))

        #result = set()
        print(heap)
        result = []
        while heap:
            result.append(heappop(heap)[1])
        return reversed(result)

    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        if  followerId not in self.followers:
            self.followers[followerId] = set()
        self.followers[followerId].add(followeeId)
            

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId in self.followers:
            if followeeId in self.followers[followerId]:
                self.followers[followerId].remove(followeeId)
        return

# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)
