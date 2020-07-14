"""
// Time Complexity : in code
// Space Complexity : in code
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no

"""
from collections import defaultdict
import heapq
class Twitter:

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.follows = defaultdict(set) # list of users one is following   
        
        self.tweet_info = defaultdict(list) #user_id : [(time,t_id)]
        
        self.timestamp = 0 #to keep track of order in which tweets arrive
        
        self.frame = 10 
        
    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        Time : o(1)
        space : o(n) - number of tweets
        """
        #if userId not in self.follows:
        self.follows[userId].add(userId) #add self to the follows list, as tweets by self are also to be kept track of
            
        self.tweet_info[userId].append((self.timestamp,tweetId)) #add tweet with timetsamp and increment timestamp for next tweet
        self.timestamp += 1

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        Time : o(n*m) - n->number of users, m -> number of tweets
        space : o(n) - o(1), constant
        """
        heap = []
        all_ids = self.follows[userId] #get all users that the userId is following, will include self too
        
        for i in all_ids: #for each ID, get all the tweets
            for t in self.tweet_info[i]:
                #use heap to maintain order
                if len(heap) == self.frame and heap[0][0] < t[0]: #if size of heap exceeds timeframe (ie 10 here), remove the tweet at node, which will have the smallest timestamp to accomodate more recent tweet
                    heapq.heappop(heap)
                    heapq.heappush(heap,t)
                    
                elif len(heap) < self.frame:
                    heapq.heappush(heap,t)
        res = []  
        
        while heap:
            res.insert(0,heapq.heappop(heap)[1]) #order has to be most recent first, hence keep popping from min-heap and inserting at index 0

        return res
            
    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        time : o(1)
        """
        self.follows[followerId].add(followeeId) #simply add to follows list
        
    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        time : o(1)
        """
        if followerId in self.follows and followerId != followeeId and followeeId in self.follows[followerId]: #follower must exist, followee must exist and followee must not be the follwer himself
            self.follows[followerId].remove(followeeId)
            


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)