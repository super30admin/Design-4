import time
class Tweet:
    def __init__(self, tweetId, time):
        self.tweetId = tweetId
        self.timestamp = time
class Twitter:

    def __init__(self):
        self.followers_map = defaultdict(set)
        self.user_tweets = defaultdict(list)

    def postTweet(self, userId, tweetId):
        newTweet = Tweet(tweetId, time.time())
        self.user_tweets[userId].append(newTweet)

    def getNewsFeed(self, userId):
        all_tweets = []
        all_tweets += self.user_tweets[userId]
        follow_list = self.followers_map[userId]
        for user in follow_list:
            all_tweets += self.user_tweets[user]
        
        all_tweets.sort(key = lambda r: r.timestamp, reverse=True)
        news_feed = all_tweets
        if len(all_tweets)>10:
            news_feed = all_tweets[0:10]
        tweetIds = [x.tweetId for x in news_feed]
        return tweetIds

    def follow(self, followerId, followeeId):
        if followeeId not in self.followers_map[followerId]:
            self.followers_map[followerId].add(followeeId)
        

    def unfollow(self, followerId, followeeId):
        if followeeId in self.followers_map[followerId]:
            self.followers_map[followerId].remove(followeeId)
        
        


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)