class Twitter:
    
    """
    Time complexity: O(n log k), where k = 10 (usually small)
    Space complexity: O(V + E), vectors + edges in graph
    
    working on Leetcode: NO
    Failed for:
    
    ["Twitter","postTweet","postTweet","getNewsFeed"]
    [[],[1,5],[1,3],[1]]
    
    ["Twitter","postTweet","getNewsFeed","follow","postTweet","getNewsFeed","unfollow","getNewsFeed"]
    [[],[1,5],[1],[1,2],[2,6],[1],[1,2],[1]]
    
    """
    
    import heapq
        
    def __init__(self):
        """
        Initialize your data structure here.
        """
        self.user_map = {}
        self.tweet_map = {}
        self.time = 0

    def postTweet(self, userId: int, tweetId: int) -> None:
        """
        Compose a new tweet.
        """
        self.follow(userId, userId)
        if not userId in self.tweet_map:
            self.tweet_map[userId] = [(self.time, tweetId)]
        else:
            self.tweet_map[userId].append((self.time, tweetId))

    def getNewsFeed(self, userId: int) -> List[int]:
        """
        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
        """
        # min heap
        pq = []
        heapq.heapify(pq)
        
        if userId in self.user_map:
            followers = self.user_map[userId]
        else: return []
        
        for user in followers:
            if user in self.tweet_map:
                for ts, fid in self.tweet_map[user]:
                    if len(pq) < 10:
                        heapq.heappush(pq, (ts, fid))
                    else:
                        if ts > pq[0][0]:
                            heapq.heappop(pq)
                            heapq.heappush(pq, (ts, fid))
                        
        result = []
        while pq:
            ts, fid = pq.pop()
            result.append(fid)
            
        return result
        

    def follow(self, followerId: int, followeeId: int) -> None:
        """
        Follower follows a followee. If the operation is invalid, it should be a no-op.
        """
        if not followerId in self.user_map:
            self.user_map[followerId] = {followeeId}
        else:
            self.user_map[followerId].add(followeeId)
            

    def unfollow(self, followerId: int, followeeId: int) -> None:
        """
        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
        """
        if followerId in self.user_map:
            self.user_map[followerId].remove(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)
