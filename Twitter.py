
"""
Time Complexity : O(N)-->O(N log 10)
Space Complexity : O(N + T)  N for users and T for Tweets

"""

import heapq 
class Tweet:
    def __init__(self,tid,createdAT):
            self.twId=tid
            self.createdAT=createdAT
    
class Twitter:

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.followed={}
        self.tweets={}
        self.time=0

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        self.follow(userId,userId)
        if userId not in self.tweets:
            self.tweets[userId]=[]
        self.tweets[userId].append(Tweet(tweetId,self.time))
        self.time+=1
            
     # override the comparison operator 
    def __lt__(self, nxt): 
        return self. createdAT< nxt.createdAT    

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
       
        res=[]
        minHp=[]
        print("self.followed",self.followed)
        if userId in self.followed:
            uids=self.followed[userId]
            print(uids)
            if uids:
                for uid in uids:
                    if uid in self.tweets:
                        for tweet in self.tweets[uid]:
                            if len(minHp) < 10: #if lt<10 just push createdAT as comparator
                                heapq.heappush(minHp,(tweet.createdAT, tweet.twId))
                            else:
                        #If heap len > 10, then comapre the newest tweet the root that hols the oldest tweet time and pop if the oldest time and add new tweet.
                                if tweet.createdAT > minHp[0][0]:#root
                                    heapq.heappop(minHp)
                                    heapq.heappush(minHp,(tweet.createdAT,tweet.twId))
        while minHp:
            curr=heapq.heappop(minHp)
            res.insert(0,curr[1])
         
     
        return res
                        
        

    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId not in self.followed:
            self.followed[followerId]=set()
        self.followed[followerId].add(followeeId)
            
        

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        # this is to check if the follower and followee is not same as the follower is following themself
        if followerId not in self.followed or followerId == followeeId :
            return
        if followeeId in self.followed[followerId]:
            self.followed[followerId].remove(followeeId)
            


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)