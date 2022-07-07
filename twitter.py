# Time Complexity : O(n); n --> no. of persons a user follows
# Space Complexity : O(1)
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No
#
#
# Your code here along with comments explaining your approach
import heapq


# creating Tweet object.
class Tweet:
    def __init__(self, tweet_id, time):
        self.tweet_id = tweet_id
        self.time = time

    # can use any of the magical operators(__lt__ or __gt__) for creating a max heap
    # since tweet is an object and two tweets needs to be compared according to its posted time,
    # creating magical operator for comparing two tweet objects according to its posted time.

    def __gt__(self, other):
        return self.time < other.time

    # def __lt__(self, other):
    #     return self.time > other.time


class Twitter:
    def __init__(self):
        self.tweets = {}  # userId to list of tweet objects posted by the user
        self.followed = {}  # userId to set of users that user is following.
        self.time = 0

    def postTweet(self, userId: int, tweetId: int) -> None:
        self.follow(userId, userId)  # following himself.
        # creating the tweet object in hashmap tweets
        if userId not in self.tweets:
            self.tweets[userId] = []
        self.time += 1
        self.tweets[userId].append(Tweet(tweetId, self.time))

    def getNewsFeed(self, userId: int):
        heap = []
        # creating a maxheap of last 10 tweets(if tweeted) of the users that userId is following.
        if userId in self.followed:
            for followees in self.followed[userId]:
                if followees in self.tweets:
                    for j in range(10):
                        if j == len(self.tweets[followees]):
                            break
                        if j < len(self.tweets[followees]):
                            heapq.heappush(heap, self.tweets[followees][-1 - j])
        # in max-heap among all tweets, the top 10(if exists) tweets will be the latest tweets posted by the users,
        # userId is following
        result = []
        while heap and len(result) < 10:
            result.append(heapq.heappop(heap).tweet_id)
        return result

    def follow(self, followerId: int, followeeId: int) -> None:
        # updating followed hashmap with the users, a userId is following.
        if followerId not in self.followed:
            self.followed[followerId] = set()
        self.followed[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        # removing userId from the set of userId's in followee hashmap.
        if followeeId in self.followed[followerId]:
            self.followed[followerId].remove(followeeId)


# Your Twitter object will be instantiated and called as such:
obj = Twitter()
obj.postTweet(1, 5)
print(obj.getNewsFeed(1))
obj.follow(1, 2)
obj.postTweet(2, 6)
print(obj.getNewsFeed(1))
obj.unfollow(1, 2)
print(obj.getNewsFeed(1))
