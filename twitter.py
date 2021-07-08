"""
// Time Complexity : O(n) // newfeed nlogk(k is constant)
// Space Complexity : O(m+ n) //tweets + users
// Did this code successfully run on Leetcode : No
// Any problem you faced while coding this : No
// Your code here along with comments explaining your approach
Algorithm explanation
Using dictionary to track the tweets for users and also the users tracking
of following 

Newsfeed -> 
    Get the tweets for the user and followee users
    Use min heap on the tweets with negative timestamp to get the most recent
tweet at the top
"""


from collections import defaultdict
import heapq

class Tweet:
    def __init__(self,id,created_at):
        self.id = id
        self.created_at = created_at

class Twitter:
    """
    Users
    id         name
    1          uname
    
    Tweet
    id  text         userid
    1   "terer"      u1
    
    Follow table - Many to Many
    userid1   userid2     bool
    1         2           True
    1         3           True
    2         1           True
    3         2           False
    
    Newsfeed
    id  tweetid    content         
    1   1          test nf1        
    2   2          test nf2
    
    Data structures
    - Stack
        - []
    - Graph
    
    
    
    
    Track data 
        - list of tweets
        - list of users
        - followed users
        - unfollowed users
    
    
    u1,t1,
    u1:[t1,t2,t3,t4,t5]
    """     
    
    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.tweets = defaultdict(list) #storing list of tweets for user
        self.users = defaultdict(set)  #storing the mapping of following for users
        self.timestamp = 0
        self.feed_size = 10

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        #if userId not in self.users: #this line causes issue if you empty set is created using direct acess in getNewsFeed self.users[userId] for userId not in users empty set will be created against the key it will not be able to follow itself
        self.users[userId].add(userId)
        
        self.timestamp+=1
        self.tweets[userId].append(Tweet(tweetId,self.timestamp))

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        followd_ids = self.users[userId]
        tweet_q = []
        feeds = []
        if followd_ids:
            for user in followd_ids:
                tweets = self.tweets[user]
                if tweets:
                    i = 0
                    for tweet in tweets:
                        if len(tweet_q) == 10: 
                            #remove the min element and push the current ele to maintain the heap size of 10
                            heapq.heappushpop(tweet_q,(tweet.created_at,tweet.id))
                        else:
                            heapq.heappush(tweet_q,(tweet.created_at,tweet.id))
                        
                        # heapq.heappush(tweet_q,(-tweet.created_at,tweet.id))
        # i = 0 # This is expensive since we are not utilizing heap size well
        # while tweet_q and i < self.feed_size:
        #     ele = heapq.heappop(tweet_q)
        #     feeds.append(ele[1])
        #     i+=1
        while tweet_q:
            ele = heapq.heappop(tweet_q)
            feeds.append(ele[1])
        
        return feeds[::-1]
        
    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        # if followerId not in self.users:
        #     self.users[followerId].add(followerId)
        self.users[followerId].add(followeeId)
        

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId in self.users and followerId!=followeeId:
            self.users[followerId].discard(followeeId)
        

# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)