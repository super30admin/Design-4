#All TC passed on leetocode

class Tweet:

    def __init__(self, id, time):
        self.tid = id
        self.timeStamp =  time


class Twitter:

    def __init__(self):
        self.curTime = 0
        self.userFollowingMap = collections.defaultdict(set)
        self.userTweetsMap = collections.defaultdict(list)
        
    
    #Here we add user to his follows as he follows himself too. Here we add the tweet to tweet map and increment the cur time.
    #Time complexity - O(1)
    #Space complexity - O(1)
    def postTweet(self, userId: int, tweetId: int) -> None:
        if userId not in self.userFollowingMap[userId]:
            self.userFollowingMap[userId].add(userId)
        tweet = Tweet(tweetId, self.curTime)
        self.curTime += 1
        self.userTweetsMap[userId].append(tweet)
        
    
    #Here we process the user's follows tweets. For each of his follows we process last 10 tweets push them into the heap. When heap size crosses 10 we pop the top root. 
    #Time complexity - O(Nlog10) => adding the last 10 tweets into heap
    #Space complexity - O(10) => O(1) for heap
    def getNewsFeed(self, userId: int) -> List[int]:
        newsFeed = []
        minHeap = []

        Tweet.__lt__ = lambda x, y: x.timeStamp < y.timeStamp

        for u in self.userFollowingMap[userId]:
            utweets = self.userTweetsMap[u]
            size = len(utweets)
            for i in range(size-1, -1, -1):
                if i<(size-10):
                    break
                else:
                    heapq.heappush(minHeap, utweets[i])
                    if len(minHeap)>10:
                        heapq.heappop(minHeap)
    
        while minHeap:
            newsFeed.insert(0, heapq.heappop(minHeap).tid)
        return newsFeed
       

    #Here we add followeeID to follower's map set.
    #Time complexity - O(1)
    #Space complexity - O(1)
    def follow(self, followerId: int, followeeId: int) -> None:
        self.userFollowingMap[followerId].add(followeeId)


    #Here we check if followeeID in present in followedid set if yes we remove it. Also we make sure that we are not removing folowerId himself from his set.
    #Time complexity - O(1)
    #Space complexity - O(1)
    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followeeId in self.userFollowingMap[followerId] and followeeId!=followerId:
            self.userFollowingMap[followerId].remove(followeeId)
        


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)