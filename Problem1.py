class Twitter:
    
    """
        
        Student : Shahreen Shahjahan Psyche
        Time.   : postTweet   : O(1)
                  getNewsFeed : O(NM) [Where N is the number of users, M is the number of tweets. We dont count the operation of priority queue beecause its constant time as
                                the capacity is 10 ]
                  follow      : O(1)
                  unfollow.   : O(1)
        Space.  : O(N^2 + MN) [where Number of users is N and number of tweets is M]
        
        Passed Test Cases : Yes
                                
    
    """
    
    
    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.tweets = {} # key : tweet_id, val:  created_at
        self.user_tweets = {} # key : user_id, val : list(tweets)
        self.user_follows = {} # key : user_id , val : set(users_id)
        self.timestamp = 0 # tracking the tweeet timings
        self.capacity = 10 # the capacity of getting most recent feeds
        

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        # add in the tweet list
        self.tweets[tweetId] = self.timestamp 
        self.timestamp += 1
        
        # add tweet to user specific list
        if userId not in self.user_tweets.keys():
            self.user_tweets[userId] = []
        self.user_tweets[userId].append(tweetId)
        
        # if the user is new, adding to the list
        if userId not in self.user_follows.keys():
            self.user_follows[userId] = set()
            self.user_follows[userId].add(userId)
        
        

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        import heapq
        
        # initilizing the news_feed list
        news_feed = []
        # conveerting into priority queue 
        heapq.heapify(news_feed)
        if userId not in self.user_follows.keys():
            return []
        following = self.user_follows[userId]
        res = []
        
        for user in following:
            if user not in self.user_tweets.keys():
                continue
            user_tweets = self.user_tweets[user]
            for tweet in user_tweets:
                # if the number of the news feed is exceeding the capacity, then depending on the timestamp either I am pushing the new tweet after popping the oldest
                # tweet or ignoring
                if len(news_feed) == self.capacity:
                    if news_feed[0][0] > self.tweets[tweet]:
                        continue
                    heapq.heappop(news_feed)
                heapq.heappush(news_feed, (self.tweets[tweet], tweet))
        
        # retriveing the tweets from the priority queue
        while news_feed:
            t = heapq.heappop(news_feed)
            res.append(t[1])
        res.reverse()

        return res
    
    
    
    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        # if the follower is not enlisted in the hashmap, adding him/her
        if followerId not in self.user_follows.keys():
            self.user_follows[followerId] = set()
            self.user_follows[followerId].add(followerId)
        self.user_follows[followerId].add(followeeId)
      

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId in self.user_follows.keys() and followeeId != followerId:
            if followeeId in self.user_follows[followerId]:
                self.user_follows[followerId].remove(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)
