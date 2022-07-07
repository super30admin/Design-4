import heapq as hq
from heapq import heappush as insert
from heapq import heappop as remove

class Tweet:
    def __init__(self, tweetId, timestamp):
        self.tweetId = tweetId
        self.createdAt = timestamp

class User:
    def __init__(self, userId):
        self.userId = userID


class Twitter:
    time = 0  # keep increasing after every tweet
    newsFeedSize = 10  # we can configure this at the system level
    def __init__(self):
        self.tweets = {}  # maps user : tweets
        self.follows = {} # maps user X: {users that are followed by userX }
        

    def postTweet(self, userId: int, tweetId: int) -> None: # TC O(1)
        """
        :type userId: int
        :type tweetId: int
        :rtype: None
        """
        t = Tweet(tweetId, self.time)
        if userId in self.tweets:
            self.tweets[userId].append(t)
        else:
            self.tweets[userId] = [t]
        self.time += 1 # increment the time
        self.follow(userId, userId)

    def getNewsFeed(self, userId: int) -> List[int]: # TC O(n*logk) - approaching constant time because n is people the userId follows and we only take k tweets each and maintain heap of SC O(k) in logk time
        """
        :type userId: int
        :rtype: List[int]
        """
        # we will use a min heap based on createdAt timestamp
        # so that when we take the tweets, and maintain heap of size 10,
        # smaller(older) tweets are popped out and newer tweets are inserted.
        # to pop out older tweets we need minHeap.
        heap = []
        # define custom comparator
        Tweet.__lt__ = lambda t1,t2: t1.createdAt < t2.createdAt
        # if userId in follows, meaning if this user follows someone
        if userId in self.follows:
            # then for all the users he follows, iterate over them
            for user in self.follows[userId]:  # whaterver users are followed by userId
                # if that user has some tweets
                if user in self.tweets:
                    # then, iterate over the tweets but just the last 10 as we don't care about any more than that
                    for tweet in self.tweets[user][-self.newsFeedSize:]:
                        # we want to insert 10 tweets(newsFeedSize) for each of the users that userId is following. So we maintain heap size of 10, if it goes beyong, we pop elements.
                        # since we pop elements and its a min heap, we pop tweets that are older by default
                        insert(heap, tweet)
                        if len(heap) > self.newsFeedSize:
                            remove(heap)
        result = []  # at the end construct result list of upto 10 tweets
        while heap: # but since its a min heap, oldest tweets are at the top, and we want to 
            # get tweets in order of newest-> oldest, so we can insert them at index 0 so
            # each newer tweet gets to the front of the list or we can append normally but return reversed list
            result.append(remove(heap).tweetId)
        return reversed(result)
            
    def follow(self, followerId: int, followeeId: int) -> None: # TC O(1)
        """
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        if not followerId in self.follows:
            self.follows[followerId] = set()
            self.follows[followerId].add(followerId)  # folllow self, so user can also see his/her own tweets in newsFeed
        self.follows[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None: # TC O(1)
        """
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        if followerId in self.follows and followeeId in self.follows[followerId]:
            self.follows[followerId].remove(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)