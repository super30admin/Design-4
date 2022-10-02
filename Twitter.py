# Time Complexity: 
# postTweet: O(1)
# getNewsFeed: O(n)
# follow: O(1)
# unfollow: O(1)

# Program ran on leetcode successfully

class Twitter(object):

    def __init__(self):
        self.num_tweets = 10
        self.followers = collections.defaultdict(set)
        self.messages = collections.defaultdict(list)
        self.time = 0
        

    def postTweet(self, userId, tweetId):
        """
        :type userId: int
        :type tweetId: int
        :rtype: None
        """
        self.time += 1
        self.messages[userId].append((self.time, tweetId))
        

    def getNewsFeed(self, userId):
        """
        :type userId: int
        :rtype: List[int]
        """
        min_heap = []
        if self.messages[userId]:
            for tweet in self.messages[userId]:
                heapq.heappush(min_heap, (tweet[0], tweet[1]))
                if (len(min_heap) > self.num_tweets):
                    heapq.heappop(min_heap)
        
        if self.followers[userId]:
            for follower in self.followers[userId]:
                if self.messages[follower]:
                    for tweet in self.messages[follower]:
                        heapq.heappush(min_heap, (tweet[0], tweet[1]))
                        if (len(min_heap) > self.num_tweets):
                            heapq.heappop(min_heap)
        
        result = []
        for tweet in reversed(min_heap):
            result.append(tweet[1])
        return result
            
        

    def follow(self, followerId, followeeId):
        """
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        self.followers[followerId].add(followeeId)

    def unfollow(self, followerId, followeeId):
        """
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