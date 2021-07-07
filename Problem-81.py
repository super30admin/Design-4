#Leet code - twitter - design
# Time:  O(klogu), k is most recently number of tweets and u is the number of the user's following.
# Space: O(t + f), t is the total number of tweets,f is the total number of followings.

#Approach - we use priority queue(max_heap) to get top 10 tweets from the news feed. For strong the tweets we store userid as key and value as list of tweetid's and timestamp. In case of followers we store followerid as key and set of followeeid's.

class Twitter(object):

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.tweets = collections.defaultdict(list)
        self.followers = collections.defaultdict(set)
        self.timestamp = 0

    def postTweet(self, userId, tweetId):
        """
        Compose a new tweet.
        :type userId: int
        :type tweetId: int
        :rtype: None
        """
        self.timestamp += 1
        self.tweets[userId].append((self.timestamp, tweetId))
        
    def getNewsFeed(self, userId):
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        :type userId: int
        :rtype: List[int]
        """
        max_heap = []
        
        for tweet in self.tweets[userId]:
            heapq.heappush(max_heap, tweet)
            if len(max_heap) > 10:
                heapq.heappop(max_heap)
                
        for other in self.followers[userId]:
            for tweet in self.tweets[other]:
                heapq.heappush(max_heap, tweet)
                if len(max_heap) > 10:
                    heapq.heappop(max_heap)
        
        return [tweetId for _, tweetId in sorted(max_heap)][::-1]

    def follow(self, followerId, followeeId):
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        if followerId != followeeId:
            self.followers[followerId].add(followeeId)

    def unfollow(self, followerId, followeeId):
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        self.followers[followerId].discard(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)