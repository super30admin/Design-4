# Leetcode : Solved and submitted

import heapq 
class Twitter:
    # define Tweet class with attributes tid and createAt (timestamp)
    class Tweet:
        def __init__(self, tid, time):
            self.tid =tid
            self.createdAt = time
    # Distance class is used to implement max heap, to calculate the difference in objects with the help of a comparator
    class Distance:
        # using the object of Tweet as we will insert the tweets into the heap
        def __init__(self, tweet):
            self.tweet = tweet

        # overloading the less than operator to compare the createdAt attribute of tweet objects to build max heap
        def __lt__(self, other):
            return self.tweet.createdAt < other.tweet.createdAt
        
    # Initializing the default parameters of Twitter class, time, followed and tweets Hashmap 
    def __init__(self):
        self.time = 0
        self.followed = {}
        self.tweets = {}
    
    # This function will post the tweet
    # Follow the user to itself to ease the extraction of tweets of user as well along with its followers
    # Time complexity : O(1)
    def postTweet(self, userId: int, tweetId: int) -> None:
        self.follow(userId, userId)
        # if userId is not presne in the tweets Hashmap, then put an empty list to start appending
        if userId not in self.tweets:
            self.tweets[userId] = []
        # increase the timestamp
        self.time += 1
        # add the tweet object (tweetId and time) to the tweet hashmap and store it with userId as the key
        self.tweets[userId].append(self.Tweet(tweetId, self.time))

    # Time complexity : O(n) --> n = total number of tweets for the userId and it's followers
    def getNewsFeed(self, userId: int) -> List[int]:
        # using the queue to store the elements out of the heap
        res = collections.deque()
        heap = []
        my_followers = []
        
        # if we have the userId in the follower hashmap, then extract the list of followers
        if userId in self.followed:
            my_followers = self.followed[userId]
        
        # if the list of followers is not empty, then traverse over each userId and check for it's tweets
        if my_followers is not None:
            for fo in my_followers:
                # IF any user does not have aa tweet then simply continue
                if fo not in self.tweets.keys(): 
                    continue
                # extract the tweets of each user in a list
                tw = self.tweets[fo]
                # if tweets present then go over each tweet and add it to the max-heap
                if tw is not None:
                    for each_tweet in tw:
                        # In the heap, we store the tweet object, by comparing the createdAt attribute using less than to build max heap
                        heapq.heappush(heap,self.Distance(each_tweet))
                        
                        # we have a capacity of 10 tweets
                        if len(heap) > 10:
                            # if heap size is greater than 10, pop the min element
                            heapq.heappop(heap)
        # removing the elements of the heap to fetch the results
        while heap:
            # store the popped element of heap in a temp
            temp = heapq.heappop(heap)
            # store the tweet id from the tweet object into res
            res.appendleft(temp.tweet.tid)
        return res

    # if followerId is not present in the followed Hashmap, then create an empty set for the followerId to avoid duplicates
    # Time complexity : O(1)
    def follow(self, followerId: int, followeeId: int) -> None:
        if followerId not in self.followed :
            self.followed[followerId] = set()
        # if present, then simply add the followeeId to the set of followers
        self.followed[followerId].add(followeeId)

    # To unfollow a user, we find if the userId of the follower is present in the Hashmap and also the two ids is not same
    # Time complexity : O(1)
    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId in self.followed and followeeId != followerId:
            # if the followeeId is present in the Hashmap, then remove it from the Hashmap, for the followerId
            if followeeId in self.followed[followerId]:
                self.followed[followerId].remove(followeeId)
            


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)
