class User:
    def __init__(self, userId, next=None) -> None:
        self.id=userId
        self.posts=None  
        self.following={self}

class Tweet:
    def __init__(self, postId, time, next=None) -> None:
        self.id=postId
        self.timestamp=time
        self.next=None

class Twitter:

    def __init__(self):
        self.time=0
        self.users={}  

    def postTweet(self, userId: int, tweetId: int) -> None:
        self.time+=1
        self.users[userId] = self.users.get(userId, User(userId))
        user=self.users[userId]
        tweet = Tweet(tweetId, self.time)
        tweet.next=user.posts
        user.posts=tweet
        

    def getNewsFeed(self, userId: int) -> List[int]:
        self.time+=1
        self.users[userId] = self.users.get(userId, User(userId))
        user=self.users[userId]
        heap=[] 
        for u in user.following:
            if u.posts:
                heapq.heappush(heap, (-u.posts.timestamp, u.posts))
        i=0
        res=[]
        while i<10 and heap:
            _, post=heapq.heappop(heap)
            res.append(post.id)
            if post.next:
                heapq.heappush(heap, (-post.next.timestamp, post.next))
            i+=1
        return res



    def follow(self, followerId: int, followeeId: int) -> None:
        self.time+=1
        self.users[followerId]=self.users.get(followerId, User(followerId))
        follower= self.users[followerId]
        self.users[followeeId]=self.users.get(followeeId, User(followeeId))
        followee= self.users[followeeId]
        follower.following.add(followee)


    def unfollow(self, followerId: int, followeeId: int) -> None:
        self.time+=1
        self.users[followerId]=self.users.get(followerId, User(followerId))
        follower= self.users[followerId]
        self.users[followeeId]=self.users.get(followeeId, User(followeeId))
        followee= self.users[followeeId]
        follower.following.discard(followee)
        


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)