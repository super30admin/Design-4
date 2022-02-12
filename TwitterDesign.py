'''
Solution:
1.  In order to design Twitter, we need a global timestamp, HashMap to map users to tweets, Hashmap to map users to
    their followers.
2.  Just to avoid null checks, we call isFirstTime() method to initialize user's info if not present already.
Time and Space Complexities are explained are in each functionality separately
Extra Global Space: O((users * tweets) + (users * followers))
                    where tweets is maximum number of tweets a particular user has,
                    where followers is maximum number of followers a particular user has.
--- Passed all testcases successfully on Leetcode
'''


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
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        
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