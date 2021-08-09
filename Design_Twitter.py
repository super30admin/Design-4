#Space: O(n) for storing queries of each tweet
#Time: O(1) for post, follow and unfollow, whereas O(nlog10) for getnews feed, where n refers to the number of users a particular user follows
from collections import defaultdict
import heapq
class Twitter:

    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.followee_dict = defaultdict(set)
        self.tweet_dict = defaultdict(list)
        self.time_stamp = 0
        
        

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        self.time_stamp+=1
        self.tweet_dict[userId].append((self.time_stamp,tweetId))
        
    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        heap = []
        self.followee_dict[userId].add(userId)
        for following in self.followee_dict[userId]:
            # if len()
            for tweet in self.tweet_dict[following]:
                if len(heap)<10:
                    heapq.heappush(heap,tweet)
                elif len(heap)>=10:
                    if tweet[0]>heap[0][0]:
                        heapq.heappop(heap)
                        heapq.heappush(heap,tweet)
        ans = []
        while(heap):
            ans.append(heapq.heappop(heap)[1])
        return reversed(ans)
        
        

    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        self.followee_dict[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        if followeeId in self.followee_dict[followerId]:
            self.followee_dict[followerId].remove(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)