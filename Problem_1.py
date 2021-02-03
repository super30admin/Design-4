"""
Time Complexity:    O(nlogn) for getUSerFeed otherwise its O(1)
Space Complexity:   O(users * max no of tweets of a user + users * max no of followers for a user)
Did this code successfully run on Leetcode : Yes
Any problem you faced while coding this : No


Your code here along with comments explaining your approach
Here, we make a class called tweet with variables as id and createdat for timestamp. Also, we need to make a 
comparator, for checking the timestamp. Inside class twitter, we define a dictionary for storing the users
that a user follows and another hshmap for storing tweets of a particular user.we make a method called 
newuser which checks for all the edge cases if a user is present or not and adds the values to the 
2 hashmaps as required. Follow and unfollow are simple as they just add and remove values from the hashmaps. 
For postTweet,we make a new tweet and add the tweet to the hashmap against respective user. remember to 
increment the timestamp. For getting the newsfeed, we need to make a list where we need to put all the tweets
tweeted by users who a particular user follows. Simultaneosly, we keep on heapifying the heap. Whenever,
the capacity becomes more than desired capacity, we pop from the heap. Remember, inside the comparator,
we have defined that we are checking for timestamp for comapring 2 tweets and this is a min heap. When we get 
our result, we just return it in reversed order.
"""
from heapq import heappush as insert
from heapq import heappop as remove


class Tweet:
    def __init__(self, id, createdAt):
        self.id = id
        self.createdAt = createdAt

    def __lt__(self, other):
        return self.createdAt < other.createdAt


class Twitter:

    def __init__(self):
        self.dictFollow = {}
        self.dictTweets = {}
        self.time = 1
        self.capacity = 10

    def newUser(self, userid):
        if userid not in self.dictFollow:
            self.dictFollow[userid] = set()
            self.dictFollow[userid].add(userid)
        if userid not in self.dictTweets:
            self.dictTweets[userid] = []

    def postTweet(self, userId: int, tweetId: int) -> None:
        self.newUser(userId)
        twt = Tweet(tweetId, self.time)
        self.dictTweets[userId].append(twt)
        self.time += 1

    def getNewsFeed(self, userId: int) -> List[int]:
        self.newUser(userId)
        heap = []
        followees = self.dictFollow[userId]
        if followees:
            for followee in followees:
                if len(self.dictTweets[followee]) > 0:
                    for tweet in self.dictTweets[followee]:
                        insert(heap, tweet)
                        if len(heap) > self.capacity:
                            remove(heap)
        result = []
        while len(heap) > 0:
            result.append(remove(heap).id)
        return result[::-1]

    def follow(self, followerId: int, followeeId: int) -> None:
        self.newUser(followerId)
        self.newUser(followeeId)
        self.dictFollow[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        self.newUser(followerId)
        self.newUser(followeeId)
        if followerId != followeeId:
            self.dictFollow[followerId].discard(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)
