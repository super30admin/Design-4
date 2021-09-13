#design Twitter 
#time complexity: O(N)
#space complexity: O(N)
class Tweet:
    def __init__(self, tweet_id, user_id):
        self.tweet_id = tweet_id
        self.user_id = user_id
        
class Twitter:

    def __init__(self):
        self.tweets = [] # list ofTweets
        self.follows = set () # set of user_id's (a,b) s.t user a follows user b

    def postTweet(self, userId: int, tweetId: int) -> None:
        new_tweet = Tweet(tweetId, userId)
        self.tweets.append(new_tweet)
        
    def getNewsFeed(self, userId: int) -> List[int]:
        news_feed = []
        i = len(self.tweets) - 1
        while len(news_feed) < 10:
            if i < 0:
                break
            if self.tweets[i].user_id == userId or (userId, self.tweets[i].user_id) in self.follows:
                news_feed.append (self.tweets[i].tweet_id)
            i-=1
        return news_feed

    def follow(self, followerId: int, followeeId: int) -> None:
        self.follows.add((followerId, followeeId))
        

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if (followerId, followeeId) in self.follows:
            self.follows.remove((followerId, followeeId))