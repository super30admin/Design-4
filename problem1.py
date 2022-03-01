# Time complexity:
#Post a tweet:O(1)
#Get a tweet:O(mn)
#Follow SOmeone:O(1)
#unfollow someone :O(1)

from queue import PriorityQueue
class Tweet:
    def __init__(self, tid, created):
        self.tid = tid
        self.created = created


class Twitter:

    def __init__(self):
        self.following = {}
        self.tweetMap = {}

        self.time1 = 0

    def postTweet(self, userId: int, tweetId: int) -> None:
        newTweet = Tweet(tweetId, self.time1)
        self.time1 += 1

        if userId not in self.tweetMap:
            self.tweetMap[userId] = []

        self.tweetMap[userId].append(newTweet)

    def getNewsFeed(self, userId: int) -> List[int]:
        pq = PriorityQueue()
        result = []
        self.follow(userId, userId)
        for userIds in self.following[userId]:
            if userIds in self.tweetMap:
                for i in range(0, len(self.tweetMap[userIds])):
                    tweet = self.tweetMap[userIds][i]
                    pq.put((tweet.created, i, tweet))

                    if pq.qsize() > 10:
                        pq.get()

        while not pq.empty():
            _, _, val = pq.get()
            result.insert(0, val.tid)

        return result

    def follow(self, followerId: int, followeeId: int) -> None:
        if followerId not in self.following:
            self.following[followerId] = set()

        self.following[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId in self.following and followerId != followeeId:
            self.following[followerId].remove(followeeId)

# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)