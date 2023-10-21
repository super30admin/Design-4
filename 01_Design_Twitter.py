'''
class Tweets:
    def __init__(self, id, count):
        self.count = count
        self.id = id

{
userID : {
    userTweets: LinkedList of <Tweets> (most recent tweet will be at head, so question becomes merge k sorted list)
    following : set()
    }
}


Now we use maxHeap to 

VK  T1 (7) -> T2 (3) -> T3 (1)
EM  T4 (5) -> T5 (4) -> T6 (2)

Now insert head of t1 to heap and t4 to heap, 
--------- 
t5, t2
--------

TopTen = [t1, t4, t5, ]


TC : f + k log f (heap)
SC : f(heap)
'''
class Tweets:
    def __init__(self, count, id):
        self.count = -count
        self.id = id
    
class User:
    def __init__(self, userId):
        self.userId = userId
        self.following = set()
        self.head = None
    
    def follow(self, followeeId: int) -> None:
        self.following.add(followeeId)

    def unfollow(self, followeeId: int) -> None:
        if followeeId in self.following:
            self.following.remove(followeeId)
    
    def postTweet(self, tweet: Tweets) -> None:
        tweet.next = self.head
        self.head = tweet
    

class Twitter:

    def __init__(self):
        self.count = 1
        self.userHashMap = {}
        

    def postTweet(self, userId: int, tweetId: int) -> None:
        t = Tweets(self.count, tweetId)
        self.count += 1
        if userId not in self.userHashMap:
            self.userFollow(userId)
        self.userHashMap[userId].postTweet(t)

    def getNewsFeed(self, userId: int) -> List[int]:
        pq = []
        
        if userId not in self.userHashMap:
            return []
        
        for followeeId in self.userHashMap[userId].following:
            followee = self.userHashMap[followeeId]
            if(followee.head):
                heapq.heappush(pq, (followee.head.count, followee.head))
        heapq.heapify(pq)
        feed = []
        
        while (pq and len(feed) != 10):
            tweetcount, tweet = heapq.heappop(pq)
            feed.append(tweet.id)
            
            if tweet.next:
                 heapq.heappush(pq, (tweet.next.count, tweet.next))
        return feed
            

    def follow(self, followerId: int, followeeId: int) -> None: 
        if followerId not in self.userHashMap:
            self.userFollow(followerId)
        if followeeId not in self.userHashMap:
             self.userFollow(followeeId)
        self.userHashMap[followerId].follow(followeeId)
        

    def unfollow(self, followerId: int, followeeId: int) -> None:
        self.userHashMap[followerId].unfollow(followeeId)
    
    def userFollow(self, userId):
        user = User(userId)
        self.userHashMap[userId] = user 
        self.userHashMap[userId].follow(userId)
        


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)