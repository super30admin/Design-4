#Time Complexity:O(n)
#Space Complexity:O(n)
#Ran successfully on Leetcode:Yes

#Algorithm : 
# 1. Create two hashmaps , one for followers for a particular userID and another for tweets, whihc stores a tweets tweeted by a particular userID. 
# 2. We have three helper functions--> posttweet, follow and unfollow.
# 3. In postTweet, I check if a userID who wants to post a tweet is present in the tweets hashmap, if not I will add them to tweets hashmap and create a empty array for storing his tweets as well as follows hashmap where he himself is added as a folower. later I append this tweet to that userID array of tweets with tweetID and timestamp. Here I ensure to increment the timestamp by 1. 
# 4. In follow function, I first check if that userID exists in tweets, add that userID to tweets  with an empty array to store his tweets and follows hashmap with himself as a follower. Then for that particular userID I add this followeeID as a follower in follows hashmap
# 5. In unfollow function, I check if this followerID  and the followeeId is present in the follows hashmap and is also followeeID !=followerID, then I remove this followeeID from the followerID list in follows hashmap. 
# 6. getnewsfeed function, I iterate through the folowers list of a followersID and put them in a priority queue untill it reaches the capacity.And each time a tweet is added , we check for the timestamp to add only the ;atest one to the list. 

from collections import defaultdict
class Twitter:

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.follows = collections.defaultdict(set) # userId -> {user's following}
        self.tweets=collections.defaultdict(list)#userID->[tweets posted]
        self.global_time=0

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        if userId not in self.tweets:
            self.tweets[userId] = []
            self.follows[userId] = [userId]
        self.tweets[userId].append((tweetId,self.global_time))
        self.global_time += 1

        
            
            
        

    def getNewsFeed(self, userId: int) -> List[int]:#O(n*log10)=O(n)
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        if userId not in self.tweets:
            self.tweets[userId] = []
            self.follows[userId] = [userId]
            return []
        followees = self.follows[userId]
        fidx = {}
        # All fidx point to the last element of the tweets list
        for j,fol in enumerate(followees):
            fidx[fol] = len(self.tweets[fol])-1
        feed = []
        selected = -1
        for _ in range(10):
            max_time = -1
            for curr_fol in followees:
                if fidx[curr_fol] >= 0:
                    curr_tweet = self.tweets[curr_fol][fidx[curr_fol]]
                    if curr_tweet[1] > max_time:
                        max_time = curr_tweet[1]
                        selected = curr_fol
            if selected!=-1 and fidx[selected]>=0:
                feed.append(self.tweets[selected][fidx[selected]][0])
                fidx[selected] -= 1
        return feed
        
        

    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId not in self.tweets:
            self.tweets[followerId] = []
            self.follows[followerId] = [followerId]
        self.follows[followerId].append(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId not in self.follows or followeeId not in self.follows or followeeId not in self.follows[followerId] or followerId == followeeId:
            return
        self.follows[followerId].remove(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)
