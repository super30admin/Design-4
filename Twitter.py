#355. Design Twitter
"""
Time Complexity : O(users + tweets)
Space Complexity : O(users * tweets)
"""
class Tweet:
    def __init__(self, tweetId, timeStamp):
        self.tweetId = tweetId
        self.timeStamp = timeStamp

class Twitter:

    def __init__(self):
        self.timeStamp = 0
        self.maxFeeds = 10
        self.userTweets = {}
        self.userFollows = {}


    def isFirstTime(self, userId: int)-> None:
        # if user has not tweetted before create his list of tweets
        if(userId not in self.userTweets):
            self.userTweets[userId] = []

        # if user does not follow anyone the create his set and add it follows dict
        if(userId not in self.userFollows):
            self.userFollows[userId] = set()
            self.userFollows[userId].add(userId)

    def postTweet(self, userId: int, tweetId: int) -> None:

        # check if is first time user
        self.isFirstTime(userId)

        # create Tweet object a timeStamp of tweet
        tweet = Tweet(tweetId,self.timeStamp)

        # add tweet to userTweet
        self.userTweets[userId].append(tweet)

        # increase timeStamp
        self.timeStamp += 1

    def getNewsFeed(self, userId: int) -> List[int]:

        lessThan = lambda x,y :x.timeStamp < y.timeStamp
        Tweet.__lt__ = lessThan

        self.isFirstTime(userId)


        #   add all tweets of user and its followers
        tweetsList = []
        for followerId in self.userFollows[userId]:
            self.isFirstTime(followerId)
            tweetsList.extend(self.userTweets[followerId])

        # sort list as per descending order of timestamp     
        tweetsList.sort(reverse = True)

        #   put only first 10 feeds
        tweetIds = []
        for i in range(self.maxFeeds):
            if i < len( tweetsList):
                tweetIds.append(tweetsList[i].tweetId)

        return tweetIds


    def follow(self, followerId: int, followeeId: int) -> None:

        self.isFirstTime(followerId)
        self.isFirstTime(followeeId)
        self.userFollows[followerId].add(followeeId)


    def unfollow(self, followerId: int, followeeId: int) -> None:
        self.isFirstTime(followerId)
        self.isFirstTime(followeeId)
        if followerId != followeeId :
            self.userFollows[followerId].discard(followeeId)


 # Your Twitter object will be instantiated and called as such:
 # obj = Twitter()
 # obj.postTweet(userId,tweetId)
 # param_2 = obj.getNewsFeed(userId)
 # obj.follow(followerId,followeeId)
 # obj.unfollow(followerId,followeeId) 
