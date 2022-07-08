import heapq as heap
'''Time Complexity: O(n)
Space Complexity: O(1)'''
class Tweet(object):
    def __init__(self, tweetId, createdAt):
        self.tweetId=tweetId
        self.createdAt=createdAt
class Twitter(object):
    def __init__(self):
        #We need User:Tweets mapping and User: user mapping (list of following)
        self.tweets=dict()
        self.following=dict()
        self.timestamp=0
    def postTweet(self, userId, tweetId):
        """
        :type userId: int
        :type tweetId: int
        :rtype: None
        """
        #When the user is posting the tweet, make the user follow itself 
        self.follow(userId,userId)
        if userId not in self.tweets:
            self.tweets[userId]=[]
        self.timestamp+=1
        self.tweets[userId].append(Tweet(tweetId, self.timestamp))

    def getNewsFeed(self, userId):
        """
        :type userId: int
        :rtype: List[int]
        """
        #Get the set of followings for the userid
        follows=self.following.get(userId)
        #Create an empty list for priority queue for tweets
        pq=[]
        
        #Custom less than comparator for tweets
        lessThan = lambda x, y: x.createdAt<y.createdAt
        Tweet.__lt__ = lessThan
        
        if follows is not None:
            for user in follows:
                usertweets=self.tweets.get(user)
                if usertweets is not None:
                    for tweet in usertweets:
                        #print(tweet.createdAt)
                        heap.heappush(pq,tweet)
                        if len(pq)>10: 
                            heap.heappop(pq)      
        result=[]
        #print(pq)
        while len(pq)!=0:
            #Insert at the start of the list and not the end
            result.insert(0,heap.heappop(pq).tweetId)
        return result

    def follow(self, followerId, followeeId):
        """
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        if followerId not in self.following:
            self.following[followerId]=set()
        self.following[followerId].add(followeeId)

    def unfollow(self, followerId, followeeId):
        """
        :type followerId: int
        :type followeeId: int
        :rtype: None
        """
        if followerId in self.following and followerId!= followeeId:
            if followeeId in self.following[followerId]:
                self.following[followerId].remove(followeeId)    


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)
