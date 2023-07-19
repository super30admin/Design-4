"""
Problem : 1

Time Complexity : O(n)
Space Complexity : O(n^2)

Did this code successfully run on Leetcode : Yes
Any problem you faced while coding this : No


"""

# Design Twitter

class Tweet(object):
    def __init__(self,tid,time):
        self.tid=tid
        self.createdAt=time

class Twitter(object):

    def __init__(self):
        self.tweets={}
        self.followed={}
        self.time=0


    def postTweet(self, userId, tweetId):
        """
        :type userId: int
        :type tweetId: int
        :rtype: None
        """
        if userId not in self.tweets:
            self.tweets[userId]=[]
            self.follow(userId,userId)
        newtweet=Tweet(tweetId,self.time)
        self.tweets[userId].append(newtweet)
        self.time+=1
        
        

    def getNewsFeed(self, userId):
        """
        :type userId: int
        :rtype: List[int]
        """
        pq=[]
        heapq.heapify(pq)
        followeds=self.followed.get(userId,set())
        for fid in followeds:
            fTweets=self.tweets.get(fid,[])
            if fTweets:
                for fTweet in fTweets:
                    heapq.heappush(pq,[fTweet.createdAt,fTweet])
                    if len(pq)>10:
                        heapq.heappop(pq)
        result=[]
        while pq:
            _,tweet=heapq.heappop(pq)
            result.append(tweet.tid)
        return result[::-1]

    def follow(self, followerId, followeeId):
        """
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        if followerId not in self.followed:
            self.followed[followerId]=set()
        self.followed[followerId].add(followeeId)
        

    def unfollow(self, followerId, followeeId):
        """
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        if followerId in self.followed and followerId!=followeeId:
            if followeeId in self.followed[followerId]:
                self.followed[followerId].remove(followeeId)
        


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)