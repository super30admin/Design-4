# Time Complexity : O(n log 10) for get news feed
# Space Complexity : O(t + u) for get news feed where t is the number of tweets and u is the number of users
# Did this code successfully run on Leetcode : Yes
# Three line explanation of solution in plain english
# I store a map from user to followed users by user and a map from user to their tweets. When get news feed is called
# I colled all the followed users tweets put them in a min-heap and pop from heap when size exceeds 10. Then at the end
# pop the remaining 10 tweets and reverse and return them.

from heapq import heappush, heappop, heapreplace

class Tweet:
    def __init__(self, tweet, timestamp):
        self.tweet = tweet
        self.timestamp = timestamp
        
    def __eq__(self, other):
        return self.tweet and self.timestamp
    
    def __ne__(self, other):
        return not self.__eq__(other)
    
    def __lt__(self, other):
        return self.timestamp < other.timestamp
    
    def __gt__(self, other):
        return not self.__le__(other)
    
    def __hash__(self):
        return self.timestamp
    
class Twitter:

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.followed = defaultdict(lambda: set())
        self.tweets = defaultdict(lambda: set())
        self.timestamp = 0
        

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        self.timestamp += 1
        self.tweets[userId].add(Tweet(tweetId, self.timestamp))
        

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        self.followed[userId].add(userId)
        followed = list(self.followed[userId])
        all_tweets = [list(self.tweets[x]) for x in followed] 
        heap = []
        for tweets in all_tweets:
            for tweet in tweets:
                heappush(heap, tweet)
                if len(heap) > 10:
                    heappop(heap)
        
        feed = []
        while len(heap) > 0:
            feed.append(heappop(heap).tweet)
            
        return reversed(feed)

    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        self.followed[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        target_set = self.followed[followerId]
        if len(target_set) and followeeId in target_set and followerId != followeeId:
            target_set.remove(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)