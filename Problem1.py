#355. Design Twitter
import heapq
class Tweet:
    def __init__(self, id, time):
        self.tweetId = id
        self.time = time

class User:
    def __init__(self, id):
        self.userId = id
        self.follows = set()
        self.tweets = []



class Twitter:

    def __init__(self):
        self.users = {}
        self.timeStamp = 0

    def postTweet(self, userId: int, tweetId: int) -> None:
        if userId not in self.users:
            self.users[userId] = User(userId)
            self.users[userId].follows.add(userId)
        self.timeStamp += 1
        
        self.users[userId].tweets.append(Tweet(tweetId, self.timeStamp))
        

    def getNewsFeed(self, userId: int) -> List[int]:
        if userId not in  self.users:
            return []
        
        hq = []
        result = []
        # print(self.users)
        for followUser in self.users[userId].follows:
            print(followUser)
            tweets = self.users[followUser].tweets
            print(tweets)
            for i in range(len(tweets) - 1, max(-1, len(tweets) - 10 - 1), -1):
                tweet = tweets[i]
                heapq.heappush(hq,(tweet.time, tweet.tweetId))

                if len(hq) > 10:
                    heapq.heappop(hq)
        
        while hq:
            result.append(heapq.heappop(hq)[1])
        
        return result[::-1]
        
        

    def follow(self, followerId: int, followeeId: int) -> None:
        if followerId not in self.users:
            self.users[followerId] = User(followerId)
            self.users[followerId].follows.add(followerId)
        if followeeId not in self.users:
            self.users[followeeId] = User(followeeId)
            self.users[followeeId].follows.add(followeeId)
        
        self.users[followerId].follows.add(followeeId)
        

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId not in self.users:
            return
        if followeeId not in self.users:
            return
        if followeeId in self.users[followerId].follows:
            self.users[followerId].follows.remove(followeeId)
        

# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)