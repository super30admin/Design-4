# in interviews, if it says: Design Twitter O(k) with min heap
# remember core operations:
# 1) postTweet
# 2) follow
# 3) unfollow
# 4) getNewsFeed

# Twitter class design:
# 1) userMap:
# stores data related to each user
# myId -> {"feedlist": stores who this user follows (set)
# eg: u1 -> {u4, u5, u8}, u2..
#  2) tweets: stores tweets posted by user
# eg: u1 -> {t1, t2, t3}, u2...

# child class for storing tweets inbuilt structure: tId, timestamp
#   tweets = [(time=1, tId=t1), (time=2, tId=t2), (time=3, tId=t3)]
# Idea:
# postTweet -> append
# follow    -> add
# unfollow  -> remove
# feed      -> heap

from collections import defaultdict
import heapq

# child function
class Tweet:
    def __init__(self, tId, time):
        self.tId = tId
        self.time = time

# main function
class Twitter:
    def __init__(self):
        self.time = 0

        # user -> people they follow ie who i follow
        self.following = defaultdict(set)

        # user -> list of Tweet objects ie my tweets
        self.mytweets = defaultdict(list)

    def postTweet(self, myId: int, tId: int) -> None:
        # store tweet under user
        self.mytweets[myId].append(Tweet(tId, self.time))

        # increment global time
        self.time += 1

    # get latest 10 tweets
    # from: 1) myself 2) people I follow
    def getNewsFeed(self, myId: int):
        minHeap = []

        # include my own tweets too without changing actual following data
        feedlist = self.following[myId].copy()  #1st make copy of the map
        feedlist.add(myId)

        for i_follow_id in feedlist:
            # get their tweets
            tweets = self.mytweets[i_follow_id]
            # process every tweet
            for tweet in tweets:
                # push: tweet object
                heapq.heappush(
                    minHeap,
                    (tweet.time, tweet.tId)
                )
                if len(minHeap) > 10:
                    heapq.heappop(minHeap)
        res = []
        # heap gives oldest -> newest
        while minHeap:
            res.append(heapq.heappop(minHeap)[1])
            #(tId,time)[0] ->gives only tId

        # reverse: newest -> oldest
        return res[::-1]

    # I follow another person
    def follow(self, myId: int, i_follow_id: int) -> None:
        self.following[myId].add(i_follow_id)

    # I unfollow another person
    def unfollow(self, myId: int, i_follow_id: int) -> None:
        # cannot unfollow self
        if i_follow_id != myId:
            self.following[myId].discard(i_follow_id)

# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(myId,tId)
# param_2 = obj.getNewsFeed(myId)
# obj.follow(followerId,i_follow_id)
# obj.unfollow(followerId,i_follow_id)
