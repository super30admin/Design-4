'''
Time Complexity: 
postTweet: O(1)
getNewsFeed: O(n log k) where n is the number of followees and k is the number of tweets to be returned (10 in this case)
follow: O(1)
unfollow: O(1) 
''' 

class Twitter:

    class Tweet:
        def __init__(self, tweetId, userId, time, userTweetIndex):
            self.tweetId = tweetId
            self.userId = userId
            self.time = time
            self.userTweetIndex = userTweetIndex

        def __lt__(self, other):
            return self.time > other.time

    def __init__(self):
        self.follow_graph = defaultdict(set)
        self.tweets = defaultdict(list)
        self.counter = 0

    def postTweet(self, userId: int, tweetId: int) -> None:
        tweet_item = self.Tweet(tweetId, userId, self.counter, len(self.tweets[userId]))
        self.tweets[userId].append(tweet_item)
        self.counter += 1

    def getNewsFeed(self, userId: int) -> List[int]:
        heap = []
        for follower in self.follow_graph[userId]:
            if len(self.tweets[follower]) > 0:
                tweet_item = self.tweets[follower][-1]
                heapq.heappush(heap, tweet_item)
        if len(self.tweets[userId]) > 0:
            tweet_item = self.tweets[userId][-1]
            heapq.heappush(heap, tweet_item)
        feed = []
        while len(feed) < 10 and len(heap) > 0:
            tweet_item = heapq.heappop(heap)
            feed.append(tweet_item.tweetId)
            user = tweet_item.userId
            if tweet_item.userTweetIndex != 0:
                next_item = self.tweets[user][tweet_item.userTweetIndex - 1]
                heapq.heappush(heap, next_item)
        return feed

    def follow(self, followerId: int, followeeId: int) -> None:
        if followeeId != followerId:
            self.follow_graph[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId != followeeId:
            self.follow_graph[followerId].discard(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)
