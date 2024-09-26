# Time Complexity : follow - O(1), unfollow-O(1), postTweet - O(1), getNewsFeed - m*n*logk--> m - number of followees
# n - no. total tweats (for all follwees)
# Space Complexity :O(n) - follwed Table + O(n) - Tweat Table + Feedsize
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No
'''
1. Start with overall picture of how twitter users will connect on follwing and disconnect on unfollowing
2. Make a hashmap to store a users and all followees. 
3. Similarly for the tweet.

4. Define a custom class for tweet with tweat id and start time as attributes. 
5. Follow and unfollow involves addition and deletion of users from hashmap respectively
6. to see latest 10 feed, we use Min heap to maintain a heap = 10 at any instant.

'''


import heapq
from collections import defaultdict
class Tweat:
    
    def __init__(self, id_, tweat_time):
        self.id = id_
        self.startedAt = tweat_time
        
        
class Twitter:
    
    FEED_SIZE = 10
    time_stamp = 0
    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.followed = defaultdict(lambda : None)
        self.tweat_map = defaultdict(lambda : None)
        self.time_stamp = 0

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        self.follow(userId, userId)
        tweat = Tweat(tweetId, self.time_stamp)
        self.time_stamp += 1
        
        if userId not in self.tweat_map:
            self.tweat_map[userId] = []
            self.tweat_map[userId].append(tweat)
        else:
            self.tweat_map[userId].append(tweat)
            
            
        

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        
        # print ("mmm",self.followed)
        pq = []
        if userId in self.followed:
            print (self.followed)
            for fIds in self.followed[userId]:
                if fIds is not None and fIds in self.tweat_map:
                    for tId in self.tweat_map[fIds]:
                        if tId is not None:
                            heapq.heappush(pq, (tId.startedAt, tId.id))
                            if len(pq) > self.FEED_SIZE:
                                heapq.heappop(pq)

            # print ("pppp",pq)
            tweats_sort = []
            while len(pq) > 0:
                tweats_sort.append(heapq.heappop(pq)[1])

            return tweats_sort[::-1]        
        

    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId not in self.followed:
            
            self.followed[followerId] = set()
            self.followed[followerId].add(followeeId)
            # print("heee",self.followed)
        
        else:
            if followeeId not in self.followed[followerId]:
                self.followed[followerId].add(followeeId)


    def unfollow(self, followerId: int, followeeId: int) -> None:
        
        if followerId in self.followed and followeeId in self.followed[followerId] and followerId != followeeId:
            self.followed[followerId].remove(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)