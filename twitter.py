"""
// Time Complexity : O(n) // newfeed nlogk(k is constant)
// Space Complexity : O(m+ n) //tweets + users
// Did this code successfully run on Leetcode : No
// Any problem you faced while coding this : No
// Your code here along with comments explaining your approach
Algorithm explanation
Using dictionary to track the tweets for users and also the users tracking
of following 

Newsfeed -> 
    Get the tweets for the user and followee users
    Use min heap on the tweets with negative timestamp to get the most recent
tweet at the top
"""

from collections import defaultdict
import heapq

class Tweet:
    def __init__(self,id,created_at):
        self.id = id
        self.created_at = created_at

class Twitter:
    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.tweets = defaultdict(list) #storing list of tweets for user
        self.users = defaultdict(set)  #storing the mapping of following for users
        self.timestamp = 0
        self.feed_size = 10

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        #follow self
        self.users[userId].add(userId)
        
        self.timestamp+=1
        self.tweets[userId].append(Tweet(tweetId,self.timestamp))

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        followd_ids = self.users[userId]
        tweet_q = []
        feeds = []
        if followd_ids:
            for user in followd_ids:
                tweets = self.tweets[user]
                if tweets:
                    i = 0
                    #heapify all the tweets
                    for tweet in tweets:
                        heapq.heappush(tweet_q,(-tweet.created_at,tweet.id))
        i = 0
        #fetch the top 10 tweets after heapifying on created_at
        while tweet_q and i < self.feed_size:
            ele = heapq.heappop(tweet_q)
            feeds.append(ele[1])
            i+=1
        
        return feeds
        
    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        # if followerId not in self.users:
        #     self.users[followerId].add(followerId)
        self.users[followerId].add(followeeId)
        

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId in self.users and followerId!=followeeId:
            self.users[followerId].discard(followeeId)
        

# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)