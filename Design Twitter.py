# Time:- postTweet:- O(1), getNewsFeed:- nlog(10)= n, follow:- O(1) unfollow:- O(1)
# Space Complexity:- O(n)
# Approach:- Maintain a followers map- append followees to the followerid in the followerrs map, Maintain a timenow variable increment it everytime a new tweet is posted, Maintain a tweets map:- userid:- {timenow:tweetid}. For getting the newsfeed accumulate all the tweets from the users which the user is following and the user, get the 10 largest timestamp tweets from this collection, reverse it and print it.
import heapq
import collections
class Twitter:

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.timenow=0
        self.followers=collections.defaultdict(set)
        self.tweets=collections.defaultdict(list)
        

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        self.tweets[userId].append([self.timenow,tweetId])
        self.timenow+=1
        
        

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        collection_of_10_tweets=[]
        self.followers[userId].add(userId)
        for i in self.followers[userId]:
            collection_of_10_tweets.extend(self.tweets[i])
        heapq.heapify(collection_of_10_tweets)
        collections_of_10_tweets=heapq.nlargest(10,collection_of_10_tweets)
        res=[]
        for i in sorted(collections_of_10_tweets,reverse=True):
            res.append(i[1])
        return res
            
        
    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        self.followers[followerId].add(followeeId)
        
        

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        if followeeId in self.followers[followerId]:
            self.followers[followerId].remove(followeeId)
        


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)