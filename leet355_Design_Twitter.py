# Solution

# // Time Complexity : follow - O(1)
#                      postTweet - O(1)
#                      getNewsFeed - O(n) it is actually O((n*10)log10), where n is number of users and we add 10 tweets for each
#                      user to priority queue and each insert will be log10 since size of priority queue will be 10. Since 10 is
#                      constant we can consider it O(n)
# // Space Complexity : O(10)
# // Did this code successfully run on Leetcode : 
# // Any problem you faced while coding this : 


# // Your code here along with comments explaining your approach
# Approach is have a nexted dictionary to keep all the users a particular user is following. Have a list inside dictionary
# to track the tweets posted by each user. For each tweet create and object with createdAt time to be stored. Now have a
# prioirty queue of size 10 so that when we are trying to getnewsfeed, we will have the 10 recent in queue and rest will be
# popped, so we can just pop those 10 into result.
from queue import PriorityQueue
class Twitter:

    class tweet:
        def __init__(self,tweetId,createdAt):
            self.tweetId = tweetId
            self.createdAt = createdAt

    def __init__(self):
        self.followList = {}
        self.tweetList = {}
        self.count = +1

    def postTweet(self, userId, tweetId):
        self.follow(userId,userId)
        if userId in self.tweetList:
            self.tweetList[userId].append(self.tweet(tweetId,self.count))
            self.count += 1
        else:
            self.tweetList[userId] = [self.tweet(tweetId,self.count)]
            self.count += 1
        

    def getNewsFeed(self, userId):
        newsFeed = PriorityQueue(11)

        if userId in self.followList:
            for userName in self.followList[userId]:
                if userName in self.tweetList:
                    n = len(self.tweetList[userName])
                    if n<10:
                        end = -1
                    else:
                        end = n-11
                    for i in range(n-1,end,-1):
                        newsFeed.put((self.tweetList[userName][i].createdAt,self.tweetList[userName][i].tweetId))
                        if newsFeed.qsize() > 10:
                            newsFeed.get()
        result = [0 for _ in range(newsFeed.qsize())]
        for i in range(newsFeed.qsize()-1,-1,-1):
            result[i] = newsFeed.get()[1]
        
        return result
        

    def follow(self, followerId, followeeId):
        if followerId in self.followList:
            if followeeId not in self.followList[followerId]:
                self.followList[followerId].add(followeeId)
        else:
            self.followList[followerId] = set()
            self.followList[followerId].add(followerId)
            self.followList[followerId].add(followeeId)
        

    def unfollow(self, followerId, followeeId):
        if followerId in self.followList:
            if followeeId in self.followList[followerId]:
                self.followList[followerId].remove(followeeId)
        

# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)