import heapq as hq
class Twitter(object):
    class Tweet(object):
        def __init__(self, idx, time):
            self.id = idx
            self.createdAt = time
        def __lt__(self, nxt):
            return self.createdAt < nxt.createdAt
            

    def __init__(self):
        self.followed = {}
        self.user_tweets = {}
        self.time = 0
        

    def postTweet(self, userId, tweetId):
        """
        :type userId: int
        :type tweetId: int
        :rtype: None
        """
        self.follow(userId, userId)
        if userId not in self.user_tweets:
            self.user_tweets[userId] = []
        self.time += 1
        new_tweet = self.Tweet(tweetId, self.time)
        self.user_tweets[userId].append(new_tweet)
            
        

    def getNewsFeed(self, userId):
        """
        :type userId: int
        :rtype: List[int]
        """
        # get the list of all the users userId is following
        if userId not in self.followed:
            return []
        followed = self.followed[userId]
        tweets = list([])
        hq.heapify(tweets)
        for f in followed:
            # get their tweets
            if f in self.user_tweets:
                f_tweets = self.user_tweets[f]
                for t in f_tweets:
                    heapq.heappush(tweets, t)
                    if len(tweets) > 10:
                        heapq.heappop(tweets)
        hq.heapify(tweets)
        res = []
        while len(tweets) != 0:
            res.insert(0, heapq.heappop(tweets).id)
        return res
                
        

    def follow(self, followerId, followeeId):
        """
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        if followerId not in self.followed:
            self.followed[followerId] = set()
        self.followed[followerId].add(followeeId)
            
        

    def unfollow(self, followerId, followeeId):
        """
        :type followerId: int
        :type followeeId: int
        :rtype: None
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
