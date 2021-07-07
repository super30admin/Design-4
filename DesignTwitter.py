class Tweet:
    """Time complexity-
    follow, unfollow, postTweet-O(1)
    getnewsfeed-O(nlog(k)) as using minheap, as k is constant i.e.(10)..so its O(n) where n is the number of tweets
    Space complexity-O(num users)
    number of users---->( as we created follwed map and it may contain in worst case as each user is following all other users)
    """
    def __init__(self, tweetId, time):
        self.tweetId=tweetId
        self.createdAt=time
class Twitter:

    def __init__(self):
        self.followed={}
        self.tweets={}
        self.time=0
        

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        self.follow(userId, userId)
        self.time+=1
        if userId not in self.tweets:
            self.tweets[userId]=[]
        self.tweets[userId].append(Tweet(tweetId, self.time))
        
        
        

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        result=[]
        feed=[]
"""       min heap using priority queue as we want to remove the ones which are older or have least time and keep the ones which are latest."""

        Tweet.__lt__=lambda x, y: x.createdAt < y.createdAt
        if userId in self.followed:
            following=self.followed[userId]
            if following:
                for i in following:
                    if i in self.tweets:
                        numtweets=self.tweets[i]
                        if numtweets:
                            for j in numtweets:
                                heapq.heappush(feed, j)
                                if len(feed)>10:
                                    heapq.heappop(feed)
        if feed:
            while feed:
                popped=heapq.heappop(feed)
                result.append(popped.tweetId)
        result.reverse()
        return result
            
        
        
        
        

    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId not in self.followed:
            self.followed[followerId]=set()
        self.followed[followerId].add(followeeId)
        
        

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId in self.followed and followerId != followeeId:
            if followeeId in self.followed[followerId]:
                self.followed[followerId].remove(followeeId)
            


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)