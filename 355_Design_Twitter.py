# Time Complexity : 
#   postTweet - O(1)
#   getNewsFeed - O(m + n) [n = total number of tweets, m = number of users]
#   follow - O(1)
#   unfollow - O(1)
# Space Complexity : O(n + m*m) [n = number of tweets , m = number of users]
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No
from collections import defaultdict
from heapq import heappush, heappop
class Twitter:

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.user_to_followers = defaultdict(set)
        self.user_to_tweets = defaultdict(list)
        self.heap_capacity = 10
        self.time_stamp = 0
        
    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        self.follow(userId, userId)
        self.user_to_tweets[userId].append((self.time_stamp, tweetId))
        self.time_stamp+=1
        

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        heap = []
        fIds = self.user_to_followers.get(userId)
        if fIds:
            for fid in fIds:
                ftweets = self.user_to_tweets.get(fid)
                if ftweets:
                    for curr_tweet in ftweets:
                        heappush(heap, curr_tweet)
                        if len(heap) > self.heap_capacity:
                            heappop(heap)
        
        res = []
        while heap:
            time, tweetid = heappop(heap)
            res.append(tweetid)
        return [curr for curr in reversed(res)]

    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        self.user_to_followers[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId in self.user_to_followers and followeeId in self.user_to_followers[followerId] and followeeId!= followerId:
            self.user_to_followers[followerId].remove(followeeId)
