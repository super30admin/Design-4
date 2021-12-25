# 355. Design Twitter
# https://leetcode.com/problems/design-twitter/

# Logic: Have a dict for followers (userid:list(followers)) and a dict of 
# tweets (userId:set((time, tweetId)). When a new tweet is poseted, add to 
# tweet dict. When a new followers is added add to follower dict. If a 
# follower is removed, remove from follower dict;

# Space Complexity: O(number of tweets + number of followers)

class Twitter:
    def __init__(self):
        self.tweets = dict()
        self.followers = dict()
        self.time = 0

    def postTweet(self, userId: int, tweetId: int) -> None:
        # Time Complexity: O(1)
        if userId not in self.tweets:
            self.tweets[userId] = list()
        self.tweets[userId].append((self.time, tweetId))
        self.time += 1

    def getNewsFeed(self, userId: int) -> List[int]:
        # Time Complexity: O(nlogn)
        result = []
        
        if userId in self.tweets:
            userTweets = self.tweets[userId]
            for i in userTweets:
                result.append(i)
        
        if userId in self.followers:
            followers = self.followers[userId]
            for i in followers:
                if i in self.tweets:
                    followersTweets = self.tweets[i]
                    for j in followersTweets:
                        result.append(j)
        
        result = [i[1] for i in sorted(result, key=lambda x: -x[0])]
        while len(result) > 10:
            result.pop()
        return result

    def follow(self, followerId: int, followeeId: int) -> None:
        # Time Complexity: O(1)
        if followerId not in self.followers:
            self.followers[followerId] = set()    
        self.followers[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        # Time Complexity: O(1)
        if followerId in self.followers:
            self.followers[followerId].discard(followeeId)
        
            if len(self.followers[followerId]) == 0:
                self.followers.pop(followerId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)