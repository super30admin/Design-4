"""
Define tweet class to hold tweet object
create followees and tweets map to maintain the followees of users and tweets created by users
use a heap to get latest 10 newsfeed

TC: O(n*klogk) to get newsfeed. where k = 10 n is number of followers, post, follow, unfollow takes O(1) time
SP: O(k) to store the top newsfeeds, O(1) for other ops 

"""
class Tweet:
    def __init__(self, id, timestamp):
        self.tweetId = id
        self.time = timestamp

class Twitter:
    def __init__(self):
        self.followees = defaultdict(set)
        self.tweets =  defaultdict(list)
        self.t = 1

    def postTweet(self, userId: int, tweetId: int) -> None:
        self.tweets[userId].append(Tweet(tweetId, self.t))
        self.t+=1       
        
    def getNewsFeed(self, userId: int) -> List[int]:
        min_heap = []
        self.followees[userId].add(userId)
        for u in self.followees[userId]:
            for t in (self.tweets[u][-10:]):
                heapq.heappush(min_heap, (t.time, t))
                if len(min_heap)>10:
                    heapq.heappop(min_heap)
         
        feed = []
        while min_heap:
            feed.append(heapq.heappop(min_heap)[1].tweetId)

        return feed[::-1]

    def follow(self, followerId: int, followeeId: int) -> None:
        self.followees[followerId].add(followeeId)
        
    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId in self.followees:
            self.followees[followerId].remove(followeeId)

        


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)