import heapq
from collections import defaultdict, deque

# Space Complexity : O(n+m), where n is the number of users and m is the number of tweets
class Twitter:
    class Tweet:
        def __init__(self, tweet_id, created_at):
            self.tweet_id = tweet_id
            self.created_at = created_at

    def __init__(self):
        self.user_map = defaultdict(set)
        self.tweet_map = defaultdict(deque)
        self.time = 0

    # Time Complexity : O(1)
    def postTweet(self, userId, tweetId):
        self.tweet_map[userId].appendleft(self.Tweet(tweetId, self.time))
        self.time += 1

    # Time Complexity : O(n+m)
    def getNewsFeed(self, userId):
        self.follow(userId, userId)
        min_heap = []
        all_users = self.user_map[userId]
        for user in all_users:
            for tweet in self.tweet_map[user]:
                heapq.heappush(min_heap, (tweet.created_at, tweet.tweet_id))
                if len(min_heap) > 10:
                    heapq.heappop(min_heap)
        result = []
        while min_heap:
            result.append(heapq.heappop(min_heap)[1])
        return result[::-1]

    # Time Complexity : O(1)
    def follow(self, followerId, followeeId):
        self.user_map[followerId].add(followeeId)

    # Time Complexity : O(1)
    def unfollow(self, followerId, followeeId):
        if followeeId in self.user_map[followerId]:
            self.user_map[followerId].remove(followeeId)

# Example 1
twitter = Twitter()
twitter.postTweet(1, 5)
print(twitter.getNewsFeed(1))  # Output: [5]

# Example 2
twitter.postTweet(1, 3)
twitter.postTweet(1, 101)
twitter.postTweet(1, 13)
twitter.postTweet(1, 10)
twitter.postTweet(1, 2)
twitter.postTweet(1, 94)
twitter.postTweet(1, 505)
twitter.postTweet(1, 333)
twitter.postTweet(1, 22)
twitter.postTweet(1, 11)
print(twitter.getNewsFeed(1))  # Output: [11, 22, 333, 505, 94, 2, 10, 13, 101, 3]

# Example 3
twitter.follow(2, 1)
print(twitter.getNewsFeed(2))  # Output: [11, 22, 333, 505, 94, 2, 10, 13, 101, 3]
twitter.postTweet(2, 6)
print(twitter.getNewsFeed(2))  # Output: [6, 11, 22, 333, 505, 94, 2, 10, 13, 101]

# Example 4 (unfollowing)
twitter.unfollow(2, 1)
print(twitter.getNewsFeed(2))  # Output: [6]