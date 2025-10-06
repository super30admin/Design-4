#Time complexity: O(1) for all functions except getNewsFeed. For getNewsFeed O(n) where n is the number of users as we are at most storing only 10 elements in the heap which is O(1) time
#Space Complexity: O(n) for using the extra heap size
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : No

# The intuition here is to use two hashmaps, one to store the followees and other to store the list of tweets with time.
# The get news feed function we use the logic to store only k elements at a time and put only latest 10 tweets from from each user into the heap.


class Twitter:

    def __init__(self):
        self.timer = 0
        self.tweets = defaultdict(list)
        self.followees = defaultdict(set)

    def postTweet(self, userId, tweetId):
        self.timer += 1
        self.tweets[userId].append((self.timer, tweetId))

    def getNewsFeed(self, userId):
        max_heap = []
        self.followees[userId].add(userId)
        for followee in self.followees[userId]:
            if self.tweets[followee]:
                time, tweetId = self.tweets[followee][-1]
                heapq.heappush(max_heap, (-time, len(self.tweets[followee]) - 1, followee))

        res = []
        while max_heap and len(res) < 10:
            neg_time, idx, followee = heapq.heappop(max_heap)
            res.append(self.tweets[followee][idx][1])
            if idx - 1 >= 0:
                prev_time, prev_tweet = self.tweets[followee][idx - 1]
                heapq.heappush(max_heap, (-prev_time, idx - 1, followee))
        return res

    def follow(self, followerId, followeeId):
        if followerId != followeeId:
            self.followees[followerId].add(followeeId)

    def unfollow(self, followerId, followeeId):
        if followeeId in self.followees[followerId]:
            self.followees[followerId].remove(followeeId)