"""
Keep a mapping of user - tweets with timestamps
Check user - set of followees
Use max-heap to pull top 10 recent tweets across followees
"""

"""
Time Complexity: postTweet: O(1) ; follow / unfollow: O(1) ; getNewsFeed: O(N log N)  N  = total tweets from self + followees (limited to last 10 each)
Space Complexity: O(U × T + U × F) U × T - total tweet storage U × F - total followee mapping
U = users
F = average number of followees per user
"""

import heapq
from collections import defaultdict

class desTwitter:
    def __init__(self):
        self.time = 0  
        self.tweets = defaultdict(list)   
        self.following = defaultdict(set) 

    def postTweet(self, userId: int, tweetId: int) -> None:
        self.time += 1
        self.tweets[userId].append((-self.time, tweetId)) 
        self.follow(userId, userId)  

    def getNewsFeed(self, userId: int) -> list[int]:
        heap = []
        for followee in self.following[userId]:
            heap.extend(self.tweets[followee][-10:])  

        heapq.heapify(heap)
        result = []
        for _ in range(10):
            if heap:
                time, tweetId = heapq.heappop(heap)
                result.append(tweetId)
        return result

    def follow(self, followerId: int, followeeId: int) -> None:
        self.following[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followeeId != followerId:
            self.following[followerId].discard(followeeId)


if __name__ == "__main__":
    twitter = desTwitter()
    twitter.postTweet(1, 5)
    print(twitter.getNewsFeed(1))       
    twitter.follow(1, 2)
    twitter.postTweet(2, 6)
    print(twitter.getNewsFeed(1))      
    twitter.unfollow(1, 2)
    print(twitter.getNewsFeed(1))       
