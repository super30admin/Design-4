# Time Complexity : O(NlogN)
# Space Complexity : O(mn) = n:users, m:tweets per user
# Did this code successfully run on Leetcode : yes
# Any problem you faced while coding this : no

import heapq

class Tweet:

    def __init__(self,userId, tweetId, ctime):
        self.userId = userId
        self.tweetId = tweetId
        self.createdAt = ctime

class Twitter:

    def __init__(self):
        self.users = {}
        self.tweets = {}
        self.ctime = 1


    def postTweet(self, userId: int, tweetId: int) -> None:
        self.follow(userId,userId)
        tweet = Tweet(userId, tweetId, self.ctime)
        if userId not in self.tweets:
            self.tweets[userId] = [tweet]
        else:
            self.tweets[userId].append(tweet)
        self.ctime += 1

    def getNewsFeed(self, userId: int) -> List[int]:
        result = []
        temp = []
        if userId in self.users:
            for user in self.users[userId]:
                if user in self.tweets:
                    if len(self.tweets[user]) > 10:
                        temp.extend(self.tweets[user][-10:])
                    else:
                        temp.extend(self.tweets[user])
                    # for tweet in t:
                    #     heapq.heappush(temp, (-tweet.createdAt,tweet.tweetId))
                    #     if len(temp) > 10:
                    #         heapq.heappop(temp)
        temp.sort(key = lambda x:x.createdAt, reverse=True)
        temp = temp[:10]
        while len(temp)>0:
            # result.append(heapq.heappop(temp)[1])
            result.append(temp.pop(0).tweetId)
        return result

    def follow(self, followerId: int, followeeId: int) -> None:
        if followerId not in self.users:
            self.users[followerId] = set([followeeId])
        else:
            self.users[followerId].add(followeeId)
        

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId != followeeId:
            if followerId in self.users:
                self.users[followerId].discard(followeeId)
        


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)