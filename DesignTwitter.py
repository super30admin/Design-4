# TC: postTweet(): O(1); getNewsFeed(): O(T.U) logT where U is the number of users and T is the number of tweets; follow(): O(1) and Unfollow(): O(1) 
# TC: O(U + T) where U is the number of users for the userToTweet hashmap and the follwer-followee hashmap + O(T) space for storing tweets in priority queue. 

class Tweet: 
    def __init__(self, id, timestamp): 
        self.tid = id
        self.tweetTime = timestamp
    
    def __lt__(self, other): 
        return self.tweetTime > other.tweetTime

class Twitter:

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.followerMap = {}
        self.userToTweetMap = {}
        self.time = 1

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        tweet = Tweet(tweetId, self.time)
        self.time += 1
        temp = self.userToTweetMap.get(userId, [])
        temp.append(tweet)
        self.userToTweetMap[userId] = temp

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        if userId not in self.followerMap.keys(): 
            temp = set()
            temp.add(userId)
            self.followerMap[userId] = temp
            
        
        tweets = []
        followeeList = self.followerMap.get(userId)
        
        for followee in followeeList: 
            tweetList = self.userToTweetMap.get(followee)
            if not tweetList: 
                continue
            for tweet in tweetList: 
                heapq.heappush(tweets, tweet)
        
        result = []
        
        if len(tweets):
            while len(result) < 10:
                result.append(heapq.heappop(tweets).tid)
                if not len(tweets): 
                    break
        
        return result
            
    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId not in self.followerMap.keys():
            temp = set()
            temp.add(followerId)
            self.followerMap[followerId] = temp
            
        temp = self.followerMap.get(followerId)
        if temp: 
            temp.add(followeeId)
            self.followerMap[followerId] = temp

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId == followeeId: 
            return
        
        followingList = self.followerMap.get(followerId)
        if followingList:
            if followeeId in followingList:
                followingList.remove(followeeId)
                self.followerMap[followerId] = followingList

# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)
