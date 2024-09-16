-------------------------------- Design Twitter ---------------------------------------------
# Time Complexity : O(NXL) number of followers and number of tweets for getNewsFeed for others its O(1)
# Space Complexity : O(2N)  for 2 dicts followers and tweets. Heap is not considered as we are storing only 10 tweets.
# Did this code successfully run on Leetcode: Yes
# Any problem you faced while coding this : No
# 
# Here 2 dictionaries, one for followers and one for tweets. In followers dict, the follower of particular users are stored.
# In tweets, the tweetid and tweet timestamp is stored based on the user. One we get a request of getFeed, we will iterate
#through the followed users and retrieve their tweets in descending order of tweet timestamps using heap.


import heapq
class Tweet:
    def __init__(self, id, time):
        self.id = id
        self.time = time
        
    def __lt__(self, other):
        return self.time < other.time
        

class Twitter:

    def __init__(self): # Initialize the Data structures dict for followers and tweets
        """
        Initialize your data structure here.
        """
        self.t = 0
        self.feedSize = 10
        self.followers = collections.defaultdict(set)
        self.tweets = collections.defaultdict(list)
        
        
        
        

    def postTweet(self, userId: int, tweetId: int) -> None: # Post the tweet by updating tweets dict and adding userId to follow same user Id
    
        """
        Compose a new tweet.
        """
        temp = Tweet(tweetId, self.t+1)
        self.tweets[userId].append(temp)
        self.follow(userId, userId)
        self.t+=1
        
        
    

    def getNewsFeed(self, userId: int) -> List[int]: # Get the news feed by iterating through dict to get followers as well as 
        ""
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        ftweets = []
        res = []
        for i in self.followers[userId]:
            for k in self.tweets[i]:
                heapq.heappush(ftweets, k)
                if len(ftweets)>self.feedSize:
                    heapq.heappop(ftweets)
                                
        while len(ftweets)>0:
            res.append(heapq.heappop(ftweets).id)
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
        if followerId != followeeId and followeeId in self.followers[followerId]:
            self.followers[followerId].remove(followeeId)