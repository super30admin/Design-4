# Time Complexity: O(nlogn)
# Space Complexity: O(n)
class Twitter:

    def __init__(self):
        self.count = 0
        self.follow_map = defaultdict(set)  # user id -> set of followeeId
        self.tweet_map = defaultdict(list)  # user id -> list[count, tweets]

    def postTweet(self, userId: int, tweetId: int) -> None: #O(1)
        self.tweet_map[userId].append([self.count, tweetId])
        self.count += 1

    def getNewsFeed(self, userId: int) -> List[int]:   #O(nlogn)
        res = []
        tweets = []
        # Get all tweets from user and users they follow
        for followeeId in self.follow_map[userId] | {userId}:
            tweets.extend(self.tweet_map[followeeId])
        # Sort the tweets based on count in descending order
        tweets.sort(key=lambda x: x[0], reverse=True)
        # Get the top 10 tweets
        for i in range(min(10, len(tweets))):
            res.append(tweets[i][1])  # Append the tweetId
        return res


    def follow(self, followerId: int, followeeId: int) -> None: #O(1)
        self.follow_map[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None: #O(1)
        self.follow_map[followerId].discard(followeeId)
