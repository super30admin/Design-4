'''
355 Design Twitter
https://leetcode.com/problems/design-twitter/description/

Design a simplified version of Twitter where users can post tweets, follow/unfollow another user, and is able to see the 10 most recent tweets in the user's news feed.

Implement the Twitter class:
Twitter() Initializes your twitter object.

void postTweet(int userId, int tweetId) Composes a new tweet with ID tweetId by the user userId. Each call to this function will be made with a unique tweetId.

List<Integer> getNewsFeed(int userId) Retrieves the 10 most recent tweet IDs in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user themself. Tweets must be ordered from most recent to least recent.

void follow(int followerId, int followeeId) The user with ID followerId started following the user with ID followeeId.

void unfollow(int followerId, int followeeId) The user with ID followerId started unfollowing the user with ID followeeId.

Example 1:
Input
["Twitter", "postTweet", "getNewsFeed", "follow", "postTweet", "getNewsFeed", "unfollow", "getNewsFeed"]
[[], [1, 5], [1], [1, 2], [2, 6], [1], [1, 2], [1]]
Output
[null, null, [5], null, null, [6, 5], null, [5]]

Explanation
Twitter twitter = new Twitter();
twitter.postTweet(1, 5); // User 1 posts a new tweet (id = 5).
twitter.getNewsFeed(1);  // User 1's news feed should return a list with 1 tweet id -> [5]. return [5]
twitter.follow(1, 2);    // User 1 follows user 2.
twitter.postTweet(2, 6); // User 2 posts a new tweet (id = 6).
twitter.getNewsFeed(1);  // User 1's news feed should return a list with 2 tweet ids -> [6, 5]. Tweet id 6 should precede tweet id 5 because it is posted after tweet id 5.
twitter.unfollow(1, 2);  // User 1 unfollows user 2.
twitter.getNewsFeed(1);  // User 1's news feed should return a list with 1 tweet id -> [5], since user 1 is no longer following user 2.


Constraints:
1 <= userId, followerId, followeeId <= 500
0 <= tweetId <= 104
All the tweets have unique IDs.
At most 3 * 104 calls will be made to postTweet, getNewsFeed, follow, and unfollow.
A user cannot follow himself.

Solution:
1. Use a hash set to to maintain the list of users (as keys) and corresponding followees as values in a hash set. Use a hash map to maintain tweets. To get the 10 most recent tweets, use a min heap. Use a generator object to fetch time from a local variable 't'.
https://www.youtube.com/watch?v=v9qSBrFRFho

                Time        Space
__init__:       O(1)        O(1)
postTweet:      O(1)        O(1)
follow:         O(1)        O(1)
unfollow:       O(1)        O(1)
getNewsFeed:    O(NM)       O(N+M)
'''
from typing import List
from collections import defaultdict
from datetime import datetime
import heapq

class Tweet:
        def __init__(self, msg=None, time=None):
            self.msg = msg
            self.time = time

        def __lt__(self, other):
            # Dunder used by minheap to sort Tweet objects by time
            return self.time < other.time

class Twitter:
    def __init__(self):
        ''' Time: O(1), Space: O(1) '''
        self.userMap = defaultdict(set) # key = userId, value = followeeId
        self.tweetMap = defaultdict(list) # key = userId, value = Tweet()
        self.maxDisplayTweets = 10
        self.time = self.get_time()

    def postTweet(self, userId: int, tweetId: int) -> None:
        ''' Time: O(1), Space: O(1) '''
        if not userId in self.userMap:
            self.userMap[userId] = set()
        self.tweetMap[userId].append(Tweet(tweetId, next(self.time)))

    def getNewsFeed(self, userId: int) -> List[int]:
        ''' Assume N users (userId and followees of userID) and M messages per user (on an avg), there a total of NM messages. Since size of heap is 10, time taken by heap push/pop = O(NM log 10) = O(NM). Space = O(N+M)
        because at for every call to getNewsFeed, we fetch a list of users which occupies O(N) space. We also fetch a list of tweets from each user which occupies O(M). Note that, at any time, the list tweets contains messages of any one user, not all users. Hence, space is O(N+M) not O(NM).
        Time: O(NM log 10) = O(MN), Space: O(N + M)
        '''
        if userId not in self.tweetMap:
            return []

        heap = []
        users = []
        users.append(userId)
        for followeeId in self.userMap[userId]:
            users.append(followeeId) # Space: O(N)

        # run a min heap over all the messages from user and followees
        for user in users: # Time: O(N)
            tweets = self.tweetMap[user] # Space: O(M)
            for t in tweets: # Time: O(M)
                if len(heap) < self.maxDisplayTweets:
                    heapq.heappush(heap, t)
                else:
                    heapq.heappushpop(heap, t)

        # at this point, the heap  contains the latest
        # maxDisplayTweets tweets arranged from earliest to latest.
        # Hence, reverse the heap to get tweets in latest to earliest order.
        return [node.msg for node in heap[::-1]]

    def follow(self, followerId: int, followeeId: int) -> None:
        ''' Time: O(1), Space: O(1) '''
        self.userMap[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        ''' Time: O(1), Space: O(1) '''
        if followerId in self.userMap:
            if followeeId in self.userMap[followerId]:
                self.userMap[followerId].remove(followeeId)

    def get_time(self):
        t = 1
        while True:
            yield t
            t = t + 1
        #t = datetime.now()
        #return int(t.strftime('%Y%m%d%H%M%S'))


def run_Twitter():
    twitter = Twitter()
    twitter.postTweet(1, 5)        # User 1 posts a new tweet (id = 5).
    print(twitter.getNewsFeed(1))  # User 1's news feed should return a list with 1 tweet id -> [5]. return [5]
    twitter.follow(1, 2)          # User 1 follows user 2.
    twitter.postTweet(2, 6)       # User 2 posts a new tweet (id = 6).
    print(twitter.getNewsFeed(1))        # User 1's news feed should return a list with 2 tweet ids -> [6, 5]. Tweet id 6 should precede tweet id 5 because it is posted after tweet id 5.
    twitter.unfollow(1, 2)           # User 1 unfollows user 2.
    print(twitter.getNewsFeed(1))    # User 1's news feed should return a list with 1 tweet id -> [5], since user 1 is no longer following user 2.

run_Twitter()