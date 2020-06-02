#Problem 1: Design Twitter
#Time Complexity: All O(1) , except getnews which is O(nlog(n)), n being the user's followees
#Space Complexity: O(T) for our structure,T number of tweets, since a user needs to have a tweet.
#Worked on leetcode? Yes

'''
Would be much faster with SQL database. Our twitter class will have a userbase which is a hashmap
with key the user id, and value a tuple containing a list of all its tweets with their time, and
a hashset of users that this user is following. This second attribute of the class is time, which
is incremented everytime someone posts something (to track the order of posts). Before calling
anything, we make sure that all the Ids mentioned are existant in the userbase. Also we make
sure that we can't stop following ourselves. when we want the news_feed, we iterate through
all the user's followee and for each of them we append the 10 most recent tweets (no need to
go beyond that), then we sort news_feed in reverse (max to min) and return the 10 first tweets.
'''

class Twitter:

    def __init__(self):
        #user id: ([(time,tweet_id)],{followee})
        self.userbase={}
        self.time=0

    def postTweet(self, userId: int, tweetId: int) -> None:
        if userId not in self.userbase:
            self.userbase[userId]=([(self.time,tweetId)],{userId})
        else:
            self.userbase[userId][0].append((self.time,tweetId))

        self.time+=1

    def getNewsFeed(self, userId: int) -> List[int]:
        if userId not in self.userbase:
            self.userbase[userId]=([],{userId})
        news_feed=[]
        for followee in self.userbase[userId][1]:
            tweet_count=0
            for i in range(len(self.userbase[followee][0])-1,-1,-1):
                #since there no need to go beyond 10
                if tweet_count>10:
                    break
                news_feed.append(self.userbase[followee][0][i])
                tweet_count+=1
        news_feed.sort(reverse=True)


        if len(news_feed)<10:
            return [tweet[1] for tweet in news_feed]

        return [tweet[1] for tweet in news_feed[:10]]


    def follow(self, followerId: int, followeeId: int) -> None:
        if followerId not in self.userbase:
            self.userbase[followerId]=([],{followerId})
        if followeeId not in self.userbase:
            self.userbase[followeeId]=([],{followeeId})

        self.userbase[followerId][1].add(followeeId)


    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followerId not in self.userbase:
            self.userbase[followerId]=([],{followerId})
        if followeeId not in self.userbase:
            self.userbase[followeeId]=([],{followeeId})

        if followerId==followeeId:
            return
        if followeeId in self.userbase[followerId][1]:
            self.userbase[followerId][1].remove(followeeId)
