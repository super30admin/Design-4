import heapq
class Twitter(object):
    # tc : follow, unfollow, postTweet : O(1), getnewsfeed is : O(f*tlog10)
    # sc : O(u+t+f) # where u is the total number of users, t is the total number of tweets, f is the total number of followeeids 

    class Tweet:
        def __init__(self,tweetId,time):
            self.tweetId = tweetId
            self.timeStamp = time

    def __init__(self):
        self.tweets_by_user = {} # having a hash map to store the user and their tweets # key is the user and # value is the list of the tweets 
        self.user_their_followers = {} # storing other hashmap, where key is the user and the value is the list of id's of whom this user is following.
        self.time = 0 # time to save the timestamp of the tweet 

    def postTweet(self, userId, tweetId): # tc : O(1)
        # while storing the tweet for the user, we need to store the tweet object where it has tweetid and the time ( as time is needed so we can give tweets in feed)
        if userId not in self.tweets_by_user: 
            self.tweets_by_user[userId] = []
        
        # converting into tweet obeject
        tweet = Twitter.Tweet(tweetId, self.time)

        # update the time with 1 so that the next tweets will have the updated timestamp 
        self.time +=1
        self.tweets_by_user[userId].append(tweet) # sc : O(u + t)
        self.follow(userId, userId)



    def getNewsFeed(self, userId):
        # to get new feed we need to get latest 10 tweets from all the followers the user is following
        # to get the latest tweets we need the timestamp 
        
        # get the userid of the followeeids
        # if userid is not following anyone then simply return empty 
        if userId not in self.user_their_followers:
            return []

        followeeIds = self.user_their_followers[userId]

        # get all the tweets of their followeeids and will use a min heap so i can store the latest 10 tweets of their followee tweets 
        pq = []

        if followeeIds is not None : # if the userid is following some people 
           for followee_id in followeeIds: # tc : O(f)
            # if the followee_id has no tweets
            if followee_id not in self.tweets_by_user:
                continue
            else :
                tweets = self.tweets_by_user[followee_id]
            for tweet in tweets: # tc :O(t)
                heapq.heappush(pq, (tweet.timeStamp, tweet.tweetId)) # tc : O(log10)
                if len(pq) > 10: # if after adding the heap size is greater than 10 its not the latest tweet so pop it out  
                        heapq.heappop(pq)
        
        # we should only retun the tweetids and not the tweettimestamp 
        # in heap we are sendign tuples such that it can compare ( custom comparator)

        feed = []
        while pq: # tc : O(1)
            feed.append(heappop(pq)[1]) # tc log10 -> O(1)

        return feed[::-1] # tc :O(1)


    def follow(self, followerId, followeeId): # tc : O(1)
        # followerId is the userid of the person who is following the followeeId
        # have to maintian a set so that we dont have any duplicates of the followeeid 
        
        if followerId not in self.user_their_followers:
            self.user_their_followers[followerId] = set() 
        self.user_their_followers[followerId].add(followeeId) # sc : O(u + f)

    def unfollow(self, followerId, followeeId): # tc : O(1)
        # followerId wants to unfollow followeeId
        if followerId not in self.user_their_followers:
            return 
        self.user_their_followers[followerId].discard(followeeId)



# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)