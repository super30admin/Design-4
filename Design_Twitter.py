# Created by Aashish Adhikari at 3:59 PM 3/23/2021

'''
Time Complexity:
follow(): O(1)
unfollow(): O(1)
postTweet(): O(1)
getNewsFeed(): O( No of users * max tweets a user has) in the worst case.

Space Complexity:
follow(): O(1) assuming the hashmap implemented as an array is not counted.
unfollow(): O(1)
postTweet(): O(1)
getNewsFeed(): O(10) ~ O(1) for the minheap
'''

import heapq

class Tweet:

    def __init__(self, tweet_id, timestamp):
        self.id = tweet_id
        self.timestamp = timestamp # time at which the tweet was created



class Twitter(object):

    def __init__(self):
        """
        Initialize your data structure here.
        """

        self.user_and_who_she_follows = {} # A hashmap that keeps track of what users each user follows
        self.user_and_tweets = {} # A hashmap that keeps track of the tweets a particular user made
        self.global_time = 0



    def postTweet(self, userId, tweetId):
        """
        Compose a new tweet.
        :type userId: int
        :type tweetId: int
        :rtype: None
        """

        # since the user should follow herself
        self.follow(userId, userId)

        # Now create a tweet and add it to the tweets table
        if userId not in self.user_and_tweets:
            self.user_and_tweets[userId] = []

        # add the tweet
        self.user_and_tweets[userId].append(Tweet(tweetId, self.global_time))

        self.global_time += 1




    def getNewsFeed(self, userId):
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        :type userId: int
        :rtype: List[int]
        """

        minheap = []
        users_that_userId_is_following = None
        if userId in self.user_and_who_she_follows:
            users_that_userId_is_following = self.user_and_who_she_follows[userId]

        if users_that_userId_is_following is not None:

            # add the tweets by these users to the minheap

            for user in users_that_userId_is_following: # O(no of users)

                tweets_by_this_user = None
                if user in self.user_and_tweets:
                    tweets_by_this_user = self.user_and_tweets [user]

                if tweets_by_this_user is not None:

                    for tweet in tweets_by_this_user: # O(max tweets by an user)

                        heapq.heappush(minheap, (tweet.timestamp, tweet)) # adding to the minheap according to the timestamp # O(log10)

                        if len(minheap) > 10:
                            heapq.heappop(minheap) # remove the tweet with the minimum timestamp


        # generate the remaining tweet ids and put them in descending order

        ascending_recent_tweet_ids = []
        while len(minheap) != 0:                        # O(10)
            priority, tweet = heapq.heappop(minheap)
            ascending_recent_tweet_ids.append(tweet.id)


        descending_recent_tweet_ids = [0 for idx in range(len(ascending_recent_tweet_ids))]
        for idx in range(len(ascending_recent_tweet_ids)):                                  # O(10)
            descending_recent_tweet_ids[len(ascending_recent_tweet_ids) -1 - idx] = ascending_recent_tweet_ids[idx]



        return descending_recent_tweet_ids



    def follow(self, followerId, followeeId):
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """

        # create an entry for this user if not already
        if not followerId in self.user_and_who_she_follows:
            self.user_and_who_she_follows[followerId] = set()

        # add the followee for this user
        self.user_and_who_she_follows[followerId].add(followeeId)






    def unfollow(self, followerId, followeeId):
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        if followerId in self.user_and_who_she_follows and followerId != followeeId:
            if followeeId in self.user_and_who_she_follows[followerId]:
                self.user_and_who_she_follows[followerId].remove(followeeId)




# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)