class Twitter:
    #Approach: Linked List
    #Time Complexity: O(f) for getNewsFeed; O(1) for rest   // f * 10
    #Space Complexity: O(follower-followee connections + tweets)
    #where, f is the number of users followed by the user queried on
    
    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.followed = {}
        self.tweets = {}
        self.time = 0

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        if userId not in self.tweets:
            self.follow(userId, userId)     #following oneself if a tweet is posted
            self.tweets[userId] = None
        self.time += 1
        new = ListNode(Tweet(tweetId, self.time))
        new.next = self.tweets[userId]
        self.tweets[userId] = new   

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        result = []
        fids = self.followed.get(userId, None)
        if fids:
            curr_ftweets = {}
            for fid in fids:
                ftweets = self.tweets.get(fid, None)
                if ftweets:
                    curr_ftweets[fid] = ftweets
                    
            while len(result) < 10:
                t = 0
                curr_fid = None
                for fid, ftweets in curr_ftweets.items():
                    if ftweets and ftweets.tweet.createdAt > t:
                        t = ftweets.tweet.createdAt
                        curr_fid = fid
                
                if curr_fid:
                    result.append(curr_ftweets[curr_fid].tweet.id)
                    curr_ftweets[curr_fid] = curr_ftweets[curr_fid].next
                else:
                    break
                            
        return result

    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId not in self.followed:
            self.followed[followerId] = set()
        self.followed[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId in self.followed and followerId != followeeId:
            self.followed[followerId].discard(followeeId)   #discard to take care of no-op
    
class Tweet:
    def __init__(self, id, createdAt):
        self.id = id
        self.createdAt = createdAt

class ListNode:
    def __init__(self, tweet):
        self.tweet = tweet
        self.next = None

'''
class Twitter:
    #Approach: Min-heap
    #Time Complexity: O(f * n) for getNewsFeed; O(1) for rest   // f * n * log 10
    #Space Complexity: O(follower-followee connections + tweets)
    #where, f is the number of users followed by the user queried on
    #and, n is the average number of tweets by each of these users

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.followed = {}
        self.tweets = {}
        self.time = 0

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        if userId not in self.tweets:
            self.follow(userId, userId)     #following oneself if a tweet is posted
            self.tweets[userId] = []
        self.time += 1
        self.tweets[userId].append(Tweet(tweetId, self.time))     

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        Tweet.__lt__ = (lambda a, b : a.createdAt < b.createdAt)
        
        heap = []
        fids = self.followed.get(userId, None)
        if fids:
            for fid in fids:
                ftweets = self.tweets.get(fid, None)
                if ftweets:
                    for ftweet in ftweets:
                        heappush(heap, ftweet)
                        if len(heap) > 10:
                            heappop(heap)
                            
        result = []
        while heap:
            result.insert(0, heappop(heap).id)
        return result

    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId not in self.followed:
            self.followed[followerId] = set()
        self.followed[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId in self.followed and followerId != followeeId:
            self.followed[followerId].discard(followeeId)   #discard to take care of no-op
    
class Tweet:
    def __init__(self, id, createdAt):
        self.id = id
        self.createdAt = createdAt
'''      

# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)