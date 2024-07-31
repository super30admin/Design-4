#I designed a simple social media simulation system where users can post tweets, follow or unfollow each other, and retrieve their news feed. The class maintains a list, tweet_to_user, that stores each tweet along with the user who posted it, ensuring tweets are stored in chronological order. For following and unfollowing users, a dictionary user_follows maps each user to a set of users they follow. When retrieving a user's news feed, the getNewsFeed method scans the tweet_to_user list in reverse order to collect up to 10 recent tweets from the user and their followees. The time complexity of postTweet and follow operations is O(1), while getNewsFeed has a time complexity of O(N), where N is the number of tweets, due to the linear scan through the tweet_to_user list. The space complexity is O(N+F), where N is the number of tweets and F is the number of user follow relationships stored in the user_follows dictionary.

class Twitter:
    def __init__(self):
        # self.user_tweets = dict()  # will be a map of dict to list as list has the order of inserted tweets
        self.user_follows = dict()  # userId points to a set having the user he/she follows
        self.tweet_to_user = list()

    def postTweet(self, userId: int, tweetId: int) -> None:
        # if userId in self.user_tweets:
        #     self.user_tweets[userId].append(tweetId)
        # else:
        #     self.user_tweets[userId] = [tweetId]
        self.tweet_to_user.append([tweetId, userId])

    def getNewsFeed(self, userId: int) -> List[int]:
        # print(self.tweet_to_user , userId)
        # 10 most recent tweet'ids
        res = []
        count = 0
        for i in range(len(self.tweet_to_user)-1,-1,-1):
            t, u = self.tweet_to_user[i]
            if u == userId or ( userId in self.user_follows and u in self.user_follows[userId]):
                count += 1
                res.append(t)
            
            if count == 10:
                break
        return res

    def follow(self, userId: int, followeeId: int) -> None:
        if userId in self.user_follows:
            self.user_follows[userId].add(followeeId)
        else:
            self.user_follows[userId] = set([followeeId])

    def unfollow(self, userId: int, followeeId: int) -> None:
        if userId in self.user_follows and followeeId in self.user_follows[userId]:
            self.user_follows[userId].remove(followeeId)



# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)