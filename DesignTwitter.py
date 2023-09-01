#TC: O(1)
#SC: O(1)

class Tweet:

    def __init__(self, tweetId, timeStamp):
        self.tweetId = tweetId
        self.timeStamp = timeStamp


class Twitter_1:

    def __init__(self):
        
        self.timeStamp = 0
        self.userTweetsMap = {}
        self.userFollowsMap = {}
        self.maxFeeds = 10

    def __isFirstTime(self, userId: int) -> None:

        if (userId not in self.userTweetsMap):
            self.userTweetsMap[userId] = []

        if (userId not in self.userFollowsMap):
            self.userFollowsMap[userId] = set()
            self.userFollowsMap[userId].add(userId)

    def postTweet(self, userId: int, tweetId: int) -> None:
        

        self.__isFirstTime(userId)              
        tweet = Tweet(tweetId, self.timeStamp)    
        self.userTweetsMap[userId].append(tweet)    
        self.timeStamp += 1            

    def getNewsFeed(self, userId: int) -> List[int]:
        
        lessThan = lambda x, y: x.timeStamp < y.timeStamp
        Tweet.__lt__ = lessThan

        self.__isFirstTime(userId)    

        tweetsList = []

        for followerId in self.userFollowsMap[userId]:
            self.__isFirstTime(followerId)
            tweetsList.extend(self.userTweetsMap[followerId])

        tweetsList.sort(reverse=True)       

        tweetIds = []

        for i in range(self.maxFeeds):
            if i < len(tweetsList):
                tweetIds.append(tweetsList[i].tweetId)

        return tweetIds             

    def follow(self, followerId: int, followeeId: int) -> None:
        
        self.__isFirstTime(followerId)                    
        self.__isFirstTime(followeeId)                  
        self.userFollowsMap[followerId].add(followeeId)   

    def unfollow(self, followerId: int, followeeId: int) -> None:

        self.__isFirstTime(followerId)                         
        self.__isFirstTime(followeeId)                         
        if (followerId != followeeId):
            self.userFollowsMap[followerId].discard(followeeId) 
