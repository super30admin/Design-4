# time complexity : O(n)
# space complexity : O(1)
# runs on leetcode : YES
# any problem faced : No
from collections import defaultdict
class Twitter:
    def __init__(self):
        self.following = defaultdict(lambda: set())
        self.tweets = defaultdict(lambda: set())
        self.twList = []

    def postTweet(self, userId: int, tweetId: int) -> None:
        self.tweets[userId].add(tweetId)
        self.twList.append(tweetId)
        
    def getNewsFeed(self, userId: int) -> List[int]:
        fList = list(self.following[userId])
        tweetList = []
        for f in fList:
            tweetList.extend(list(self.tweets[f]))
        tweetList.extend(list(self.tweets[userId]))
        ind = 0
        for t in self.twList[::-1]:
            if t in tweetList:
                swap = tweetList.index(t)
                tweetList[swap], tweetList[ind] = tweetList[ind], tweetList[swap]
                ind+=1
        return tweetList[:10]

    def follow(self, followerId: int, followeeId: int) -> None:
        self.following[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followeeId in self.following[followerId]:
            self.following[followerId].remove(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)