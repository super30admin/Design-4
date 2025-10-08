#  Time Complexity : postTweet: O(1), follow and unfollow: O(1), getNewsFeed: O(N log 10) where N is total number of tweets from all followees,   
#  Space Complexity : O(t + f + u) where t is the number of tweets, f is the total number of followers, u is the total number of users
#  Did this code successfully run on Leetcode : Yes
#  Three line explanation of solution in plain english : This code implements a simplified Twitter system where users can post tweets, follow or unfollow others, and view their news feed. Each tweet is stored with a timestamp, and the getNewsFeed method uses a min-heap to efficiently retrieve the 10 most recent tweets from the user and their followees. The followeesMap tracks user relationships, while tweetsMap stores each user’s tweets in posting order.

class Twitter:

    class Tweet:
        def __init__(self, tId, time):
            self.tweetId = tId
            self.timeStamp = time

    def __init__(self):
        self.followeesMap = {}
        self.tweetsMap = {}
        self.time = 0

    def postTweet(self, userId: int, tweetId: int) -> None:
        if userId not in self.tweetsMap:
            self.tweetsMap[userId] = []
        
        tweet = Twitter.Tweet(tweetId, self.time)
        self.time += 1
        self.tweetsMap[userId].append(tweet)
        self.follow(userId, userId)
        
    def getNewsFeed(self, userId: int) -> List[int]:
        # TC: O(N *log10) N is the total tweets for all the followers
        import heapq
        pq = []

        followees = self.followeesMap.get(userId)
        if followees is not None:
            for followee in followees:
                tweets = self.tweetsMap.get(followee)
                if tweets is not None:
                    for tweet in tweets:
                        heapq.heappush(pq, (tweet.timeStamp, tweet))

                        if len(pq) > 10:
                            heapq.heappop(pq)
            
        result = []
        while pq:
            result.append(heapq.heappop(pq)[1].tweetId)
        result.reverse()
        return result

    def follow(self, followerId: int, followeeId: int) -> None:
        # TC: O(1)
        if followerId not in self.followeesMap:
            self.followeesMap[followerId] = set()
        self.followeesMap[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        # TC: O(1)
        if followerId not in self.followeesMap:
            return
        self.followeesMap[followerId].discard(followeeId)