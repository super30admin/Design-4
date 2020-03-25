'''
Time Complexity: O()
Space Complexity: O()
Did this code successfully run on Leetcode : Yes
Explanation: Create a structure for tweets, we use a hashmap with lists for userTweetMap and use hashmap with set for
userFollowersMap. Create a tweet class to save tweet information as well.
'''
class Twitter:

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.timestamp = 0
        self.feedsize = 10
        # userFollowsMap, userTweetMap
        self.userTweetMap = {}
        self.userFollowsMap = {}

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        self.isFirstTime(userId)

        self.timestamp = self.timestamp + 1

        self.userTweetMap[userId].append(Tweet(tweetId, self.timestamp))

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        self.isFirstTime(userId)

        feeds = []

        followees = self.userFollowsMap.get(userId)

        for followee in followees:
            feeds = feeds + self.userTweetMap.get(followee)

        feeds.sort(key=lambda a: a.createdAt, reverse=True)

        topFeeds = []
        length = min(self.feedsize, len(feeds))
        for i in range(0, length):
            # print(feeds[i].tweetId)
            topFeeds.append(feeds[i].tweetId)

        return topFeeds

    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        self.isFirstTime(followerId)
        self.isFirstTime(followeeId)

        followees = self.userFollowsMap.get(followerId)
        followees.add(followeeId)
        self.userFollowsMap[followerId] = followees

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        self.isFirstTime(followerId)
        self.isFirstTime(followeeId)

        followees = self.userFollowsMap.get(followerId)
        print(self.userFollowsMap)
        if followerId != followeeId:
            if followeeId in followees:
                followees.remove(followeeId)
                self.userFollowsMap[followerId] = followees

    # check if

    def isFirstTime(self, userId):
        # print( self.userFollowsMap)
        followees = self.userFollowsMap.get(userId)

        if followees == None:
            followees = set()
            followees.add(userId)
            self.userFollowsMap[userId] = followees

        tweets = self.userTweetMap.get(userId)
        if tweets == None:
            self.userTweetMap[userId] = []


class Tweet:
    def __init__(self, tweetId, createdAt):
        self.tweetId = tweetId
        self.createdAt = createdAt

# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)