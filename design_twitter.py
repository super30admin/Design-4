class Tweet:
    def __init__(self, tweetId, time):
        self.tweet_id = tweetId
        self.time = time
         
    def __lt__(self, val):
        return self.time < val.time

class Twitter:

    def __init__(self):
        """
        Initialize your data structure here.
        """
        
        self.users = {} # keeps tracks of users ids and followee ids
        self.tweets = {} # keeps track of tweets with tweet id and time
        self.time = 0
        

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        
        if userId not in self.tweets:
            self.tweets[userId] = []
            
        self.tweets[userId].append(Tweet(tweetId, self.time))
        self.time += 1
        

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        self.follow(userId, userId)
        pq = []
         
        followers = self.users[userId]
        if followers is not None:
            for fid in followers:
                if fid in self.tweets:
                    tweets = self.tweets[fid]
                    if tweets is not None:
                        for t in tweets:
                            heapq.heappush(pq, t)
                            if len(pq) > 10:
                                heapq.heappop(pq)
        result = []
        while len(pq) > 0:
            current = heapq.heappop(pq)
            result.insert(0, current.tweet_id)
        return result
        

    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId not in self.users:
          
            self.users[followerId] = set() # so we dont keep adding same user ids
   
        self.users[followerId].add(followeeId) 

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId in self.users:
            
            if followeeId in self.users[followerId]:
                self.users[followerId].remove(followeeId)
        


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)