# Time Complexity : O(1) for postTweet, follow, unfollow O(nlog10) for getNewsFeed
# Space Complexity : O(1) for postTweet, follow, unfollow. O(N) for getNewsFeed
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : I had trouble with the comparator.. I was wondering why 
# it was printing the wrong thing. 


# Your code here along with comments explaining your approach

class Tweet:
    def __init__(self, tID, createdAt):
        self.tID = tID
        self.createdAt = createdAt
        
    def __lt__(self, other):
        self.createdAt > other.createdAt
        
class Twitter:

    def __init__(self):
        """
        Initialize your data structure here.
        """
        #a map of user: it'sTweets
        self.tweets = defaultdict()
        #a map of users : it'sFollowers
        self.followed = defaultdict()
        self.time = 0 
        
    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        self.time += 1 

        self.follow(userId, userId)
        if userId not in self.tweets:
            self.tweets[userId] = []
        tweet = Tweet(tweetId, self.time)
        self.tweets[userId].append(tweet)
        
        

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        Tweet.__lt__ = (lambda a, b : a.createdAt < b.createdAt)
        heap = []
        if userId in self.followed:
            fids = self.followed[userId]
            if fids:
                for followees in self.followed[userId]:
                    if followees in self.tweets:
                        followerTweets = self.tweets[followees]
                        if followerTweets:
                            for tweet in self.tweets[followees]:
                                heappush(heap, tweet)
                                if len(heap) > 10:
                                    heappop(heap)
        res = [] 
        while heap:
            curr = heappop(heap).tID
            res.append(curr)
            #res.append(heappop(heap).tID)
            #print(res)
        return res[::-1]

    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId not in self.followed:
            self.followed[followerId] = set()
        self.followed[followerId].add(followeeId)
 
    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId in self.followed and followerId != followeeId:
            self.followed[followerId].discard(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)