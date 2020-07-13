# Space Complexity : O(users * tweets) + O(users * followers) 
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No
# Your code here along with comments explaining your approach
import collections
class Twitter:

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.users = collections.defaultdict(list)
        self.follows = collections.defaultdict(set)
        self.time = 1 
        self.max = 10 

    # Time = O(1) 
    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        self.users[userId].append((self.time,tweetId))
        self.time += 1 

    # Time = O(nlogn)
    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        temp = list(self.users[userId]) # O(1)
        for user in self.follows[userId]: # O(users)
            temp.extend(self.users[user])
        temp.sort(reverse = True, key = lambda x : x[0]) # based on timestamp # O(nlogn)
        res = []
        for i in range(len(temp)):
            if i == self.max:
                break 
            res.append(temp[i][1])
        return res

    # Time = O(1)
    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId != followeeId :
            self.follows[followerId].add(followeeId)

    # Time = O(1)
    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        if followeeId in self.follows[followerId]:
            self.follows[followerId].remove(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId) 
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)