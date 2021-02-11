# T = O(nlogk)
# S = O(n)

# Approach:
# Maintain two maps to hold:
	# a.UserId and people who the person follows
	# b.TweetsMap:hold the time and the tweetid mapped to a user
# Aplly a min heap on the tweetmap to display the  getNewsFeed method

from heapq import heappush as insert
from heapq import heappop as remove
class Twitter:

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.UserMap = defaultdict(set)
        self.TweetsMap = defaultdict(list)
        self.time = 0

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        self.TweetsMap[userId].append([self.time,tweetId])
        self.time += 1

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        q = []
        followers = self.UserMap[userId]
        followers.add(userId)
        for f in followers:
            for tweet in self.TweetsMap[f]:
                t,post = tweet[0],tweet[1]
                if len(q) < 10:
                    insert(q,(t,post))
                elif t > q[0][0]:
                    remove(q)
                    insert(q,(t,post))
        result = []
        while q:
            t,post = remove(q)
            result.append(post)
        return result[::-1]
            
                

    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        self.UserMap[followerId].add(followeeId)
        

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        if(followeeId in self.UserMap[followerId]):
            self.UserMap[followerId].remove(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)