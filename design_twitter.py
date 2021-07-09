
from heapq import heappush as insert
from heapq import heappop as remove


class Tweet:
    def __init__(self, tweetId, timestamp):
        self.tweetId=tweetId
        self.timestamp=timestamp

    # custom comparator function to compare tweet objets in priorityqueue/ heapsorting
    def __lt__(self,other):
        return self.timestamp < other.timestamp


class Twitter:
    """
    : posttweet= add new tweet ---> required user and respective tweets (user_id, tweet_id) ---> one hashmap---> mapping uId with tweets ---> tweetMap
    : getnewsfeed = required user ---> extract all tweets from created hashmaps ---> from followee and user's tweets ---> tweetMap, userMap
    : follow = required user Ids for both whom to follow and the person who is following ---> adding user ---> in userMap
    : unfollow = required user Ids for both whom to follow and the person following ---> removing user ---> from userMap
    """
    """
    TC: O(nlog10), n=total following users
    SC: O(total tweets + total following users)
    """
    
    def __init__(self):
        """
        Initialize your data structure here.
        """
        # map to maintain tweets respective to particular user -- {user_id, [list of tweets]}
        self.tweets={}
        # map to maintain all following of a particular user -- {user_id, set(following)}
        self.following={}
        # global timestamp to update value when new tweet is added
        self.timestamp=0
        
    # to check if userId is already present, if not, add in maps
    def check_newUser(self, userId):
        if userId not in self.following:
            self.following[userId]=set()
            # intialize with adding key itself so later can extract tweets from following and given uid
            self.following[userId].add(userId)
        if userId not in self.tweets:
            self.tweets[userId]=[]
        

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        self.check_newUser(userId)
        self.timestamp+=1
        tweet=Tweet(tweetId, self.timestamp)
        self.tweets[userId].append(tweet)
        self.timestamp+=1

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        self.check_newUser(userId)
        followingUsers = self.following[userId]
        # to store all the tweets associated to given user
        tweetList=[]
        if followingUsers:
            # extract all tweets from following
            for followeee in followingUsers:
                if followeee in self.tweets and len(self.tweets[followeee]) >0:
                    for tweet in self.tweets[followeee]:
                        insert(tweetList, tweet)
                        # to maintain min heap with size 10
                        if len(tweetList)>10:
                            remove(tweetList)
        feed=[]
        # to get tweetIds from min heap tweet objects by popping and storing in list
        while len(tweetList)>0:
            feed.append(remove(tweetList).tweetId)
        # to get top 10 recent tweets, reverse the feed
        return feed[::-1]
        
        

    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        self.check_newUser(followerId)
        self.check_newUser(followeeId)
        self.following[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        self.check_newUser(followerId)
        self.check_newUser(followeeId)
        if followerId in self.following and followerId != followeeId:
            if followeeId in self.following[followerId]:
                self.following[followerId].remove(followeeId)
        


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)