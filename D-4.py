# Design-4

## Problem 1: Design Twitter (https://leetcode.com/problems/design-twitter/)

class Twitter:
    def __init__(self):
        self.timer = itertools.count(step=-1)
        self.tweets = collections.defaultdict(deque)
        self.followees = collections.defaultdict(set)

    def postTweet(self, userId: int, tweetId: int) -> None:
        self.tweets[userId].appendleft((next(self.timer), tweetId))
        if len(self.tweets[userId]) > 10:
            self.tweets[userId].pop()

    def getNewsFeed(self, userId: int) -> list[int]:
        tweets = list(
            heapq.merge(
                *
                (self.tweets[followee]
                for followee in self.followees[userId] | {userId})))
        return [tweetId for _, tweetId in tweets[:10]]

    def follow(self, followerId: int, followeeId: int) -> None:
        self.followees[followerId].add(followeeId)

    def unfollow(self, followerId: int, followeeId: int) -> None:
        self.followees[followerId].discard(followeeId)

## Problem 2: Skip Iterator(https://leetcode.com/discuss/interview-question/341818/Google-or-Onsite-or-Skip-Iterator)

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
