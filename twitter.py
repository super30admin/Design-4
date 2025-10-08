# Time Complexity : O(1) amortized for postTweet, O(n) for getNewsFeed where n is number of followees and k is 10
# Space Complexity : O(u + t) where u is number of users and t is number of tweets
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No

# Your code here along with comments explaining your approach:
# I am using two hashmaps, one to store the followees of each user and another to store the tweets of each user.
# For posting a tweet, I am adding the tweet to the user's tweet list and also making the user follow themselves.
# For getting the news feed, I am using a min heap to store the top 10 tweets from the user's followees.
# I am iterating through the followees and adding their tweets to the min heap.
# If the size of the min heap exceeds 10, I am removing the tweet with the smallest timestamp.
# Finally, I am returning the tweet ids in reverse order to get the most recent tweets first.
# For following a user, I am adding the followee to the follower's followee set.
# For unfollowing a user, I am removing the followee from the follower's followee set.

from typing import List
import heapq     
class Tweet:
    def __init__(self,tId,time):
        self.tweetId = tId 
        self.timeStamp = time
class Twitter:
    def __init__(self):
        self.followeesMap = {}
        self.tweetsMap = {}
        self.time = 0        

    def postTweet(self, userId: int, tweetId: int) -> None:
        if userId not in self.tweetsMap:
            self.tweetsMap[userId] = []
        tweet = Tweet(tweetId,self.time)
        self.time += 1
        self.tweetsMap[userId].append(tweet)
        self.follow(userId,userId)

    def getNewsFeed(self, userId: int) -> List[int]:
        minHeap = []
        follow = self.followeesMap.get(userId)
        if follow:
            for i in follow:
                tweets = self.tweetsMap.get(i)
                if tweets:
                    for j in range(len(tweets) -1 ,max(len(tweets)-10-1,-1),-1):
                        tweet = tweets[j]
                        heapq.heappush(minHeap,(tweet.timeStamp,tweet))
                        if len(minHeap) > 10:
                            heapq.heappop(minHeap)
        result = []
        while minHeap:
            result.append(heapq.heappop(minHeap)[1].tweetId)
        return result[::-1]



        

    def follow(self, followerId: int, followeeId: int) -> None:
        if followerId not in self.followeesMap:
            self.followeesMap[followerId] = set()
        self.followeesMap[followerId].add(followeeId)
    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId in self.followeesMap:
            self.followeesMap[followerId].discard(followeeId)
        
        


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)