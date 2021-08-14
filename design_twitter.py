import collections
import heapq
#Time complexity : O(N)
#space complexity : O(N)
class Tweet:
    def __init__(self,timer,tid):
        self.tid = tid
        self.timer = timer

class Twitter:

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.timer = 0
        self.user_tweets_map = {}
        self.followees = {}

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        
        if userId not in self.user_tweets_map:
            self.follow(userId,userId)
            self.user_tweets_map[userId] = []
        self.timer+=1
        self.user_tweets_map[userId].append(Tweet(self.timer, tweetId))


    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        setattr(Tweet, "__lt__", lambda self, other: self.timer <= other.timer)
        pqueue = []
        if userId in self.followees:
            user_list = self.followees[userId]
            for user in user_list:
                if user in self.user_tweets_map:
                    user_tweets = self.user_tweets_map[user]
                    for tweet in user_tweets:
                        heapq.heappush(pqueue,(tweet.timer,tweet))
                        if len(pqueue)>10:
                            heapq.heappop(pqueue)
            if pqueue:
                result = []
                while pqueue:
                    (t,tid) = heapq.heappop(pqueue)
                    result.append(tid.tid)
                print(result)
                return result[::-1]
   
        else:
            return []
        

    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId not in self.followees:
            self.followees[followerId] = set()
        self.followees[followerId].add(followeeId)
            

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId not in self.followees:
            return
        if followeeId not in self.followees[followerId]:
            return
        if followerId == followeeId:
            return
        self.followees[followerId].remove(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)