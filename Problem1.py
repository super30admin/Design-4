# Time Complexity : O(nlogn) where n is the total number of tweets made by all the users
# Space Complexity : Confused between O(n) where n is the total number of tweets made by all the users
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : Yes. I have problem implementing using a heap in python



import collections
class Twitter(object):

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.timer = 1
        self.tweets = collections.defaultdict(list)
        self.follows = collections.defaultdict(set)

    def postTweet(self, userId, tweetId):
        """
        Compose a new tweet.
        :type userId: int
        :type tweetId: int
        :rtype: None
        """

        self.tweets[userId] += [(self.timer, tweetId)]
        self.timer += 1

    def getNewsFeed(self, userId):
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users           who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        :type userId: int
        :rtype: List[int]
        """

        # ------------------------------------------Using List----------------------------------------------------------
        tweet_list = []
        if userId in self.tweets:
            tweet_list += self.tweets[userId]
        if userId in self.follows:
            for people in self.follows[userId]:
                if people in self.tweets:
                    tweet_list += self.tweets[people]
        freq_10_tweets = sorted(tweet_list, key=lambda x: x[0], reverse=True)
        return [freq_10_tweets[i][1] for i in range(min(10, len(freq_10_tweets)))]
        # -----------------------------------------------------------------------------------------------------------------

    def follow(self, followerId, followeeId):
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        if followerId != followeeId:
            self.follows[followerId].add(followeeId)

    def unfollow(self, followerId, followeeId):
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        if followerId not in self.follows or followerId == followeeId:
            return
        elif followeeId in self.follows[followerId]:
            self.follows[followerId].remove(followeeId)

        # Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)

# Here i have made dictionary of list for the tweets made. Each element of it will be a tuple consisting of the time and
# the tweet id. For the follows I have created a dictionary of sets containing the users to whome a particular user is
# following. In the GetNewsFeed, I am finding tweets made by the user and the user whom it follows and then am sorting
# based on time and am returning the greatest 10.
