from collections import deque
import heapq


class Tweet:
    def __init__(self, tweetId, t_timestamp) -> None:
        self.tweetId=tweetId
        self.t_timestamp=t_timestamp

class Twitter:

    def __init__(self):
        self.tweets={}
        self.following={}
        self.timestamp=0

    def postTweet(self, userId: int, tweetId: int) -> None:
        self.follow(userId, userId)
        if(userId not in self.tweets):
            self.tweets[userId]=[]
        self.timestamp+=1
        self.tweets[userId].append(Tweet(tweetId, self.timestamp))

    def getNewsFeed(self, userId: int) -> List[int]:
        user_follows=self.following.get(userId)
        tweet_queue=[]
        heapq.heapify(tweet_queue)
        if(user_follows!=None):
            for i in user_follows:
                tweet_by_user=self.tweets.get(i)
                if(tweet_by_user !=None):
                    for j in tweet_by_user:
                        heapq.heappush(tweet_queue, j.t_timestamp)
                        if(len(tweet_queue)>10):
                            heapq.heappop(tweet_queue)
        feed_tweets=[]
        while(len(tweet_queue)):
            feed_tweets.append(heapq.heappop(tweet_queue))
        return feed_tweets


        pass

    def follow(self, followerId: int, followeeId: int) -> None:
        if(followerId not in self.following):
            self.following[followerId]=set()
        self.following[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if(followerId in self.following and followerId != followeeId):
            if(followeeId in self.following[followerId]):
                self.following[followerId].remove(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)