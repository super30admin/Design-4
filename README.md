# Design-4

## Problem 1: Design Twitter (https://leetcode.com/problems/design-twitter/)

import time
from collections import defaultdict

class Tweet:
    def __init__(self, tweetId, timestamp):
        self.tweetId = tweetId
        self.timestamp = timestamp
class Twitter:

    def __init__(self):
        self.followers_map = defaultdict(list)
        self.user_tweets = defaultdict(list)
        
        

    def postTweet(self, userId: int, tweetId: int) -> None:
        newTweet = Tweet(tweetId, time.time())
        self.user_tweets[userId].append(newTweet)

    def getNewsFeed(self, userId: int) -> List[int]:
        all_tweets = []
        all_tweets = all_tweets + self.user_tweets[userId]
        
    
        follow_list = self.followers_map[userId]
        for user in follow_list:
            all_tweets = all_tweets + self.user_tweets[user]
        
        all_tweets.sort(key = lambda r: r.timestamp, reverse=True)
        news_feed = all_tweets
        if len(all_tweets)>10:
            news_feed = all_tweets[0:10]
        tweetIds = [x.tweetId for x in news_feed]
        return tweetIds

    def follow(self, followerId: int, followeeId: int) -> None:
        if followeeId not in self.followers_map[followerId]:
            self.followers_map[followerId].append(followeeId)
        

    def unfollow(self, followerId: int, followeeId: int) -> None:
        if followeeId in self.followers_map[followerId]:
            self.followers_map[followerId].remove(followeeId)


## Problem 2: Skip Iterator(https://leetcode.com/discuss/interview-question/341818/Google-or-Onsite-or-Skip-Iterator)

Design a SkipIterator that supports a method skip(int val). When it is called the next element equals val in iterator sequence should be skipped. If you are not familiar with Iterators check similar problems.

class SkipIterator implements Iterator<Integer> {

	public SkipIterator(Iterator<Integer> it) {
	}

	public boolean hasNext() {
	}

	public Integer next() {
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
	}
}
Example:

SkipIterator itr = new SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10]);
itr.hasNext(); // true
itr.next(); // returns 2
itr.skip(5);
itr.next(); // returns 3
itr.next(); // returns 6 because 5 should be skipped
itr.next(); // returns 5
itr.skip(5);
itr.skip(5);
itr.next(); // returns 7
itr.next(); // returns -1
itr.next(); // returns 10
itr.hasNext(); // false
itr.next(); // error

from collections import defaultdict, deque

class SkipIterator:
    def __init__(self, nums):
        self.nums = deque(nums)
        self.cnt = defaultdict(int)

    def hasNext(self):
      self._skip()
      return len(self.nums) > 0
    
    def skip(self, i):
      self.cnt[i] += 1

    def next(self):
      if not self.hasNext():
        return None

      return self.nums.popleft()

    def _skip(self):
      while len(self.nums) > 0 and self.nums[0] in self.cnt:
        self.cnt[self.nums[0]] -= 1

        if self.cnt[self.nums[0]] == 0:
          del self.cnt[self.nums[0]]

        self.nums.popleft()