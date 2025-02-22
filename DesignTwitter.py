# Time Complexity : follow, unfollow, postTweet - O(1) and getNewsFeed - O(mn) where m is no of users and n is no of tweets (it should be O(mn log k) but since k = 10, log k is contstant)
# Space Complexity : size of min heap = k but k = 10 always, so O(1) + O(m + n) (usermap and tweetmap)
# Did this code successfully run on Leetcode : Yes
# Any problem you faced while coding this : NA 

# Your code here along with comments explaining your approach:
# using two maps - user map has userids as key, and a set of the users they follow
# tweet map stores the user id as the key and a list tweet objects as value showing what all tweets wre posted by the user, and at what time
# we need the sequence of the tweets thus we use the time variable in global scope
# for follow operation, we will just add the user id the set of user ids of followees at the index - given followerid
# for unfollow, we remove it from the usermap
# for posttweets, we add the tweetid and incremented time value in the object, and add the object to the list of tweets
# for getnewsfeed - we will use a min heap, that will store recent k (or k largest created at values) for the tweet objects
# if the size of the heap increases the given k value (10)
# the oldest tweet object - at the node - is removed from the heap
# at the end we have 10 latest tweets, and we can make a list of the ids and return them


import heapq

class Twitter(object):
    
    # because we need to associate every tweet with a time
    class Tweet:
        def __init__(self, tweetId, createdAt):
            self.tweetId = tweetId
            self.createdAt = createdAt

    def __init__(self):
        # hashmap to user which user follows which users
        # key is user id and the value is a hashset of user ids
        # we chose hashset to store user ids because it's easy to remove elements in hashset - for unfollow
        self.userMap = {} 
        # hashmap to store which user posted which tweets
        # key is the tweet id, and the value is a list of tweet objects
        self.tweetMap = {} 
        # global time variable
        self.time = 0

    def postTweet(self, userId, tweetId):
        """
        :type userId: int
        :type tweetId: int
        :rtype: None
        """
        if userId not in self.tweetMap:
            # the user has not made a tweet post before
            self.tweetMap[userId] = [] # empty list that will store tweet objects
        
        # now the user's key exists in tweet map
        # we will add the tweet object to the list at userId key
        self.time += 1 # incrementing time for created at
        self.tweetMap[userId].append(self.Tweet(tweetId, self.time))


    def getNewsFeed(self, userId):
        """
        :type userId: int
        :rtype: List[int]
        """
        # getting tweets from tweet map

        
        # we will storing tweet objects in the pq (min heap)
        # according to createdAt
        pq = []
        heapq.heapify(pq)

        # getting all tweets by the user himself
        # if the user has made any tweets
        if userId in self.tweetMap:
            # self.tweetMap[userId] - is a list
            for tw in self.tweetMap[userId]:
                # adding all these tweets in the pq
                heapq.heappush(pq, (tw.createdAt, tw.tweetId))
                if len(pq) > 10:
                    # remove the smalled/oldest tweet from the min heap
                    heapq.heappop(pq)
                
        # fetch all the followees for this user
        followees = self.userMap.get(userId, set())
        # iterate over these users
        if followees is not None:

            for followee in followees:
                # getting the tweets made by each user
                if followee in self.tweetMap:
                    tweets = self.tweetMap[followee]
                    if tweets is not None:
                        # iterate over all the tweets
                        for tw in tweets:
                            # add each tweet to pq
                            heapq.heappush(pq, (tw.createdAt, tw.tweetId))

                            # if size of the pq exceeds k
                            if len(pq) > 10:
                                # remove the oldest one
                                heapq.heappop(pq)
                        

        # now the pq contains the list most recent 10 tweets
        # oldest one is at the root
        # we need to return list of integers
        result = []

        while pq:
            # extracting tweetid
            result.append(heapq.heappop(pq)[1])
        
        return result[::-1] 
   

    def follow(self, followerId, followeeId):
        """
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        # if a user follows another user
        # we check if the followerid doesn't exist in usermap
        # we will create a new hashset for this followerid in the usermap
        if followerId not in self.userMap:
            # then we create an empty set at that id
            self.userMap[followerId] = set()

        # here a hashset should exist for the followerid
        # so we add the followee id to that hashset
        self.userMap[followerId].add(followeeId)


    def unfollow(self, followerId, followeeId):
        """
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        # first we check if the followerId is present in the userMap
        if followerId not in self.userMap:
            return
        
        # now we know that the followerId exists in the user map
        # now we check if the hashset for followerID, contains followeeId
        if followeeId in self.userMap[followerId]:
            # remove this id
            self.userMap[followerId].remove(followeeId)
            

# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)