# O(1) TIME FOR FOLLOW, UNFOLLOW, POST TWEET, GETNEWSFEED

from collections import heapq
class Tweet:
    def __init__(self,t_id,t_time):
        self.t_id = t_id
        self.t_time = t_time


class Twitter:
    
    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.relations = {}
        self.tweets = {}
        self.timestamp = 0
        

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        if userId not in self.tweets:
            self.tweets[userId] = []
            self.follow(userId,userId)
        self.tweets[userId].append(Tweet(tweetId,self.timestamp))
        self.timestamp += 1
        
        
        

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        heap = []
        if userId in self.relations:
            for user in self.relations[userId]:
                if user in self.tweets:
                    for tweet in self.tweets[user]:
                        if len(heap) < 10:
                            heapq.heappush(heap,(tweet.t_time,tweet.t_id))
                        else:
                            if tweet.t_time > heap[0][0]:
                                heapq.heappop(heap)
                                heapq.heappush(heap,(tweet.t_time,tweet.t_id))
        recent_tweets = []
        while heap:
            current_tweet = heapq.heappop(heap)
            recent_tweets.append(current_tweet[1])
        return reversed(recent_tweets)
        

    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId not in self.relations:
            self.relations[followerId] = set()
            self.relations[followerId].add(followerId)
            
        self.relations[followerId].add(followeeId)
        

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId not in self.relations or followerId == followeeId :
            return
        if followeeId in self.relations[followerId]:
            self.relations[followerId].remove(followeeId)
        


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)