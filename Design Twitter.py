
class Twitter:
    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.followers = defaultdict(set)
        self.tweets = defaultdict(list)
        self.order= 0
        

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        newTweet = (-self.order,tweetId)
        self.tweets[userId].append(newTweet)
        self.order+=1


    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        userfeed = self.tweets[userId][len(self.tweets[userId])-1:-11:-1]
        for users in self.followers[userId]:
            if users!=userId:
                userfeed+=self.tweets[users][len(self.tweets[users])-1:-11:-1]

        heapq.heapify(userfeed)
        output = []
        for i in range(10):
            if userfeed:
                output.append(heapq.heappop(userfeed)[1])
        return output
            

    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """

        if followerId not in self.followers[followerId]:
            self.followers[followerId].add(followerId)
        self.followers[followerId].add(followeeId)

        

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        if followeeId in self.followers[followerId]:
            self.followers[followerId].remove(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)
Time: NewsFeed: O(NlogN), Rest: O(1)
Space: O(Total Tweets + Users*Avg Following)