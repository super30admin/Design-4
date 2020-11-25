#Time Complexity : O(n)
#Space Complexity : O(u + t) where u is the number of users and t is the number of tweets
#Did this code successfully run on Leetcode : Yes

class Tweet:
    def __init__(self, tid, createdAt):
        self.tweetId = tid
        self.createdAt = createdAt

class Twitter:
    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.following = collections.defaultdict(set)
        self.tweets =  collections.defaultdict(list)
        self.time = 0

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        #check if user exists, if not make a new entry for it in both hashmaps
        if userId not in self.following:
            self.follow(userId, userId)
            self.tweets[userId] = []
        #make a tweet object and store it in the tweets hashmap
        self.tweets[userId].append(Tweet(tweetId, self.time))
        self.time += 1


    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        heap = []
        userFeed = []

        #itertate through all the users that the user follows
        for uid in self.following[userId]:
            #iterate through all the tweets of the following user
            for tweet in self.tweets[uid]:
                #check if length of heap is still less than 10, then just push using the timestamp as the comparator
                if len(heap) < 10:
                    heapq.heappush(heap,(tweet.createdAt, tweet.tweetId))
                #if len of heap is greater than 10, since the oldest tweet will be at the top just compare the input tweet's timestamp to the first element of the heap and if it newer then that, then pop the oldest element and add the newest element to the heap
                else:
                    if tweet.createdAt > heap[0][0]:
                        heapq.heappop(heap)
                        heapq.heappush(heap,(tweet.createdAt,tweet. tweet.tweetId))
        while heap:
            curr = heapq.heappop(heap)
            userFeed.append(curr[1])

        return reversed(userFeed)

    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        #add follower and followee to following hashmap
        self.following[followerId].add(followeeId)


    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        #if follower or follower == followee then do nothing
        if followerId not in self.following or followerId == followeeId :
            return
        #else remove followee from follower set
        if followeeId in self.following[followerId]:
            self.following[followerId].remove(followeeId)



# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)
