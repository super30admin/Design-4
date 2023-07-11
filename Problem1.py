#Time complexity is O(nlogk)
#Space complexity is O(1)
#Code ran successfully on leetcode
#No issues faced while coding
import heapq
#Creating a tweet object which contains tweetid and time
class Tweet():
    def __init__(self,tid=0,time=0):
        self.id=tid
        self.time=time

class Twitter(object):

    def __init__(self):
        #Initializing two hashmaps tweets and followed
        #Tweets contains setails of tweets posted by a particular user
        #Followed hashmap contains details of users following other users
        #times will be the time at which the tweet has been posted
        self.tweets={}
        self.followed={}
        self.times=0

    def postTweet(self, userId, tweetId):
        """
        :type userId: int
        :type tweetId: int
        :rtype: None
        """
        #If userId is not there in the tweets hashmap
        #User follows the same user
        if(userId not in self.tweets):
            self.tweets[userId]=[]
            self.follow(userId,userId)
        #We are appending the tweet object for the tweets that a particular user posts
        self.tweets[userId].append(Tweet(tweetId,self.times))
        #Here we re incrementing the time
        self.times+=1
        
        

    def getNewsFeed(self, userId):
        """
        :type userId: int
        :rtype: List[int]
        """
        #Creating a priority queue pq
        pq=[]
        #Here we are implementing min heap in case of tweet object
        moreThan = lambda x, y: x.time<y.time
        Tweet.__lt__ = moreThan
        #Here we are getting list of users followed by a particular userId
        followeds = self.followed.get(userId, [])
        if(followeds is not None):
            for fid in followeds:
                #For each followed person, we will be retriving tweets
                fTweets = self.tweets.get(fid, [])
                if(fTweets!=None):
                    for fTweet in fTweets:
                        #We will be pushing the tweets into the heap
                        heapq.heappush(pq, fTweet)
                        if len(pq) > 10:
                            #If lenght is greater than 10, we will pop the heap
                            heapq.heappop(pq)
        #We willbe creating an result list and we will be appending all the values in the heap and return them in reverse order
        result=[]
        while(len(pq)):
            x=heapq.heappop(pq)
            result.append(x.id)
        return list(result[::-1])
        

    def follow(self, followerId, followeeId):
        """
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        #Here we will be updating the followed hashmap
        if(followerId not in self.followed):
            self.followed[followerId]=[]
        if(followeeId not in self.followed[followerId]):
            self.followed[followerId].append(followeeId)

        

    def unfollow(self, followerId, followeeId):
        """
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        #Here we will be updating the followed hashmap
        if(followerId in self.followed and followeeId!=followerId and followeeId in self.followed[followerId]):
            self.followed[followerId].remove(followeeId)

        


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)