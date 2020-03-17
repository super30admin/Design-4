# Problem 1
## Time Complexity :
O(n)

## Space Complexity :
O(n)

## Did this code successfully run on Leetcode :
Yes.

## Any problem you faced while coding this :
No.

## Your code here along with comments explaining your approach
### Solution:
    class Twitter:

        def __init__(self):
            """
            Initialize your data structure here.
            """
            self.timer = itertools.count(step=-1)
            self.tweets = collections.defaultdict(collections.deque)
            self.followees = collections.defaultdict(set)


        def postTweet(self, userId: int, tweetId: int) -> None:
            """
            Compose a new tweet.
            """
            self.tweets[userId].appendleft((next(self.timer), tweetId))


        def getNewsFeed(self, userId: int) -> List[int]:
            """
            Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
            """
            tweets = heapq.merge(*(self.tweets[u] for u in self.followees[userId] | {userId}))
            return [t for _, t in itertools.islice(tweets, 10)]


        def follow(self, followerId: int, followeeId: int) -> None:
            """
            Follower follows a followee. If the operation is invalid, it should be a no-op.
            """
            self.followees[followerId].add(followeeId)


        def unfollow(self, followerId: int, followeeId: int) -> None:
            """
            Follower unfollows a followee. If the operation is invalid, it should be a no-op.
            """
            self.followees[followerId].discard(followeeId)



    # Your Twitter object will be instantiated and called as such:
    # obj = Twitter()
    # obj.postTweet(userId,tweetId)
    # param_2 = obj.getNewsFeed(userId)
    # obj.follow(followerId,followeeId)
    # obj.unfollow(followerId,followeeId)
    

# Problem 2
## Time Complexity :
O(n)

## Space Complexity :
O(n)

## Did this code successfully run on Leetcode :
N/A

## Any problem you faced while coding this :
No.

## Your code here along with comments explaining your approach
### Solution:
    class SkipIterator(Iterator):
      def __init__(self, it):
        self._it = it
        self._skip = collections.Counter()
        self._next = None

      def has_next(self):
        # there is still an unprocessed next
        if self._next is not None:
          return True
        # fill in self._next, unless reached end
        while self._it.has_next():
          next = self._it.next()
          if next not in self._skip or self._skip[next] == 0:
            self._next = next
            return True
          else:
            self._skip[next] -= 1
        return False

      def next(self):
        # this check is not needed if guaranteed to call has_next before next
        if not self.has_next():
          raise Exception('called next but has no next')
        if self._next is not None:
          next = self._next
          self._next = None
          return next
        return self._it.next()

      def skip(self, num):
        self._skip[num] += 1
