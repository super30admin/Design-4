'''
TC:
postTweet: O(1) {appending to a list and incrementing a counter}
getNewsFeed: O(K * N * log(10)) {where K is the number of users to check (user + followers) and N is the number of tweets to retrieve from each. We iterate through each user and their recent tweets, pushing them to a heap of size 10.}
follow: O(1) {adding an item to a set}
unfollow: O(1) {removing an item from a set}

SC: O(U + T) {where U is the total number of users and T is the total number of tweets. We store user follow relationships and all tweets.}

Approach:

This solution simulates a simplified Twitter feed using two hash maps and a min-heap. One hash map (`tweetMap`) stores a list of tweets for each user, while the other (`userMap`) tracks follower relationships. A global `timestamp` is used to order tweets chronologically.

1.  **postTweet**: A new tweet is created with its `tweetId` and the current `timestamp`. This tweet is then appended to the corresponding user's list in `tweetMap`.
2.  **getNewsFeed**: To generate the news feed, we first identify the user and all the users they follow. We then iterate through this combined list of users. For each user, we get a limited number of their most recent tweets (up to 10) and push them into a **min-heap**. The heap is maintained at a maximum size of 10, ensuring it only contains the 10 most recent tweets overall. The heap's priority is based on the `createdAt` timestamp.
3.  **follow & unfollow**: These operations manage the `userMap` by adding or removing a `followeeId` from a `followerId`'s set of followed users.

This implementation effectively simulates the Twitter functionality, although the `getNewsFeed` method has room for optimization. The use of a heap ensures that the most recent tweets are always returned, and the hash maps provide efficient lookups for user data.

The problem ran successfully on LeetCode.
'''

class Twitter:
    class Tweet:
        def __init__(self, tweetId, createdAt):
            self.tweetId = tweetId
            self.createdAt = createdAt

    def __init__(self):
        self.userMap = {}      #{user_id: set of users this user is following}
        self.tweetMap = {}     #{user_id: list of tweets this user has made}
        self.timestamp = 0
        

    def postTweet(self, userId: int, tweetId: int) -> None:
        if userId not in self.tweetMap:
            self.tweetMap[userId] = []
        
        self.tweetMap[userId].append(self.Tweet(tweetId, self.timestamp))
        self.timestamp += 1
        

    def getNewsFeed(self, userId: int) -> List[int]:
        heap = []

        followingSet = [userId]
        if userId in self.userMap:
            followingSet += list(self.userMap[userId])


        for u in followingSet:
            tweetList = self.tweetMap.get(u, [])
            tweetListLength = min(10, len(tweetList))
            for t in tweetList[:tweetListLength+1]:
                tweet_id, createdAt = t.tweetId, t.createdAt
                heapq.heappush(heap, (createdAt, tweet_id))
                if len(heap) > 10:
                    heapq.heappop(heap)
        
        result = []
        while heap:
            result.insert(0, heapq.heappop(heap)[1])
        return result
        

    def follow(self, followerId: int, followeeId: int) -> None:
        #{follower_id: followed_id}
        if followerId not in self.userMap:
            self.userMap[followerId] = set()
        
        self.userMap[followerId].add(followeeId)
        

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId in self.userMap:
            self.userMap[followerId].remove(followeeId)
        


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)