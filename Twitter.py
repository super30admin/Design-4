class Twitter(object):
    def __init__(self):
        self.users = {}
        self.followers = {}
        self.assign_priority = 0
        
    def postTweet(self, userId, tweetId):
        self.assign_priority+=1
        if userId in self.users:
            self.users[userId].append((tweetId,self.assign_priority))
        else: self.users[userId] = [(tweetId,self.assign_priority)]
        
    def get_tweet(self,userId):
        tweets = []
        if userId in self.users:
            tweets+=self.users[userId]
        if userId in self.followers:
            followees = self.followers[userId]
            for u_id in followees:
                if u_id in self.users:
                    tweets+=self.users[u_id]
        tweets = sorted(tweets, key = lambda posts: posts[1],reverse=True)
        return tweets[0:10]

    def getNewsFeed(self, userId):
        recent_Tweets = self.get_tweet(userId)
        return [post[0] for post in recent_Tweets] 

    def follow(self, followerId, followeeId):
        if followerId != followeeId:
            if followerId in self.followers :
                self.followers[followerId].add(followeeId)
            else: self.followers[followerId] = {followeeId}

    def unfollow(self, followerId, followeeId):
        if followerId in self.followers:
            if followeeId in self.followers[followerId]:
                self.followers[followerId].remove(followeeId)