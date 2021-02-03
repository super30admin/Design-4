class Twitter:

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.users = {}
        self.tweet = []
        # userId- key
        # value: set()
        
        # tweet [(tweetid,userid)]-> order it as well, will use stack
        

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        O(1)
        """
        if(userId in self.users.keys()):
            self.tweet.append((tweetId, userId))
        else:
            self.users[userId] = set()
            self.tweet.append((tweetId, userId))
                              
            
        

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        O(n) worst case where n is total number of tweets
        """
        if(not userId in self.users.keys()):
            return []
        o = []
        i = len(self.tweet)-1
        while(i>=0 and len(o)<10):
            if(self.tweet[i][1] == userId or self.tweet[i][1] in self.users[userId]):
                o.append(self.tweet[i][0])
            i-=1
        
        return o
                
        

    def follow(self, followeeId: int, followerId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        O(1)
        """
        if(followeeId in self.users.keys()):
            self.users[followeeId].add(followerId)
        else:
            self.users[followeeId] = set([followerId])
        
        if(not followerId in self.users.keys()):
            self.users[followerId] = set()

    def unfollow(self, followeeId: int, followerId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        O(1)
        """
        if(followeeId in self.users.keys()):
            try:
                self.users[followeeId].remove(followerId)
            except:
                pass
        else:
            self.users[followeeId] = set()
        
        if(not followerId in self.users.keys()):
            self.users[followerId] = set()



# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)
