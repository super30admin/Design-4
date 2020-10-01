# Time Complexity : O(1)-> postTweet(), follow(), unfollow(); O(n*m) where n= num of followers, m= num of tweets
# Space Complexity : O(numUsers+tweets)
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No

# Your code here along with comments explaining your approach

# import data structures based on design requirements
import heapq
from collections import defaultdict

#tweet class to store tweet info: only adding two major items, many more can be added later
class Tweet(obj):
    def __init__(self, userid, timestamp):
        self.userid = userid
        self.timestamp = timestamp

class Twitter:
    #feed size 10, initialize tweet and followers hashmap/set
    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.tweet = defaultdict(list)
        self.timestamp = 0
        self.followers = defaultdict(set)
        self.feedSize = 10
        
    #add your ID to followers list(follow yourself)
    #add new tweet to hashmap
    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        self.follow(userId, userId)
        newTweet = Tweet(tweetId, self.timestamp)
        self.timestamp += 1
        self.tweet[userId].append(newTweet)
        
    #make use of heap/priority queue to get most recent tweets while maintaining feed size
    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        heap = []
        output = []
        followerIDs = self.followers[userId]

        for f in followerIDs:
            #get all tweets
            tweets = self.tweet[f]

            for tweet in tweets:
                heapq.heappush(heap, (tweet.userid, tweet.timestamp))
                if len(heap)> self.feedSize:
                    heapq.heappop(heap)
        
        while heap:
            temp = heapq.heappop(heap)
            output.append(temp[1])
        return output[::-1]


    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        self.followers[followeeId].add(followeeId)
        

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        if followeeId in self.followers[followerId]:
            if followeeId != followerId:
                self.followers[followerId].remove(followeeId)
        


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)