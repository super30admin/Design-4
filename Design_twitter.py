# Explain your approach in briefly only at top of your code
# Approach:
# To design a simplified version of Twitter, we use:
# - A dictionary to maintain the list of tweets for each user.
# - A dictionary to maintain the followers of each user.
# - Each tweet is stored with a timestamp to help retrieve the most recent tweets.
# - For `getNewsFeed`, we collect tweets from the user and their followees, sort them by timestamp, and return the 10 most recent.
#
# Time Complexity :
# - `postTweet`: O(1)
# - `getNewsFeed`: O(k log k), where k is the total number of tweets collected.
# - `follow` and `unfollow`: O(1)
#
# Space Complexity :
# - O(n + f), where n is the total number of tweets and f is the total number of follow relationships.
#
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : None


class Twitter:

    def __init__(self):
        """
        Initialize the Twitter data structure.
        """
        self.tweets = defaultdict(list)  # Map userId to a list of their tweets (tweetId, timestamp)
        self.followees = defaultdict(set)  # Map userId to a set of users they follow
        self.timestamp = 0  # Global timestamp to maintain order of tweets

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet by the user.
        """
        # Append the tweet with the current timestamp
        self.tweets[userId].append((self.timestamp, tweetId))
        self.timestamp += 1

    def getNewsFeed(self, userId: int) -> list[int]:
        """
        Retrieve the 10 most recent tweets in the user's news feed.
        """
        # Collect tweets from the user and their followees
        tweet_heap = []
        users_to_check = self.followees[userId] | {userId}  # Include the user themselves
        for user in users_to_check:
            for tweet in self.tweets[user]:
                heapq.heappush(tweet_heap, tweet)  # Add tweets to a min-heap
                if len(tweet_heap) > 10:
                    heapq.heappop(tweet_heap)  # Maintain only the 10 most recent tweets

        # Extract tweets from the heap, sorted by most recent
        return [tweet[1] for tweet in sorted(tweet_heap, reverse=True)]

    def follow(self, followerId: int, followeeId: int) -> None:
        """
        The follower starts following the followee.
        """
        if followerId != followeeId:  # Prevent users from following themselves
            self.followees[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        The follower stops following the followee.
        """
        if followeeId in self.followees[followerId]:
            self.followees[followerId].remove(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)
