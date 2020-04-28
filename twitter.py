"""
// Time Complexity : O(1)
// Space Complexity : O(n + m) n-> userss, m-> tweets
// Did this code successfully run on Leetcode : No
// Any problem you faced while coding this : 
I think my condition for updating the newsfeed might be wrong
// Your code here along with comments explaining your approach
Algorithm explanation
Using dictionary to track the tweets for users and also the users tracking
of following 
"""
from collections import defaultdict

class Twitter:
    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.tweets = defaultdict(list) #storing list of tweets for user
        self.users = defaultdict(int)  #storing the mapping of following for users
        

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        self.tweets[userId].append(tweetId)
        

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        news = []
        for tweet in self.tweets[userId][:10]:
            if userId in self.users:
                news.append(tweet)
        return news
        
    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        self.users[followeeId] = followerId
        

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        del self.users[followeeId]
        

# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)