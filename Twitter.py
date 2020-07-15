#Time Complexity : O(n * m * log(k)) --> where n is number of followers of a current user and m is the average number of tweets by each user being followed and k is the feedsize
#Space Complexity : O(m) --> where m is number of tweets
# Did this code successfully run on Leetcode: Yes
# Any problem you faced while coding this : No
# we will use 2 dictionaries, one for followers and one for tweets. In followers dict, user who are followers are stored.
# In tweets dict, the tweetid and tweet timestamp is . We will all the followers ids to followers dict similarly add tweets to dict. One we get a request of getFeed, we will iterate
#through the followed users and retrieve their tweets in descending order of tweet timestamps using min heap.

import heapq 
class Tweet:
    def __init__(self,ids,timestamp):
        self.ids = ids
        self.timestamp = timestamp
    def __lt__(self, other):
        return self.timestamp < other.timestamp
class Twitter:
    

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.tweets = collections.defaultdict(list)
        self.followers = collections.defaultdict(set)
        self.time = 0
        self.feedsize = 10

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        self.follow(userId,userId)
        
        temp = Tweet(tweetId,self.time+1)
        self.tweets[userId].append(temp)
        self.time += 1

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        ftweets = []
        res = []
        for i in self.followers[userId]:
            for k in self.tweets[i]:
                heapq.heappush(ftweets, k)
                if len(ftweets)>self.feedsize:
                    heapq.heappop(ftweets)
        while len(ftweets)>0:
            res.append(heapq.heappop(ftweets).ids)
        return res[::-1]
        

    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        self.followers[followerId].add(followeeId)
            

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId in self.followers[followeeId] and followerId != followeeId:
            self.followers[followerId].remove[followeeId]
            
        
