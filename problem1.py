# Complexity: 
# PostTweet: Time O(1), Space O(1) per tweet; getNewsFeed: Time O(Nf log Nf), Space O(Nf); follow: Time O(1), Space O(1); unfollow: Time O(1), Space O(1)
class Twitter:

    class Tweet: 
        def __init__(self, tweetId, timestamp):
            self.tweetId = tweetId
            self.timestamp = timestamp

    def __init__(self):
        self.userMap = {}
        self.tweetMap = {}
        self.time = 0
        

    def postTweet(self, userId: int, tweetId: int) -> None:
        tweet = Twitter.Tweet(tweetId, self.time)
        self.time += 1
        if userId not in self.tweetMap:
            self.tweetMap[userId] = []
        
        self.tweetMap[userId].append(tweet)
        if userId not in self.userMap:
            self.userMap[userId] = set()
        self.userMap[userId].add(userId)

    def getNewsFeed(self, userId: int) -> List[int]:
        lst = []
        res = []
        followees = self.userMap.get(userId)
        if followees is not None: 
            for followee in followees:
                tweets = self.tweetMap.get(followee)
                if tweets is not None: 
                    for tweet in tweets: 
                        lst.append(tweet)
        lst.sort(key=lambda x: x.timestamp, reverse=True)
        return [t.tweetId for t in lst[:10]]

    def follow(self, followerId: int, followeeId: int) -> None:
        if followerId not in self.userMap:
            self.userMap[followerId] = set()
        self.userMap[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId not in self.userMap:
            return 
        self.userMap[followerId].discard(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)
