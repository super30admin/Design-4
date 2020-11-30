class Tweet:
    def __init__(self, userId, tweetId, createdAt):
        self.userId = userId
        self.tweetId = tweetId
        self.createdAt = createdAt
        
class Twitter:
​
    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.time=0
        self.follow_ = collections.defaultdict(set)
        self.tweets_ = collections.defaultdict(list)
        
​
    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        
        if userId not in self.follow_:
            self.follow(userId, userId)
        self.time += 1
        tweet = Tweet(userId, tweetId, self.time)
        self.tweets_[userId].append(tweet)
        return
