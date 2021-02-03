'''
Solution:
1.  In order to design Twitter, we need a global timestamp, HashMap to map users to tweets, Hashmap to map users to
    their followers.
2.  Just to avoid null checks, we call isFirstTime() method to initialize user's info if not present already.

Time and Space Complexities are explained are in each functionality separately
Extra Global Space: O((users * tweets) + (users * followers))
                    where tweets is maximum number of tweets a particular user has,
                    where followers is maximum number of followers a particular user has.

--- Passed all testcases successfully on Leetcode
'''


class Tweet:

    def __init__(self, tweetId, timeStamp):
        self.tweetId = tweetId
        self.timeStamp = timeStamp


class Twitter_1:

    def __init__(self):
        """
        Initialize your data structure here.
        """
        #   global timestamp, HashMap to map users to tweets, Hashmap to map users to their followers.
        self.timeStamp = 0
        self.userTweetsMap = {}
        self.userFollowsMap = {}
        self.maxFeeds = 10

    def __isFirstTime(self, userId: int) -> None:

        #   initialize user's info if not present already
        #   Time Complexity:    O(1)
        #   Space Complexity:   O(1)

        if (userId not in self.userTweetsMap):
            self.userTweetsMap[userId] = []

        if (userId not in self.userFollowsMap):
            self.userFollowsMap[userId] = set()
            self.userFollowsMap[userId].add(userId)

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        #   Time Complexity:    O(1)
        #   Space Complexity:   O(1)

        self.__isFirstTime(userId)                  #   check user exists
        tweet = Tweet(tweetId, self.timeStamp)      #   create Tweet object
        self.userTweetsMap[userId].append(tweet)    #   append the Tweet object to corresponding userId
        self.timeStamp += 1                         #   update the timestamp

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        #   Time Complexity:    O(NlogN) -> N is total tweets user and its followers have
        #   Space Complexity:   O(N)     -> N is total tweets user and its followers have

        #   custom less than function for Tweet class object
        lessThan = lambda x, y: x.timeStamp < y.timeStamp
        Tweet.__lt__ = lessThan

        self.__isFirstTime(userId)          #   check user exists

        tweetsList = []

        #   add all tweets of user's and its follower's
        for followerId in self.userFollowsMap[userId]:
            self.__isFirstTime(followerId)
            tweetsList.extend(self.userTweetsMap[followerId])

        tweetsList.sort(reverse=True)       #   sort the entire list according to timestamp in descending order

        tweetIds = []

        #   put only first 10 feeds
        for i in range(self.maxFeeds):
            if i < len(tweetsList):
                tweetIds.append(tweetsList[i].tweetId)

        return tweetIds                 #   return the top 10

    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        #   Time Complexity:    O(1)
        #   Space Complexity:   O(1)
        self.__isFirstTime(followerId)                      #   check follower exists
        self.__isFirstTime(followeeId)                      #   check followee exists
        self.userFollowsMap[followerId].add(followeeId)     #   add to the follower's list

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        #   Time Complexity:    O(1)
        #   Space Complexity:   O(1)
        self.__isFirstTime(followerId)                              #   check follower exists
        self.__isFirstTime(followeeId)                              #   check followee exists
        if (followerId != followeeId):
            self.userFollowsMap[followerId].discard(followeeId)     #   remove from the follower's list if not same

# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)