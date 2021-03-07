class Twitter(object):
    def __init__(self):
        self.tweets = dict() # will store the userId and the list of tweets in the order that they were posted
        self.followers = dict() # key is the followerId and value is a set of followeeIds
        self.alltweets = [] # (userId, TweetId) in the order that they were posted

    """Time Complexity : O(1) adding to the dictionary and adding to a list 
    Add the tweetId to both the tweets dictionary and the alltweets list"""
    def postTweet(self, userId, tweetId):
        if userId not in self.tweets:
            self.tweets[userId] = []
        self.tweets[userId].append(tweetId)
        self.alltweets.append((userId, tweetId))

    """We are checking if the user has any followers then we have to even consider the tweets posted by the following ids
    Time Complexity : O(n) where n is the number of tweets posted till now"""
    def getNewsFeed(self, userId):
        res = []
        if userId in self.followers:
            """if len(self.followers[userId]) > 0:
                If there are any ids that the current user is following then we have to iterate through the entire list of tweets and collect recent 10 tweets"""
            followers = self.followers[userId]
            followers.add(userId) # Easier to check if currentid is the userid or one of the followeeids
            for i in self.alltweets[::-1]: # going through the tweets in reverse order
                if i[0] in followers: # if the currentId is in the followers list then add it to the result
                    res.append(i[1])
                if len(res) == 10: # if the length exceeds 10, we break and don't go further ahead
                    break
        elif userId in self.tweets: # this becomes true if the userid doesn't follow any users
            res = self.tweets[userId][::-1][:10] # reverse the array and take the 10 values
        return res
    """Time Complexity : O(1) add to the set and checking if its is present in the dictionary
    # add the followeeId to the dictionary containing the followers
    # key is the followerId and followeeId is added to the set containing all the ids which the current user is following"""
    def follow(self, followerId, followeeId):
        if followerId not in self.followers:
            self.followers[followerId] = set()
        self.followers[followerId].add(followeeId)
    """Time Complexity : O(1) Here we are doing operation on sets and dictionary
    remove the followeeId from the set of followers for that followerId. Do this only if followeeId is present in the set and followerId is present in the dictionary
    If there are no more ids in the set, remove the followerId from the dictionary"""
    def unfollow(self, followerId, followeeId):
        if followerId in self.followers:
            if followeeId in self.followers[followerId]:
                self.followers[followerId].remove(followeeId)

            if len(self.followers[followerId]) == 0:

                self.followers.pop(followerId)

# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)