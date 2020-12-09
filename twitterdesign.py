import heapq

class Tweet:
    def __init__(self, tid, timestamp):
        self.tweet_id = tid
        self.creationTime = timestamp
        
        
class Twitter:
    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.userTweets = dict()
        self.userFollows = dict()
        self.timeStamp = -1                         # increments with every tweet.
        
        
    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        # add the user himself as his own followee
        # add the tweets from the user to hashmap userTweets
        self.follow(userId, userId)
        if userId not in self.userTweets:
            self.userTweets[userId] = []
        self.timeStamp += 1     # increment the timestamp
        self.userTweets[userId].append(Tweet(tweetId, self.timeStamp))
        

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        topTweets = []
        # check for all the tweets from the followee and use a min heap to keep track of the 10 most recent tweets.
        if userId in self.userFollows:
            for followee in self.userFollows[userId]:
                if followee in self.userTweets:     # followee has tweeted something.
                    for post in self.userTweets[followee]:
                        if len(topTweets) == 10:
                            heapq.heappushpop(topTweets, (post.creationTime, post.tweet_id))
                        else:
                            heapq.heappush(topTweets, (post.creationTime, post.tweet_id))
                            
        newsFeed = []   # user's news feed
        while topTweets:
            newsFeed.append(heapq.heappop(topTweets)[1])
        newsFeed.reverse()
        return newsFeed
                            
                            
    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        # check if userFollows has information of follower and followee.
        if followerId not in self.userFollows:
            self.userFollows[followerId] = set()
        self.userFollows[followerId].add(followeeId)
        

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        # edge case - user cannot unfollow himself.
        # check if followee is present in the follower's set. if yes, delete the followee.
        if followerId == followeeId:
            return 
        
        if followerId in self.userFollows and followeeId in self.userFollows[followerId]:
            self.userFollows[followerId].remove(followeeId)
            

# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)