# Time Complexity for postTweet: O(1)
# Time Complexity for getNewsFeed: O(nlogk) where n is the number of tweets and k is the number of users followed
# Space Complexity for postTweet: O(1)
# Space Complexity for getNewsFeed: O(k) where k is the number of users followed
# Did this code run on Leetcode: Yes

# Approach:
# 1. Create a class Tweet to store the tweetId and the time it was created.
# 2. Create a class Twitter to store the tweets and the users followed.
# 3. Create a method postTweet to add a tweet to the user's list of tweets.
# 4. Create a method getNewsFeed to get the 10 most recent tweets from the user's timeline.
# 5. Create a method follow to allow a user to follow another user.
# 6. Create a method unfollow to allow a user to unfollow another user.
#7. Use a priority queue to store the tweets in order of their creation time.
# 8. Use a lambda function to compare the creation time of the tweets.
# 9. Users can follow themselves, so when a user posts a tweet, they should follow themselves.

import heapq as heap

class Tweet: 
    def __init__(self, tweetId, createdAt):
        self.tweetId = tweetId
        self.createdAt = createdAt

class Twitter:

    def __init__(self):
        self.tweets = dict()
        self.following = dict()
        self.timestamp = 0

    def postTweet(self, userId: int, tweetId: int) -> None:
        #When user posts tweet, they should follow themselves
        self.follow(userId, userId)
        if userId not in self.tweets:
            self.tweets[userId] = []
        self.timestamp += 1
        self.tweets[userId].append(Tweet(tweetId, self.timestamp))

    def getNewsFeed(self, userId: int) -> List[int]:
        follows = self.following.get(userId)
        pq = []
        moreThan = lambda x , y : x.createdAt < y.createdAt
        Tweet.__lt__ = moreThan
        if follows is not None:
            for user in follows: 
                userTweets = self.tweets.get(user)
                if userTweets is not None: 
                    for tweet in userTweets: 
                        heap.heappush(pq, tweet)
                        if len(pq) > 10:
                            heap.heappop(pq)
        
        result = []
        while len(pq) != 0:
            result.insert(0, heap.heappop(pq).tweetId)
        return result

    def follow(self, followerId: int, followeeId: int) -> None:
        if followerId not in self.following: 
            self.following[followerId] = set()
        self.following[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId in self.following and followerId != followeeId: 
            if followeeId in self.following[followerId]:
                self.following[followerId].remove(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)