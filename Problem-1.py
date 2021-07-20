from collections import defaultdict
import heapq

"""
Approach:
hashmap of set: to store followees of each user/follower
hashmap of list: to store tweets of each user
priority q: to get n latest tweets

TC: O(users * tweets) for getfeed; others -> O(1)
SC: O(users + tweets)
"""


# creating a new tweet class
class Tweet:
    def __init__(self, tweet_id, created_time):
        self.id = tweet_id
        self.created_time = created_time


class Twitter:
    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.followers = defaultdict(
            set)  # int of user_ids to set of int of userids # followers: {user_id1 : set(user_id1, user_id2, user_id3)}
        self.tweets = defaultdict(
            list)  # int of user_ids to list of Tweet objects # tweets: {user_id1 : [tweet_obj1, tweet_obj2, tweet_obj3]}
        self.time = 1  # used to keep track of latest tweets

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        self.follow(userId, userId)  # user should follow self, if we had a create user method, we'd have added it there
        self.time += 1
        tweet_obj = Tweet(tweetId, self.time)
        self.tweets[userId].append(tweet_obj)

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        # get all users
        pq = []
        all_followees = self.followers[userId]
        for followee in all_followees:
            tweets = self.tweets[followee]  # get all tweets
            for tweet in tweets:
                heapq.heappush(pq, (tweet.created_time, tweet.id))
                if len(pq) > 10:  # retain the latest 10 tweets
                    heapq.heappop(pq)
        result = []
        while pq:
            result.append(heapq.heappop(pq)[1])
        result.reverse()  # need to reverse since you have added them in reverse order. THis is not a good approach but you are reversing only 10 tweets everytime.
        return result

    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        self.followers[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        if followeeId != followerId:
            self.followers[followerId].discard(followeeId)  # remove can throw key error

# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)