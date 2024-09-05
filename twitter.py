class Twitter:
    def __init__(self):
        self.user_tweet = defaultdict(list) # list of tweets
        self.user_follow_map = defaultdict(set) # set because we dont want one user to be followed multiple times so set avoids the duplicates
        self.seq_num = -1

    def postTweet(self, userId: int, tweetId: int) -> None:
        self.user_tweet[userId].append((self.seq_num,tweetId))
        self.seq_num -= 1

    def getNewsFeed(self, userId: int) -> List[int]:
        tweets = []
        tweets.extend(self.user_tweet[userId])
        for follower in self.user_follow_map[userId]:
            tweets.extend(self.user_tweet[follower])
        result = []
        heapify(tweets)
        for i in range(10):
            tweets and result.append(heappop(tweets)[1])
        return result


    def follow(self, followerId: int, followeeId: int) -> None:
        self.user_follow_map[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followeeId in self.user_follow_map[followerId]:
            self.user_follow_map[followerId].remove(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)
