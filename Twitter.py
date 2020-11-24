from heapq import *
class Tweet:
    def __init__(self, tid, timestamp):
        self.tid = tid
        self.timestamp = timestamp
        self.next = None
    
    # def __lt__(self, other):
    #     return self.timestamp < other.timestamp
        
class User:
    def __init__(self, uid):
        self.uid = uid
        self.followed = set()
        self.tweet_head = None
        self.follow(uid)
    
    def follow(self, id):
        self.followed.add(id)
    
    def unfollow(self, id):
        if id in self.followed:
            self.followed.remove(id)
        
    def post(self, id, timestamp):
        tweet = Tweet(id, timestamp)
        tweet.next = self.tweet_head
        self.tweet_head = tweet

class Twitter(object):

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.time = 0
        self.user_map = collections.defaultdict(User)
          

    def postTweet(self, userId, tweetId):
        """
        Compose a new tweet.
        :type userId: int
        :type tweetId: int
        :rtype: None
        """
        if not self.user_map.has_key(userId):
            user = User(userId)
            self.user_map[userId] = user
        self.user_map[userId].post(tweetId, self.time)
        self.time += 1

    def getNewsFeed(self, userId):
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        :type userId: int
        :rtype: List[int]
        """
        res = collections.deque()
        if userId not in self.user_map:
            return []
        minheap = []
        users = self.user_map[userId].followed
        for user in users:
            t = self.user_map[user].tweet_head
            if t:
                heappush(minheap, (-t.timestamp, t))
            
        n = 0
        while minheap and n < 10:
            time, tweet = heappop(minheap)
            res.append(tweet.tid)
            n += 1
            if tweet.next:
                heappush(minheap, (-tweet.timestamp, tweet.next))
        return res
        

    def follow(self, followerId, followeeId):
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        if followerId not in self.user_map:
            user = User(followerId)
            self.user_map[followerId] = user
        if followeeId not in self.user_map:
            user = User(followeeId)
            self.user_map[followeeId] = user
        self.user_map[followerId].follow(followeeId)    
        
        
    def unfollow(self, followerId, followeeId):
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        if followerId not in self.user_map or followeeId == followerId:
            return
        self.user_map[followerId].unfollow(followeeId)
        


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)