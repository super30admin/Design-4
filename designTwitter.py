'''
Time Complexity:    O(nlogk) for getFeed where k = 10
Space Complexity:   we have created 2 dictionaries. Therefore 0(u) where u is users count; 0(t) where
t is tweet count
'''
 
import heapq

class Tweet:
    
    def __init__(self,tweetId,timestamp):
        self.tweetId = tweetId
        self.timestamp = timestamp
    
    # override the __It__ method
    def __lt__(self,tweet):
        return self.timestamp < tweet.timestamp
    
class Twitter:
    
    def __init__(self):
        
        # define data members
        self.tweets = {}
        self.users = {}
        
        # maintain timestamp  --- for tweets
        self.timestamp = 0
        
    def postTweet(self, userId: int, tweetId: int) -> None:
        # maintain the tweets in the tweets dictionary
        
        # chk if userId exist in users
        if userId not in self.users:
            self.users[userId] = {}
        
        # chk if userId exist in "tweets" dictionary
        if userId not in self.tweets:
            self.tweets[userId] = []
        
        # map tweet to the key
        # create an object of class tweet
        self.timestamp += 1
        objTweet = Tweet(tweetId,self.timestamp)
        
        # append the objClass to the list
        self.tweets[userId].append(objTweet)
        
    def getNewsFeed(self, userId: int) -> List[int]:
        
        # create a heapList, perform minHeap
        heapList = []
        
        # maintain threshold
        count = 0
        
        # iterate the tweets and take care of the threshold i.e. 10
        # base-case; user doesn't exist
        if userId not in self.users:
            return []
        
        getUserDict = (self.users[userId]).copy()
        getUserDict[userId] = 1
        
        # own-tweets + followee tweet
        for key in getUserDict:
            
            # get the tweets of the user
            
            tweetsList = None
            # case-- I can have a user, but might not have it's tweet
            if key in self.tweets:
                tweetsList = self.tweets[key]
            
            else:
                continue    
            
            # iterate the tweetsList
            for i in range(0,len(tweetsList)):
                heapq.heappush(heapList,tweetsList[i])
                count+=1
                
                # chk for threshold
                if count > 10:
                    # it's breached
                    # extract-min
                    heapq.heappop(heapList)
                    count-=1
        
        '''I have my top 10 latest feeds'''
        
        # return list of tweetId's
        returnList = []
        
        for i in range(0,len(heapList)):
            returnList.append(heapq.heappop(heapList).tweetId)
        
        returnList = returnList[-1::-1]
        
        return returnList
        
    def follow(self, followerId: int, followeeId: int) -> None:
        # followerId started following followeeId ----> add the key to the value dictionary
        
        # chk if the "followerId" exists in "self.users"
        if followerId not in self.users:
            # create the key-value pair
            self.users[followerId] = {}
        
        # add the followeeId to the value dictionary
        self.users[followerId][followeeId] = 1 # 1 is a dummy
        
    def unfollow(self, followerId: int, followeeId: int) -> None:
        
        # chk if the "followerId" exists in "self.users"
        if (followerId in self.users) and (followeeId in self.users[followerId]):
            # simply remove
            del self.users[followerId][followeeId]
        
# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)